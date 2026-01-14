package software.aoc.day12;

import java.util.*;
import java.util.stream.IntStream;

public class PermutationUtils {

    public static <T> Iterable<List<T>> getSubsets(List<T> sourceList, int subsetSize) {
        if (subsetSize < 0 || subsetSize > sourceList.size()) {
            return Collections.emptyList();
        }

        return () -> new Iterator<>() {
            private final int[] pointers = IntStream.range(0, subsetSize).toArray();
            private boolean hasMore = true;

            @Override
            public boolean hasNext() {
                return hasMore;
            }

            @Override
            public List<T> next() {
                if (!hasMore) throw new NoSuchElementException();

                List<T> currentBatch = new ArrayList<>(subsetSize);
                for (int idx : pointers) {
                    currentBatch.add(sourceList.get(idx));
                }

                hasMore = calculateNextIndices(pointers, sourceList.size(), subsetSize);
                return currentBatch;
            }
        };
    }

    private static boolean calculateNextIndices(int[] indices, int totalElements, int k) {
        for (int i = k - 1; i >= 0; i--) {
            if (indices[i] < totalElements - k + i) {
                indices[i]++;
                for (int j = i + 1; j < k; j++) {
                    indices[j] = indices[j - 1] + 1;
                }
                return true;
            }
        }
        return false;
    }
}