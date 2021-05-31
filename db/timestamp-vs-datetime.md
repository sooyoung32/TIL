# Timestamp vs Datetime
## timestamp

- 시스템 타임존에 의존
- '1970-01-01 00:00:01' UTC 부터  '2038-01-19 03:14:07' UTC까지 지원
- 타입 숫자형
- 4 byte
- index 생성이 빠르다 !!
- UPDATE 실행 시 TIMESTAMP 값을 명시적으로 할당하지 않고 실행하면 TIMESTAMP 컬럼의 값이 자동으로 업데이트 된다. (즉, TIMESTAMP 는 기본적으로 NOT NULL)

## datetime

- 시스템 타임존에 의존하지 않음
- 1000-01-01 00:00:00부터 9999-12-31 23:59:59까지 지원
- 타입 문자형
- 8 byte

참고

- [https://daniel-hebn.github.io/2018/04/14/2018-04-14-MySQL-성능최적화-3/](https://daniel-hebn.github.io/2018/04/14/2018-04-14-MySQL-%EC%84%B1%EB%8A%A5%EC%B5%9C%EC%A0%81%ED%99%94-3/)
- [https://dev.mysql.com/doc/refman/8.0/en/datetime.html](https://dev.mysql.com/doc/refman/8.0/en/datetime.html)