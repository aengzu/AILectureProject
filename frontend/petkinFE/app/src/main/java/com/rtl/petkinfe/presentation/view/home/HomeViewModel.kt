package com.rtl.petkinfe.presentation.view.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.model.ItemType
import com.rtl.petkinfe.domain.model.Prediction
import com.rtl.petkinfe.domain.usecases.GetTodayPredictionUseCase
import com.rtl.petkinfe.domain.usecases.GetTodayRecordUseCase
import com.rtl.petkinfe.domain.usecases.RequestPredictionUseCase
import com.rtl.petkinfe.domain.usecases.SaveHealthRecordsUseCase
import com.rtl.petkinfe.domain.usecases.SavePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodayRecordUseCase: GetTodayRecordUseCase,
    private val getTodayPredictionUseCase: GetTodayPredictionUseCase,
    private val savePhotoUseCase: SavePhotoUseCase,
    private val requestPredictionUseCase: RequestPredictionUseCase,
    private val saveRecordUseCase: SaveHealthRecordsUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(HomeUIState())
    val uiState: State<HomeUIState> = _uiState

    private val _iconStates = mutableStateOf(emptyMap<ItemType, Boolean>())
    val iconStates: State<Map<ItemType, Boolean>> = _iconStates


    init {
        loadRecords()
        loadPrediction()
    }

    private fun loadRecords() {
        viewModelScope.launch {
            val records = getTodayRecordUseCase.execute() ?: emptyList()
            val cardStates = records.associate { it.itemType to CardState() }
            _uiState.value = HomeUIState(records = records, cardStates = cardStates)
            // 아이콘 상태 초기화
            initializeIconStates(records)
            Log.d("ViewModel", "Loaded records: $records")
        }
    }

    private fun loadPrediction() {
        viewModelScope.launch {
            val prediction = getTodayPredictionUseCase.execute()
            if (prediction != null) {
                Log.d("testt", "loadPrediction: $prediction")
                Log.d("testt", "loadPrediction: ${prediction.imageUrl}")
                updateCardState(ItemType.PHOTO) { it.copy(prediction = prediction.prediction, photoUrl = prediction.imageUrl) }
            }
        }
    }

    private fun updateCardState(itemType: ItemType, update: (CardState) -> CardState) {
        _uiState.value = _uiState.value.copy(
            cardStates = _uiState.value.cardStates.toMutableMap().apply {
                val currentState = this[itemType] ?: return@apply
                this[itemType] = update(currentState)
            }
        )
    }


    private fun initializeIconStates(records: List<HealthRecord>) {
        val activeIcons = records.map { it.itemType }.toSet()
        val allIcons = ItemType.values().associateWith { it in activeIcons }
        _iconStates.value = allIcons
    }

    fun toggleCard(itemType: ItemType) {
        _uiState.value = _uiState.value.copy(
            cardStates = _uiState.value.cardStates.toMutableMap().apply {
                val currentState = this[itemType] ?: CardState()
                this[itemType] = currentState.copy(isExpanded = !currentState.isExpanded)
            }
        )
    }



    // 사진 업로드 처리
    fun uploadPhoto(itemType: ItemType, imageFile: File) {
        if (itemType != ItemType.PHOTO) return // PHOTO만 처리
        viewModelScope.launch {
            val url = savePhotoUseCase(imageFile) // 사진 저장 후 URL 반환
            Log.d("testt", "uploadPhoto: $url")
            _uiState.value = _uiState.value.copy(
                cardStates = _uiState.value.cardStates.toMutableMap().apply {
                    val currentState = this[itemType] ?: return@apply // null인 경우 반환
                    this[itemType] = currentState.copy(
                        isPhotoUploaded = true,
                        photoUrl = url.toString()
                    )
                }
            )
        }
    }

    fun requestPrediction() {
        viewModelScope.launch {
            try {
                // 예측 결과를 요청하고 반환
                val prediction = requestPredictionUseCase.execute(File(_uiState.value.cardStates[ItemType.PHOTO]?.photoUrl!!))

                // 반환된 예측 데이터를 CardState에 업데이트
                _uiState.value = _uiState.value.copy(
                    cardStates = _uiState.value.cardStates.toMutableMap().apply {
                        val currentState = this[ItemType.PHOTO] ?: return@apply
                        this[ItemType.PHOTO] = currentState.copy(prediction = prediction)
                    }
                )
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Prediction request failed: ${e.message}", e)
            }
        }
    }


    fun addRecord(itemType: ItemType, memo: String) {
        viewModelScope.launch {
            try {
                val newRecord = HealthRecord(
                    recordId = null,
                    itemType = itemType,
                    memo = memo
                )
                saveRecordUseCase.invoke(newRecord)

                // 성공 메시지 처리
                _uiState.value = _uiState.value.copy(message = "기록이 성공적으로 추가되었습니다.")
                loadRecords() // 기록 새로고침

            } catch (e: Exception) {
                Log.e("AddRecord", "Error adding record", e)
                _uiState.value = _uiState.value.copy(message = "기록 추가 중 오류가 발생했습니다.")
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}


data class CardState(
    val isExpanded: Boolean = false, // 카드 확장 상태
    val isPhotoUploaded: Boolean = false, // 사진 업로드 여부
    val photoUrl: String? = null, // 사진 URL 추가
    val prediction: Prediction? = null // 예측 결과 추가
)



data class HomeUIState(
    val records: List<HealthRecord> = emptyList(), // 오늘의 기록
    val cardStates: Map<ItemType, CardState> = emptyMap(), // 카드 상태
    val message: String? = null // 성공/실패 메시지 추가
)