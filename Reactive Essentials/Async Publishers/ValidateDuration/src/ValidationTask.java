import java.time.Duration;

import reactor.core.publisher.Mono;

public class ValidationTask {

	public static Mono<Void> validate(Duration duration) {
		if (duration.isNegative() || duration.isZero()){
			return Mono.error(new IllegalArgumentException("Duration can`t be negative or zero"));
		}else{
			return Mono.empty();
		}
	}
}