package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class RiskGameTest {
    @Test
    public void getInstance() throws Exception {
        RiskGame riskGameObj1 = RiskGame.getInstance();
        RiskGame riskGameObj2 = RiskGame.getInstance();
        
        // checks if the two game objects are not null
        System.out.println("Testing to see if two getInstance() of RiskGame are the same.");
        assertEquals(riskGameObj1, riskGameObj2);
    }

    
}