/* 
 * Risk Game Team 2
 * DiceTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.assertEquals;

/**
 * The Class DiceTest.
 */
public class DiceTest {
    private Vector<Integer> inputResult;
    
    @Before
    public void setup() {
        inputResult = new Vector<>();
    }
    
    @Test
    public void testGetResult_1() {
        inputResult.add(1);
        inputResult.add(4);
        inputResult.add(5);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(5, dice.getTheBestResult());
        assertEquals(4, dice.getSecondBestResult());
    }
    
    @Test
    public void testGetResult_2() {
        inputResult.add(6);
        inputResult.add(4);
        inputResult.add(3);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(4, dice.getSecondBestResult());
    }
    
    @Test
    public void testGetResult_3() {
        inputResult.add(1);
        inputResult.add(5);
        inputResult.add(5);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(5, dice.getTheBestResult());
        assertEquals(5, dice.getSecondBestResult());
    }
    
    @Test
    public void testGetResult_4() {
        inputResult.add(6);
        inputResult.add(6);
        inputResult.add(2);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(6, dice.getSecondBestResult());
    }
    
    @Test
    public void testGetResult_5() {
        inputResult.add(3);
        inputResult.add(3);
        inputResult.add(6);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(3, dice.getSecondBestResult());
    }
    
    @Test
    public void testGetResult_6() {
        inputResult.add(5);
        inputResult.add(2);
        inputResult.add(2);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(5, dice.getTheBestResult());
        assertEquals(2, dice.getSecondBestResult());
    }
    
    @Test
    public void testGetResult_7() {
        inputResult.add(6);
        inputResult.add(6);
        inputResult.add(6);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(6, dice.getSecondBestResult());
    }
    
    @Test
    public void testGetResult_8() {
        inputResult.add(2);
        inputResult.add(5);
        inputResult.add(2);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(5, dice.getTheBestResult());
        assertEquals(2, dice.getSecondBestResult());
    }
    
    @Test
    public void testGetResult_9() {
        inputResult.add(5);
        inputResult.add(2);
        inputResult.add(5);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(5, dice.getTheBestResult());
        assertEquals(5, dice.getSecondBestResult());
    }
    
    @Test
    public void testGetResult_10() {
        inputResult.add(2);
        inputResult.add(6);
        inputResult.add(5);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(5, dice.getSecondBestResult());
    }
    
    @Test
    public void testGetResult_11() {
        inputResult.add(4);
        inputResult.add(6);
        inputResult.add(3);
        
        Dice dice = new Dice(3);
        dice.setRollsResult(inputResult);
        assertEquals(6, dice.getTheBestResult());
        assertEquals(4, dice.getSecondBestResult());
    }
}