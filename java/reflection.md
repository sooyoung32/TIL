# Java Reflection

```
Reflection is commonly used by programs which require the ability to examine or modify the runtime behavior of applications running in the Java virtual machine. 
This is a relatively advanced feature and should be used only by developers who have a strong grasp of the fundamentals of the language.
With that caveat in mind, reflection is a powerful technique and can enable applications to perform operations which would otherwise be impossible.
```

Reflection is an API which is used to examine or modify the behavior of methods, classes, interfaces at runtime.

JVM에서 실행되는 어플리케이션의 ***런타임*** 동작을 검사하거나 수정할 수 있는 기능이 필요한 프로그램에서 사용됨.

should be used only by developers who have a strong grasp of the fundamentals of the language....ㅎㅎㅎ

## 사용처

- IDE,디버거, 테스트툴, 스프링에서도 많이 쓰이고..

## 예제

- [https://www.baeldung.com/java-reflection](https://www.baeldung.com/java-reflection) 를 보면 잘 나와있음!!

## 단점

리플렉션은 강력하지만  리플렉션을 피할 수 있으면 피하는게 좋다.

- 성능 오버헤드
    - 리플렉션으로 만들어진 코드는 런타임시 동적으로 생성되기 때문에 JVM 최적화가 되지 않는다.
    - 따라서 리플렉션은 성능이 좋지 않아 자주 호출되는 코드 부분에서는 피해야한다.
- 보안 이슈
    - 리플렉션은 Security Manager에서 실행할때 (아마도 보여지지 않을??) 런타임시 권한을 요구한다.
    - 이는 제한된 Security Context 에서 실행해야하는 코드라면 고려해야할 사항이다..
- 내부 노출
    - 리플렉션은 private field 에 접근하는 것과 같이 일반 객체가 접근하면 안되는 필드에 접근이 가능할 수 있게 하기 때문에 예기키 않은 부작용을 초래해 코드가 정상적으로 작동하지 않을 가능성이 있다..

### 참고

[https://docs.oracle.com/javase/tutorial/reflect/index.html](https://docs.oracle.com/javase/tutorial/reflect/index.html)