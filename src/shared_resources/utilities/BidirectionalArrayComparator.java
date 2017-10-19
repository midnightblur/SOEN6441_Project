/* 
 * Risk Game Team 2
 * BidirectionalArrayComparator.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.utilities;

import java.util.Comparator;

/**
 * This is a Comparator Class for ascending sorting a bidirectional array.
 *
 * @author Team 2
 * @version 1.0
 */

public class BidirectionalArrayComparator implements Comparator {
    
    // region Attributes declaration
    private int columnToSort;
    // endregion
    
    // region Constructors
    /**
     * Constructor taking the column used for sorting.
     *
     * @param columnToSort the column to sort by
     */
    public BidirectionalArrayComparator(int columnToSort) {
        this.columnToSort = columnToSort;
    }
    // endregion
    
    // region Public methods
    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Object o1, Object o2) {
        String[] row1 = (String[]) o1;
        String[] row2 = (String[]) o2;
        
        return row1[columnToSort].compareTo(row2[columnToSort]);
    }
    // endregion
}

