
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thomasthimothee
 */
public class DAL {

    private static final boolean DEBUG = true;
    private Connector connector = null;

    public DAL() {
        try {
            this.connector = new Connector();
        } catch (Exception ex) {
            if (DEBUG) {
                ex.printStackTrace();
            }
            System.out.println("Unable to connect to DB!");
            System.exit(0);

        }
    }

    public List<String> retrievePlayers() {
        List<String> list = new ArrayList();
        try {
            Statement stmt = connector.getConnection().createStatement();
            String sql = "select PlayerName from Predictions";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                list.add(rs.getString("PlayerName"));
            }
        } catch (SQLException ex) {
            System.out.println("addToDB error" + ex);
        }
        return list;
    }

    public void addToDB(String playerName, String guess1, String guess2, String guess3, String guess4, String guess5) {
        try {
            Statement stmt = connector.getConnection().createStatement();
            String sql1 = "insert into predictions(PlayerName, Round1, Round2, Round3, Round4, Round5)  values('" + playerName + "'  ,'" + guess1 + "','" + guess2 + "'  ,'" + guess3 + "'  ,'" + guess4 + "','" + guess5 + "'  )";
            stmt.executeUpdate(sql1);
            String sql2 = "insert into scores(PlayerName, score)  values('" + playerName +"',"+ 0 +")";
            stmt.executeUpdate(sql2);
        } catch (SQLException ex) {
            System.out.println("addToDB error" + ex);
        }
    }

    public String[] getFromDB(String name) {
        String[] choices = new String[5];
        String guess1;
        String guess2;
        String guess3;
        String guess4;
        String guess5;

        try {
            Statement stmt = connector.getConnection().createStatement();
            String sql1 = "select Round1 from predictions where PlayerName = '" + name + "'";
            ResultSet rs = stmt.executeQuery(sql1);
            if (rs.next()) {
                guess1 = rs.getString("Round1");
                choices[0] = guess1;
            }
            String sql2 = "select Round2 from Predictions where PlayerName = '" + name + "'";
            rs = stmt.executeQuery(sql2);
            if (rs.next()) {
                guess2 = rs.getString("Round2");
                choices[1] = guess2;
            }
            String sql3 = "select Round3 from Predictions where PlayerName = '" + name + "'";
            rs = stmt.executeQuery(sql3);
            if (rs.next()) {
                guess3 = rs.getString("Round3");
                choices[2] = guess3;
            }
            String sql4 = "select Round4 from Predictions where PlayerName = '" + name + "'";
            rs = stmt.executeQuery(sql4);
            if (rs.next()) {
                guess4 = rs.getString("Round4");
                choices[3] = guess4;
            }
            String sql5 = "select Round5 from Predictions where PlayerName = '" + name + "'";
            rs = stmt.executeQuery(sql5);
            if (rs.next()) {
                guess5 = rs.getString("Round5");
                choices[4] = guess5;
            }

        } catch (SQLException ex) {
            System.out.println("getFromDB error" + ex);
        }
        return choices;
    }

    public void updateToDB(String playerName, String choice1, String choice2, String choice3, String choice4, String choice5) {
        try {
            Statement stmt = connector.getConnection().createStatement();
            String sql = "update Predictions set Round1 = '" + choice1 + "', Round2 = '" + choice2 + "', Round3 = '" + choice3 + "', Round4 = '" + choice4 + "', Round5 = '" + choice5 + "'  where PlayerName = '" + playerName + "' ";
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("updateToDB error" + ex);
        }
    }

    public void addToScore(String playerName, int score) {
        try {
            Statement stmt = connector.getConnection().createStatement();
            int oldScore = retrieveScore(playerName);
            int scoree = score;
            int newScore = oldScore + scoree;
            String sql = "update scores set score = "+newScore+" where playerName = '"+playerName+"'";
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("updateToDB error" + ex);
        }
    }
    
    public int retrieveScore(String playerName)
    {
        int score = 0;
        try {
            Statement stmt = connector.getConnection().createStatement();
            String sql = "select score from scores where playerName = '" + playerName + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
            {
                score = rs.getInt("score");
            }

        } catch (SQLException ex) {
            System.out.println("addToDB error" + ex);
        }
        return score;        
    }

}
