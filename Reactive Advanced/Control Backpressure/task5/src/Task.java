import java.util.concurrent.CountDownLatch;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

public class Task {

	public static void dynamicDemand(Flux<String> source, CountDownLatch countDownOnComplete) {

		source.subscribe(new BaseSubscriber<String>() {

			long rqstd = 1;
			long cnt = 0;
			@Override
			protected void hookOnSubscribe(Subscription subscription) {
				request(rqstd);
			}

			@Override
			protected void hookOnNext(String value) {
				cnt++;
				if (cnt == rqstd) {
					cnt = 0;
					rqstd *= 2;
					request(rqstd);
				}
			}
			@Override
			protected void hookFinally(SignalType type){
				countDownOnComplete.countDown();
			}
		});
	}
}