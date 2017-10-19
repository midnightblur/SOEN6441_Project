/* 
 * Risk Game Team 2
 * BidiArrayComparator.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.utilities;

import java.util.Comparator;

/**
 * This is a Comparator Class.
 *
 * @author Team 2
 * @version 1.0
 */

public class BidiArrayComparator implements Comparator {
    
    // region Attributes declaration
    /** The col to sort. */
    private int colToSort;
    // endregion
    
    // region Constructors
    /**
     * Constructor taking the column used for sorting.
     *
     * @param colToSort the col to sort
     */
    public BidiArrayComparator(int colToSort) {
        this.colToSort = colToSort;
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
        
        return row1[colToSort].compareTo(row2[colToSort]);
    }
    // endregion
}

