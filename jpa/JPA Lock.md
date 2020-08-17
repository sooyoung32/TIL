# Lock in JPA 
## Optimistic Lock

낙관적 잠금은 현실적으로 데이터 경합이 발생하지 않을 거라고 낙관적으로 보고 잠금을 거는 방법.

동시에 수정이 이뤄진 경우를 감지해 예외를 발생시켜도 실제로 예외 발생할 가능성이 낮다고 낙관적으로 보는 것

엄밀히 말하면 잠금이라기 보단, 일종의 충돌감지에 가까움

## Pessimistic Lock

동일한 데이터를 동시에 수정할 가능성이 높다는 비관적인 전제로 잠금을 거는 방식

충돌 감지를 통해 잠금을 발생시키면 충돌발생 의한 예외가 자주  발생함으로 이 경우 비관적 잠금을 통해서 예외를 발생시키지 않고 정합성을 보장하는 것이 가능. 

다만 성능적인 측면은 손실을 감수해야하 함.. 주로 DB에서 제공하는 배타 잠금 (Exclusive Lock)을 사용.

## Implicit Lick

암시적 잠금은 프로그램 코드 상에 명시적으로 지정하지 않아도 잠금이 발생하는 것을 의미.

JPA에서는 entity에 @Version 이 붙은 필드가 존재하거나 @OptimisticLocking 어노테이션이 설정되어 있는 경우 자동으로 충돌감지를 위한 잠금이 실행됨.

DB의 경우 업데이트 삭제 쿼리에 암시적으로 해당 로우에 대한 행 배타잠금이 실행된다. JPA가 충돌감지가 역할을 할 수 있는것도 DB의 암시적 잠금이 존재하기 때문. 

## Explicit Lock

프로그램을 통해 의도적으로 잠금을 실행하는 것이 명시적 잠금.

JPA에서 엔티티를 조회할 때 LockMode를 지정하거나 select for update쿼리를 통해 직접 잠금을 지정할 수 있음. 

## JPA LOCK

## @Version

특정 필드에 @version 어노테이션을 추가하면 자동으로 낙관적 잠금이 적용됨.

@Version 적용시 select, update 쿼리 

```sql
select member.memberno
			, member.version 
from member 
where memberno =?

update member
set member = ?, version = ?
where memberno=?
and version=?
```

다른 트랜젝션에서 버전정보가 바뀐상태가 되면 업데이트 로우 수가 0이 반환되면서 충돌감지가 되어  예외(OptimisticLockException)이 발생 됨. 

일단 update가 실행되면 위에서  암시적 잠금이 실행되며 동시에 실행된 동일한 엔티티에 대한 쿼리는 앞선 update가 커밋될때까지 대기하게 되어 정합성을 보증해 줌.

### @OptimisticLocking

NONE  -낙관적 잠금 사용하지 않음

VERSION - @Version 어노테이션 붙어 있는 필드를 조건으로 낙관적 잠금
DIRTY - 변경된 필드에 의해서 낙관적 잠금 사용
ALL - 모든 필드를 충돌감지 조건으로 사용하는 낙관적 잠금

- ALL을 사용할 때는 dynamic update를 같이 사용해야함.  그 이유는 필드 단위로 dirty여부를 확인하기 위해

```java
@Entity
@OptimisticLocking(type = OptimisticLockType.ALL)
@DynamicUpdate
public class Member {
	private Long id;
	private String name;
	private String email;
}
```

```sql
update 
	member
set name = ?
where 
	id=?
	and name=?
	and email=?
```

DIRTY - 변경된 필드에 의해서 낙관적 잠금 사용

```java
@Entity
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
public class Member {
	private Long id;
	private String name;
	private String email;
}

```

```sql
update
	member
set name=?
where 
	id=?
	and name=?
```

DIRTY 인 경우 갱신될 컬럼의 갱신전 값으로 조건절에 바인딩 됨.

특정한 컬럼만 충돌확인에 사용하므로 ALL이나 @Version을 사용했을때에 비해 충돌한 가능성은 낮출 수 있엄. 다시 말해 특정 엔티티의 서로다른 부분을 업데이트 하는 프로그램이 있을 경우 충돌하지 않고 수행 가능. 

### 명시적 낙관적 잠금

JPA에서 엔티티 매니저가 제공하는 EntityManager.find(clazz, key, lockModeType) 사용하거나, entityManager.lock(entity, lockModeType) 사용함

find를 사용하는 경우에는 엔티티를 영속성 컨텍스트로부터 찾거나 없을 경우 select를 하면서 동시에 잠금을 걸때 사용하고 lock은 이미 영속성 컨텍스트에 담겨있는 엔티티를 대상으로 잠금을 걸 때 사용한다.

### LockModeType.OPTIMISTIC

버전필드의 갱신여부와 상관없이 커밋 직전에 버전을 확인하는 쿼리를 한번 더 실행

