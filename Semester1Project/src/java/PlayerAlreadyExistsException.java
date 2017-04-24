/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomasthimothee
 */
class PlayerAlreadyExistsException extends Exception {
    private String playerName;

    public PlayerAlreadyExistsException(String name)
    {
        this.playerName = name;
    }

    @Override
    public String toString()
    {
        return "Error, we have already registered choices for the name: " + playerName;
    }
    
}
