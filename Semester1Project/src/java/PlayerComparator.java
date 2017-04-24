
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomasthimothee
 */

public class PlayerComparator implements Comparator<Profile>
{

    @Override
    public int compare(Profile o1, Profile o2) {
        return o2.getScore() - o1.getScore();
    }
    
}
