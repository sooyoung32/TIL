## 다형성 (polymorphism)
프로그램 언어의 다형성(polymorphism; 폴리모피즘)은 그 프로그래밍 언어의 자료형 체계의 성질을 나타내는 것으로, 프로그램 언어의 각 요소들(상수, 변수, 식, 오브젝트, 함수, 메소드 등)이 다양한 자료형(type)에 속하는 것이 허가되는 성질을 가리킨다.

多形性 : 여러 형태의 성질을 가진다. 
poly+morphism : poly (여러개, 다수의) + morphism (형태를 가지다)


#### overloading은 다형성의 한 예다
Overloading은 같은 이름의 메소드를 여러 개 가지면서 매개변수의 유형과 개수가 다른 것. [참고 - Overriding 은 상위 클래스가 가지고 있는 메소드를 하위 클래스가 재정의 해서 사용하는 것.] 즉 하나의 메소드가 **여러 형태**를 가진것.

~~~java
class O{
    public void a(int param){
        System.out.println("숫자출력");
        System.out.println(param);
    }
    public void a(String param){
        System.out.println("문자출력");
        System.out.println(param);
    }
}
class PolymorphismOverloadingDemo {
    public static void main(String[] args) {
        O o = new O();
        o.a(1);
        o.a("one");
    }
}
~~~

### 클래스와 다형성
~~~java
class A {
    public String methodA() {return "A";}
}
class B extends A {
    public String methodB() {return "B";}
}
class PolimorphismTest {

    public static void main(String[] args) {
        // B는 A를 상속 받고 있기 때문에 B는 A를 데이터형으로 삼을수 있다. 의미는?
        A object = new B();
        // 두개의 메소드는 실행될까?
        object.methodA();
        object.methodB();
    }
}
~~~

* 두 메소드는 실행될까?
object.methodB(); 는 실행되지 않고 컴파일 에러난다.
근데 만약 B object = new B(); 를하면 둘다 실행된다.

* 이유는?
클래스 B의 데이터 형을 클래스 A로 하면 클래스 B는 **마치 클래스 A인것처럼 동작**하게 되는 것이다. 클래스 B를 사용하는 입장에서는 클래스 B를 클래스 A인것처럼 사용하면 된다.


>  흠.. 아직 그래도 감이 안온다. 코드를 더 보자.



~~~java
class A {
    //수정됨
    public String methodA() {return "classA-A";}
}

class B extends A {
    //class A에서 overriding한 methodA를 재정의
    public String methodA(){return "classB-A";}
    public String methodB() {return "B";}
}

class PolimorphismTest {

    public static void main(String[] args) {
        A object = new B();
        // 뭐가 출력될까?
        System.out.println(object.methodA());
    }
}
~~~



* 출력은 "classB-A"다.

	1. 클래스 B의 데이터 타입을 클래스 A로 인스턴스화 했을 때 클래스 B의 메소드 y는 마치 존재하지 않는 것처럼 실행되지 않았다. => **클래스 B가 클래스 A화 되었다.**
	2. 클래스 B의 데이터 타입을 클래스 A로해서 인스턴스화 했을 때 클래스 B의 메소드 x를 실행하면 클래스 A에서 정의된 메소드가 아니라 클래스 B에서 정의된 메소드가 실행 되었다. => **클래스 B의 기본적인 성질은 그대로 간직하고 있다.**


==클래스 B를 클래스 A의 데이터 타입으로 인스턴스화 했을 때 클래스 A에 존재하는 맴버만이 클래스 B의 맴버가 된다.(1) 동시에 클래스 B에서 오버라이딩한 맴버의 동작방식은 그대로 유지한다.(2)==


~~~java
class A {
    //수정됨
    public String methodA() {return "classA-A";}
}

class B extends A {
    //class A에서 overriding한 methodA를 재정의
    public String methodA(){return "classB-A";}
    public String methodB() {return "B";}
}

// 추가
class B2 extends A{
    public String x(){return "classB2-A";}
}

class PolimorphismTest {

