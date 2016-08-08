## JVM, JRE, JDK 차이

#### JVM
* JVM 자바 가상 머신 (Java Virtual Machine)의 약자
* JVM은 자바 코드로부터 만들어지는 자바 바이너리파일(.class)파일을 실행할수 있다
* JVM은 플랫폼의존적이라 리눅스의 JVM, 윈도우의 JVM 이 다르다.

##### JVM  역할
* 바이너리 코드를 읽는다
* 바이너리 코드를 검증한다
* 바이너리 코드를 실행한다.
* 실행환경 (Runtime Environment)의 규격을 제공한다.


#### JRE
* Java Runtime Environment이다
* JRE는 JVM이 자바 프로그램을 동작시킬때 필요한 라이브러리 파일과 기타 파일을 가지고 있다. JRE는 JVM의 실행환경을 구현했다.


#### JDK
* Java Development Kit 자바 개발도구의 약자다
* JDK는 JRE + 개발에 필요한 도구(javac, java 등)을 포함한다.

> 정리하자면 JVM은 우리가 작성한 코드를 실행하는 virtual machine이다. 따라서 어떠한 운영체제 위에서라도 JVM만 있으면 java 코드를 실행할수있다.
> 이러한 JVM을 실행할수 있는 환경으 구성해주는 것이 JRE이다. 그리고 이러한 JRE를 가지고 있고 그외 개발을 위해 필요한 도구를 가지고 있는것이 JDK인 셈이다.


참고 : [점프투 자바 - JVM, JRE, JDK 차이] (https://wikidocs.net/257)
