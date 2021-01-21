# RxJava 의 기본
[RxJava리액티브 프로그래밍](https://book.naver.com/bookdb/book_detail.nhn?bid=14689555) 책

## RxJava와 리액티브 프로그래밍

- RxJava는 리액티브 프로그래밍을 구현하는 데 사용하는 라이브러리
- 이벤트 처리와 비동기처리에 최적화
- 2.0 부터는 Reative Strems 사양을 구현
    - Reative Strems는 어떤 라이브러리나 프레임워크도 데이터 스트림을 비동기로 처리하는 공통 메커니즘을 인터페이스로 제공

### 리액티프 프로그래밍이란

- 데이터가 통지될때마다 관련 프로그램이 반응(reaction)해 데이터를 처리하는 프로그래밍 방식
- 프로그램이 필요한 데이터를 가져와 처리하느 ㄴ것이 아니라, 보내온 데이터를 받은 시점에 반응해 이를 처리하는 프로그램을 만드는것이 리액티브 프로그래밍

### RxJava의 개요

- 에릭마이어가 개발한 .Net 프래임워크의 실험적인 라이브러리인 Reactive Extention (Rx) 를 2009년 마이크로소프트에서 공개하고, 2013년 넷플릭스가 자바로 이식한것이 Rx자바의 시작
- Reativce Extension 의 다양한 라이브러리 등장 → 구현체가 재각각
- 데이터 스트림을 비동기로 다루는 최소한의 API를 정함  → Rxjava 탄생
- 1.x은 Reative Stream를 지원하지 않음, Reactive Stream를 지원하는 2.0버전은 기본 구조를 유지하면서 내부를 새로 구현해 성능 개선함 (2.0에서 배압 등장)

## RxJava의 특징

- RxJava는 디자인 패턴인 옵저버패턴의 확장
    - 옵저버패턴은 감시 대상 객체의 상태가 변하면 이를 관찰하는 객체제 알려주는 구조
    - 데이터 생성자와 데이터 소비자를 나눌 수 있어 쉽게 데이터 스트림을 처리
- RxJava는 쉬운 비동기 처리
- RxJava는 함수형 프로그래밍의 영향 → 대부분 함수형 인터페이스 인자로 전달받는 메서드를 사용해 처리 구현

## Reative Streams

- Reative Streams란 라이브러리나 프레임워크에 상관없이 데이터 스트림을 비동기로 다룰 수 있는 공통 매커니즘으로 이 매커니즘을 편리하게 사용할 수 있는 인터페이스 제공
- 데이터를 만들어 통지하는 생산자(Publisher)와 데이터를 받아 처리하는 소비자(Subscriber)로 구성
- 데이터의 흐름
    - 생산자는 통지 준비가 끝나면 소비자에게 통지 (onSubScripbe)
    - 통지를 받은 소비자는 받고자하는 데이터 개수를 요청 , 요청하지 않으면 생상자는 통지해야할 데이터 개수 요청을 기다리게 됨으로 통지를 시작하지 않음
    - 생상자는 데이터를 생산해 소비자에게 통지 (onNext)
    - 데이터를 받은 소비자는 받은 데이터를 처리
    - 생상자는 요청받은 만큼 데이터를 통지 후 소비자가 다음 요청이 올때까지 통지 중단
    - 이후 소비자가 처리작업을 완료하면 다음에 받을 데이터 개수를 생산자에게 요청
    - 생산자는 소비자가 모든 데이터를 통지하고 마지막으로 데이터 전송이 완료되면 종료되었다고 통지 (onComplete)
    - 생상자는 처리 도중에 에러가 발생하면 에러 통지 (onError)
- 규칙
    - 구독 시작 통지(onSubscribe)는 해당 구독에서 한 번만 발생
    - 통지는 순차적으로 발생
    - null은 통지 하지 않음
    - 생산자의 처리는 완료 또는 에러를 통지해 종료한다.

### RxJava의  기본 구조

- 생산자와 소비자
    - Reative Streams를 지원하는 Flowable 과 Subscriber
    - Reative Streams를 지원하지 않고 배압기능이 없는 Observable과 Observer
        - 배압기능이 없어 데이터 개수를 요청하지 않음
        - Subscription을 사용하지 않고 Disposiable이라는 구독해지 메서드를 인터페이스로 사용

### 비동기 처리

- RxJava에서는 개발자가 직접 스레드를 관리하지 않게 각 처리 목적에 맞춰 스레드를 관리하는 스케줄러(scheduler)제공
- 스케줄러는 데이터를 생성해 통지하는 부분, 데이터를 받아 처리하는 부분에 지정가능
- 비동기 처리시 스레드 안전하도록 주의

### Cold 생산자, Hot 생산자

- Cold
    - 1개의 소비자와 구독
- Hot
    - N개의 소비자와 구독
- RxJava는 기본 Cold 생산자
- 관련 클래스
    - ConnectableFlowable / ConnectableObservable

## 마블다이어그램

- 리액티브프로그래밍에서 시간 경과에 따라 데이터가 전달되고 변화되는지를 표현
- 주로 Flowable Observable에서 메서드를 호출할때, 시간의 경과에 따라 데이터가 어떻게 변화하는지를 설명


## 예제

### Flowable

```java
## 예제

```java
package example.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class FlowableTest {
  public static void main(String[] args) throws InterruptedException {

    Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() { // 통지 시작, FlowableEmitter는 통지 메서드의 내부에서 구독 해지 여부 확인. 
      @Override
      public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Exception { // 에러를 던지는 것은, 처리중에 예외가 발생하면,  Subscriber에 에러를 통지하기 위해 구현됨.
        String[] data = {"Hello, World", "안녕 rxjava"};
        for (String item : data) {

          if (emitter.isCancelled()) { // 구독 해지시 통지 중단
            return;
          }
          emitter.onNext(item); // 데이터를 전달해 Subscriber에게 통지
        }
        emitter.onComplete(); // 데이터 완료 통지
      }
    }, BackpressureStrategy.BUFFER); //초과한 데이터는 버퍼링

    flowable.observeOn(Schedulers.computation())
            .subscribe(new Subscriber<>() {
              private Subscription subscription;

              @Override
              public void onSubscribe(Subscription s) { //Flowable이 구독되고 데이터 통지가 준비됐을때 호출하느 ㄴ메서드. 
                this.subscription = s;
                this.subscription.request(1L);
              }

              @Override
              public void onNext(String s) { // 통지한 데이터를 받음 
                String thread = Thread.currentThread().getName();
                System.out.println("thread = " + thread +"data  = " + s);
                this.subscription.request(1L);
              }

              @Override
              public void onError(Throwable t) { //에러가 발생하거나 에러 통지를 할때 실행되는 메서드 
                t.printStackTrace();
              }

              @Override
              public void onComplete() { // 통지를 끝내고 처리가 완료됐을때 실행되는 메서드 
                String thread = Thread.currentThread().getName();
                System.out.println("thread = " + thread + " 완료");
              }
            });

    Thread.sleep(1000L);
  }

}

