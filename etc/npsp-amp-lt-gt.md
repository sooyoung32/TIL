## ＆nbsp; ＆amp; ＆lt; ＆gt; ＆quot; 의미

*  ＆nbsp; : "", 공백
* ＆amp; : 앰퍼샌드 &
* &gt; : greater than >
* &lt; : less than <
* &quot; : " 쌍따옴표
* &#039; :  ' 따옴표
* &#035;  : #


ex)
~~~query
  date &gt; = now();
~~~

***
참고로 mybatis에서 쿼리를 작성할시에는 위의 예시와 같이 사용해도되고, 아래와 같이 CDATA를 사용해도된다.

~~~
<![CDATA[ date > now(); ]]>
~~~