해당 엔티티에 변경사항이 있는 경우 update 쿼리에 의해 이미 충돌 감지가 동작하므로 사실상 불필요한 쿼리가 수행될 수 있지만, 엔티티에 대한 변경 없이 해당 엔티티에 대한 처리를 수행할 수 있음

엔티티에 대한 변경이 없어 암시적인 배타잠금이 발생하지 않아 완벽한 잠금이라고 보기는 힘듬.. 

### LockModeType.OPTIMISTIC_FORCE_INCREMENT

OPTIMISTIC과 달리 버전을 강제로 증가시키는 잠금. 

커밋 직전에  버전만 증가시키는 쿼리가 발생함. 따라서 해당 엔티티에 변경이 있을때 변경사항에 대한 업데이트문과 버전을 증가시키는 업데이트문에 의해 두번 버전이 증가함. 

엔티티 자체에 변경사항이 있을 경우 불필요하게 업데이트 문이 발생되므로 주의해야함.

암시적인 행 배타잠금이 발생되어 정합성을 보증 할 수 있음. 따라서 자식 엔티티를 수정할때 자식 엔티티 전체에 대한 잠금용도로 사용 가능.

### LockModeType.PESSIMISTIC_READ

DB에서  제공하는 공유잠금 (여러 트랜잭션에서 동시에 읽을 수 있지만 쓸수 는 없는 잠금 for share)을 이용하여 잠금 획득. 

DB가 공유잠금을 제공하지 않는 경우 PESSIMISTIC_WRITE와 동일하게 동작함.

```sql
select 
	* 
from member
where id =? for update
```

### LockModeType.PESSIMISTIC_FORCE_INCREMENT

DB에서 제공하는 행 배타잠금을 이용한 잠금과 동시에 버전을 증가.

```sql
select 
	...
	version
from member
where 
	id = ? for update 

update 
	member
set version = ?
where id =?
	and version =?
```

## 주의사항

### 격리 수준

일반적인 DB는 주로 READ_COMMITED 에 해당하는 격리수준을 가짐. 

JPA의 경우 한번 영속성 컨텍스트에 적재된 엔티티를 다시 조회한 경우 DB를 조회하지 않고 영속성 컨텍스트에 적재된 엔티티를 다시 조회함. → REPEATABLE_READ 격리수준과 동일하게 동작 

```java
entityManager.find(Member.class, 1);
entityManager.find(Member.class, 1, LockModeType.PESSIMIESTIC_WRITE);
```

```sql
select * from member where id = ?
select * from member where id = ? for update
```

두번째 조회시 비관적 잠금을 사용.

버전 필드가 존재하지 않는 엔티티의 경우 첫번째 조회시에 영속 컨텍스트에 적재된 엔티티의 상태를 바뀌지 않고 단순히 selecgt for update에 의한 행 배타잠금이 실행됨.

즉 REPEATABLE_READ 격리 수준과 동일하게 동작하므로 처음 엔티티가 조회되어 잠금이 실행되기 전에 다른 트랜잭션에 의해 엔티티가 변경되어 커밋된 상태가 반영되지 않고, 현재 트랜잭션의 엔티티의 상태가 유지된다는점을 주의.

이 경우 앞선 트랜잭션에 의해 변경된 값을 잃어버리는 문제가 있을 수 있음. 

즉 잠금은 동작하지만, 정합성에 문제가 있을 수 있음. 예를 들어 포인트를 사용하는 경우 앞선 트랜잭션에서 차감된 포인트를 반영하지 않아 이중 사용 문제가 발생할 수 있음.......

영속컨텍스트에 엔티티가 존재하는 것이 확실한 경우 entity manger refesh나 jpql을 사용하여 db 로부터 엔티티를 조회하도록 강제할 필교아 있음. 

### 쿼리 직접 사용

Version 필드가 존재하는 엔티티에 JPQL이나 native쿼리를 사용하는 경우 version 누락 주의! 

### Timeout

비관적 잠금에 의해 db에 행 배타잠금이 발생한 경우 이어서 들어오는 동일한 행에 대한 배타잠금 요청을 앞선 요청의 잠금이 해제될때까지 대기하게 된다. 이때 잠금을 가진 요청의 처리가 길어지게 되면 커넥션 풀의 커넥션이 부족하게 되어 앱 전체에 영향을 줄 수 있음

잠금 획득 대기 시작을 설정하는 timeout을 사용해 예방할 수 있음

```java
entityManager.find(Member.class, 1, MockModeType.PRESSIMISTIC_WRITE, MAP.of("javax.persistence.lock.timeout", 0L)
```

이렇게 설정하면 select for update 쿼리에 nowaite이 추가되어 잠금을 취득할 수 없는 경우 lockTimeOutException 발생.

DB에 따라 지원 여부가 다르고 지원하지 않는 경우 설정이 무시됨을 주의해야 함.

### 참고

[https://reiphiel.tistory.com/entry/understanding-jpa-lock](https://reiphiel.tistory.com/entry/understanding-jpa-lock)

[https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html](https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html)

[https://www.baeldung.com/java-jpa-transaction-locks](https://www.baeldung.com/java-jpa-transaction-locks)