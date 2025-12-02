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
