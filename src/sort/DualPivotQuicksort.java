/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package sort;

/**
 * This class implements the Dual-Pivot Quicksort algorithm by
 * Vladimir Yaroslavskiy, Jon Bentley, and Josh Bloch. The algorithm
 * offers O(n log(n)) performance on many data sets that cause other
 * quicksorts to degrade to quadratic performance, and is typically
 * faster than traditional (one-pivot) Quicksort implementations.
 * <p>
 * All exposed methods are package-private, designed to be invoked
 * from public methods (in class Arrays) after performing any
 * necessary array bounds checks and expanding parameters into the
 * required forms.
 *
 * @author Vladimir Yaroslavskiy
 * @author Jon Bentley
 * @author Josh Bloch
 * @version 2011.02.11 m765.827.12i:5\7pm
 * @since 1.7
 */
public final class DualPivotQuicksort {

    /**
     * Prevents instantiation.
     */
    private DualPivotQuicksort() {
    }

    /*
     * Tuning parameters.
     */

    /**
     * The maximum number of runs in merge sort.
     */
    private static final int MAX_RUN_COUNT = 67;

    /**
     * The maximum length of run in merge sort.
     */
    private static final int MAX_RUN_LENGTH = 33;

    /**
     * If the length of an array to be sorted is less than this
     * constant, Quicksort is used in preference to merge sort.
     */
    private static final int QUICKSORT_THRESHOLD = 286;

    /**
     * If the length of an array to be sorted is less than this
     * constant, insertion sort is used in preference to Quicksort.
     */
    private static final int INSERTION_SORT_THRESHOLD = 47;

    /*
     * Sorting methods for seven primitive types.
     */

    /**
     * @param array the array to be sorted
     * @param left  the index of the first element, inclusive, to be sorted
     * @param right the index of the last element, inclusive, to be sorted
     */
    static void sort(int[] array, int left, int right) {
        // Use Quicksort on small arrays
        if (right - left < QUICKSORT_THRESHOLD) {
            sort(array, left, right, true);
            return;
        }

        /*
         * Index run[i] is the start of i-th run
         * (ascending or descending sequence).
         */
        int[] run = new int[MAX_RUN_COUNT + 1];
        int count = 0;
        run[0] = left;

        /*
         * Check if the array is nearly sorted
         * O(n)扫过，成一个一个的非降序序列，方便归并
         */
        for (int k = left; k < right; run[count] = k) {
            if (array[k] < array[k + 1]) { // ascending
                while (++k <= right && array[k - 1] <= array[k]) ;
            } else if (array[k] > array[k + 1]) { // descending 把降序部分整理成升序
                while (++k <= right && array[k - 1] >= array[k]) ;
                for (int lo = run[count] - 1, hi = k; ++lo < --hi; ) {
                    int t = array[lo];
                    array[lo] = array[hi];
                    array[hi] = t;
                }
            } else { // equal 判断是否有长度为MAX_RUN_LENGTH的相等块，有就用快排
                for (int m = MAX_RUN_LENGTH; ++k <= right && array[k - 1] == array[k]; ) {
                    if (--m == 0) {
                        sort(array, left, right, true);
                        return;
                    }
                }
            }

            /*
             * The array is not highly structured,
             * use Quicksort instead of merge sort.
             * 不同次序的块超过MAX_RUN_COUNT用快排
             */
            if (++count == MAX_RUN_COUNT) {
                sort(array, left, right, true);
                return;
            }
        }

        // Check special cases
        // Implementation note: variable "right" is increased by 1.
        // 如果最后一个run只有一个元素，那么right++
        if (run[count] == right++) { // The last run contains one element
            run[++count] = right;
        } else if (count == 1) { // The array is already sorted
            return;
        }

        // 执行到这，count为单调增的分组数
        // 后面是TimSort 没看懂
        // Determine alternation base for merge
        byte odd = 0;
        for (int n = 1; (n <<= 1) < count; odd ^= 1) ; //odd为((count-1)的二进制数的长度+1)%2

        // Use or create temporary array b for merging
        int blen = right - left; // space needed for b
        int[] b = new int[blen];                 // temp array; alternates with a
        int ao, bo;              // array offsets from 'left'
        if (odd == 0) {
            System.arraycopy(array, left, b, 0, blen);
            int[] t = b;
            b = array;
            array = t;
            bo = 0;
            ao = -left;
        } else {
            ao = 0;
            bo = -left;
        }

        // Merging
        for (int last; count > 1; count = last) {
            for (int k = (last = 0) + 2; k <= count; k += 2) {
                int hi = run[k], mi = run[k - 1];
                for (int i = run[k - 2], p = i, q = mi; i < hi; ++i) {
                    if (q >= hi || p < mi && array[p + ao] <= array[q + ao]) {
                        b[i + bo] = array[p++ + ao];
                    } else {
                        b[i + bo] = array[q++ + ao];
                    }
                }
                run[++last] = hi;
            }
            if ((count & 1) != 0) {
                for (int i = right, lo = run[count - 1]; --i >= lo; b[i + bo] = array[i + ao]);
                run[++last] = right;
            }
            int[] t = array;
            array = b;
            b = t;
            int o = ao;
            ao = bo;
            bo = o;
        }
    }

