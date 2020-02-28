package pe.com.jersonramirez.controllers;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import pe.com.jersonramirez.dao.manager.DaoManager;
import pe.com.jersonramirez.dao.mysql.PostDao;
import pe.com.jersonramirez.enumerates.EDao;
import pe.com.jersonramirez.exceptions.NotFound;
import pe.com.jersonramirez.exceptions.NotUpdated;
import pe.com.jersonramirez.interfaces.ICrud;
import pe.com.jersonramirez.interfaces.IUser;
import pe.com.jersonramirez.models.Post;
import pe.com.jersonramirez.models.TypeUser;
import pe.com.jersonramirez.models.User;
import pe.com.jersonramirez.utils.JeFile;
import pe.com.jersonramirez.utils.TypeUserIcon;

/**
 *
 * @author Jerson
 */
@WebServlet(name = "ControllerProfile", urlPatterns = {"/users", "/users/profile"})
@MultipartConfig
public class ControllerUser extends HttpServlet {

    private Gson JSON;

    @Override
    public void init() throws ServletException {

        JSON = new Gson();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = request.getServletPath().substring(1);

        System.out.println(url);

        switch (url) {

            case "users":

                listarUsuarios(request, response);

                break;

            case "users/profile":

                mostrarPerfil(request, response);

                break;

            default:

                response.sendError(404, "El recurso solicitado no es válido, by jerson");

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControllerUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        String id = request.getParameter("id");
        String action = request.getParameter("action");

        if (id != null && id.matches("[0-9]+")) {

            if (action != null) {

                switch (action) {

                    case "updatedProfile":
                        updateImageUser(request, response);
                }

            }else{
                
                response.sendError(404,"No se puede procesar la petición...");
                
            }

        }else{
            
            response.sendError(404,"No se puede procesar la petición...");
            
        }
        
    }
    
    

    private void mostrarPerfil(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String id = request.getParameter("id");

        if (id != null && id.matches("[0-9]+")) {

            ICrud<User> dao = DaoManager.getDaoManager(EDao.UserDao);

            try {

                User us = dao.read(Integer.parseInt(id));

                request.setAttribute("datauser", us);

                List<TypeUser> typeUser = TypeUserIcon.getListIconTypeUser(us.getTypeUser());

                PostDao postDao = (PostDao)DaoManager.getDaoManager(EDao.PostDao);
                
                List<Post> posts = null;
                
                try{
                    
                    posts = postDao.all(Integer.parseInt(id));
                    
                }catch(NotFound ex){}
                
                request.setAttribute("posts", posts);
                
                request.setAttribute("typeUser", typeUser);

                request.getRequestDispatcher("/WEB-INF/user/profile.jsp").forward(request, response);

            } catch (NotFound ex) {

                response.sendError(404, "El perfil no existe");

            }

        } else {

            response.sendError(404, "El recurso solicitado no es válido");

        }

    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        
        try(Writer w = response.getWriter()){
            w.write("Aquí se mostrará la lista de usuarios registrados, para que todos lo puedan ver y seguir");
        }

    }

    private void updateImageUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        IUser dao = (IUser) DaoManager.getDaoManager(EDao.UserDao);

        Part img = request.getPart("img");

        Map<String, Object> map = new HashMap<>();

        String message = "No se pudo actualizar la imagen";

        String pathBd = null;

        boolean state = false;

        if (request.getSession().getAttribute("user") == null) {

            response.sendError(401, "Acceso denegado... BYE");

        } else {

            if (img != null && img.getSize() > 0) {

                String extension = JeFile.validarImagen(img.getSubmittedFileName());

                if (extension != null) {

                    try {

                        //directorio donde se almacenará las imagenes de los perfiles de los usuarios
                        String mkdr = "imgs\\users";

                        //obtenemos el id del usuario
                        String idUser = request.getParameter("id");

                        //obtenemos la ruta relativa de la imagen y la guardamos en la bd
                        pathBd = mkdr + "\\" + idUser + "-"+(new Date().getTime())+ "." + extension;

                        //almacenamos la ruta relativa en la bd, siempre y cuando exista ese usuario
                        dao.updateImageProfile(Integer.parseInt(idUser), pathBd);

                        message = "Imagen actualizada exitosamente";
                        
                        map.put("id", request.getParameter("id"));
                        
                        //actualizamos la imagen del usuario de la sessión
                        
                        User us = (User)request.getSession().getAttribute("user");
                        
                        us.setUrlImage(pathBd);

                        state = true; //para decirle al cliente web, que todo se llevo correctamente a cabo

                        //obtenemos la ruta relativa del contexto de la aplicación
                        String rutaRelativa = this.getServletContext().getRealPath("").replaceAll("[\\\\]build", "");
                        
                        verificarProfile(rutaRelativa + "\\" + mkdr, idUser);
                        
                        //si se llega a guardar la ruta relativa, entonces recién almacenamos la imagen en el servidor
                        //obtenemos el flujo de entrada de datos.
                        InputStream input = img.getInputStream();

                        //ruta completa, donde se debe almacenar la imagen
                        String path = rutaRelativa + "\\" + pathBd;

                        System.out.println(path);

                        //Ahora declaramos un flujo de salida de datos, para poder almacenar la imagen
                        FileOutputStream output = new FileOutputStream(path);

                        int read;

                        while ((read = input.read()) != -1) {

                            output.write(read);

                        }

                        //cerramos los flujos de entrada y salida...
                        output.close();

                        input.close();

                    } catch (NotUpdated ex) {

                        message = ex.getMessage();

                    }

                } else {

                    message = "La imagen no es un formato admitido";

                }

            }

            map.put("message", message);
            map.put("status", state);
            map.put("newImage", pathBd);

            //ahora enviamos el map en formato json al cliente.
            response.setContentType("Application/json;charset=UTF-8");
            try(Writer w  = response.getWriter()){
                        w.write(JSON.toJson(map));
            }
    

        }

    }

    private boolean verificarProfile(String string, String id) {
    
       File file = new File(string);
        
       if(file.canRead() && file.canWrite()){
           
           File[] fileList = file.listFiles();
           
           for(File f:fileList){
               
               if(f.getName().indexOf(id)==0){
                   
                   f.delete();//borramos el archivo
                   
                   return true;
                   
               }
               
               System.out.println(f.getName());
               
           }
           
       }
       
       return false;
       
    }

}
