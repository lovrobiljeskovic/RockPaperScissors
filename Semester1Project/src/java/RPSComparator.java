
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
class RPSComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (o1.equals("Rock")) {
            switch (o2) {
                case "Paper":
                    return -1;
                case "Scissors":
                    return 1;
                default:
                    return 0;
            }
        }
        if (o1.equals("Paper")) {
            switch (o2) {
                case "Scissors":
                    return -1;
                case "Rock":
                    return 1;
                default:
                    return 0;
            }
        }
        if (o1.equals("Scissors")) {
            switch (o2) {
                case "Rock":
                    return -1;
                case "Paper":
                    return 1;
                default:
                    return 0;
            }
        }
        return 0;
    }

}