    /**
     * Sorts the specified range of the array by Dual-Pivot Quicksort.
     * 没怎么看懂
     *
     * @param array    the array to be sorted
     * @param left     the index of the first element, inclusive, to be sorted
     * @param right    the index of the last element, inclusive, to be sorted
     * @param leftmost indicates if this part is the leftmost in the range
     */
    private static void sort(int[] array, int left, int right, boolean leftmost) {
        int length = right - left + 1;

        // Use insertion sort on tiny arrays
        if (length < INSERTION_SORT_THRESHOLD) {
            if (leftmost) {
                /*
                 * Traditional (without sentinel) insertion sort,
                 * optimized for server VM, is used in case of
                 * the leftmost part.
                 */
                for (int i = left, j = i; i < right; j = ++i) {
                    int ai = array[i + 1];
                    while (ai < array[j]) {
                        array[j + 1] = array[j];
                        if (j-- == left) {
                            break;
                        }
                    }
                    array[j + 1] = ai;
                }
            } else {
                /*
                 * Skip the longest ascending sequence.
                 */
                do {
                    if (left >= right) {
                        return;
                    }
                } while (array[++left] >= array[left - 1]);

                /*
                 * Every element from adjoining part plays the role
                 * of sentinel, therefore this allows us to avoid the
                 * left range check on each iteration. Moreover, we use
                 * the more optimized algorithm, so called pair insertion
                 * sort, which is faster (in the context of Quicksort)
                 * than traditional implementation of insertion sort.
                 */
                for (int k = left; ++left <= right; k = ++left) {
                    int a1 = array[k], a2 = array[left];

                    if (a1 < a2) {
                        a2 = a1;
                        a1 = array[left];
                    }
                    while (a1 < array[--k]) {
                        array[k + 2] = array[k];
                    }
                    array[++k + 1] = a1;

                    while (a2 < array[--k]) {
                        array[k + 1] = array[k];
                    }
                    array[k + 1] = a2;
                }
                int last = array[right];

                while (last < array[--right]) {
                    array[right + 1] = array[right];
                }
                array[right + 1] = last;
            }
            return;
        }

        // Inexpensive approximation of length / 7
        int seventh = (length >> 3) + (length >> 6) + 1;

        /*
         * Sort five evenly spaced elements around (and including) the
         * center element in the range. These elements will be used for
         * pivot selection as described below. The choice for spacing
         * these elements was empirically determined to work well on
         * a wide variety of inputs.
         */
        int e3 = (left + right) >>> 1; // The midpoint
        int e2 = e3 - seventh;
        int e1 = e2 - seventh;
        int e4 = e3 + seventh;
        int e5 = e4 + seventh;

        // Sort these elements using insertion sort
        if (array[e2] < array[e1]) {
            int t = array[e2];
            array[e2] = array[e1];
            array[e1] = t;
        }

        if (array[e3] < array[e2]) {
            int t = array[e3];
            array[e3] = array[e2];
            array[e2] = t;
            if (t < array[e1]) {
                array[e2] = array[e1];
                array[e1] = t;
            }
        }
        if (array[e4] < array[e3]) {
            int t = array[e4];
            array[e4] = array[e3];
            array[e3] = t;
            if (t < array[e2]) {
                array[e3] = array[e2];
                array[e2] = t;
                if (t < array[e1]) {
                    array[e2] = array[e1];
                    array[e1] = t;
                }
            }
        }
        if (array[e5] < array[e4]) {
            int t = array[e5];
            array[e5] = array[e4];
            array[e4] = t;
            if (t < array[e3]) {
                array[e4] = array[e3];
                array[e3] = t;
                if (t < array[e2]) {
                    array[e3] = array[e2];
                    array[e2] = t;
                    if (t < array[e1]) {
                        array[e2] = array[e1];
                        array[e1] = t;
                    }
                }
            }
        }

        // Pointers
        int less = left;  // The index of the first element of center part
        int great = right; // The index before the first element of right part

        if (array[e1] != array[e2] && array[e2] != array[e3] && array[e3] != array[e4] && array[e4] != array[e5]) {
            /*
             * Use the second and fourth of the five sorted elements as pivots.
             * These values are inexpensive approximations of the first and
             * second terciles of the array. Note that pivot1 <= pivot2.
             */
            int pivot1 = array[e2];
            int pivot2 = array[e4];

            /*
             * The first and the last elements to be sorted are moved to the
             * locations formerly occupied by the pivots. When partitioning
             * is complete, the pivots are swapped back into their final
             * positions, and excluded from subsequent sorting.
             */
            array[e2] = array[left];
            array[e4] = array[right];

            /*
             * Skip elements, which are less or greater than pivot values.
             */
            while (array[++less] < pivot1) ;
            while (array[--great] > pivot2) ;

            /*
             * Partitioning:
             *
             *   left part           center part                   right part
             * +--------------------------------------------------------------+
             * |  < pivot1  |  pivot1 <= && <= pivot2  |    ?    |  > pivot2  |
             * +--------------------------------------------------------------+
             *               ^                          ^       ^
             *               |                          |       |
             *              less                        k     great
             *
             * Invariants:
             *
             *              all in (left, less)   < pivot1
             *    pivot1 <= all in [less, k)     <= pivot2
             *              all in (great, right) > pivot2
             *
             * Pointer k is the first index of ?-part.
             */
            outer:
            for (int k = less - 1; ++k <= great; ) {
                int ak = array[k];
                if (ak < pivot1) { // Move a[k] to left part
                    array[k] = array[less];
                    /*
                     * Here and below we use "a[i] = b; i++;" instead
                     * of "a[i++] = b;" due to performance issue.
                     */
                    array[less] = ak;
                    ++less;
                } else if (ak > pivot2) { // Move a[k] to right part
                    while (array[great] > pivot2) {
                        if (great-- == k) {
                            break outer;
                        }
                    }
                    if (array[great] < pivot1) { // a[great] <= pivot2
                        array[k] = array[less];
                        array[less] = array[great];
                        ++less;
                    } else { // pivot1 <= a[great] <= pivot2
                        array[k] = array[great];
                    }
                    /*
                     * Here and below we use "a[i] = b; i--;" instead
                     * of "a[i--] = b;" due to performance issue.
                     */
                    array[great] = ak;
                    --great;
                }
            }

            // Swap pivots into their final positions
            array[left] = array[less - 1];
            array[less - 1] = pivot1;
            array[right] = array[great + 1];
            array[great + 1] = pivot2;

            // Sort left and right parts recursively, excluding known pivots
            sort(array, left, less - 2, leftmost);
            sort(array, great + 2, right, false);

            /*
             * If center part is too large (comprises > 4/7 of the array),
             * swap internal pivot values to ends.
             */
            if (less < e1 && e5 < great) {
                /*
                 * Skip elements, which are equal to pivot values.
                 */
                while (array[less] == pivot1) {
                    ++less;
                }

                while (array[great] == pivot2) {
                    --great;
                }

                /*
                 * Partitioning:
                 *
                 *   left part         center part                  right part
                 * +----------------------------------------------------------+
                 * | == pivot1 |  pivot1 < && < pivot2  |    ?    | == pivot2 |
                 * +----------------------------------------------------------+
                 *              ^                        ^       ^
                 *              |                        |       |
                 *             less                      k     great
                 *
                 * Invariants:
                 *
                 *              all in (*,  less) == pivot1
                 *     pivot1 < all in [less,  k)  < pivot2
                 *              all in (great, *) == pivot2
                 *
                 * Pointer k is the first index of ?-part.
                 */
                outer:
                for (int k = less - 1; ++k <= great; ) {
                    int ak = array[k];
                    if (ak == pivot1) { // Move a[k] to left part
                        array[k] = array[less];
                        array[less] = ak;
                        ++less;
                    } else if (ak == pivot2) { // Move a[k] to right part
                        while (array[great] == pivot2) {
                            if (great-- == k) {
                                break outer;
                            }
                        }
                        if (array[great] == pivot1) { // a[great] < pivot2
                            array[k] = array[less];
                            /*
                             * Even though a[great] equals to pivot1, the
                             * assignment a[less] = pivot1 may be incorrect,
                             * if a[great] and pivot1 are floating-point zeros
                             * of different signs. Therefore in float and
                             * double sorting methods we have to use more
                             * accurate assignment a[less] = a[great].
                             */
                            array[less] = pivot1;
                            ++less;
                        } else { // pivot1 < a[great] < pivot2
                            array[k] = array[great];
                        }
                        array[great] = ak;
                        --great;
                    }
                }
            }

            // Sort center part recursively
            sort(array, less, great, false);

        } else { // Partitioning with one pivot
            /*
             * Use the third of the five sorted elements as pivot.
             * This value is inexpensive approximation of the median.
             */
            int pivot = array[e3];

            /*
             * Partitioning degenerates to the traditional 3-way
             * (or "Dutch National Flag") schema:
             *
             *   left part    center part              right part
             * +-------------------------------------------------+
             * |  < pivot  |   == pivot   |     ?    |  > pivot  |
             * +-------------------------------------------------+
             *              ^              ^        ^
             *              |              |        |
             *             less            k      great
             *
             * Invariants:
             *
             *   all in (left, less)   < pivot
             *   all in [less, k)     == pivot
             *   all in (great, right) > pivot
             *
             * Pointer k is the first index of ?-part.
             */
            for (int k = less; k <= great; ++k) {
                if (array[k] == pivot) {
                    continue;
                }
                int ak = array[k];
                if (ak < pivot) { // Move a[k] to left part
                    array[k] = array[less];
                    array[less] = ak;
                    ++less;
                } else { // a[k] > pivot - Move a[k] to right part
                    while (array[great] > pivot) {
                        --great;
                    }
                    if (array[great] < pivot) { // a[great] <= pivot
                        array[k] = array[less];
                        array[less] = array[great];
                        ++less;
                    } else { // a[great] == pivot
                        /*
                         * Even though a[great] equals to pivot, the
                         * assignment a[k] = pivot may be incorrect,
                         * if a[great] and pivot are floating-point
                         * zeros of different signs. Therefore in float
                         * and double sorting methods we have to use
                         * more accurate assignment a[k] = a[great].
                         */
                        array[k] = pivot;
                    }
                    array[great] = ak;
                    --great;
                }
            }

            /*
             * Sort left and right parts recursively.
             * All elements from center part are equal
             * and, therefore, already sorted.
             */
            sort(array, left, less - 1, leftmost);
            sort(array, great + 1, right, false);
        }
    }
}
