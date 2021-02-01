# 4장 Flwoable, Observable 연산자
## Flowable , Observable 생성 연산자

### just

인자 데이터를 통지

```java
Flowable<String> flowable = Flowable.just("a", "b");
```

### fromArray/fromIterable

배열이나 iterable에서 생성

```java
Flowable.fromArray("a", "b");
```

### fromCallable

Callable의 처리를 실행하고 그 결과를 통지하는

```java
Flowable.fromCallable(()-> System.currentTimeMilles())
```

### range/rangeLong

지정한 숫자 부터 지정한 개수 만큼 통지

```java
Flowable.range(10, 3)
```

### interval

지정한 간격마다 숫자를 통지

```java
Flowable.interval(1000L, TimeUnit.MILLESECOND)
```

### timer

지정 시간 경과 후 0을 통지

```java
Flowable.timer(1000L, TimeUnit.MILLESECOND)
```

### defer

구독될때마다 새로운 Flowable Observable 생성

just 메서드 등과 다르게 defer는 선언한 시점의 데이터를 통지하는것이 아니라, 호출 시점에 데이터 생성이 필요하면 사용

```java
Flowable.defer(() -> Flowable.just(LocalTime.now()));
```

### empty

빈 Flowable, Observable 생성

이 메서드는 flatmap 메서드의 통지 데이터가 null 인경우 이 대신에 empty 메서드에서 생성한  Flowable, Observable 로 이후 데ㅣ어트 통지 대상에서 제외하는 작업등에서 사용

### error

에러만 통지

### never

아무것도 통지 하지 않는

## 통지 데이터 반환 연산자

### map

데이터 변환 통지

### flatMap

받은 데이터를 Flowable, Observable 로 변환하고 이 Flowable Observable 의 데이터를 통지

map 메서드와 달리 여러 데이터가 담김 Flowable Observable을 반홚하므로 데이터 한 개로 여러 데이터를 통지 할 수 있음.

```java
Flowable<String> flowable = Flowable.just("a", "", "b", "")
. flatMap(data -> {
	  if ("".equals(data)) {
			 return Flowable.empty();
		} else {
			 Flowable.just(data.toUpperCase());
    }
})
flowable.subscribe();

// result 
main : a
main : b 
```

### concatMap, concatMapDelayError

원본 데이터를 Flowable Observable로 변환해 이 변환한 Flowable,Observable 데이터를 통지하는 연산자

```java
Flowable.range(10,3)
.concatMap(data -> Flowable.interval(500L, TimeUnit.MILLISECONDS)) 
.take(2)
.map(data -> return System.currentTimeMilles()+"ms"+data);
```

### buffer

통지할 데이터를 지정한 범위까지 모아 리스트나 컬렉션으로 통지

### toList, toMap

통지할 데이터를 모두 리스트, 맵에 담아 통지

## 통지 데이터 제한 연산자

### filter

지정한 조건에 맞는 데이터만 통지

### distinct

이미 통지한 데이터와 같은 데이터를 제회하고 통지

### takeUntil

지정된 조건에 도달할때까지 통지

- 받은 데이터를 지정한 조건으로 판단해, 그 결과가 true가 될때까지 통지
- 인자로 받은 Flowable, Observable 이 처음으로  데이터를 통지할때까지 계속해서 데이터를 통지하는 메서드

```java
Flowable.interval(300L, TimeUnit.MILLISECONDS)
.takeUntill(data -> data == 3);
```

### takeWhile

지정한 조건에 해당할 때만 데이터 통지

### skipWhile

지정한 조건에 해당할 때는 데이터 통지 제외

### throttleFirst/Last/

데이터 통지 후 지정 시간 동안 데이터를 통지하지 않음 / 지정된 시간마다 가장 마지막에 통지된 데이터만 통지

### elementAt

지정된 위치의 데이터만 통지

## Flowable , Observable 결합 연산자

### merge

여러개의 Flowable, Observable을 하나로 병합하고 동시 실행

```java
Flowable flowable1 = Flowable.interval(300L, TimeUnit.MILLESECONDS)
.take(5);
Flowable flowable2 = Flowable.interval(300L, TimeUnit.MILLESECONDS)
.take(1);

Flowable.merge(flowable1, flowable2);
```

### concat

여러개의 Flowable, Observable을 하나씩 수행

### zip, zipWith

여러 Flowable Observable 의 데이터를 모아 새로운 데이터를 통지

```java
Flowable flowable1 = Flowable.interval(300L, TimeUnit.MILLESECONDS)
.take(5);
Flowable flowable2 = Flowable.interval(300L, TimeUnit.MILLESECONDS)
.take(1);

Flowable<List<Long>> result = Flowable.zip(flowable1, flowable2 (data1, data2) -> Arrays.asList(data1, data2));

result.subscribe();
```

## Flowable , Observable 상태 통지 연산자

### isEmpty

Flowable, Observable이 통지할 데이터가 있는지 판단 하는 메서드

### contains

Flowable Observable이 지정한 데이터 포함하는지 판단

### sequenceEqual

두 Flowable Observable이 같은 순서로 같은 수의 같은 데이트를 통지하는지 판단

### count

데이터 개수 통지



## Flowable , Observable 데이터 집계 연산자

### reduce, reduceWith

Flowable Observable 데이터를 계산하고 최종 집계결과만 통지

```java
Single<Integer> single = Flowable.jsut(1,10,100)
.reduce(0, (sum, data)-> sum+data);

single.subscribe();

// result
111
```