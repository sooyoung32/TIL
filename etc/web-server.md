## Web Server와 Web Application Server
평소에 웹서버와 웹 어플리케이션 서버의 차이. 보통 apache와 같은 웹서버는 정적파일을 다루고, 동적파일은 tomcat과 같은 웹 어플리케이션 서버에 보내게 된다고만 알고 있었다.
그러다 문득, IDE에서 작업을 할때 우리는 주로 톰캣만 띄우지 apatch같은 웹서버는 띄우지 않아서 그럼 어떻게 전달 받는지가 궁금해 찾아보았다.
(톰캣은 웹서와 웹어플리케이션 서버 역할을 둘다한다...쩝 나만 몰랐나보다.)

여튼 Web Server와 Web Application Server의 차이점을 알아보자.

### Web Server
Web Server란 Web Http Server를 말한다. 즉 Http 요청을 처리하는 서버다.
주된 기능은 웹 페이지를 클라이언트에 전달해주는 것이다. 주로 이미지파일, HTML, js, css와 같은 정적 파일을 처리한다.  

대부분의 웹서버는 다음과 같은 기능을 제공한다.

* 인증
* 정적 콘텐츠 관리
* HTTPS 지원
* 콘텐츠 압축
* 가상 호스팅
* 대용량 파일지원
* 대역폭 스로틀링

### Web Application Server
Http를 통해 사용자의 컴퓨터나 장치에 있는 어플리케이션을 수행해주는 미들웨어(소프트웨어 엔진)이다. 웹 어플리케이션 서버는 동적 서버 콘텐츠를 수행한다.
웹 컨테이너라고도 한다.

#### Web Application Server 기본 기능
* 프로그램 실행환경과 디비 접속 기능
* 트랜젝션관리
* 비지니스 로직 수행
