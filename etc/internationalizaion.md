## 웹 사이트 다국어 처리
웹 사이트 다국어 처리시 고려할 사항은 세가지다. 백엔드 다국어처리, 프론트엔드 다국어처리, 데이타베이스 다국어 처리.
Java 언어 와, Spring 프레임워크를 사용한다면 백엔드는 Spring 에서 쉽게 제공하기 때문에 이를 설명한다.
프론트엔드 쪽은 jqeury-i18n-properties라는 라이브러리를 사용했다. 다른 라이브러리도 많지만 properties를 사용하는 Spring 프레임워크와 사용방법이 비슷하기 때문이다.
데이터베이스 다국어 처리는 디비 모델링을 vertical 형식, horizontal 형식이 있다. 여기서는 데이터 베이스는 다루지 않고 백엔드와 프론트엔드의 다국어 처리만 다룰예정이다.

### 백엔드 다국어 처리
#### Spring  MVC internatiolization (i18n) 사용
###### messages.properties 작성
프로퍼티는 아래와 같이 작성합니다
~~~properties
test.hello=Hello!
test.name=My name is {0}
~~~

* messages.properties 네이밍에서 국가는 국가 코드를 적어주셔야 합니다. en, ko, jp..
* messages.properties, messages-en.properties, messages-ko.properties 있고 messages.properties 가없는 나라에서 접속하면 default인 messages.properties를 읽고 messages.properties에는 있는 코드가 ko 에는 없다면 messages.properties에 있는 값을 읽습니다.


###### servelet-context.xml
~~~xml
<!--ReloadableResourceBundleMessageSource로 변경:ReloadableResourceBundleMessageSource는 properties가 바뀌면 자동으로 로드 됨!!-->
<!--대신 중요한건 cacheSeconds가 디폴드 값인 -1일 경우에는 안됨. 꼭 값이 있어줘야함. 참고 :http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/support/ReloadableResourceBundleMessageSource.html-->
<beans:bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
  <beans:property name="basenames" value="classpath:locale/messages"/>
  <beans:property name="defaultEncoding" value="UTF-8"/>
  <!--만약 true면, cn,en,ko 이외에 locale이 지정됐을때 현재 시스템 locale이 자동설정됨.-->
  <!--false면, default locale로 설정됨.-->
  <beans:property name="fallbackToSystemLocale" value="false"/>
  <beans:property name="cacheSeconds" value="10"/>
</beans:bean>

<beans:bean id="localeResolver"
      class="org.springframework.web.servlet.i18n.SessionLocaleResolver">
  <beans:property name="defaultLocale" value="en" />
</beans:bean>

<mvc:interceptors>
  <beans:bean id="localeChangeInterceptor" class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor">
    <beans:property name="paramName" value="locale" />
  </beans:bean>
</mvc:interceptors>
~~~

