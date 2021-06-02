# String performance Hints
[이 문서](https://www.baeldung.com/java-string-performance) 를 내 뜻대로 번역한 셈 ㅎㅎ

## 생성자로 String 생성

- String은 불변이라 + 연산자로 문자열을 연결할 때마다 생성됨. → loop에서 생성한다면 아주 큰 비용
- 대부분 이 방법은 사용하지 않음
- 테스트
    - 루프 안에서 new String()
- JMH(Java Microbenchmark Harness) 사용해서 테스트
    - 각자 JVM 환경에 따라 왜곡된 결과를 측정할 수 있어서 주의해야함.

```java
@BenchmarkMode(Mode.SingleShotTime) // 한 번만 메서드 실행
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(batchSize = 10000, iterations = 10) // 루프내에서 String 동작을 측정할 예정
@Warmup(batchSize = 10000, iterations = 10)
public class StringPerformance {
		@Benchmark
		public String benchmarkStringConstructor() {
		    return new String("baeldung"); //매번 생성
		}
		
		@Benchmark
		public String benchmarkStringLiteral() {
		    return "baeldung"; //딱 한번만 생성됨  왜냐면 String 상수풀에서 return 되기 때문
		}
}
```

- count = 1,000,000 번 했을때 결과는 score  봤을ㅐ 다름을 알 수 있음

```java
Benchmark                   Mode  Cnt  Score    Error     Units
benchmarkStringConstructor  ss     10  16.089 ± 3.355     ms/op
benchmarkStringLiteral      ss     10  9.523  ± 3.331     ms/op
```

### + 연산자

다이나믹한 String concat

```java
@State(Scope.Thread)
public static class StringPerformanceHints {
    String result = "";
    String baeldung = "baeldung";
}

@Benchmark
public String benchmarkStringDynamicConcat() {
    return result + baeldung;
}
```

```java
Benchmark                       1000     10,000
benchmarkStringDynamicConcat    47.331   4370.411
```

- 1000 아이템을 더하는데 47.331 ms, 연속해서 10배를 늘렸을때 시간이 4370.411ms 걸렸다는 뜻
- 요약하면 수행 시간은 2차로? 증가 . 따라서 반복 루프에서 동적 연결의 복잡성은 O(n^2)이다.

### String.concat()

```java
@Benchmark
public String benchmarkStringConcat() {
    return result.concat(baeldung);
}
```

```java
Benchmark              Mode  Cnt  Score     Error     Units
benchmarkStringConcat    ss   10  3403.146 ± 852.520  ms/op
```

- 100000 번 반복했을때 점수 3403.146 (점수 의미 뭐지..)

### String.format()

내부적으로 input 값을 파싱하기 위해 정규 표현식을 사용

```java
String formatString = "hello %s, nice to meet you";

@Benchmark
public String benchmarkStringFormat_s() {
    return String.format(formatString, baeldung);
}
```

```java
Number of Iterations      10,000   100,000   1,000,000
benchmarkStringFormat_s   17.181   140.456   1636.279    ms/op
```

- 코드로는 String format()이 깔끔하고 가독성이 좋지만, 성능은 좋지 않음..

### StringBuilder and StringBuffer

StringBuilder는 조절가능한 배열(resizable array)과 해당 배열에 마지막 셀의 위치를 가르키는 인덱스를 사용하고, 배열이 다 찬 경우 그 사이즈를 두배로 늘리고, 새 배열에 모든 문자를 복사한다.

resize 되는것이 드문것을 고려하면, 각 append() 작업은 O(1) 상수시간! 그래서 전체는 O(N)의 복잡도를 가진다.

StringBuilder와 StringBuffer의 다이나믹한 문자열 연산을 측정해 보면 -

```java
Benchmark               Mode  Cnt  Score   Error  Units
benchmarkStringBuffer   ss    10  1.409  ± 1.665  ms/op
benchmarkStringBuilder  ss    10  1.200  ± 0.648  ms/op
```

StringBuilder 가 더 빠르다 - 당연하겠지만..

다행히도 문자 더하는 연산에 StringBuilder이 필요하진 않은데,  + 연산이 실제로 StringBuilder로 대체되기 때문. 내부적으로 최신 자바 컴파일러는 string 을 concat 하는데 StringBuilder.append()은 사용!

### 다른 유틸

다른 유틸은 결과만 -

### StringUtils.replace() vs String.replace()

Apache Commons 버전이 더 좋다.  String.replace()은 내부적으로 정규표현식을 사용하기 때문 - StringUtils.replace()는 indexOf()를 사용해서 빠르다.

### split()

String.split(regex) 이거는 성능이슈가 있는데, 이게 정규식도 쓸수 있기 때문.. 대안으로 StringTokenizer 를 사용하는게 낫다.

다른 선택은 Guava's Splitter API 도 있고, 마지막으로 우리의 좋고 오래된 indexOf()를 사용하는것이 좋다. (정규표현식이 필요하지 않다면 )

### Converting to String

```java
Integer.toString()
String.valueOf()
Integer + "" 
String.foramt() 
```

Integer.toString() 가성능이 제일 좋고, String.foramt() 가 제일 안좋음. 문자열을 파싱하는건 엄청난 피용이 드는 연산이기 때문..

### Comparing Strings

```java
String.equals()
String.equalsIgnoreCase()
String.matches()
String.compareTo()
```

matches() 가장 느리고 equals() and equalsIgnoreCase()가 최고의 선택!

### String.matches() vs Precompiled Pattern

```java
@Benchmark
public boolean benchmarkStringMatches() {
    return longString.matches(baeldung);
}

@Benchmark
public boolean benchmarkPrecompiledMatches() {
		Pattern longPattern = Pattern.compile(longString);// 이거!! 
    return longPattern.matcher(baeldung).matches();
}
```

미리 컴파일된 정규표현식에 대한 성능이 훨씬 좋음.. !!

### Checking the Length

```java
String.isEmpty()
String.length()
```

둘다 성능은 비슷하지만 isEmpty()가  String.length() 보다 빠르다.

### 결론

- String concat 연산에는 StringBuilder가 가장 편리한 선택  + 연산자도 거의 같은 성능
- String convert 시에는 [some type].toString() (Integer.toString()) 이 String.valueOf() 보다 빠름. 근데 큰 차이는 없어서 인풋 타입에 의존하지 않는 String.valueOf()를 사용해도 괜춘
- String 비교할때는 String.equals()가 최고
- String split 할때는 indexOf()를 사용하는게 성능상 가장 좋음. 다만, 큰 이슈가 아니라면 String.split() 기능을 사용하는게 적합할지도
- Pattern.match()를 사용하는게 의미있는 성능 향상이 있다.
- String.isEmpty() 가 String.length() ==0 보다 빠르다.

## 참고

- [https://www.baeldung.com/java-string-performance](https://www.baeldung.com/java-string-performance)