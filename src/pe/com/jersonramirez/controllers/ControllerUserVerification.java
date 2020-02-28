package pe.com.jersonramirez.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.com.jersonramirez.dao.manager.DaoManager;
import pe.com.jersonramirez.dao.mysql.UserDao;
import pe.com.jersonramirez.enumerates.EDao;

/**
 *
 * @author Jerson
 */
@WebServlet(name = "ControllerUserVerification", urlPatterns = {"/user/verification"})
public class ControllerUserVerification extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String token = request.getParameter("token");
        
        UserDao dao = (UserDao) DaoManager.getDaoManager(EDao.UserDao);
        
        String opc = dao.validateToken(token);
        
        request.setAttribute("message", opc);
        
        request.getRequestDispatcher("/WEB-INF/user/user_verification.jsp").forward(request, response);
        
    }

   
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

 
}
