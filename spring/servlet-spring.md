# Servlet & Spring

## Servlet

- HTML 등의 웹 콘텐츠를 생성하고 전달하기 위해 Servlet 클래스의 구현 규칙을 지켜 자바로 만들어진 프로그램

Servelt 등장 배경

- 초기 웹서버는 정적 페이지로 구현됨.
- 동적페이지를 처리하는 CGI (Common Gateway Interace)
    - web server <→ cgi 구현체
    - http 통신 규약을 사용하는 웹서버가 프로그램과 데이터를 주고 받는 처리 규약

    - CGI 문제점
        - Request 들어올때마다 Process 를 생성  → Thread 로 교체
            - Process는 메모리에 적재된 실행중인 프로그램 인스턴스
            - Thread는 메모리를 공유하고 한 프로세스 내의 동작 흐름
        - 여러 구현체를 사용해도 thread 가 달라도 다른 구현체를 생성함  → Singleton 교체

### Servlet

- 서블릿도 CGI 규칙에 따라 데이터를 주고 받지만 이를 서블릿을 가지고 있는 컨테이너에게 위임
- 대신 서블릿 컨테이너와 서블릿 사이에 규칙이 존재
- CGI의 개선된 버전 Servlet
    - 생명주기가 있는Thread로 교체
    - Singleton 교체
    - WebServer(정적데이터처리) WebApplication Server 추가 (동적 데이터 처리)
    - 싱글턴이어서 메로리를 공유하고 지연변수는 스레드마자 가지고 있음
- 생명주기
    - init
    - service
    - destroy
- 이미 생성된 서블릿은 재사용해서 메모리를 효율적으로 사용함
- 상속 구조
    - (톰캣) Servlet → GenericServlet → HttpServlet
    - (스프링)→ HttpServletBean → FrameworkServlet → DispatcherServlet

### 스프링에서 Servlet

- Servlet Container
- Dispatcher Servlet
    - web.xml에 설정되어 있음
    - 모든 요청은 해당 servlet으로 고
    - 제일 먼저 dispatcher servlet으로 가서, 공통으로 처리되는 작업을 여기서하고, 세부 컨트롤러로 위임.
    -
- HandlerMapping
    - 요청에 맞는 컨트롤러 찾음
        - BeanNameHandlerMapping : bean 이름과 url을 매핑
        - ContollerClassNameHandlerMapping :
        - SimpleUrlHanlderMapping
        - DefaultAnnotaionHandlerMapping : @RequestMapping 애노테이션을 찾는 방법
- HandlerAdapter
    - HandlerMapping에서 찾은 Handler(Controller)의 메소드를 호출 및 ModelAndView 형태로 변경
- Controller
- ViewResolver
    - 이름을 가지고 실제 view를  찾음
- DispatcherServlet → View

1. 클라이언트 요청이 DispatcherServlet으로 전달
2. DispatcherServlet은 HandlerMapping을 사용하여 클라이언트의 요청을 처리할 컨트롤러 객체를 찾음
3. DispatcherServlet은 컨트롤러 객체의 handleRequest() 메서드를 호출하여 클라이언트 요청을 처리
4. 컨트롤러의 hadlerRequest() 메서드는 처리 컬과 정보를 담은 ModelAndView 객체를 리턴
5. DispatcherServlet은 ViewResolver로부터 응답 결과를 생성할 뷰 객체를 구함
6. View는 클라이언트에 전동할 응답을 생성

### 정리

Spring Web MVC가 없을떄는 Url 마다 servlet을 생성, servlet에서 view로 보내주는 역할까지 다 만들어 졌음

Spring Web Mvc는 dispatcher servlet 1개로 관리됨. view를 분리시킴