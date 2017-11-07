package shared_resources.game_entities;

/**
 * Battle is responsible for encapsulating all information about a battle between 2 territories
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
    public Player getAttacker() {
        return attacker;
    }
    
    public Territory getAttackingTerritory() {
        return attackingTerritory;
    }
    
    public Player getDefender() {
        return defender;
    }
    
    public Territory getDefendingTerritory() {
        return defendingTerritory;
    }
    
    public Dice getAttackerDice() {
        return attackerDice;
    }
    
    public Dice getDefenderDice() {
        return defenderDice;
    }
    
    public int getAttackerLoseCount() {
        return attackerLoseCount;
    }
    
    public int getDefenderLoseCount() {
        return defenderLoseCount;
    }
    // endregion
    
    // region Public methods
    public void attackerRollDice() {
        attackerDice.roll();
    }
    
    public void defenderRollDice() {
        defenderDice.roll();
    }
    
    public void increaseAttackerLoseCount() {
        attackerLoseCount++;
    }
    
    public void increaseDefenderLoseCount() {
        defenderLoseCount++;
    }
    // endregion
}