```
```

- Subscriber는 자신이 처리할 수 있는 속도로 데이터를 받게 Flowable이 통지하는 데이터 개수를 제한 할 수 있음 → 배압!
- 다만 요청 데이터를 처리하는 도중에 문제가 발생하면 데이터 개수를 요청하지 못하는 문제 발생 → Subscriber가 데이터를 받을 수 있는 상황이라도, Flowable 이 데이터 요청을 대기하는 상태가 지속되 처리가 멈출 가능성도 존재
    - 데이터 개수가 많지 않으면 통지 데이터 개수를 제한할 필요가 없을때도 많음.
    - 이때는 onSubscribe.request 메서드에 Long.MAX_VALUE를 설정하면 생산자는 데이터 개수의 제한 없이 전송 가능

## Observable

```java
package example.rxjava;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ObservableTest {
    public static void main(String[] args) throws InterruptedException {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                String[] data = {"test", "test2"};
                for (String item :  data) {
                    if (emitter.isDisposed()) {  //disposed 됨.
                        return;
                    }
                    emitter.onNext(item);
                }
                emitter.onComplete();
            }
        }); // 배압이 없음

        observable.observeOn(Schedulers.computation())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //do nothing
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        System.out.println("thread = "+ Thread.currentThread().getName() + " data = "+ s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("thread = "+ Thread.currentThread().getName() + " 완료 ");
                    }
                });

        Thread.sleep(1000L);
    }
}
```

### Flowable vs Observable

Flowable

- 대량 데이터 처리 시 사용
- 네트워크 통신이나 데이터베이스의 I/O 처리

Observable

- GUI 이벤트
- 소량 데이터시 사용
- 데이터 처리가 기본으로 동기방식이며 자바 표준의 Stream을 대신하여 사용할때

## RxJava의 전체구성

- 소비자 (Subscriber, Observer)가 생산자 (Flowable, Observable)을 구독하는 형태, 생산자와 소비자 사이에서 공유되는 Subsciption과 Disposable
- 생산자와 소비자의 관계는 Reactive Streams 사양을 지원하는 Flowable Subscriber 와,  지원하지 않는 Observable, Observer 로 구성
- Flowable은 배압 존재, Observable은 배압 없음

### 통지시 규칙

- null 은 안됨
- 데이터 통지는 해도 되고 안해도 된다.
- 처리를 끝낼때는 완료나 에러 통지를 해야한다며 둘 다 하지 않는다
- 통지할 때는 1건씩 순차적으로 통지하며 동시에 하지 않는다.
- 여러스레드에서 데이터 생성하고 통지할때는 동기화를 한 뒤에 데이터를 통지 메서드에 구현
  - 스레드를 관리해야함으로 1개의 스레드에서 처리하는 생산자를 여러개 준비하고 이들을 하나로 결합하는 메서드로 처리하는것이 안전

- Subscriber/Observer
  - 통지데이터를 전달 받아 데이터를 처리하는 인터페이스
- Subsciption
  - 통지 데이터 개수를 요청하는 request와 처리 도중에 구독 해지하는 cancel 메서드 포함
- Disposable
  - 구독을 해지하는 메서드를 포함한 인터페이스
- FlowableProcessor /  Subject
  - 생산자와 소비자의 기능이 모두 있는 인터페이스
- DisposableSubscriber or DisposableObserver
  - 외부에서 비동기로 구독해지 메서드를 호출해도 안전하게 구독해지 하게 해줌
- ResourceSubscriber or ResourceObserver
  - 외부에서 비동기로 구독해지 메서드를 호출해도 안전하게 구독해지 하게 해줌
- Subscribe or subscribeWith
  - 소비자가 생상자를 구독하는 메서드 생상자가 데이터를 통지할 소비자를 등록
- CompositeDisposable
  - 가지고 있는 모든 disposable 을 호출 할 수 있는 클래스

### Single , Maybe, Completable

- Single : 데이터를 1건만 통지하거나 에러를 통지하는 클래스
- Maybe : 데이터를 1건만 통지하거나 1건도 통지하지 않고 완료를 통지하거나 에러를 통지
- Completable : 데이터를 1건도 통지하지 않고 완료나 에러를 통지