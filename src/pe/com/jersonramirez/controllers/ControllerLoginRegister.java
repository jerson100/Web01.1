package pe.com.jersonramirez.controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import pe.com.jersonramirez.dao.manager.DaoManager;
import pe.com.jersonramirez.dao.mysql.UserDao;
import pe.com.jersonramirez.enumerates.EDao;
import pe.com.jersonramirez.exceptions.NotCreated;
import pe.com.jersonramirez.interfaces.ICrud;
import pe.com.jersonramirez.models.User;
import pe.com.jersonramirez.utils.JeValidationUsers;
import pe.com.jersonramirez.utils.Mail;

/**
 *
 * @author Jerson
 */
@WebServlet(name = "ControllerLogin", urlPatterns = {"/login", "/registro"})
@MultipartConfig
public class ControllerLoginRegister extends HttpServlet {
    
    private static Gson JSON;

    @Override
    public void init() throws ServletException {
        
        JSON = new Gson();
        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath().substring(1);

        request.setAttribute("path", path);

        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        
        try {
            Thread.sleep(4000);
            
            String action = request.getParameter("action");
            
            if (action == null) {
                
                response.setCharacterEncoding("UTF-8");
                
                response.sendError(404, "No se encontró el recurso solicitado");
                
            } else {
                
                switch (action) {
                    
                    case "acceder":
                        
                        acceder(request,response);
                        
                        break;
                        
                    case "registrar":
                        
                        registrarUsuario(request,response);
                        
                        break;
                        
                    default:
                        
                        response.sendError(404, "No se encontró el recurso solicitado");
                        
                }
                
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ControllerLoginRegister.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private String validarUsuario(User us){
    
        
        /*
            Nombre
        */
        
        if(us.getName()==null || us.getName().isEmpty()){
            return "El nombre no debe estar vacío";
        }
        
        
        if(!us.getName().matches("^[a-z|A-Z|áéíóúÁÉÍÓÚ| ]+$")){
            
            return "El nombre ingresado no es correcto, solo puede contener las siguientes letras a-z, áéíóú";
            
        }
        
        if(us.getName().length()>45){
            
            return "El nombre no puede superar los 45 carácteres";
            
        }
        
        /*
            APELLIDO
        */
        
         if(us.getLastname()==null || us.getLastname().isEmpty()){
            return "El apellido no debe estar vacío";
        }
        
         
            System.out.println(us.getLastname());
            
         
        if(!us.getLastname().matches("^[a-z|A-Z|áéíóúÁÉÍÓÚ| ]+$")){
            
            return "El apellido ingresado no es correcto, solo puede contener las siguientes letras: a-z, áéíóú";
            
        }
        
        if(us.getLastname().length()>70){
            
            return "El apellido no puede superar los 70 carácteres";
            
        }
        
        //username
        
        if(us.getUsername() ==null || us.getUsername().isEmpty()){
            
            return "El username no puede estar vacío";
            
        }
        
        if(!JeValidationUsers.validateUsername(us.getUsername())){
        
            return "El username ingresado no es correcto, asegúrese de seguir las siguientes"
                    + " restricciones:"
                    + " Solo se contempla números y letras en un rango de {9-20},"
                    + " debe contener un número como mínimo pero no al inicio";
            
        }
        
        if(us.getEmail() == null || !JeValidationUsers.validateEmail(us.getEmail())){
            
            return "El correo no es válido";
            
        }
        
        if(us.getPassword() == null || !JeValidationUsers.validatePassword(us.getPassword())){
            
            return "La contraseña debe tener como mínimo 10 carácteres y 20 carácteres,"
                    + " además contener como mínimo una letra mínuscula, una mayúzcula,"
                    + " un dígito y un carácter especial:"
                    + "!@#$&*";
            
        }
        
        return null;
    
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
        response.setContentType("application/json;charset=UTF-8");
        
        
        User us = new User();
        
        us.setName(request.getParameter("nombre"));
        us.setLastname(request.getParameter("apellidos"));
        us.setUsername(request.getParameter("username"));
        us.setPassword(request.getParameter("password"));
        us.setEmail(request.getParameter("email"));
        
        Map<String, Object> map = new HashMap<>();
        System.out.println(us);
        
        String msg = validarUsuario(us);
        String username = null;
        boolean estado  = false;
        
        if(msg == null){
        
            ICrud<User> dao = DaoManager.getDaoManager(EDao.UserDao);
            
            try {
                
                us.setFecha(new Date());
                
                dao.create(us);
                
                estado = true;
                
                msg = "Por favor active su cuenta con el link que se le enviará a su correo y luego inicie sesión.";
                
                enviarCorreo(us);
                
                username = us.getUsername();
                
            } catch (NotCreated ex) {
            
                msg = ex.getMessage();
                
            }

            map.put("usuario", username);
            
        }
        
        map.put("mensaje", msg);
        map.put("estado", estado);
        
        
        response.getWriter().write(JSON.toJson(map));
        
    
    }

    private void acceder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        if(request.getSession().getAttribute("user") != null){
            
            response.sendRedirect("/home");
            
            return;
            
        }
        
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            
            String username = request.getParameter("username");
            String pass = request.getParameter("password");
            
            UserDao dao = (UserDao) DaoManager.getDaoManager(EDao.UserDao);
            
            User us = dao.login(username, pass);
            
            Map<String,Object> map = new HashMap<>();
            
            if(us!=null){
            
                //creamos la sesión
                HttpSession session = request.getSession();
                
                session.setAttribute("user", us);
                
            }
            
            map.put("estado", us!=null);
            map.put("mensaje", (us!=null?"Bienvenido : "+us.getUsername():"Verifique su usuario o contraseña."));
            
            response.getWriter().write(JSON.toJson(map));
            
        } catch (IOException ex) {}
        
    }

    private boolean enviarCorreo(User us) {
    
        try {
            Mail correo = new Mail();
            correo.setDestination(us.getEmail());
            correo.setMail("juamkoo@gmail.com");
            correo.setIssue("Verifica tu cuenta");
            correo.setMessage("<p>Hola "+us.getName()+"</p>"
                    + "<h2>Ya casi está</h2>"
                    + "<p>Bienvenido a jersonramirez. Antes de empezar, tenemos que verificar"
                    + "su dirección de correo electrónico.</p>"
                    + "<p>Haga click en el vínculo que aparece a continuación</p>"
                    + "<a href='http://localhost:21682/ProyectoWeb01.1/user/verification?token="+ DigestUtils.md5Hex(us.getPassword() + us.getFecha().getTime() + us.getIdUser()) +">Link</a>"
                            + "<br/>"
                            + "<p>Nos alegramos que pertenezca a nuestra familia.</p>"
                            + "<p>Atentamente: <strong>Jerson Ramírez Ortiz</strong> - ADM</p>");
            correo.setPass("donatoamador");
            
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable","true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", correo.getMail());
            p.setProperty("mail.smtp.auth", "true");
            
            Session s = Session.getDefaultInstance(p, null);
            
            BodyPart text = new MimeBodyPart();
            text.setText(correo.getMessage());
          
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(text);
            
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress(correo.getMail()));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correo.getDestination()));
            mensaje.setSubject(correo.getIssue());
            mensaje.setContent(m);
            
            Transport t = s.getTransport("smtp");
            
            t.connect(correo.getMail(),correo.getPass());
            
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            
            t.close();
            
            return true;
            
        } catch (MessagingException ex) {
            
            ex.printStackTrace();
            
            return false;
            
        }
        
    }

}
