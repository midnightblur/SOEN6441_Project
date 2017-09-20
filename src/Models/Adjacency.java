package Models;

/**
 * The relationship between two territories having mutual border
 * Between two territories will have only one (not two) adjacency
 */
public class Adjacency {
    /**
     * Private data members of Adjacency class
     * territory1, territory2: two adjacent territories' names
     */
    private String territory1;
    private String territory2;

    /**
     * Public constructor
     * @param territory1: name of territory 1
     * @param territory2: name of territory 2
     */
    public Adjacency(String territory1, String territory2) {
        this.territory1 = territory1;
        this.territory2 = territory2;
    }

    /**
     * Getters
     * @return
     */
    public String getTerritory1() {
        return territory1;
    }

    public String getTerritory2() {
        return territory2;
    }

    /**
     * Helps compare two Adjacency objects
     * Used when inserting a new adjacency into the game map
     * The adjacency will be ignored if there is another adjacency
     * (maybe having reversed order of territories) already in the map
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Adjacency))
            return false;

        Adjacency adjacency = (Adjacency) other;
        if ((this.territory1.equals(adjacency.territory1) && this.territory2.equals(adjacency.territory2)) ||
                (this.territory1.equals(adjacency.territory2) && this.territory2.equals(adjacency.territory1)))
            return true;

        return false;
    }
}
