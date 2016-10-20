package gridler.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import gridler.Utilities.Utilities;
import gridler.types.UserType;
import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amit Elbaz
 */
@WebServlet(urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

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

        try {
            String userNameParam = request.getParameter("user");
            String userType = request.getParameter("type");

            Map<String, UserType> userList = Utilities.getUsersList(getServletContext());
            if (userList.containsKey(userNameParam)) {
                throw new ServletException("User already exist. must choose a different username");
            }

            userList.put(userNameParam, Utilities.getUserTypeByString(userType));

            request.getSession().setAttribute("username", userNameParam);
            request.getSession().setAttribute("userType", userType);
            
            response.sendRedirect("DashboardController");
        } catch (ServletException ex) {
            request.setAttribute("error", ex.getMessage());
            getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login controller";
    }
}
