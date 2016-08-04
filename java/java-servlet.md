## 자바 서블릿
* Java Servlet은 자바를 사용해 웹페이지를 동적으로 생성하는 서버측 프로그램 또는 그 사양이다.
* 자바 서블릿은  웹 서버의 성능을 향상하기위해 사용되는 자바 클래스의 일종이다.
* JSP가 HTML 문서 안에 Java 코드를 포함하고 있따면, 서블릿은 자바코드안에 HTML을 포함하고 있는 것이다.
* 자바 서블릿은 외부 요청마다 프로세스보다 가벼운 스레드로 응답해 가볍다.

*참고* : [자바서블릿 wiki](https://ko.wikipedia.org/wiki/%EC%9E%90%EB%B0%94_%EC%84%9C%EB%B8%94%EB%A6%BF)

### 서블릿 컨테이너
* HTTP 요청을 받아 Servlet을 실행시키고 그 결과를 사용자 브라우저에게 전댈해주는 기능을 제공하는 컴포넌트다.
* Servlet을 실행하고 생명주기를 관리하는 역할을 한다
* Servlet과 웹서버가 서버 통신할 수 있는 방법을 제공한다.
* 멀티 스레딩을 지원하여 클라이언트의 다중 요청을 처리한다.
* 대표적으로 tomcat, jetty가 있다.

### 서블릿 동작 과정
1. **사용자의 URL 요청** : 사용자가 URL을 클릭하면 HTTPRequest 를 Servlet Container에게 보낸다
2. **Reqeust, Response 객체 생성** : 서블릿 컨테이너는 요청을 처리하기 위해 HttpServletRequest 객체와, HttpServletResponse  객체를 생성한다.
3. **서블릿 인스턴스와 스레드생성** : 서블릿 컨테이너는 사용자가 요청한 URL을 분석해 어느 서블릿에 대한 요청인지 찾는다(DD참조). 만약 그 클래스가 웹 컨테이너에서 한번도 실행된적이 없고, 메모리에 생성된 프로세스가 없다면 새로 인스턴스를 만들고(메모리에 로드) init() 메소드를 실행해 초기화하고 스레드를 생성한다. 이미 인스턴스가 존재하는 경우 스레드만 하나 생성된다. 각 서블릿 인스턴스는 웹 컨테이너 당 하나만 존재하기 때문에  init() 메소드는 한번만 호출된다.
4. **service() 메소드 호출과 서블릿 클래스 실행** : 서블릿 컨테이너는 스레드가 생성되면 service() 가 호출되고, 메서드 호출 방식이 get이면 doGet(), post면 doPost()를 호출한다.
5. **응답과 스레드 소멸** : doGet(), doPost()로 사용자 요청에 의해 페이지를 생성한 후 HttpServletResponse 객체에 응답을 보낸다. 그리고 사용이 다 끝나면 HttpServletRequest HttpServletResponse 객체를 소멸시키고 스레드를 종료한다.

* Deployment Descriptor 배포서술자  : web.xml
  * Servlet, Error Page, Listener, Filter, 보안 설정 등 Web Application 설정파일이다
  * URL과 실제 서블릿 매핑정보를 가진다.
  * 하나의 어플리케이션에 하나만 존재하고, Web Document Root 디렉토리에 WEB-INF 폴더 아래 web.xml 파일로 존재한다.  

*참고* : [초코빵빵님의 블로그](http://dkatlf900.tistory.com/68), [서블릿 간단예제](http://wiki.gurubee.net/pages/viewpage.action?pageId=26740202)
