# 의미있는 이름 
### 의도를 분명히 하라

```java
int d; //시간
int daysSinceCreation;
```

### 의미있게 구분하라

- ProductData, ProductInfo → 개념을 구분하지 않은 채 이름만 달리한 경우
- NameString vs Name?
- Customer vs CustomerObejct?
- MoneyAmount vs Money?

### 발음하기 쉬운 이름을 사용하라

- 약어 (Mangement → Mgmt)는 발음하기 어렵다.

### 검색하기 쉬운 이름을 사용하라

- 문자 하나인 변수이름과 상수는 찾기 어렵다. 한번에 찾을 수 있도록!

### 인코딩을 피하라

- PhoneNumber phoneString ← String 굳이..
- 멤버변수 접두어라거나 String m_id;

### 기억력을 자랑하지 마라

- 문자 하나만 사용하는 변수이름 지양 (i,j,k)
- 클래스 이름 : 명사
- 메서드 이름 : 동사

### 기발한 이름은 피하라

- whack() → kill()
- eatMyShort() → abort()

### 한 개념에 한 단어를 사용하라

- 추상적인 개념 하나에를 선택해 이를 **고수**하라
    - fetch, get, retrieve...
- 일관선 있는 어휘 코드가 중요하다

### 말장난을 하지 마라

- 한 단어를 두가지 목적으로 사용하지 마라. 다른 개념에 같은 단어를 사용하면 그것은 말장난이다.
- 기존 add 메서는 모두 기존 값을 두개 더하거나, 새로운 값을 만든다고 가정한다. 새로 작성하는 메서드는 집합에 값을 추가한다 - 이 메서드는 add인가? 이 경우 add의 기존의 의미와 다르기 때문에 append와 insert가 적당하다.

### 해법 영역에서 가져온 이름을 사용하라

- 알고리즘 이름, 패턴이름, 수학용어
- 모든 용어를 도메인에서 가져오는 정책은 현명하진 않다.

### 문제 영역에서 가져온 이름을 사용하라

- 프로그래머용어가 없다면 문제 영역에서 이름을 가져온다.
- 우수한 프로그래머라면 해법영역과 문제 영역을 구분할 줄 알아야...(?)

### 의미있는 맥락을 추가하라

- 스스로 의미가 분명한 이름이 없지 않지만 대다수는 그렇지 못한다.
- 이때 클래스, 함수, 이름 공간에 넣어 맥락을 부여한다.
- 그래도 안되면 접두어를 붙인다

```java
String firstName;
String lastName 
String state;
// Address 라는 클래스를 추가하면 각 변수의 의미가 더욱 분명하다.

class Address {
		String firstName;
		String lastName 
		String state;
}
```

### 불필요한 맥락을 없애라

- 일반적으로 짧은 이름이 좋다.
- MaillingAddress, AccountAddress 클래스는 → 이런건 지나치게 세분화한 것