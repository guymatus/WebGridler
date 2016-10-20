/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridler.controllers;

import Logic.Engine;
import Logic.IGameManager;
import gridler.Utilities.Utilities;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Danny
 */
@WebServlet(name = "LoadXmlController", urlPatterns = {"/LoadXmlController"})
@MultipartConfig()
public class LoadXmlController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        IGameManager gameManager = new Engine();
        try {
            String name =(String) request.getSession().getAttribute("username");
            String type= (String) request.getSession().getAttribute("userType");
            gameManager.loadXml(filePart.getInputStream(),name,type);
        } catch (Exception ex) {
            ex.printStackTrace(response.getWriter());
            return;
        }
        Utilities.getRunningGamesList(getServletContext()).add(gameManager);
        response.getWriter().print("success");
    }
}
