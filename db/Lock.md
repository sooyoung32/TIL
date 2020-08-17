# Database Lock
데이터의 일관성을 보장하기 위한 방법

## Lock 종류

### Shared Lock

다른 트랙젝션이 읽는 것은 가능하나, 변경 (수정, 삭제)는 불가

### Exclusive Lock

다른 드랜젝션이 읽는것도, 변경(수정, 삭제)도 불가

공유락은 읽기 동안에만 일어난다.

### Update Lock

X 락과 유사하지만, 더 유연함.

업데이트 락은 이미 공유락을 가지고 있는 레코드에도 부여 가능하나,  트렌젝션이 데이터를 변경하려고 할때, 업데이트 락은 배타락으로 바뀐다.

업데이트 락은 공유락과는 비대칭라는것 일하는것이 중요.

### Intent Lock

이 락은 특정 트랜젝션이 다른 트랜젝션에게 이것의 락을 획득하려는 intention 알려주는 수단으로 사용된다.

이 락의 목적은 다른 트랜젝션이 다음 객체에대한 잠금을 획득하는것을 방지하면서 데이터 수정이 적절하게 수행되로고 함.

## Blocking

블로킹은 Lock의 경합 (race condition) 이 발생해 특정 세션이 작업을 진행하지 못하고 멈춘 상태를 의미.

공유락-배타락, 배타락-배타락 끼리 블로킹이 발생할 수 있음.

경합이 발생하면 Lock을 설정한 트랜젝션을 기다려야 함..ㅠㅠ

**해결 방안**

- SQL 빠르게 수행되도록 함
- 트랜젝션을 가능한 짧게
- 동일한 데이터를 동시에 변경하는 작업을 피함 → 피할 수 없다면 lock timeout 설정

## DeadLock

데드락은 트랜젝션간 교착상태를 의미

두개의 트랜젝션간에 각각의 트랜젝션이 가지고 있는 리소스의 lock을 획득하려고 할때 발생.

예제

- 1번 트랜젝션이 2번 리소스의 잠금을 획득하고, 2번 트랜젝션에서는 1번 리소스르의 잠금을 획득한 상태에서 동시에 상대방의 데이터에 접근하려고 할때 기존 lock이 해제될때까지 기다리게 되는 상황
- 1번 트랜젝션이 공유 lock을 설정하고 sleep 상태에 빠짐. 이 때 2번 트랜젝션이 배타 lock을 설정하려고할때 무기한 기다리게 되는 교착 상태...

## Lock Level 과  Escalation

SQL에 따라 lock 설정대상이 데이터 row일지 db일지 나누어 짐.

[lock lovel](https://www.notion.so/c3083e6f61af437fafa127a4e5303d49)

### Escalation

Lock리소스가 임계치를 넘으면 Lock  레벨이 확장되는 것을 의미함.

Lock 레벨이 낮을 수록 동시성이 좋아지지만 관리해야할 Lock이 많아지기 때문에 메모리 효율성은 떨어짐.

참고

[https://www.sqlshack.com/locking-sql-server/](https://www.sqlshack.com/locking-sql-server/)

[https://medium.com/@chrisjune_13837/db-lock-락이란-무엇인가-d908296d0279](https://medium.com/@chrisjune_13837/db-lock-%EB%9D%BD%EC%9D%B4%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80-d908296d0279)