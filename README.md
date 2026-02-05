# ⚪⚫ CLI Network Omok (오목)
> **Java 기반의 CLI 네트워크 오목 게임입니다.** > 네트워크 소켓 통신을 통해 실시간으로 1:1 대전이 가능하도록 설계되었습니다.

---

## 🚀 Key Features (주요 기능)
- **Host/Guest 모드**: 방장(Host)과 참여자(Guest)를 구분하여 네트워크 연결.
- **실시간 대전**: 소켓 통신을 이용한 턴제 게임 로직 구현.
- **Top-Down 설계**: 인터페이스 기반의 확장이 용이한 구조.
- **CLI GUI**: 터미널 환경에서 가독성 높은 바둑판 렌더링.
---

## 📂 Project Structure (프로젝트 구조)
```text
src/
├── main/
│   └── OmokApplication.java  # 어플리케이션 진입점 및 메인 흐름 제어
├── engine/
│   └── GameEngine.java       # 게임의 규칙(Rule) 및 턴 관리 로직
├── board/
│   └── Board.java            # 15x15 바둑판 데이터 관리 및 렌더링
├── player/
│   ├── Player.java           # 플레이어 공통 인터페이스
│   ├── LocalPlayer.java      # 로컬 입력 처리 구현체
│   └── RemotePlayer.java     # 원격 네트워크 입력 처리 구현체
└── network/
    └── NetworkManager.java   # 소켓 통신 및 연결 관리 (TCP)
