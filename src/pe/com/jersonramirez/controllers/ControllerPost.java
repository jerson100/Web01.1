package pe.com.jersonramirez.controllers;

import com.google.gson.Gson;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import pe.com.jersonramirez.dao.manager.DaoManager;
import pe.com.jersonramirez.enumerates.EDao;
import pe.com.jersonramirez.enumerates.ETypeUser;
import pe.com.jersonramirez.exceptions.NotCreated;
import pe.com.jersonramirez.interfaces.ICrud;
import pe.com.jersonramirez.models.Post;
import pe.com.jersonramirez.models.TypeUser;
import pe.com.jersonramirez.models.User;
import pe.com.jersonramirez.utils.JeFile;
import pe.com.jersonramirez.utils.TypeUserIcon;

/**
 *
 * @author Jerson
 */
@WebServlet(name = "ControllerPost", urlPatterns = {"/post"})
@MultipartConfig(fileSizeThreshold=5024*5024,
    maxFileSize=5024*5024*5, maxRequestSize=5024*5024*5*5)
public class ControllerPost extends HttpServlet {

    private ICrud<Post> dao;
    private Gson json;

    @Override
    public void init() throws ServletException {
        
        dao = DaoManager.getDaoManager(EDao.PostDao);
        json = new Gson();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try {
    		Thread.sleep(3000);
    	}catch (Exception e) {
		}
        request.setCharacterEncoding("UTF-8");
        
        if(request.getSession().getAttribute("user")!=null){
            
            String action = request.getParameter("action");
        
            log(action);
            
            if(action != null){

                switch(action){

                    case "register":

                        registerPost(request,response);

                        break;

                }

            }
            
        }else{
            
            //response.sendError(401,"Para poder publicar algo, primero debe iniciar sesión");
        	response.sendRedirect("login");
            
        }
        
    }

    private void registerPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        response.setContentType("Application/json;charset=UTF-8");
        
        Part img = request.getPart("img");
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Map<String,Object> map = new HashMap<>();
        int id = ((User)request.getSession().getAttribute("user")).getIdUser();
        
        String msg = "";
        boolean status = false;
        
        
        if(title != null && !title.isEmpty()){
            
            if(img != null && img.getSize()>0){
                
                try {
                    
                    InputStream input = img.getInputStream();//leemos el flujo de datos del archivo
                    
                    Date datePos = new Date();
                    
                    String extension = JeFile.validarImagen(img.getSubmittedFileName());
                    
                    if(extension!=null){
                        
                        String urlImage = datePos.getTime()+"."+extension;
                    
                        Post p = new Post();
                        
                        p.setDate(datePos);
                        p.setTitle(title);
                        p.setDescription(description);
                        p.setUrlImage(urlImage);
                        p.setIdUser(id);
                        
                        dao.create(p);
                        
                        //si se inserta correctamente, entonces insertamos la imagen en la carpeta
                        
                        insertFileInServer(input,p.getUrlImage(),this.getServletContext().getRealPath(""));
                        
                        msg = "La publicación se llevo a cabo exitosamente";
                        
                        status = true;
                        User ses = (User)(request.getSession().getAttribute("user"));
                        map.put("post", p);
                        map.put("urlImgUser", ses.getUrlImage());
                        map.put("username", ses.getUsername());
                        List<TypeUser> type = TypeUserIcon.getListIconTypeUser(ses.getTypeUser());
                        map.put("typeUser", type);
                        
                    }else{
                        
                        msg = "La extensión de la imagen no es válida";
                        
                    }
                    
                } catch (NotCreated ex) {
                
                    msg = ex.getMessage();
                    
                }
                
            }else{
                
                msg = "Ingrese la imagen a publicar";
                
            }
            
        }else{
            
            msg = "Ingrese el título de la publicación";
            
        }
        
        map.put("message", msg);
        map.put("status",status);
        try(Writer w  = response.getWriter()){
            w.write(json.toJson(map));
        }
    }

    

	private void insertFileInServer(InputStream input, String urlImage, String realPath) throws IOException{
    
        realPath = realPath.replaceAll("[\\\\]build","");
        
        FileOutputStream output = null;
        
        try {
            output = new FileOutputStream(realPath+"\\"+urlImage);
            int leido = 0;
            leido = input.read();
            while (leido != -1) {
                output.write(leido);
                leido = input.read();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                output.flush();
                output.close();
                input.close();
            } catch (IOException ex) {
            }
        }
        
        
        
    }

   
}
