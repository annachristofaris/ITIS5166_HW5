package storage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
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
public class ProfileController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.owasp.esapi.errors.ValidationException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String action = null;

        String url = "/myItems.jsp";
        HttpSession session = request.getSession(false);
        boolean invalidLogin = false;
        //load user and item database
        UserDB userDatabase = new UserDB();
        ArrayList<User> users = userDatabase.getAllUsers();

        ItemDB itemDatabase = new ItemDB();

        User currentUser = (User) session.getAttribute("theUser");
        UserProfile currentProfile = (UserProfile) session.getAttribute("userCP");

        try {
            action = ESAPI.validator().getValidInput("action", request.getParameter("action"), "SafeString", 200, false);
        } catch (ValidationException | IntrusionException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //sign-in functionality 
        if (action.equals("signIn")) {
            String username = null;
            String password = null;
            
            //check sign-in inputs
            if (request.getParameter("username") != null && request.getParameter("password") != null) {
                try {
                    username = ESAPI.validator().getValidInput("username", request.getParameter("username"), "Email", 200, false);
                    password = ESAPI.validator().getValidInput("password", request.getParameter("password"), "SafeString", 200, false);
                } catch (ValidationException | IntrusionException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }

                //username and password are validated
                if (validateLogin(username, password)) {
                    for (int i = 0; i < users.size(); i++) {
                        if (username.equals(users.get(i).getEmail())) {
                            currentUser = users.get(i);

                            currentProfile = new UserProfile(currentUser);
                            session = request.getSession(true);

                            //assign session attributes
                            session.setAttribute("theUser", currentUser);
                            session.setAttribute("userCP", currentProfile);
                            url = "/myItems.jsp";

                            currentProfile.getSavedItems();
                            ArrayList<UserItem> userList = currentProfile.getItemList();
                            request.setAttribute("userList", userList);
                            session.removeAttribute("guestUser");
                        }
                    }
                } else {
                   //username and/or password are not valid
                    url = "/login.jsp";
                }
                invalidLogin = true;
                session.setAttribute("invalidLogin", invalidLogin);
            } else {
                url = "/login.jsp";
                session.setAttribute("invalidLogin", invalidLogin);
            }
            getServletContext().getRequestDispatcher(url).forward(request, response);
        }

        if (action.equals("signout")) {
            session.removeAttribute("theUser");
            session.removeAttribute("userCP");
            request.getSession(false).invalidate();
            response.sendRedirect("./index.jsp");
        }

        if (currentProfile != null && !action.equals("signout")) {
            if (request.getSession().getAttribute("theUser") != null) {
                url = "/myItems.jsp";
            }

            if (action.equals("viewMyCorner")) {
                url = "/myItems.jsp";
                request.getSession().setAttribute("userCP", currentProfile);
            }

            if (action.equals("save")) {
                url = "/myItems.jsp";
              
                String savedItemCode = null;
                 try {
                        savedItemCode = ESAPI.validator().getValidInput("itemCode", request.getParameter("itemCode"),"SafeString", 200, false);
                        } catch (ValidationException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IntrusionException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                      //check that item codes belong to item in DB
                if (verifyItem(savedItemCode) && !currentProfile.inList(savedItemCode)) {
                    itemDatabase.addUserItem(currentProfile, savedItemCode);
                }
            }
            if (action.equals("feedback")) {
                url = "/feedback.jsp";
                // New java bean
                String savedItemCode = null;
                 try {
                        savedItemCode = ESAPI.validator().getValidInput("itemCode", request.getParameter("itemCode"),"SafeString", 200, false);
                        } catch (ValidationException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IntrusionException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                Item detailItem = itemDatabase.getItem(savedItemCode);
                request.setAttribute("detailItem", detailItem);

            }
            if (action.equals("delete")) {
                url = "/myItems.jsp";
                String removeItem = null;
                 try {
                        removeItem = ESAPI.validator().getValidInput("itemCode", request.getParameter("itemCode"),"SafeString", 200, false);
                        } catch (ValidationException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IntrusionException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                itemDatabase.removeUserItem(currentProfile, removeItem);
            }
            getServletContext().getRequestDispatcher(url).forward(request, response);

        } else if (currentProfile == null) {

            boolean guestUser = true;
            session.setAttribute("guestUser", guestUser);
            if (action.equals("feedback") || action.equals("save")) {
                 String savedItemCode = null;
                 try {
                        savedItemCode = ESAPI.validator().getValidInput("itemCode", request.getParameter("itemCode"),"SafeString", 200, false);
                        } catch (ValidationException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IntrusionException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                url = "./CatalogController?action=details&value=" + savedItemCode;
            }
            if (action.equals("viewMyCorner")) {
                url = "./myItems.jsp";
            }
            response.sendRedirect(url);
        }
    }

    //helper methods for verifying input
    public boolean validateLogin(String username, String password) {
        UserDB userDB = new UserDB();
        ArrayList<User> users = userDB.getAllUsers();
        boolean validUser = false;
        boolean verified = false;

        //check if User is in registered/in database
        for (int i = 0; i < users.size(); i++) {
            if (username.equals(users.get(i).getEmail())) {
                validUser = true;
            }
        }

        //check password for  user's email
        if (validUser) {
            for (int i = 0; i < users.size(); i++) {
                if (username.equals(users.get(i).getEmail()) && password.equals(users.get(i).getPassword())) {
                    verified = true;
                }
            }
        }
        return verified;
    }

    public boolean verifyItem(String itemCode) {
        boolean inDB = false;
        ArrayList<Item> items = ItemDB.getAllItems();
        Iterator<Item> itr = items.iterator();
        while (itr.hasNext()) {
            Item currentItem = itr.next();
            if ((currentItem.getItemCode().equals(itemCode))) {
                inDB = true;
            }
        }
        return inDB;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
