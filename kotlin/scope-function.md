# 스코프 함수 

인스턴스의 속성이나 함수를 스코프 내에서 분리하여 사용하는 점에서 가독성이 좋다.

## apply

> The context object is available as a receiver (this). The return value is the object itself.
Use apply for code blocks that don't return a value and mainly operate on the members of the receiver object. The common case for apply is the object configuration. Such calls can be read as “apply the following assignments to the object.”

"다음 멤버변수를 해당 객체에 적용하세요"

```kotlin
@kotlin.internal.InlineOnly
public inline fun <T> T.apply(block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block()
    return this
}
```

- 인스턴스 생성한 후 변수에 담기전에 초기화 과정을 사용할때 많이 쓰임
- apply 스코프 내에서 인스턴스의 참조 연산자 없이 사용 가능
- apply는 자기 자신 인스턴스를 반환
- main 함수와 별도의 스코프에서 인스턴스의 변수와 함수를 조작해서 코드가 깔끔해짐 .

```kotlin
fun main() {
		var a = Book("test", 1000).apply {
				name = "prefix"+name
				discount()
		}
}
```

## run

> The context object is available as a receiver (this). The return value is the lambda result.
run does the same as with but invokes as let - as an extension function of the context object.
run is useful when your lambda contains both the object initialization and the computation of the return value.

```kotlin
@kotlin.internal.InlineOnly
public inline fun <R> run(block: () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block()
}
```

- rum 스코프 안에서 참조연산자를 사용하지 않아도 된다는 점은 apply와  같음 일반 람다 함수처럼 마지막 줄에 결과값을 반환함.
- 인스턴스 생성후 인스턴스의 함수나 속성을 스코프 내에서 사용할때 유용

```kotlin
val service = MultiportService("https://example.kotlinlang.org", 80)

val result = service.run {
    port = 8080
    query(prepareRequest() + " to port $port")
}

// the same code written with let() function:
val letResult = service.let {
    it.port = 8080
    it.query(it.prepareRequest() + " to port ${it.port}")
}
```

## with

> A non-extension function: the context object is passed as an argument, but inside the lambda, it's available as a receiver (this). The return value is the lambda result.
We recommend with for calling functions on the context object without providing the lambda result. In the code, with can be read as “with this object, do the following.”

"이 객체를 가지고 다음을 수행하세요"

```kotlin
@kotlin.internal.InlineOnly
public inline fun <T, R> with(receiver: T, block: T.() -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return receiver.block()
}
```

run과 동일한 기능이지만 대신 인스턴스를 참조 연산자 대신 파라미터로 받는정도의 차이 

```kotlin
val numbers = mutableListOf("one", "two", "three")
with(numbers) {
    println("'with' is called with argument $this")
    println("It contains $size elements")
}

val numbers = mutableListOf("one", "two", "three")
val firstAndLast = with(numbers) {
    "The first element is ${first()}," +
    " the last element is ${last()}"
}
println(firstAndLast)
```

## also

> The context object is available as an argument (it). The return value is the object itself.
also is good for performing some actions that take the context object as an argument. Use also for actions that need a reference rather to the object than to its properties and functions, or when you don't want to shadow this reference from an outer scope.
When you see also in the code, you can read it as “and also do the following with the object.”

"그리고 또한 이 객체를 가지고 다음을 수행하세요."

```kotlin
@kotlin.internal.InlineOnly
@SinceKotlin("1.1")
public inline fun <T> T.also(block: (T) -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(this)
    return this
}
```

- 처리가 끝나면 자신 객체 반환
- 컨텍스트 객제를 인자로 사용할때 좋음.
- 속성이나 함수를 사용하기 보다 레퍼런스/객체를 사용하는게 좋다.
- apply run이 참조 연산자 없이 인스턴스의 변수와 함수를 사용할 수 있었다면 also, let은 파라미터로 넘긴것처럼 it을 통해서 인스턴스를 사용할 수 있음.
- 왜 패러미터로 인스턴스에 점근할까?
    - 이는 같은이름의 변수나 함수가 scope 바깥에 중복되어 있는 경우에 혼란은 방지하기 위함
    - run 함수가 인스턴스 내의 지역변수 보다 run이 속해 있는 main 함수의 변수를 우선하기 때문

```kotlin
//스코프 이슈 
var price = 5000

fun main() {
		var a = Book("test", 1000).apply {
				name = "prefix"+name
				discount()
		}
		var a.run {
			print("이름 $name, 가격 $price")
		}
	// 출력이 8000원이 아닌, 5000원 반환 
		a.let {
		print("이름 ${it.name}, 가격 ${it.price}")
	}
		// 8000원으로 출력 
}

```

```kotlin
val numbers = mutableListOf("one", "two", "three")
numbers
    .also { println("The list elements before adding new one: $it") }
    .add("four")
```

- apply 도 also로 대체하여 사용 가능

## let

> The context object is available as an argument (it). The return value is the lambda result.

```kotlin
@kotlin.internal.InlineOnly
public inline fun <T, R> T.let(block: (T) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(this)
}
```

- 처리가 끝나면 최종 값을 반환
- 하나또는 그 이상의 함수를 call chain으로 호출할 수 있음.
- 만약 호출 블락 내에서 딱 하나의 함수만 사용할거라면 method reference (::) 도 사용 가능

```kotlin
val numbers = mutableListOf("one", "two", "three", "four", "five")
val resultList = numbers.map { it.length }.filter { it > 3 }
println(resultList)

// 위의 코드를 let을 사용 하면 이렇게 
val numbers = mutableListOf("one", "two", "three", "four", "five")
numbers.map { it.length }.filter { it > 3 }.let { 
    println(it)
    // and more function calls if needed
}
```

- let은 코드 블럭이 오직 non-null 값일때에만 실행되도록 할때 사용되기도 함.

```kotlin
val str: String? = "Hello"   
//processNonNullString(str)       // compilation error: str can be null
val length = str?.let { 
    println("let() called on $it")        
    processNonNullString(it)      // OK: 'it' is not null inside '?.let { }'
    it.length
}
```

- 코드 가독성을 높이기 위해 제한텐 스코프내에서 지역 변수를 소개하는데 사용되기도 함.

## Here is a short guide for choosing scope functions depending on the intended purpose

- Non-null 객체인것만 수행해야할때는 → Executing a lambda on non-null objects: `let`
- 지역 스코프내에서 표현식을 변수로 소개?할때 → Introducing an expression as a variable in local scope: `let`
- 객체 설정시 → Object configuration: `apply`
- 객체 설정 후 결과를 수행할때 → Object configuration and computing the result: `run`
- 표현식이 필수인 곳에서 실행할때 → Running statements where an expression is required: non-extension `run`
- 추가 기능이 있을때 → Additional effects: `also`
- 객체을 가지고 그룹핑 함수?를 호출할때 → Grouping function calls on an object: `with`

## 참고

[https://kotlinlang.org/docs/reference/scope-functions.html](https://kotlinlang.org/docs/reference/scope-functions.html#apply)