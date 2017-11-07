/*
 * Risk Game Team 2
 * Battle.java
 * Version 1.0
 * Nov 6, 2017
 */
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
    
    /**
     * The constructor for the Battle
     *
     * @param attacker           the attacking player
     * @param attackingTerritory the territory originating the attack
     * @param attackingDice      the dice for attacking player
     * @param defender           the defender player
     * @param defendingTerritory the territory being attached
     */
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
    
    /**
     * Get the attacking player
     *
     * @return the player that attacks
     */
    public Player getAttacker() {
        return attacker;
    }
    
    /**
     * Get attacking from territory
     *
     * @return the territory from where the attack is launched
     */
    public Territory getAttackingTerritory() {
        return attackingTerritory;
    }
    
    /**
     * Get the attacker dice
     *
     * @return the dice for the attacked
     */
    public int getAttackingDice() {
        return attackingDice;
    }
    
    /**
     * Get the defender player
     *
     * @return the player that defends its territory
     */
    public Player getDefender() {
        return defender;
    }
    
    /**
     * Get the territory being defended
     *
     * @return the territory being defended
     */
    public Territory getDefendingTerritory() {
        return defendingTerritory;
    }
    
    /**
     * Get the dice for defending player
     *
     * @return the dice for defending player
     */
    public int getDefendingDice() {
        return defendingDice;
    }
    
    /**
     * Set the dice for defending player
     *
     * @param defendingDice the dice for defending player
     */
    public void setDefendingDice(int defendingDice) {
        this.defendingDice = defendingDice;
    }
    // endregion
    
    // region Public methods
    
    /**
     * Roll the dice for attacker
     */
    public void attackerRollDice() {
        
    }
    // endregion
}
