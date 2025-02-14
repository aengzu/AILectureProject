<h1 align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Grinning%20Cat%20with%20Smiling%20Eyes.png" alt="Grinning Cat with Smiling Eyes" width="25" height="25" /> PETKIN <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Grinning%20Cat%20with%20Smiling%20Eyes.png" alt="Grinning Cat with Smiling Eyes" width="25" height="25" />
</h1>

---

### <p align="center">AI 기반 반려동물 피부질환 예측 서비스</p>

![12월 17일 최종발표 (최신 ai 특강)](https://github.com/user-attachments/assets/73524d63-9bc9-4f3f-b03e-383151554b6a)

> ’Petkin’은 반려동물 보호자를 위한 **AI 피부질환 예측 서비스**입니다. 사용자는 반려동물의 피부 사진을 업로드하여 **딥러닝 모델을 활용한 피부질환 분석 결과**를 확인할 수 있습니다. 이 서비스는 **병원 방문 전 기본적인 피부 건강 진단**을 가능하게 하며, 보호자가 반려동물의 피부 상태를 기록하고 모니터링할 수 있도록 지원합니다.



## 소개

### 🔎 Background
![12월 17일 최종발표 (최신 ai 특강) (3)](https://github.com/user-attachments/assets/1f695ba8-d0a7-4571-a0bf-d1c1835b8de9)


### 🎯 배경

- 반려동물 보호자들의 반려동물 건강에 대한 관심도 증가
- 동물병원 방문 전 **기본적인 피부 상태를 확인**할 수 있는 AI 기반 서비스 필요
- 시간과 비용 문제로 인해 **반려동물의 피부 상태를 자가 진단**할 수 있는 도구 제공

<br>

## 핵심 기능

✅ **반려동물 피부 상태 분석**

사용자가 반려동물 피부 사진을 업로드하면 AI가 분석하여 **피부 질환 여부 및 유형을 예측**합니다.

![image](https://github.com/user-attachments/assets/59cf0689-0f54-4041-b4cd-603a28012a15)


✅ **반려동물 건강 정보 기록** 

반려동물의 건강과 관련된 상태를 **하루 단위로 기록**할 수 있습니다.

<br>

## 📊 AI 모델 및 학습 방법

### 🔬 데이터셋 및 전처리

- AI Hub 제공 **반려동물 피부질환 데이터셋** 활용🔗 [데이터셋 링크](https://www.aihub.or.kr/aihubdata/data/view.do?currMenu=115&topMenu=100&dataSetSn=561)

- **데이터 구조**:
    - **A1:** 구진, 플라크
    - **A2:** 비듬, 각질
    - **A3:** 태선화, 과다색소침착
    - **A4:** 농포, 여드름
    - **A5:** 미란, 궤양
    - **A6:** 결절, 종괴
    - **A7:** 무증상

- **데이터 분포**:
    - > ⛔️ 데이터가 불균형하다
    - ![image](https://github.com/user-attachments/assets/a9db2188-0163-4569-bb70-c24ac63e0d13)


- **데이터 전처리**:
    - 이미지 데이터에 대해 Transform 적용 -> **이미지 데이터 특징 다양화**
    - 학습에 사용한 Features

        | age | breed | gender | label 인 lessons |
        | --- | --- | --- | --- |
        | 수치형 데이터이므로, MinMaxScaling 적용 | 범주형 데이터 이므로, One-hot encoding 적용 | 범주형 데이터이므로, One-hot encoding 적용 | 범주형 데이터이므로, One-hot encoding 적용 |

<br>

### 🔍 모델 구조 및 학습 방식

- **EfficientNet-b1** 기반 **Multi Modal Model** 활용 (이미지 데이터 학습이 주 목적이지만, 다른 features 을 통한 학습 성능 향상 기대)
- **Adam, AdamW** 옵티마이저 사용
- **CosineAnnealingLR** 스케줄러 적용
- **CrossEntropy Loss** 적용
- **Hyper Parameter 조정**을 통한 최적화 실험 진행

<br>

### 📈 실험 결과

1.  **실험 1) 데이터 불균형 해결을 위한 Undersampling 적용**
    -   | No resample | Resample | 
        | --- | --- | 
        | ![image](https://github.com/user-attachments/assets/419fe554-3ff5-4310-9258-9424c344bf5f)| ![image](https://github.com/user-attachments/assets/15d951a4-2008-42c5-84b7-61e344f72c84) |
      - > Loss 와 Accuracy 를 보면 Resampling 을 적용하지 않은 모델이 더 좋은 성능을 보이는 것처럼 보이지만, 평가 데이터가 불균형하기 때문에(A7이 많음) 그렇게 나타나며, **평가 데이터가 균형할때는 성능이 향상됨**을 확인할 수 있다.
      - | 불균형 평가데이터에 대한 혼동 행렬 비교 | 균형 평가 데이터에 대한 혼동행렬 비교 | 
        | --- | --- | 
        | ![image](https://github.com/user-attachments/assets/1dd0814b-6f6f-4558-b977-f9c98786bca2)| ![image](https://github.com/user-attachments/assets/591102af-20ab-4de2-90d7-45ca8396d135)|

<br>

2.  **실험 2) Multi Modal Model 적용 시 성능 변화 분석**
    - 이미지만 사용하였을 때와 멀티 모달로 다른 features 도 학습 시켰을 때를 비교하면, 극적인 차이는 없지만 Multi Modal Model 적용 시 성능이 약간 떨어진다.
      - ![image](https://github.com/user-attachments/assets/81ebc9ed-1d9b-4c20-856f-029aa30eb116)

<br>

3. **실험 3) Batch Size 및 Learning Rate 변경 실험**
    - batch size 를 크게 할수록 모델 성능이 향상될 것이라 예측했지만, 아주 미미한 차이만 있을 뿐 Batch size 에 따른 성능 변화는 거의 없다.
      - ![image](https://github.com/user-attachments/assets/fb5c4d79-4142-4990-88f2-96dc8f648df4)

<br>

## 🛠 개발 내용
### 🔧 서비스 아키텍처 구조도
![Frame 7](https://github.com/user-attachments/assets/e881f647-68ed-44e7-944b-fce3ac0ac6c2)

<br>

### 🔧 기술 스택

| Frontend | Backend | AI Model | Deployment |
| --- | --- | --- | --- |
| <img src="https://img.shields.io/badge/Flutter-02569B?style=for-the-badge&logo=Flutter&logoColor=white"> | <img src="https://img.shields.io/badge/FastAPI-009688?style=for-the-badge&logo=FastAPI&logoColor=white"> | <img src="https://img.shields.io/badge/PyTorch-EE4C2C?style=for-the-badge&logo=PyTorch&logoColor=white"> | <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"> |

<br>

### 🎬 시연 영상

📺 [시연 영상 보기](https://www.youtube.com/watch?v=Xs13ZMWp7xc)

<br>

### 개발 세부 내용

1. **ERD 설계서**
![erd petkin](https://github.com/user-attachments/assets/c7cc051c-a9b5-4419-8dec-5447aad2ac9a)



2. **API 문서**
    1. 🏠 **회원 API (Customer Controller)**
        | **회원 API (Customer Controller)** | 설명 | 엔드포인트 | 메서드 |
        | --- | --- | --- | --- |
        | 카카오 토큰 갱신 | 리프레시 토큰을 이용해 새로운 액세스 토큰을 발급 | `/api/customers/token/refresh` | `POST` |
        | 카카오 로그인 | Oauth 제공자(KAKAO) 로그인 후, 액세스 및 리프레시 토큰 반환 | `/api/customers/oauth/login/{oauthProvider}` | `POST` |
        | 내 정보 조회 | 로그인한 회원의 닉네임 반환 | `/api/customers/me` | `GET` |
    2. 🧑‍⚕️ **질병 예측 API (Prediction Controller)**
        | 기능 | 엔드포인트 | 메서드 | 상태 | 설명 |
        | --- | --- | --- | --- | --- |
        | 질병 분석 (Client → Server) | `/predict/disease` | `POST` | ✅ 완료 | 서버에서 AI 서버로 질병 분석 요청 |
    3. 📖 **건강 기록 API (Health Record Controller)**
        | 기능 | 엔드포인트 | 메서드 | 상태 | 설명 |
        | --- | --- | --- | --- | --- |
        | 특정 반려동물 건강 기록 조회 | `/api/pets/{pet_id}/health-records/` | `GET` | ✅ 완료 | 특정 반려동물의 모든 건강 기록 조회 |
        | 건강 기록 생성 | `/api/health-records` | `POST` | ✅ 완료 | 로그인된 사용자가 건강 기록 생성 |
        | 건강 기록 조회 | `/api/health-records/{record_id}` | `GET` | ✅ 완료 | 특정 건강 기록 세부 정보 조회 |
        | 건강 기록 수정 | `/api/health-records/{record_id}` | `PUT` | ✅ 완료 | 특정 건강 기록 내용 수정 |
        | 건강 기록 삭제 | `/api/health-records/{record_id}` | `DELETE` | ✅ 완료 | 특정 건강 기록 삭제 |
        | 특정 날짜 건강 기록 조회 | `/api/pets/{pet_id}/health-records?date={date}` | `GET` | ✅ 완료 | 특정 날짜의 건강 기록 조회 |
        | 특정 달 건강 기록 조회 | `/api/pets/{pet_id}/health-records?month={month}` | `GET` | ✅ 완료 | 특정 달의 건강 기록 조회 |
        
        📌 **사전 정의된 건강 기록 아이템**
        | ItemType | Item Name |
        | --- | --- |
        | 1 | 📷 Photo (사진 기록) |
        | 2 | 🛁 Bath (목욕) |
        | 3 | 🚶 Walk (산책) |
        | 4 | 🍖 Snack (간식) |
        | 5 | 💊 Medicine (약) |
        | 6 | 💉 Vaccination (예방 접종) |
        | 7 | 🏥 Hospital (병원 방문) |
        | 8 | 📝 Memo (메모) |
    4. 🐶 **반려동물 API (Pet Controller)**
        | 기능 | 엔드포인트 | 메서드 | 상태 | 설명 |
        | --- | --- | --- | --- | --- |
        | 반려동물 등록 | `/api/pets` | `POST` | ✅ 완료 | 새로운 반려동물을 등록하고 고유 ID 반환 |
        | 반려동물 정보 조회 | `/api/pets/{pet_id}` | `GET` | ✅ 완료 | 등록된 반려동물 조회 |
        | 반려동물 정보 수정 | `/api/pets/{pet_id}` | `PUT` | ✅ 완료 | 등록된 반려동물 정보 수정 |
        | 반려동물 삭제 | `/api/pets/{pet_id}` | `DELETE` | ✅ 완료 | 등록된 반려동물 삭제 |
        | 회원 반려동물 목록 조회 | `/api/pets/mine` | `GET` | ✅ 완료 | 로그인된 회원의 반려동물 목록 조회 |
    

<br>

## 팀원
|<img src="https://avatars.githubusercontent.com/u/102356873?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/82194112?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/105425800?v=4" width="150" height="150"/>|
|:-:|:-:|:-:|
|aengzu<br/>[@aengzu](https://github.com/aengzu)|[@choisieun](https://github.com/choisieun)|Heewon Choi<br/>[@o4e3](https://github.com/o4e3)|