* 설명
  *  messageSource
  스프링에서 제공해주는 message source 는 ResourceBundleMessageSource,  ReloadableResourceBundleMessageSource가 있다. ResourceBundleMessageSource  클래스명 그대로 우리가 작성한 messages.properties를 읽는 역할을 한다. ReloadableResourceBundleMessageSource는 properties 를 리로드하는 기능이 있어서 편리하다. 서버를 재시작 하지 않아도 된다. 이 클래스를 사용하기 위해서는 basenames을 classpath로 적어줘야한다. 그래야 서버를 재시작하지 않고도 읽기 때문이다.
    * fallbackToSystemLocale 속성은 locale이 존재하지 않을때 시스템 locale로 간다는 의미다. default가 ture기 때문에 false라고 명시해주는 것이 좋다. (false 라고 적어주지 않으면 localeResolver에서 적어주는 defaultEncoding을 설정해도 system lacale로 설정되기 때문에 system locale과 defaultEncoding 이 같지 않는 한 defaultEncoding이 소용이 없다.)
     * cacheSeconds는 ReloadableResourceBundleMessageSource을 사용하기 위해 꼭 필요하다. 추측하건데 properties를 변경하면d 여기 cache에 저장하는 것 같다. ReloadableResourceBundleMessageSource가 API 문서에서도 cacheSeconds를 꼭 0 이상해야한다고 나온다. cacheSeconds는 default 값이 0이기 때문에 따로 설정해줘야한다.
  만약 ResourceBundleMessageSource 를 사용한다면 그냥 basenames, defaultEncoding만 적어줘도 잘 동작한다.

  * localeResolver
  localeResolver는 4 가지가 있다. AcceptHeaderLocaleResolver, CookieLocaleResolver, SessionLocaleResolver, FixedLocaleResolver 다.
    * AcceptHeaderLocaleResolver
      *  requst 에서 넘어오는 Accept-Language 헤더로부터 locale값을 기본 locale로 설정합니다.
    * CookieLocaleResolver
      * 쿠키에 locale정보를 저장한다. setLocale();
    * SessionLocaleResolver
      * 세션에 locale정보를 저장한다. setLocale();
    * FixedLocaleResolver
      * 특정 로케일로 고정한다.
  저는 특정 로케일이 바뀔때마다 set할수 있도록 SessionLocaleResolver을 사용했습니다. (CookieLocaleResolver도 가능!) 이때 속성으로 defaultLocale 을 en으로 설정합니다. 여기서 en 값은 messages-en.properties 에서 중간값입니다. 만약 defaultLocale을 설정하지 않으면 브라우저의 locale 값을 가져옵니다.

  * LocaleChangeInterceptor
    이는 파라미터를 설정하고 이파라미터로 들어왔을때 스프링에서 locale을 변경해줍니다.

만약 locale 값을 임의로 변경하고 싶다면, 예를들어 처음 들어올시 브라우저 로케일 설정값을 바로 읽기 위해 java 코드로 locale을 변경할수 있습니다.
~~~java
LocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setLocale(request, response, locale);
		HttpSession session = request.getSession(false);
    //프론트엔트 다국어처리시 locale값 필요
		session.setAttribute("locale", locale);
~~~


###### <spring:messages /> 사용
  설정을 잘 마쳤다면 jsp에서 messages.properties에 정의된 내용을 표현하기위해 <spring:messages>를 사용합니다.
  <spring:messages>이 가지고 있는 속성은 아래와 같습니다.

* arguments :부가적인 인자를 넘겨줌. 콤마로 구분된 문자열, 객체 배열, 객체 하나를 넘김.
* code: 룩업할 메시지의 키 지정. 지정하지 않으면 text에 입력한 값 출력.
* argumentSeparator: 넘겨줄 인자들의 구분자 설정. 기본값은 콤마.
* htmlEscape: html 기본 escapse 속성 오버라이딩. 기본값 false.
* javaScriptEscape : 기본값 false
* message:부가적인 인자를 넘겨줌. 콤마로 구분된 문자열, 객체 배열, 객체 하나를 넘김.
* scope :결과 값을 변수에 지정할 때 변수의 scope 지정 (page, request, session, application)
* text :해당 code로 가져온 값이 없을 때 기본으로 보여줄 문자열. 빈 값이면 null 출력.
* var : 결과 값을 이 속성에 해당한 문자열에 바인딩 할 때 사용. 빈 값이면 그냥 JSP에 뿌려줌.

  ~~~html
    !-- messages.properties 에 정의된 code를 넣는다, text는 혹시 지정된 코드가 없거나 값이 없으면 출력되는 값 --!
    <spring:messages code="test.hello" text="Hollo!"/>
    !-- messages.properties 에서 test.name 에 {0} 이라고 표현된 곳에 "Sooyoung" 이라는 인자값이 들어가 결과적으로 My name is Sooyoung이 출력됩니다. --!
    !--인자가 여러개일 경우 properties 파일에는 {0}, {1}, {2} 라고 작성하고 arguments="Sooyoung, Seohee, SeoYoon"으로 , 로 구분하면 {0} ,{1},{2} 에 매핑됩니다.  --!
    <spring:messages code="test.name"  arguments="Sooyoung"/>  
  ~~~

