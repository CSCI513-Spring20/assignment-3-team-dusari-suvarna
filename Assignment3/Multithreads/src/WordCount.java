import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* This class has two methods one will filter the words and other will fill the wordCounts
 * this method is synchronized as the count value should be incremented for the respective
 * word only
 */
public class WordCount {

	public String[] filteredList(String[] words) {
		List<String> filteredList = new ArrayList<>();
		for (String word : words) {
			if (word.matches("[a-zA-Z]+")) {
				filteredList.add(word);
			}
		}
		return filteredList.toArray(new String[filteredList.size()]);
	}

	public synchronized void fillingWordCounter(Map<String, Integer> wordCounts, String word, int count) {
		if (wordCounts.containsKey(word)) {
			wordCounts.put(word, wordCounts.get(word) + 1);
		} else {
			wordCounts.put(word, 1);
		}
		if (count == 0) {
			System.out.printf("Current Thread Running: %s\n", Thread.currentThread().getName());
			System.out.printf("%-9s %13s %n", "WORD", "COUNT");
		}
		System.out.printf("%-15s", word);
		System.out.printf("%5d\n", wordCounts.get(word));
	}
}