    public static void main(String[] args) {
        //서로 다른 클래스 B와 B2가 동일한 데이터 타입 A로 인스턴스화 되었다.
        A object = new B();
        A object2 = new B2();

        System.out.println(object.methodA());
        System.out.println(object2.methodA());
    }
}
~~~
서로 다른 클래스 B와 B2가 동일한 데이터 타입 A로 인스턴스화 되었지만 같은 method를 출력하는 결과는 다르다.

* 확실하게 예제를 하나 더보자.


~~~java
// 추상클래스 : 추상메소드가 하나라도 있는 클래스!!
abstract class Calculator{
    int left, right;
    public void setOprands(int left, int right){
        this.left = left;
        this.right = right;
    }
    int _sum() {
        return this.left + this.right;
    }
    public abstract void sum();
    public abstract void avg();
    public void run(){
        sum();
        avg();
    }
}
class CalculatorPlus extends Calculator {
    public void sum(){
        System.out.println("CalculatorPlus + sum :"+_sum());
    }
    public void avg(){
        System.out.println("CalculatorPlus + avg :"+(this.left+this.right)/2);
    }
}
class CalculatorMinus extends Calculator {
    public void sum(){
        System.out.println("CalculatorMinus - sum :"+_sum());
    }
    public void avg(){
        System.out.println("CalculatorMinus - avg :"+(this.left+this.right)/2);
    }
}
class CalculatorDemo {
    public static void execute(Calculator cal){
        System.out.println("실행결과");
        cal.run();
    }
    public static void main(String[] args) {
        Calculator c1 = new CalculatorPlus();
        c1.setOprands(10, 20);

        Calculator c2 = new CalculatorMinus();
        c2.setOprands(10, 20);

        execute(c1);
        execute(c2);
    }
}
~~~
클래스 CalculatorDemo의 execute 메소드는 CalculatorPlus와 CalculatorMinus 클래스의 공통 메소드 run을 호출하면서 그것이 '실행결과'라는 사실을 화면에 표시하는 기능을 가지고 있다. 이 때 메소드 execute 내부에서는 매개변수로 전달된 객체의 메소드 run을 호출하고 있다.

만약 Calculator를 인자로 받는게 아니면 CalculatorPlus와 CalculatorMinus의 execute 메소드를 각각 오버로딩해서 만들어야 한다. 이는 중복이어서 좋은 코드가 아니다. 

==이 맥락에서의 다형성이란 하나의 클래스(Calculator)가 다양한 동작 방법(ClaculatorPlus, ClaculatorMinus)을 가지고 있는데 이것을 다형성이라고 할 수 있겠다. ==


### 인터페이스와 다형성
다형성은 인터페이스와도 많이 사용된다. 예제를 보자

~~~java
interface I {
    public String A();
}
interface I2 {
    public String B();
}
class C implements I, I2 {
    public String A(){
        return "A";
    }
    public String B(){
        return "B";
    }
} 
class PolymorphismTest {
    public static void main(String[] args) {
        C obj = new C();
        I objI2 = new C();
        I2 objI3 = new C();
        
        //C 가 가진 모든 기능을 사용하고 싶을때.
        obj.A();
        obj.B();

        // C가 가진 기능중 I의 기능만 사용하고 싶을때
        objI2.A();
        //objI2.B();

        // C가 가진 기증중 I2의 기능만 사용하고 싶을때
        //objI3.A();
        objI3.B();
    }
}
~~~

* 하나의 기능으로 여러 형태를 보여주고 싶을때. 

~~~java
interface 후식 {
    String eat();
}

class 아이스크림 implements 후식 {

    @Override
    public String eat() {
        return "붕어싸만코 먹기";
    }
}

class 우유 implements 후식 {
    @Override
    public String eat() {
        return "커피우유 먹기";
    }
}

class 과자 implements 후식 {

    @Override
    public String eat() {
        return "쌀과자";
    }
}

class Test {
    public static void execute(후식 후식) {
        System.out.println(후식.eat());
    }

    public static void main(String[] args) {
        후식 후식1 = new 아이스크림();
        execute(후식1);
        후식 후식2 = new 우유();
        execute(후식2);
        후식 후식3 = new 과자();
        execute(후식2);
    }

}
~~~

