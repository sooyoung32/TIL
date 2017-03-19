## 실행 컨텍스트와 클로져 

* 실행 컨텍스트 Execution Context 개념
* 활성 객체 Activation Object 와 변수 객체 Variable Object
* 스코프 체인 Scope Chain
* 클로저 Closure

### Execution Context. 실행 컨텍스트
A wrapper to help manage the cod that is running. It contain things beyound what you'v be written in your code.

###### ECMAScript  실행컨텍스트 정의
실행 가능한 코드를 형상화하고 구분하는 추상적이 개념, 즉 실행 가능한 자바스크립트 코드 블록이 실행되는 환경. 

###### 실행컨텍스트가 형성 되는 경우
* 전역 코드 global
* eval() 함수로 실행되는 함수
* 함수 안의 코드를 실행할 경우

실행 컨텍스트는 stack 구조를 가지고 실행 된다. 

```javascript
console.log("글로벌");

function exContext1() { console.log("실행 컨텍스트 1") }

function exContext2() { 
	exContext1(); 
	console.log("실행 컨텍스트 2") 
}

exContext2();
```

![실행컨텍스트](http://cfile24.uf.tistory.com/image/254D833A56AB10C51C4CB5)

1. 실행 컨텍스트 스택에, 전역 실행 컨텍스트가 생성된다. (console.log("글로벌"); 이 실행됨)
2. exContext2() 함수가 실행되면서 새로운 실행 컨텍스트 생성! 
3. 그 안에서 새로 exContext1() 함수를 호출하면서 또 새로운 실행 컨텍스트가 실행되어, exContext1() 실행을 종료하면 실행컨텍스트도 반환되고,
4. exContext2() 도 실행 종료후 반환한다~

#### 실행 컨텍스트 생성 과정?! (궁금!)

다음 예제를 보면 실행 컨텍스트가 어떻게 만들어질지 생각해보자!

```javascript
function execute(param1, param2) {
	var a = 1, b = 2;
	function func() {
		return a+b;
	
    }
	return param1 + param2 + func();
}

execute(3.4);

```
# ?!

![실행컨텍스트 생성 과정](http://cfile27.uf.tistory.com/image/25154D33557EBDE03A0E54)


##### 1. 활성 객체 (= 변수객체) 생성
살행 컨텍스트가 생성되면 자바스크립트 엔진은 해당 컨텍스트에서 실행에 필요한 여러가지 정보를 담을 객체를 만드는데, 이를 ***-활성객체-*** 라고 한다. 

활성객체에 앞으로 사용할 변수, 사용자가 정의한 변수, 객체를 저장하고  새로 만들어진 컨텍스트로 접근가능하다. (근데 엔진 내부에서 접근할수 있는거지 사용자가 접근할수 있는건 아님....)

자. 실행컨텍스트가 생성되고, 그안에 여러 정보를 담은 활성객체 = 변수객체가 만들어졌다!


##### 2. arguments 객체 생성
그 다음 arguments 객체를 생성해준다. (오.. 뭔가 우리가 나름 사용하는 객체 등장. 실행컨텍스트가 만들어주는 거였음.) 

2.번 그립은 execute() 함수에 param1, param2가 매개변수로 들어왔을 경우를 표현한다. 

##### 스코프 정보 생성
그 다음 현재 컨텍스트의 유효범위를 나타네는 스코프 정보를 생성한다. 이 스코프 정보는 실행컨텍스트 내부에서 [연결리스트](https://ko.wikipedia.org/wiki/%EC%97%B0%EA%B2%B0_%EB%A6%AC%EC%8A%A4%ED%8A%B8)와 유사한 형식으로 만들어진다.

현재 컨텍스트에서 특정 변수에 접근할경우 이 리스트를 활용한다. 이리스트로 현재 실행 실행 컨텍스트 뿐 아니라, 상위 실행 컨텍스트 변수도 접근이 가능하다 (이것이 클로져어어어!?) 

만약 리스트에서 찾지 못한 변수는 결국 정의 되지 않은 변수로 판단되어 에러가 난다. 

```javascipt
Uncaught ReferenceError: test is not defined
```

이 리스트를 ***==스코프체인==*** 이라고 하고 [[scope]] 프로퍼티로 참조된다. 

이 리스트가 왜 리스트라는 자료구조 형대를 띠며, 이 체인이 어떻게 형성되며 리스트의 구성요소가 무엇인지 (~~완전 궁금!~~) 좀 이따 보자. 

지금은 현재 생성된 활성 객체가 스코프 체인의 제일 앞에 추가되며, execute() 함수의 인자나 지역 변수에도 접근 가능하다는 사실만 알고 넘어가자!

#####  3. 변수 생성
그 다음 현재 실행컨텍스트 내에서 사용되는 지역 변수가 생성된다. 생성되는 변수를 변수객체(활성객체)에 저장한다. 변수객체(활성객체)안에서 호출된 함수 인자는 각가 프로퍼치가 만
들어지고 값이 할당 횐다.

==주의!== 
이 과정은 변수나 내부 함수를 단지 메모리에 생성 (instantiation)만한다! 초기화 (initialization)는 각 변수나 함수에 해당하는 표현식이 실행되기 전까지 이뤄지지 않는다! 따라서 변수 a, b에는 undefined가 할당되고 ㅠㅛ현식의 실행은 변수 객체 생성이 다 이뤄진 후 시작된다. 

###### 참고
ECMAScript 에선 생성 되는 변수를 변수 객체라고 설명하며 언급한느데 실제로 활성객체가 변수객체로 사용된다. 즉 활성객체와 변수객체는 같다~! 여기선 변수객체로 통일!


##### 4. this 바인딩
마지막으로 this 키워드를 사용하는 값이 할당된다. 여기서는 this가 참조하는 객체가 없으면 전역객체를 참조한다.

##### 5. 코드 실행
* 실행컨텍스트 생성
* 활성 객체 생성
* arguemnt 객체 생성
* scope 체인 생성
* 변수 할당
* this 바인딩

과정을 통해 실행 컨텍스트가 생성되고 활성객체가 만들어진 후에 코드에 있는 여러가지 표현식 실행이 이뤄진다.

###### 참고! 전역 실행 컨텍스트와 실행 컨텍스트와이 차이
전역 실행 컨텍스트는 ==**arguments** 객체가 없고 전역객체 하나만을 포함하는 **스코프 체인**==이 있다. 전역 컨텍스트는 전역 코드가  실행될 떄 생성되는 실행 컨텍스트로 이는 변수를 초기화하고 이것의 내부함수는 탑 레벨 함수로 선언된다. (전역 함수가 된다는 말인듯) 또한 ==전역 실행 컨텍스트늬 변수객체가 전역 객체==다.  따라서 ==전역으로 선언된 함수와 변수가 전역 객체의 프로퍼티가 된다==.  전역 실행 컨텍스트 역시 ==this를 전역 객체의 참조==로 사용된다.(전역실행 컨텍스트 내 활성/변수 객체가 전역 객체고, 여기에 선언된 함수도 전역함수고~ 다 전역을 참조하넹)

##### 참고 링크

* [execution context에 대하여](http://alnova2.tistory.com/967)


### 스코프 체인
자. 그럼 아까 궁금했던 ==스코프체인이 어떻게 만들어지는지==를 알아보자.
스코프 체인을 알아야 변수에 대한 인식 identifier resolution 매카니즘을 알 수 있고, 현재 사용되는 변수가 어디에서 선언된 변수인지 정확히 할 수 있기 때문이다!


```java

// 여기서 잠깐 자바퀴즈. 접근제어자 안쓰면 뭘까?
// 그리고 그 접근제어자의 범위는?
void exeampleScope() {
	int i = 0;
    int value = 1;
    for (i = 0; i < 10 : i++) {
    	int a = 10;
    }
    
    System.out.println("a : " + a); // 컴파일 에러
    
    if (i == 10) {
    	int b = 20;
    }
    
    System.out.println("b : "+ b) // 컴파일 에러
    System.out.ptintln("value : "+ value) // 1
	
}

```

당연한?말이지만 java, c 등과 같은 프로그래밍 언어에서는 변수 스코프가 함수의 {} 범위 뿐 아니라 if, for {} 에서도 한 블록으로 붂여 그 안에서 선언된 변수가 밖에서 접근이 불가능 하다. 

자바스크립트는 오직 함수만이 유효범위의 단위가 된다 (for, if x). ==이 유효범위를 나타내느 스코프가 [[Scope]] 프로퍼티로 각 함수 객체 내에서 연결 리스트 형식으로 관리되는데, 이를 스코프 체인이라 한다.==

각 함수는 [[scope]] 프로퍼티로 자신이 생성된 실행 컨텍스트의 스코프 체인을 참조한다. 


예제를 보자.

```javascript

var value = 'value1';

function printFunc() {
	var value = 'value2';
    
    function printValue() {
    	return value;
    }
    
    console.log(printValue());
    
}

printFunc();

```



![스코프체인](http://cfile22.uf.tistory.com/image/25710745557EBDE6320C46)


* 각 함수 객체는 [[scope]] 프로퍼티로 현재 실행 컨텍스트의 스코프 체인을 참조한다.


* 함 함수가 실행되면 새로운 실행 컨텍스트가 만들어지고, 이 실행 컨텍스트는 현재 실행되는 함수 객체의 [[scope]] 프로퍼티를 복사하고 새로 생성된 변수 객체를 해당 체인의 제일 앞에 추가하면서 스코프 체인을 만든다. 
(쉽게 말해서 이전에 실행 컨테스트의 스코프 체인을 실행된 함수 객체의 실행컨텍스트의 스코프 체인 맨 앞에 추가!!)

* 즉 스코프 체인 = 현재 실행 컨텍스트이 변수 객체 + 상위 컨텍스트의 스코프 체인


다른 예제를 보자.

```javascript

var value = 'value';

function printValue() {
	return value;
}

function printFunc(func) {
	var value = 'value2';
    console.log(func());
}

printFunc(printValue);

```

코드를 잘보자. 출력값은? value1 ? value2?



![스코프 체인 예제](http://cfile24.uf.tistory.com/image/240FDC40557EBDE902551C)


이 예제는 각 함수 객체가 처음 생성될 당시 실행 컨텍스트가 무엇인지 생각해야한다.

각 함수 객체가 처음 생성될때 [[scope]] 는 전역 객체의 [[scope]] 를 참조한다. 

따라서 각 함수가 실행될 때 생성되는 실행 컨텍스트의 스코프 체인은 전역 객체와 그 앞에 새롬게 만들어진 변수 객체가 추가 된다.

* 이러한 스코프 체인으로 식별자 인식 Identifier resolution이 이뤄진다. 식별자 인식은 스코프 체인의 첫 변째 변수 객체부터 시작해, 식별자와 대응되는 이름을 가진 프로퍼티가 있는지 확인한다. 
* 즉 스코프체인의 가장 앞에 있는 변수 객체이기 때문에 이 객체에 있는 인자, 함수, 변수에 대응되는지 확인하고, 없다면 다음 객체로 이동하여 찾는다. 
* 여기서 this는 식별자가 아닌 키워드로 분류 되어 스코프 체인의 참조 없이 접근가능한다.

###### with 함수
자바스크립트 스코프체인을 사용자가 임의로 수정하는 키워드. 성능 최적화에 with는 evil 이라는! 

```javascript

var y = {x:5};

function withExamFunc() {
	var x = 10;
    var z;
    
    with(y) {
    	z = function () {
        	console.log(x); 출력값 5 
        }
    }
    
    z();
}
withExamFunc();

```

with 구문의 실행으로 전역 변수 Y 에 의해 참조되는 객체를 함수 표현식이 실행되는 동안 스코프체인의 맨 앞에 추가함.


* 책 154 그림 참조


##### 호이스팅의 이유


```javascript

foo();
bar();

var foo = function() {
	console.log("foo and x = "+ x);
}

function bar () {
	console.log("bar and x = "+ x);
}

var x = 1;

```

위의 코드는 함수의 실행 과정을 통해 보면 아래 와 같다.


```javascript

var foo;

function bar () {
	console.log("bar and x = "+x);
}

var x;

foo(); // TypeError
bar(); 

foo = function () {
	console.log("foo and x = " + x);
};

x = 1;

```


* 변수 foo, 함수, 변수 x 를 생성
* 이떄 foo와 x는 생성만되고 값 할당이 되어있지 않다. 따라서 undefind 가 할당됨.

* 행이 시작 되면서 foo(), bar()를 호출하고, foo에 함수 객체가 할당되며, 변수 x = 1 이 할당 된다.

* 결국 이때 foo는 함수가 아니기 때문에 에러를 낸다.


즉 호이스팅은 자바스크립트 함수 생성 순서 때문!



### 클로저 


예제를 보자.

```javascript

function outerFunc() {
	var x = 10;
    var innerFunc = function () {console.log (x);}
    
    return  innerFunc;
}

var inner - outerFunc();
inner(); //10

```
![클로저](http://cfile24.uf.tistory.com/image/240FDC40557EBDE902551C)

설명을 하자면 innerFunc()의 [[scope]]는 outerFunc 변수객체와 전역 객체를 가진다. 근데 신기한 점은 innerFunc()은 outerFunc()실행이 끝난 후에 실행된다.그렇다면!! outerFunc()의 실행 컨텍스트가 이미 사라진 후에 innserFunc 의 실행 컨텍스트가 생성되는 것인데 어떻게 innserFunc()의 스코프 체인은 outerFunc의 변수 객체를 여전히 참조할수 있는 것일까? 

이것이 자바 스크립트에서 구현한 클로저라는 기능 때문!

위의 코드에서는 최종 반환되는 함수가 외부함수의 지역변수에 접근하고 있다. 즉 함수가 종료되어 외부 함수의 실행 컨텍스트가 반환되더라도 변수 객체는 내부함수의 스코프체인에 그대로 남아있다는 말이다. 이것이 클로저다!!

정리하자면
==**이미 생명 주기가 끝난 외부함수의 변수를 참조하는 함수를 클로저라고 한다.**==

앞 예제 에선 outerFunc에서 선언된 x를 참조하는 innerFunc가 클로저가 된다. 그리고 클로저로 참조되는 외부변수, outerFunc을 자유변수라고한다. 

> **참고**
클로저는 자바스크립트에만 있는 개면이 아니고 여러 언어에서 차용하고 있다. 특히 함수를 일급객체로 취급하는 언어 즉 함수형 언어에서 주요하게 사용되는 특성이다.




```javascript
function outerFunc(arg1, arg1) {
	var local = 8;
    function innserFunc(innserArg) {
    	console.log((arg1, arg2)/(innserArg+ local));
    }
    return innserFunc();
}

var exam1 = outerFUnc(2,4);
exam1(2);

```

글로 다시한번 정리해보자

* outerFunc()이 실행되면서 생성되는 변수 객체가 스코프 체인에 들어가게 되고, 이 스코프 체인으 innerFunc의 스코프체인으로 참조된다.

* outerFunc()은 종료되었고, outerFunc()의 실행 컨텍스트도 반환되었지만 여전히 내부 함수 (innerFunc())의 [[scope]] 에서 참조되므로 가비지 컬렉션의 대상이 되지 않고, 여전히 접근 가능하다. 

* 따라서 이후에 exeam1(n) 을 호출해도 , innserFunc() 에서 참조하고자 하는 변수 local에 접근할수 있다. 


#### 클로저의 활용
자, 그럼 클로저가 어떻게 활용되는지 보자! 7장 함수형 프로그래밍에서 소개할 대부분의 예제가 클로저를 활용한것!

```javascript

function HelloFunc(func) {
	this.greeting = "hello";
}

HelloFunc.prototype.call = function (func) {
	func ? func(this.greeting) : this.func(this.greeting);
}

var userFunc = function(greeting) {
	console.log(greeting);
}

var objHello = new HelloFunc();
objHello.func = userFunc;
objHello.call();

```


