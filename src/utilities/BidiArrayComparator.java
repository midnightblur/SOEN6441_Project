package utilities;

import java.util.Comparator;

public class BidiArrayComparator implements Comparator {
    private int colToSort;
    
    /* Constructor taking the column used for sorting */
    public BidiArrayComparator(int colToSort) {
        this.colToSort = colToSort;
    }
    
    @Override
    public int compare(Object o1, Object o2) {
        String[] row1 = (String[]) o1;
        String[] row2 = (String[]) o2;
        
        return row1[colToSort].compareTo(row2[colToSort]);
    }
}

