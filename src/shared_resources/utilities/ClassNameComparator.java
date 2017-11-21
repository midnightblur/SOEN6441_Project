/*
 * Risk Game Team 2
 * ClassNameComparator.java
 * Version 3.0
 * Nov 19, 2017
 */
package shared_resources.utilities;

import java.util.Comparator;

/**
 * Custom comparator for comparing class names
 */
public class ClassNameComparator implements Comparator<Class> {
    
    /**
     * @see Comparator#compare(Object, Object)
     */
    @Override
    public int compare(Class e1, Class e2) {
        return e1.getSimpleName().compareTo(e2.getSimpleName());
    }
}