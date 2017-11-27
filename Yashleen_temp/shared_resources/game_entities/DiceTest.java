
package shared_resources.game_entities;

import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.assertEquals;


public class DiceTest {
    private Vector<Integer> inputResult;

    @Before
    public void setup() {
        inputResult = new Vector<>();
    }

    @Test
    public void testGetResult_1_4_5() {
        inputResult.add(1);
        inputResult.add(4);
        inputResult.add(5);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(5, dice.getTheBestResult());
        assertEquals(4, dice.getSecondBestResult());
    }
    @Test
    public void testGetResult_6_4_3() {
        inputResult.add(6);
        inputResult.add(4);
        inputResult.add(3);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(4, dice.getSecondBestResult());
    }
    

    @Test
    public void testGetResult_1_5_5() {
        inputResult.add(1);
        inputResult.add(5);
        inputResult.add(5);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(5, dice.getTheBestResult());
        assertEquals(5, dice.getSecondBestResult());
    }
    
    /**
     * Testing best and second best return for [6, 6, 2]
     */
    @Test
    public void testGetResult_6_6_2() {
        inputResult.add(6);
        inputResult.add(6);
        inputResult.add(2);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(6, dice.getSecondBestResult());
    }
    
    /**
     * Testing best and second best return for [3, 3, 6]
     */
    @Test
    public void testGetResult_3_3_6() {
        inputResult.add(3);
        inputResult.add(3);
        inputResult.add(6);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(3, dice.getSecondBestResult());
    }
    
    /**
     * Testing best and second best return for [5, 2, 2]
     */
    @Test
    public void testGetResult_5_2_2() {
        inputResult.add(5);
        inputResult.add(2);
        inputResult.add(2);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(5, dice.getTheBestResult());
        assertEquals(2, dice.getSecondBestResult());
    }
    
    /**
     * Testing best and second best return for [6, 6, 6]
     */
    @Test
    public void testGetResult_6_6_6() {
        inputResult.add(6);
        inputResult.add(6);
        inputResult.add(6);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(6, dice.getSecondBestResult());
    }
    
    /**
     * Testing best and second best return for [2, 5, 2]
     */
    @Test
    public void testGetResult_2_5_2() {
        inputResult.add(2);
        inputResult.add(5);
        inputResult.add(2);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(5, dice.getTheBestResult());
        assertEquals(2, dice.getSecondBestResult());
    }
    
    /**
     * Testing best and second best return for [5, 2, 5]
     */
    @Test
    public void testGetResult_5_2_5() {
        inputResult.add(5);
        inputResult.add(2);
        inputResult.add(5);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(5, dice.getTheBestResult());
        assertEquals(5, dice.getSecondBestResult());
    }
    
    /**
     * Testing best and second best return for [2, 6, 5]
     */
    @Test
    public void testGetResult_2_6_5() {
        inputResult.add(2);
        inputResult.add(6);
        inputResult.add(5);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(5, dice.getSecondBestResult());
    }
    
    /**
     * Testing best and second best return for [4, 6, 3]
     */
    @Test
    public void testGetResult_4_6_3() {
        inputResult.add(4);
        inputResult.add(6);
        inputResult.add(3);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(4, dice.getSecondBestResult());
    }
}