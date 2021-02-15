# Switch

## break

- swich 문을 exit 할때 사용
- break 문 작성을 빼면, switch block은 계속 실행됨.
- 같은 결과를 실행하고 싶을때는 의도적으로 break문을 생략하기도 -

```java
public String exampleOfSwitch(String animal) {
    String result;
    switch (animal) {
        case "DOG":
        case "CAT":
            result = "domestic animal";
            break;
        case "TIGER":
            result = "wild animal";
            break;
        default:
            result = "unknown animal";
            break;
    }
    return result;
}
```

## switch 인자와 case 값

### Data Type

- 모든 타입의 객체와 원시타입을 switch 문에 사용할 수 없음.
- **가능한 타입**
  - byte and Byte
  - short and Short
  - int and Integer
  - char and Character
  - enum (after java 5)
  - String ( after java 7)
- null은 사용할 수 없음
- final 값만 사용 가능

### String 비교

객체로 생성해서 연산자로 비교하는 경우, 일반 객체기 때문에 비교할수 없지만 다행히 내부적으로 equals() 비교를 하기 때문에 가능

## New Switch Expressions

- switch 문은 JDK 12에서 신규기능을 선보였고, JDK 13에서 향상된 버전으로 출시.
- 사용하기 위해서는 ***-enable-preview***  옵션 필요

```java
var result = switch(month) {
    case JANUARY, JUNE, JULY -> 3;
    case FEBRUARY, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER -> 1;
    case MARCH, MAY, APRIL, AUGUST -> 2;
    default -> 0; 
};
```

- statment or expression 모두 사용 가능!
- `:`  대신 `→` 오퍼래이터 사용
- `break` 가 없어짐
- **`,`** 로 구분지어서 조건 추가

### yield keyword

- yield 키워드를 사용하여 좀 더 세밀한 제어가 가능

```java
var result = switch (month) {
    case JANUARY, JUNE, JULY -> 3;
    case FEBRUARY, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER -> 1;
    case MARCH, MAY, APRIL, AUGUST -> {
        int monthLength = month.toString().length();
        yield monthLength * 4;
    }
    default -> 0;
};
```

- yield는 특정 값을 생산하기 위해 swich 문의  종력을 야기하는 것을 제어하는 문
- break 없어지고 yield 가 생김!

### return

- switch 식에서는 return 사용 못함.
- switch 문에서만 사용 가능

### Exhaustiveness

- switch 식에서는 defualt 가 없어도 compile 가능
- switch 문에서는 defualt 값이 필수! 아니면 compile 불가능

JDK 12

[https://openjdk.java.net/jeps/325](https://openjdk.java.net/jeps/325)

JDK 13

[https://openjdk.java.net/jeps/354](https://openjdk.java.net/jeps/354)