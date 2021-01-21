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
