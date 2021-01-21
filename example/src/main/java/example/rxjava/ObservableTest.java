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
