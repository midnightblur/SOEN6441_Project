/*
 * Risk Game Team 2
 * Strategy.java
 * Version 3.0
 * Nov 10, 2017
 */
package shared_resources.utilities;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Vector;

/**
 * The classes implementing a concrete strategy should implement this.
 * The players use this to adopt a concrete strategy.
 */
public interface Strategy {
    String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace);
    void attack(GamePlayModel gamePlayModel);
    String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies);
    
    
    /**
     * The human strategy
     */
    class Human implements Strategy {
        
        @Override
        public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
            return null;
        }
    
        @Override
        public void attack(GamePlayModel gamePlayModel) {
        
        }
    
        @Override
        public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
            return null;
        }
    }
    
    /**
     * The aggressive AI strategy. Reinforces its strongest country, then always attacks with it until it
     * cannot attack anymore, then fortifies in order to maximize aggregation of forces in one country.
     */
    class AggressiveAI implements Strategy {
        
    
        @Override
        public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
            return null;
        }
    
        @Override
        public void attack(GamePlayModel gamePlayModel) {
        
        }
    
        @Override
        public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
            return null;
        }
    }
    
    /**
     * The benevolent AI strategy. Reinforces its weakest countries, never attacks, then fortifies in order
     * to move armies to weaker countries.
     */
    class BenevolentAI implements Strategy {
    
    
        @Override
        public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
            return null;
        }
    
        @Override
        public void attack(GamePlayModel gamePlayModel) {
        
        }
    
        @Override
        public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
            return null;
        }
    }

    /**
     * The random AI strategy. Reinforces a random country, attacks random countries a random number of times, and
     * fortifies a random country.
     */
    class RandomAI implements Strategy {


        @Override
        public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
            return null;
        }

        @Override
        public void attack(GamePlayModel gamePlayModel) {

        }

        @Override
        public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
            return null;
        }
    }

    /**
     * The cheater AI strategy. Reinforcement doubles the number of armies on all its countries, attacks automatically
     * conquer all the neighbors of all its countries, and fortification doubles the number of armies on its countries
     * that have neighbors that belong to other players
     */
    class CheaterAI implements Strategy {


        @Override
        public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
            return null;
        }

        @Override
        public void attack(GamePlayModel gamePlayModel) {

        }

        @Override
        public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
            return null;
        }
    }
}