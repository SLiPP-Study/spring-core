package jyp.springframework.core;

import java.util.Comparator;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 16.
 */
public class OrderComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        int i1 = (o1 instanceof Ordered ? ((Ordered) o1).getOrder() : Integer.MAX_VALUE);
        int i2 = (o2 instanceof Ordered ? ((Ordered) o2).getOrder() : Integer.MAX_VALUE);

        if (i1 < i2) {
            return -1;
        } else if (i1 > i2) {
            return 1;
        } else {
            return 0;
        }
    }
}
