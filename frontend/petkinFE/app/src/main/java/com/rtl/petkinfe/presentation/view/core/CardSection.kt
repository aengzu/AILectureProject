package com.rtl.petkinfe.presentation.view.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rtl.petkinfe.R
import com.rtl.petkinfe.presentation.view.home.CardState
import com.rtl.petkinfe.ui.theme.PhotoIconActiveColor
import com.rtl.petkinfe.ui.theme.SplashBackgroundColor

@Composable
fun CardContent(
    title: String,
    isPhotoUploaded: Boolean,
    memo: String?,
    onPhotoUpload: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        when (title) {
            "피부 질환 검사" -> {
                SkinCheckCardContent(isPhotoUploaded, onPhotoUpload)
            }
            else -> {
                GeneralCardContent(memo)
            }
        }
    }
}

@Composable
fun SkinCheckCardContent(
    isPhotoUploaded: Boolean,
    onPhotoUpload: () -> Unit
) {
    if (isPhotoUploaded) {
        PhotoUploadedContent(onPhotoUpload)
    } else {
        PhotoNotUploadedContent(onPhotoUpload)
    }
}

@Composable
fun PhotoNotUploadedContent(onPhotoUpload: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "사진이 없습니다",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        UploadCameraButton(
            onClick = onPhotoUpload,
        )
    }
}

@Composable
fun PhotoUploadedContent(onPhotoUpload: () -> Unit) {
    Text(
        text = "사진이 등록되었습니다.",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    TextButton(
        onClick = { /* TODO: 피부 질환 검사 로직 */ },
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(vertical = 12.dp),
        enabled = true // 버튼 활성화
    ) {
        Text(
            text = "피부 질환 검사하기",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

@Composable
fun GeneralCardContent(memo: String?) {
    Text(
        text = "📋 메모",
        style = MaterialTheme.typography.titleMedium,
        color = Color.Black
    )
    Spacer(modifier = Modifier.height(8.dp))
    if (memo != null) {
        Text(
            text = memo,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}

@Composable
fun UploadCameraButton(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .border(
                BorderStroke(1.dp, PhotoIconActiveColor),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = SplashBackgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp) // 최소한의 패딩으로 높이를 줄임
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "사진 등록",
                tint = PhotoIconActiveColor,
                modifier = Modifier.size(12.dp) // 아이콘 크기 줄임
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "사진 등록",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = PhotoIconActiveColor
            )
        }
    }
}


@Composable
fun CardHeader(
    title: String,
    isExpanded: Boolean,
    memo: String?,
    onToggle: () -> Unit,
    onAddRecord: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (memo == null) "기록이 없습니다" else "기록이 있습니다",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(start = 6.dp)
        )
        HeaderActionButtons(title, isExpanded, memo, onToggle, onAddRecord)
    }
}

@Composable
fun HeaderActionButtons(
    title: String,
    isExpanded: Boolean,
    memo: String?,
    onToggle: () -> Unit,
    onAddRecord: () -> Unit
) {
    when {
        title == "피부 질환 검사" -> {
            ToggleButton(isExpanded, onToggle)
        }
        memo == null -> {
            AddRecordButton(onClick = onAddRecord)
        }
        else -> {
            ToggleButton(isExpanded, onToggle)
        }
    }
}

@Composable
fun ToggleButton(isExpanded: Boolean, onToggle: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(end = 6.dp)
            .clickable { onToggle() }
    ) {
        Text(
            text = if (isExpanded) "닫기" else "열기",
            color = Color.Black,
            fontSize = 14.sp
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Composable
fun AddRecordButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(32.dp)
            .background(
                color = SplashBackgroundColor,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                BorderStroke(0.4.dp, PhotoIconActiveColor),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "기록 추가",
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun ExpandableCard(
    title: String,
    color: Color,
    state: CardState,
    memo: String?,
    onToggle: () -> Unit,
    onPhotoUpload: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(26.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color)
                .padding(12.dp)
        ) {
            CardHeader(title = title, isExpanded = state.isExpanded, memo = memo, onToggle = onToggle, onAddRecord = { TODO() })
            if (state.isExpanded) {
                CardContent(
                    title = title,
                    isPhotoUploaded = state.isPhotoUploaded,
                    memo = memo,
                    onPhotoUpload = onPhotoUpload
                )
            }
        }
    }
}