* 참고
  * [mkyong example](https://www.mkyong.com/spring-mvc/spring-mvc-internationalization-example/)
  * [/Spring-Locale-처리](http://devbox.tistory.com/entry/Spring-Locale-%EC%B2%98%EB%A6%AC
  * [egov](http://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte:ptl:internationalization)


### 프론트 엔드 다국어 처리
#### jquery-i18n-properties 라이브러리 사용.
우설 라이브러리는 [여기](https://github.com/jquery-i18n-properties/jquery-i18n-properties/wiki) 서 받을수 있습니다.
이 라이브러리는 messages.properties에 정의된 코드를 사용합니다. 동작 원리는 jqeury에서 messages bundle의 이름, locale 값 ,호출할 ajax url (서버에서 properties를 읽습니다.) 을 보내면 callback에서 messages.properties의 code/key를 넣어주면 값을 return해 줍니다. 따라서 아래와 같은 함수를 사용합니다.

~~~javascript
function loadBundle(key) {
var lang = $('#locale').val();
var returnValue;
jQuery.i18n.properties({
  name:'messages',
  path:'/locale/',
  mode:'map',
  language:lang,
  callback: function() {
    returnValue=jQuery.i18n.prop(key);
  }
});
return returnValue;
}
~~~
lang 값은 서버에서 session에 담은 locale 정보 입니다. 콜백부분에서 jQuery.i18n.prop(key); key를 보내면 값을 리턴합니다. 만약 인자가 필요하다면 properties 에는 백엔드에서 정의된 것처럼 인자 받을 곳에 {0}으로 처리하고, 콜백함수에서 값을 가져올때 jQuery.i18n.prop(key, arg); 이렇게 arg 값을 보내줍니다.

* 참고
  * http://jsbin.com/losejo/edit?html,js,output
  * http://apieceofspace.blogspot.kr/2014/01/spring-spring-java-web-application.html
  * [jqeury i18n library github](https://github.com/jquery-i18n-properties/jquery-i18n-properties/wiki)
  * [이게 도움이 많이 됐음 ㅠㅠ](http://egloos.zum.com/aretias/v/970593)



## 데이터 베이스 다국어 처리
* 참고
  * [디비 구조](http://stackoverflow.com/questions/10693508/database-modeling-for-international-and-multilingual-purposes)
  * [스프링 메시지소스 이용한 db처리](http://antop.tistory.com/144) :  결국은 jdbcTemplate으로 감싸서 Service를 만든것..
  * http://okjsp.pe.kr:8080/article/335046?note=1083469
  * http://blog.morfik.com/2010/03/02/internationalization-in-morfik-part-2/
  * https://blog.mornati.net/spring-mvc-database-messagesource-fall-back-to-properties-file/

##### 다국어 처리 필요한 table 또는 화면
* Code table
* 화면에서 date 처리/DB 에서 dataformat 이 %b. %d, %Y Sep 3, 2016   -> javascript로 영어 기준으로 돼있음 -> 괜찮을것 같음..
* App Summary 화면 중 App Result Table
* App Info 정보 - App info , App description -> 이정보는 앱을 만드는 사람이 넣는 정보기 때문에 다국어 처리를 하지 않아도 될것 같기도하구. 하려면 앱을 만드는 쪽에서 올려야 할것 같은데.
* Company 정보, 소개, 개요. -> 개요같은거..흠.. 넘 길어서 필요할것 같기도 하고.
* 다국어 적용된 시간 국가에 따라 시간


***
## 사용방법
* 앞으로 JSP 파일에 들어가는 Text들은 message.properties에 미리 메시지를 적어두시고 <spring:messages code=""/>를 사용해주세요
* javascript에서 보여주는 text들은 (custom alert와 같이) messages.properties에 보여줄 메시지를 적어주시고 loadBundle("key") 함수를 호출해주세요.
* controller에서 보내는 텍스트들 (예를들어 validation 에서 common/message로 가는 값들) 또한 messages.properties에 정의해주시고 model.setAttribute("message","key")라고 보내주세요. ErrorCode를 작성할때도 위와 같습니다.
