package storage;

import database.DbConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author annachristofaris
 */
public class CatalogController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = null;
        String url = "/categories.jsp";

        String value = null;

        // Initialize the database and get all items
        ItemDB database = new ItemDB();
        boolean favorited = false;
        HttpSession session = request.getSession();

        //pull items by category from sql item table
        ArrayList<Item> popularItems = database.getItemsByCat("popular");
        session.setAttribute("popularItems", popularItems);

        ArrayList<Item> tipItems = database.getItemsByCat("tips");

        request.setAttribute("tipItems", tipItems);

        if (request.getParameter("action") != null) {
            try {
                action = ESAPI.validator().getValidInput("action", request.getParameter("action"), "SafeString", 200, false);
            } catch (ValidationException | IntrusionException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (request.getParameter("value") != null) {
            try {
                value = ESAPI.validator().getValidInput("value", request.getParameter("value"), "SafeString", 200, false);
            } catch (ValidationException | IntrusionException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (null == action) {

            getServletContext().getRequestDispatcher(url).forward(request, response);

        } else switch (action) {
            case "":
                getServletContext().getRequestDispatcher(url).forward(request, response);
                break;
            case "details":
                {
                    url = "/item.jsp";
                    // New java bean
                    Item detailItem = database.getItem(value);
                    request.setAttribute("detailItem", detailItem);
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                    break;
                }
            case "rating":
                {
                    url = "/item.jsp";
                    String rating = null;
                    try {
                        rating = ESAPI.validator().getValidInput("newRating", request.getParameter("newRating"), "SafeString", 200, false);
                    } catch (ValidationException | IntrusionException ex) {
                        Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    }       String fav = null;
                    try {
                        fav = ESAPI.validator().getValidInput("favorite", request.getParameter("favorite"), "SafeString", 200, true);
                    } catch (ValidationException | IntrusionException ex) {
                        Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    }       if (fav != null) {
                        favorited = true;
                    }       Item detailItem = database.getItem(value);
                    detailItem.setRating(rating);
                    request.setAttribute("detailItem", detailItem);
                    //check if rating submittal is coming from a logged-in user
                    UserProfile currentProfile = (UserProfile) session.getAttribute("userCP");
                    //check if item is in User Profile's list
                    if (currentProfile != null) {
                        if (currentProfile.inList(value)) {
                            //update userItem sql table
                            database.updateFeedback(currentProfile, value, rating);
                            database.updateFavorited(currentProfile, value, favorited);
                            
                            //send back to their list after feedback is submitted
                            url = "/myItems.jsp";
                        }
                        
                    }       getServletContext().getRequestDispatcher(url).forward(request, response);
                    break;
                }
            default:
                break;
        }
    }

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

        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
