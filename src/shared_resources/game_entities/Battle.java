/*
 * Risk Game Team 2
 * Battle.java
 * Version 1.0
 * Nov 6, 2017
 */
package shared_resources.game_entities;

/**
 * Battle is responsible for encapsulating all information about a battle between 2 territories
 *
 * @author Team 2
 * @version 2.0
 */
public class Battle {
    // region Attributes declaration
    private Player attacker;
    private Territory attackingTerritory;
    private Dice attackerDice;
    private int attackerLoseCount;
    
    private Player defender;
    private Territory defendingTerritory;
    private Dice defenderDice;
    private int defenderLoseCount;
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
     * @param defendingDice      the dice for defending player
     */
    public Battle(Player attacker, Territory attackingTerritory, int attackingDice,
                  Player defender, Territory defendingTerritory, int defendingDice) {
        this.attacker = attacker;
        this.attackingTerritory = attackingTerritory;
        attackerDice = new Dice(attackingDice);
        attackerLoseCount = 0;
        
        this.defender = defender;
        this.defendingTerritory = defendingTerritory;
        defenderDice = new Dice(defendingDice);
        defenderLoseCount = 0;
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
     * Get the attacker dice
     *
     * @return the dice for the attacked
     */
    public Dice getAttackerDice() {
        return attackerDice;
    }
    
    /**
     * Get the dice for defending player
     *
     * @return the dice for defending player
     */
    public Dice getDefenderDice() {
        return defenderDice;
    }
    
    /**
     * Get the attacker loss count
     *
     * @return the attacker loss count
     */
    public int getAttackerLossCount() {
        return attackerLoseCount;
    }
    
    /**
     * Get the defender loss count
     *
     * @return the defender loss count
     */
    public int getDefenderLossCount() {
        return defenderLoseCount;
    }
    // endregion
    
    // region Public methods
    
    /**
     * Roll the dice for attacker
     */
    public void attackerRollDice() {
        attackerDice.roll();
    }
    
    /**
     * Set the dice for defending player
     */
    public void defenderRollDice() {
        defenderDice.roll();
    }
    
    /**
     * Increase the attacker loss count
     */
    public void increaseAttackerLossCount() {
        attackerLoseCount++;
    }
    
    /**
     * Increase the defender loss count
     */
    public void increaseDefenderLossCount() {
        defenderLoseCount++;
    }
    // endregion
}
