import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Taxicab_number">Taxicab
 *      Number </a>
 * @author vivek
 *
 */
public class TaxicabNumber {
	static class Pair<L, R> {

		private final L left;
		private final R right;

		public Pair(L left, R right) {
			this.left = left;
			this.right = right;
		}

		public L getLeft() {
			return left;
		}

		public R getRight() {
			return right;
		}

		@Override
		public int hashCode() {
			return left.hashCode() ^ right.hashCode();
		}

		@Override
		public String toString() {
			return "Pair(" + left + "," + right + ")";
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Pair<?, ?>))
				return false;
			else {
				Pair<?, ?> pairo = (Pair<?, ?>) o;
				return this.left.equals(pairo.getLeft()) && this.right.equals(pairo.getRight());
			}

		}
	}

	public static void main(String[] args) throws Exception {
		// Store the sum of cubes in the priority queue
		PriorityQueue<Long> sumOfCubes = new PriorityQueue<>();

		// Store the numbers that add up to the cube.
		Map<Long, Set<Pair<Long, Long>>> pairsForNumber = new HashMap<>();

		Pair<Long, Long> entry = new Pair<>(1L, 2L);
		tryAddingPair(entry, sumOfCubes, pairsForNumber);

		int N = 2000;
		int found = 0;
		while (found < N) {
			long nextCandidate = sumOfCubes.poll();
			Set<Pair<Long, Long>> pairSet = pairsForNumber.get(nextCandidate);
			for (Pair<Long, Long> pair : pairSet) {
				if (pair.left + 1 != pair.right)
					tryAddingPair(new Pair<>(pair.left + 1, pair.right), sumOfCubes, pairsForNumber);
				tryAddingPair(new Pair<>(pair.left, pair.right + 1), sumOfCubes, pairsForNumber);
			}
			if (pairSet.size() > 1) {
				found++;
				System.out.println(found+". " + nextCandidate + "=>" + pairSet);
				
			}
		}
	}

	private static void tryAddingPair(Pair<Long, Long> entry, PriorityQueue<Long> sumOfCubes,
			Map<Long, Set<Pair<Long, Long>>> pairsForNumber) {
		long sum = cubesum(entry);
		Set<Pair<Long, Long>> pairSet;
		if (!pairsForNumber.containsKey(sum)) {
			sumOfCubes.add(sum);
			pairSet = new HashSet<>();
			pairsForNumber.put(sum, pairSet);
		} else {
			pairSet = pairsForNumber.get(sum);
		}

		if (!pairSet.contains(entry))
			pairSet.add(entry);
	}

	private static long cubesum(Pair<Long, Long> entry) {
		return entry.left * entry.left * entry.left + entry.right * entry.right * entry.right;
	}

}
