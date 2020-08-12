## JPA 시작

#### @Entity 
이 클래스를 테이블과 매핑한다고 JPA에 알려준다

#### @Table
엔티티 클래스에 매핑할 테이블 정보를 알려준다

#### @Id
엔티티 클래스의 필드를 테이블의 기본키에 매핑한다.


### 어플리케이션 개발

``` java
public static void main(String[] args) {
    EntityManagerFacfory emf = Persistence.createEntityManagerFactory(""jpabook);

    EntityManager em = emf.createdEntityManager();
    
    EntityTransaction = txx= em.getTransactino();

    try {
            tx.begin();
            logic();
            tx.commit();
    } catch (Exception e) {
        tx.rollback();
    } finally {
        em.close();
    }
    emf.close();

}
```


#### 엔티티 매니저 설정
* 엔티티 매니저 팩토리 생성
* JPA를 동작시키지 위한 기반 객체를 만들고 JPA구현체에 따라서 DB 커넥션 풀도 생성 -> 생성 비용이 큼.
* 어플리케이션 전체에서 딱 한번만 생성해서 공유해서 사용

#### 엔티티 매니져
* 엔티티매니저를 사용해서 엔티티를 CRUD 할 수 있음.
* 엔티티 매니저는 내부에 데이터 소스를 유지하면서 DB와 통신
* 엔티티 매니저는 데이터베이스 커넥션과 밀접한 관계가 있으므로 스레드간 공유하거나 재사용하면 안됨!
    
#### 트랜젝션 관리
* JPA를 사용하면 항상 트랜잭션 안에서 데이터를 변경해야함.
* 로직이 정상 수행하면 commit, 아니면 rollback


### JPQL 
* JPQL은 엔티티 객체를 대상으로 쿼리한다. 디비 테이블을 전혀 알지 못한다.
* SQL은 데이터베이스 테이블 대상으로 쿼리한다.
  