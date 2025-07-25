import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Randoms {
    public static List<Integer> pickUniqueNumbersInRange(int start, int end, int amount) {
        Random random = new Random();
        Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < amount) {
            numbers.add(random.nextInt(end) + start);
        }
        List<Integer> result = (new ArrayList<>(numbers));
        result.sort(Comparator.naturalOrder());
        return result;
    }
}
