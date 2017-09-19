package Models;

public class Adjacency {
    // Private data member of Models.Adjacency class
    private String territory1;
    private String territory2;

    public Adjacency(String territory1, String territory2) {
        this.territory1 = territory1;
        this.territory2 = territory2;
    }

    public String getTerritory1() {
        return territory1;
    }

    public String getTerritory2() {
        return territory2;
    }

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
