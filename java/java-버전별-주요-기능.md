# Java 버전별 주요 기능 

```json
간단히 적고 나중에 추가 공부 
```

## Java 7

- Diamond operator <>
- Try-resource
- Multi-catch
- Concurrency API
- File NIO

## Java 8

- Lambda Expression
- Stream API
- Type Annotaion
- Reapeating Annotaion
- Static Link Library
- Interface default method
- Datetime API

## Java 9

- Jingsaw 기반 런타임 모듈화
- Jshell 추가
- private interface method
- inmutable collection
- 익명 내부 클래스에서 diamond operator 사용가능
- 통합로깅
- 프로퍼티 파일에 UTF-8 지원
- 프로세스 API 개선
- Reative Stream API 추가
- Optional to Stream
- Http 2 클라이언트
- Indify String Concatenation

## JAVA 10

- var 키워드를 이용한 지역 변수 타입추론
- 병렬처리 가비지 컬렉터
- 개별 Thread로 분리된 Stop the world
- 루트 CA
- JVM heap 영역을 시스템 메모리가 아닌 다른 종류의 메로리에도 할당

## JAVA 11

- Nest  based Access Control
  - 
- Lambda 파라미터에 지역변수 문법
- 새로운 가비지 컬렉터
    - Epsilon :  A no-op GC
    - ZGC : A scalable Low latenc GC
- Http 클라이언트 표준화
    - HttpUrlConnection → Http Client API 소개
    - WebSocket 프로토콜
- Flight Recorder
    - 실행중인 JAVA 어플리케이션 진단 및 프로파일링 데이터 수집 도구
- Launch Single File Source Code Program
    - main 메서드 실생히 javac를 하지 않고도 바로 실행 가능
- 새로운 String method
- 람다에 지역변수 var 사용 가능

## JAVA 12

- switch 문 확장 (→)
- GC 개선
- 마이크로 벤치마크 툴 추가
- 성능개선

## Java 13

- swich 문에 yield 예약어 추가
- Dynamic CDS Archives
    - 어플리케이션 종료될때  CDS가 동작되어 작동하도록 향상
- ZGC
- 레거시 Socket API 재구현

## Java 14

- Pattern Matching for instacne of
- NUMA 메모리 시스템의 G1GC 성능 향상
- NUMA는 불균형적인 메모리 접근을 의미하는데, 멀티 프로세서 환경에서의 메모리 접근 방식
- Helpful NullPointException
- record 키워드
    - kotlin 의 data클래스 같은
- swicth 기능 추가
- CMS GC 삭제
- Text Block
    - """ test """