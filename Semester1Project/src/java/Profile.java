/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomasthimothee
 */
public class Profile {
    int score;
    String name;

    public Profile(String name, int score) {
        this.score = score;
        this.name = name;
    }
    
    public void increaseScore()
    {
        score ++;
    }
    
    public void decreaseScore()
    {
        score--;       
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
    
    
}
