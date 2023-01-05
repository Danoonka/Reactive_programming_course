import java.util.stream.Collectors;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

public class Task {

	public static Publisher<Tuple2<Character, Integer>> groupWordsByFirstLatter(Flux<String> words) {
		return words
				.transform(Task::groupByFirstLetter)
				.transform(Task::countLettersInWordsInGroup);
	}

	public static Flux<GroupedFlux<Character, String>> groupByFirstLetter(Flux<String> words) {
		return words.groupBy(elem -> elem.charAt(0));
	}

	public static Flux<Tuple2<Character, Integer>> countLettersInWordsInGroup(Flux<GroupedFlux<Character,
			String>> groupedWords) {
		return groupedWords.flatMap(w-> w.map(elem ->
						{
							int counter = 0;
							for (char c: elem.toCharArray()) {
								if (c == w.key()) {
									counter += 1;
								}
							}
							return counter;
						}).collect(Collectors.summingInt(Integer::intValue)).map(counter -> Tuples.of(w.key(), counter))
				);
	}
}