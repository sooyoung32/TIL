# @Component, @Controller, @Service, @Repository

## @Component
componet-scan은 컴포넌트 에너테이션만 읽음.

Controller, Service, Repository 애너테이션에도 모두 Component가 있음.

그렇다면 Component를 사용하지, 왜 Controller, Service, Repository를 사용할까? 애노테이션의 특정한 역할은 무엇일까?

## @Repository

- Data Repository를 표시하는 애너테이션
- 특정 플랫폼 예외를 잡아서, 스프링의 단일화된 unchecked excpetion으로 다시 던지는 역할을 함.
    - 이는 @Transactional의 예외 처리도 같이 봐야하는데 -
        - @Transactional 로직은 checked exception은 rollback을 안하고, unchecked exception만 롤백됨.
        - 대표적인 checked exception은 SQLException 이 있는데, 이는 SQL 에러라 스프링에서는 롤백이 안되면 안된다고 판단해 SQLException이 발생할경우, 이를 unchecked exception으로  wrapping 해서 re throw 함.
        - 이런 로직은 @Repository를 선언해야 가능..!
- 이를 위해 PersistenceExceptionTranslationPostProcessor 를 제공해서, 이를 등록하게 되어있음

## @Controlelr

- 컨트롤러 역할을 하는 클래스를 표시.
- Dispatcher가 Controller 클래스를 스캔하여, 컨트롤러 내 @RequestMapping을 감지하는 역할!

## @Service

- 비즈니스 로직을 담고 있는 클래스를 표시함.
- 딱히 역할은 없으나, 나중에 추가 예외 로직을 스프링이 할 수 도 있음!