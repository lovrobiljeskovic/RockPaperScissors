/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author thomasthimothee
 */
@WebServlet(urlPatterns = {"/finalProjectServlet"})
public class finalProjectServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /*    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }//doGet

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //   doGet(request, response); // since the form calls the doPost method, we insert doGet here so it gets executed first
        response.setContentType("text/html;charset=UTF-8");
        String formName = request.getParameter("formName");

        try (PrintWriter out = response.getWriter()) {
            DAL dal = new DAL();
            List<String> playerList = new ArrayList();

            playerList = dal.retrievePlayers();
            String playerName;
            String opponentName;
            String choice1;
            String choice2;
            String choice3;
            String choice4;
            String choice5;
//
//            out.println("test1");

            switch (formName) {

                case "newPlayerForm":
                    playerName = request.getParameter("name");

                    if (playerList.contains(playerName)) {
                        out.println("AlreadyExist");
                    }// need to throw an exception

                    choice1 = request.getParameter("round1");
                    choice2 = request.getParameter("round2");
                    choice3 = request.getParameter("round3");
                    choice4 = request.getParameter("round4");
                    choice5 = request.getParameter("round5");
                    
                    dal.addToDB(playerName, choice1, choice2, choice3, choice4, choice5);
                    response.sendRedirect("./projectWelcomePage.html");
                    break;

                case "updateUserForm":
                    playerName = request.getParameter("name");

                    if (!playerList.contains(playerName)) {
                        out.println("Player does not exist");
                    }
                    //need to throw an exception rather than out.print        

                    choice1 = request.getParameter("round1");
                    choice2 = request.getParameter("round2");
                    choice3 = request.getParameter("round3");
                    choice4 = request.getParameter("round4");
                    choice5 = request.getParameter("round5");

                    dal.updateToDB(playerName, choice1, choice2, choice3, choice4, choice5);
                    response.sendRedirect("./projectWelcomePage.html");
                    break;

                case "singleMatch":
                    request.setAttribute("list", playerList);
                    RequestDispatcher rd1 = request.getRequestDispatcher("./singleMatchForm.jsp");
                    rd1.forward(request, response);
                    break;

                case "singleMatchForm":
                    playerName = request.getParameter("name");
                    opponentName = request.getParameter("name2");
                    out.println(singleMatch(playerName, opponentName, dal));
                    break;

                case "tournament":
                    request.setAttribute("list", playerList);
                    RequestDispatcher rd2 = request.getRequestDispatcher("./tournamentForm.jsp");
                    rd2.forward(request, response);
                    break;

                case "tournamentForm":

                    playerName = request.getParameter("name");
                    int numberOfMatch = 0;
// need to add exception if playerName and opponent don't exist in db

                    HashMap<String, Profile> playerMap = new HashMap<String, Profile>();
                    int len = playerList.size();
                    for (int i = 0; i < len; i++) {
                        playerMap.put(playerList.get(i), new Profile(playerList.get(i),0));
                    }

                    for (int i = 0; i < len; i++) {
                        for (int j = i + 1; j < len; j++) {
                            tournamentMatch(playerList.get(i), playerList.get(j), dal, playerMap);
                            numberOfMatch++;
                        }
                    }

                    out.println("tournament over your total score is " + playerName + ": " + playerMap.get(playerName).getScore() + " number of players = " + len + " number of match = " + numberOfMatch);
                    List<Profile> ranking = new ArrayList(playerMap.values());
                    Collections.sort(ranking, new PlayerComparator());
                    for (int i = 0; i < ranking.size(); i++)
                        out.println(ranking.get(i).getName() + " " + ranking.get(i).getScore());
                    break;
                    
                default:
                    out.println("Default");
            }

//            out.println("test3");
        } catch (Exception e) {
            out.println("An error occured, please make sure that you entered valid measurements.");
        }

    }//doPost

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public String singleMatch(String name1, String name2, DAL dal) {
        RPSComparator game = new RPSComparator();

        String rs = "";
        String[] player1 = new String[5];
        player1 = dal.getFromDB(name1);
        String[] player2 = new String[5];
        player2 = dal.getFromDB(name2);
        int scorePlayer = 0;
        for (int i = 0; i < 5; i++) {
            scorePlayer += game.compare(player1[i], player2[i]); // 
        }

        if (scorePlayer < 0) {
            rs = "you lost";
            dal.addToScore(name1, -1);
            dal.addToScore(name2, 1);

        } else if (scorePlayer > 0) {
            rs = "you won";
            dal.addToScore(name1, 1);
            dal.addToScore(name2, -1);
        } else {
            rs = "it's a draw";
        }
        return rs;

    }// singleMatch

    public void tournamentMatch(String name1, String name2, DAL dal, Map<String, Profile> playerMap) {
        RPSComparator game = new RPSComparator();

        String rs = "";
        String[] player1 = new String[5];
        player1 = dal.getFromDB(name1);
        String[] player2 = new String[5];
        player2 = dal.getFromDB(name2);
        int scorePlayer = 0;

        Profile scorePlayer1 = playerMap.get(name1);
        Profile scorePlayer2 = playerMap.get(name2);

        for (int i = 0; i < 5; i++) {
            scorePlayer += game.compare(player1[i], player2[i]); // 
        }

        if (scorePlayer < 0) { //player1 lost
            scorePlayer1.decreaseScore();
            scorePlayer2.increaseScore();

        } else if (scorePlayer > 0) { // player2 lost
            scorePlayer1.increaseScore();
            scorePlayer2.decreaseScore();
        }
        playerMap.put(name1, scorePlayer1);
        playerMap.put(name2, scorePlayer2);
    }// tournamentMatch

}