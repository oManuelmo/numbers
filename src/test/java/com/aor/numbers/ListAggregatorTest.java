package com.aor.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;



public class ListAggregatorTest {
    private List<Integer> helper() {
        return Arrays.asList(1,2,4,2,5);
    }
    @Test
    public void sum() {
        List<Integer> list = helper();

        ListAggregator aggregator = new ListAggregator();
        int sum = aggregator.sum(list);

        Assertions.assertEquals(14, sum);
    }

    @Test
    public void max() {
        List<Integer> list = helper();

        ListAggregator aggregator = new ListAggregator();
        int max = aggregator.max(list);

        Assertions.assertEquals(5, max);
    }

    @Test
    public void min() {
        List<Integer> list = helper();

        ListAggregator aggregator = new ListAggregator();
        int min = aggregator.min(list);

        Assertions.assertEquals(1, min);
    }

    @Test
    public void distinct() {
        List<Integer> list = helper();
        ListAggregator aggregator = new ListAggregator();
        int distinct = aggregator.distinct(list, new ListDeduplicator(), new ListSorter());

        Assertions.assertEquals(4, distinct);
    }
    @Test
    public void max_bug_7263() {
        List<Integer> list = Arrays.asList(-1,-4,-5);
        ListAggregator aggregator = new ListAggregator();
        int max = aggregator.max(list);

        Assertions.assertEquals(-1, max);
    }
    @Test
    public void distinct_bug_8726() {
        class StubDeduplicator implements GenericListDeduplicator {
            @Override
            public List<Integer> deduplicate(List<Integer> list, GenericListSorter listSorter) {
                return Arrays.asList(1,2,4);
            }
        }
        class StubSorter implements GenericListSorter {

            @Override
            public List<Integer> sort(List<Integer> list) {
                return Arrays.asList(1,2,2,4);
            }
        }
        ListAggregator aggregator = new ListAggregator();
        int distinct = aggregator.distinct(Arrays.asList(1, 2, 4, 2), new StubDeduplicator() , new ListSorter());
        Assertions.assertEquals(3, distinct);
    }
}
