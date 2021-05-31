# Enum  Type
- 값의 입력을 특정 값으로 제한하고 싶을 때 사용
- enum 값중 하나를 삭제하면 해당 값을 갖고 있던 컬럼은 empty string으로 변경
- enum 값에 정의 되지 않은 항목을 넣을때 empty string으로 삽입
    - strict mode에서는 에러 발생
- 정렬은 eum index 기준으로 정렬.
    - empty string 은 그렇지 않은것보다 작은것으로
    - null은 모든 값보다 작은것으로 취급
    - null < empty string < 값
- 장점
    - 데이터 용량을 적게 차지한다. 문자열은 자동으로 숫자로 인코딩되어 저장하기 때문이다.
    - 데이터 정합성을 높일 수 있다 (유효하지 않은 값은 무효화 되므로)
- 단점
    - ENUM 항목 변경이 어렵다.
    - 다른 DBMS 이식성이 좋지 않다.
- 사용하지 말아야할 이유 (유명한 글 )
    - 정규화를 위반한 설계
        - enum 값을 가지고 있는 테이블이 나와야 -
    - enum 칼럼은 데이터 수정이 어렵다.
        - a → b를 바꾸려면 전체를 찾아서 변경해야함
        - enum 값을 참조하고 있는 경우는 더 수정이 어려움
    - enum 컬럼에 연관된 정보를 저장할 수 없다. → 테이블인 경우 가능
    - enum 값 조회가 어렵다 → 테이블인 경우 가능
    - enum 사용시 얻는 최적화 효과가 크지 않다.
        - 데이터가 일정 규모가 되지 않는 이상 성능에 영향을 미치지 않는다
        - enum이 전체 테이블과 외래키 개수를 줄여준다..
    - enum 칼럼에 저장되어 있는 값은 다른 테이블에서 재사용 불가
- 이럴때 사용
    - 유일하고 변하지 않는 값의 경우
    - 연관 정보가 없는 경우
    - 2< 값 < 20 이하인 경우
        - 대상이 2개면 enum 보다 tinyint or bit

### 참고

- [https://velog.io/@leejh3224/번역-MySQL의-ENUM-타입을-사용하지-말아야-할-8가지-이유](https://velog.io/@leejh3224/%EB%B2%88%EC%97%AD-MySQL%EC%9D%98-ENUM-%ED%83%80%EC%9E%85%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%98%EC%A7%80-%EB%A7%90%EC%95%84%EC%95%BC-%ED%95%A0-8%EA%B0%80%EC%A7%80-%EC%9D%B4%EC%9C%A0)