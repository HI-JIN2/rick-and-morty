## Rick and Morty

[칸반보드](https://github.com/users/HI-JIN2/projects/4)

## 빌드

- 별도의 local.properties 추가사항 없음

## 사용 기술

- Kotlin
- Compose
- Coil
- Ktor
- Paging3
- Clean Architecture
- Navigation3
- kotlinx-serializable
- Hilt (Dependency Injection)

## 아키텍처

이 프로젝트는 **Clean Architecture**와 **MVVM 패턴**을 기반으로 구성되어 있습니다.

### 모듈 구조

```
RickAndMorty/
├── app/                    # 앱 진입점 모듈
│   ├── MainActivity       # Single Activity
│   └── RickAndMortyApp   # Application 클래스 (Hilt 설정)
│
├── presentation/          # 프레젠테이션 레이어
│   ├── navigation/       # 네비게이션 로직 (Navigation3)
│   ├── characterlist/    # 캐릭터 목록 화면
│   ├── characterdetail/  # 캐릭터 상세 화면
│   ├── search/           # 검색 화면
│   └── common/           # 공통 컴포넌트 및 유틸리티
│
├── domain/               # 도메인 레이어 (비즈니스 로직)
│   ├── model/           # 도메인 모델
│   ├── repository/      # Repository 인터페이스
│   └── usecase/         # UseCase (비즈니스 로직)
│
├── data/                # 데이터 레이어
│   ├── api/            # API 인터페이스 및 구현 (Ktor)
│   ├── dto/            # Data Transfer Object
│   ├── repository/     # Repository 구현
│   └── paging/         # PagingSource 구현
│
└── core/               # 공통 모듈
    ├── common/         # 공통 모델 및 유틸리티
    │   └── ApiResult   # API 결과 래퍼 (Sealed Interface)
    └── designsystem/   # 디자인 시스템
        ├── theme/      # 테마, 색상, 타이포그래피
        └── Dimens      # 디자인 시스템 상수
```

### 레이어별 역할

#### 1. Presentation Layer (`presentation`)

- **역할**: UI 로직 및 사용자 인터랙션 처리
- **구성요소**:
    - `Screen`: Compose UI 컴포넌트
    - `Route`: 화면 라우팅 및 상태 관리
    - `ViewModel`: UI 상태 관리 및 비즈니스 로직 호출
    - `Navigation`: 화면 전환 로직
- **의존성**: `domain`, `core:designsystem`, `core:common`

#### 2. Domain Layer (`domain`)

- **역할**: 비즈니스 로직 및 도메인 규칙
- **구성요소**:
    - `UseCase`: 비즈니스 로직 실행
    - `Repository` (인터페이스): 데이터 접근 계약
    - `Model`: 도메인 모델
- **의존성**: `core:common` (순수 Kotlin 모듈)

#### 3. Data Layer (`data`)

- **역할**: 데이터 소스 구현 및 네트워크 통신
- **구성요소**:
    - `Api`: Ktor 기반 네트워크 API
    - `Repository` (구현): Repository 인터페이스 구현
    - `DTO`: 네트워크 응답 모델
    - `PagingSource`: Paging3 데이터 소스
- **의존성**: `domain`, `core:common`

#### 4. Core Modules (`core`)

- **common**: 공통 모델 및 유틸리티
    - `ApiResult`: API 결과 래퍼 (Success/Error)
- **designsystem**: 디자인 시스템
    - 테마, 색상, 타이포그래피
    - 디자인 시스템 상수 (Dimens)

### 데이터 흐름

```
UI (Screen)
    ↓
Route (상태 관리)
    ↓
ViewModel (UI 상태 관리)
    ↓
UseCase (비즈니스 로직)
    ↓
Repository (인터페이스)
    ↓
RepositoryImpl (구현)
    ↓
API (Ktor)
    ↓
Network
```

### 주요 설계 패턴

1. **MVVM (Model-View-ViewModel)**
    - View: Compose Screen
    - ViewModel: UI 상태 관리
    - Model: Domain Model

2. **Repository Pattern**
    - 데이터 소스 추상화
    - Domain과 Data 레이어 분리

3. **UseCase Pattern**
    - 단일 책임 원칙
    - 비즈니스 로직 캡슐화

4. **Dependency Injection (Hilt)**
    - 의존성 관리
    - 테스트 용이성 향상

### 네비게이션

- **Navigation3** 사용
- Single Activity 아키텍처
- Type-safe navigation (Kotlin Serialization)
- Bottom Navigation Bar 지원

### 상태 관리

- **StateFlow**: ViewModel에서 UI 상태 관리
- **UiState**: Sealed Interface로 상태 표현
    - `Init`: 초기 상태
    - `Loading`: 로딩 중
    - `Success<T>`: 성공 (데이터 포함)
    - `Error`: 에러 (Throwable 포함)

## 주요 기능

Navigation3을 사용하여 Single Activity에서 Screen 전환합니다.

### 1. 캐릭터 목록 화면

- 캐릭터 목록을 요청하여 출력합니다.
- 각 아이템은 이미지(image), 이름(name), 상태(status), 젠더(gender)를 필수적으로 보여줍니다.
- 그 외에 원하는 필드를 보여주는 것은 선택사항 입니다.
- Paging을 사용하여 30개씩 끊어서 보여줍니다
- image 로딩에는 coil 라이브러리를 사용했습니다.

### 2. 각 캐릭터 상세 화면

- 단일 캐릭터에 대한 화면입니다. 목록에서 각 아이템을 클릭하여 이동할 수 있습니다.
- image, name, status, gender, species, origin, location 정보를 필수적으로 보여줍니다.
- 그 외에 원하는 필드를 보여주는 것은 선택사항 입니다.

### 3. 캐릭터 검색 화면

- 이름으로 캐릭터를 검색할 수 있습니다.
- 검색 시 submit 버튼은 따로 없습니다. 검색어를 입력하면 자동으로 검색되도록 구현합니다. -> debounce 구현
- 검색 결과에는 이미지(image), 이름(name), 상태(status), 젠더(gender)를 필수적으로 포함합니다. 목록 화면과 동일한 아이템을 사용해도 무방합니다.
- 그 외에 원하는 필드를 보여주는 것은 선택사항 입니다.
