package shared_resources.game_entities;

import java.util.Vector;

/**
 * Battle is responsible for encapsulating all information about a battle between 2 territories
 */
public class Battle {
    // region Attributes declaration
    private Player attacker;
    private Territory attackingTerritory;
    private int attackingDice;
    
    private Player defender;
    private Territory defendingTerritory;
    private int defendingDice;
    
    private Vector<Integer> attackerRolls;
    private Vector<Integer> defenderRolls;
    // endregion
    
    // region Constructors/
    public Battle(Player attacker, Territory attackingTerritory, int attackingDice, Player defender, Territory defendingTerritory) {
        this.attacker = attacker;
        this.attackingTerritory = attackingTerritory;
        this.attackingDice = attackingDice;
        this.defender = defender;
        this.defendingTerritory = defendingTerritory;
    
        attackerRolls = new Vector<>();
        defenderRolls = new Vector<>();
    }
    // endregion
    
    // region Getters & Setters
    public Player getAttacker() {
        return attacker;
    }
    
    public Territory getAttackingTerritory() {
        return attackingTerritory;
    }
    
    public int getAttackingDice() {
        return attackingDice;
    }
    
    public Player getDefender() {
        return defender;
    }
    
    public Territory getDefendingTerritory() {
        return defendingTerritory;
    }
    
    public int getDefendingDice() {
        return defendingDice;
    }
    
    public void setDefendingDice(int defendingDice) {
        this.defendingDice = defendingDice;
    }
    // endregion
    
    // region Public methods
    public void attackerRollDice() {
        
    }
    // endregion
}
