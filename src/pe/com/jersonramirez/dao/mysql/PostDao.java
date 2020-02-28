package pe.com.jersonramirez.dao.mysql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.com.jersonramirez.connections.ConnectionMysql;
import pe.com.jersonramirez.exceptions.NotCreated;
import pe.com.jersonramirez.exceptions.NotDeleted;
import pe.com.jersonramirez.exceptions.NotFound;
import pe.com.jersonramirez.exceptions.NotUpdated;
import pe.com.jersonramirez.interfaces.IConnection;
import pe.com.jersonramirez.interfaces.IPost;
import pe.com.jersonramirez.models.Post;

/**
 *
 * @author Jerson
 */
public class PostDao implements IPost{
    
    private PreparedStatement pr;
    private ResultSet rs;
    private CallableStatement cl;
    private IConnection conn;

    @Override
    public void create(Post e) throws NotCreated {
        
        try {
            
            conn = ConnectionMysql.getInstance();
            
            cl = conn.getConnection().prepareCall("call sp_insert_post(?,?,?,?,?)");
            
            cl.setString(1, e.getTitle());
            cl.setString(2, e.getUrlImage());
            cl.setTimestamp(3, new Timestamp(e.getDate().getTime()));
            cl.setInt(4, e.getIdUser());
            cl.setString(5, e.getDescription());
            
            rs = cl.executeQuery();
            
            if(rs.next()) {
                
                e.setId(rs.getInt(1));
                e.setUrlImage(rs.getString(2));//actualizamos la nueva url
                
            }else{
                
                throw new NotCreated("No se pudo crear el post");
                
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new NotCreated("No se pudo crear el post");
            
        } finally{
            
            cerrarConexiones();
            
        }    
        
    }

    @Override
    public Post read(int id) throws NotFound {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Post e) throws NotUpdated {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) throws NotDeleted {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Post> all() throws NotFound {
    
        List<Post> list = null;
        
        try {
            
            
            conn = ConnectionMysql.getInstance();
            
            pr = conn.getConnection().prepareStatement("select * from vposts order date desc");
            
            rs = pr.executeQuery();
            
            if(rs.next()){
                
                rs.previous();
                
                list = new ArrayList<>();
                
                Post p;
                
                while(rs.next()){
                    
                    p = new Post();
                    p.setId(rs.getInt(1));
                    p.setTitle(rs.getString(2));
                    p.setUrlImage(rs.getString(3));
                    p.setDate(new java.util.Date(rs.getTimestamp(4).getTime()));
                    p.setIdState(rs.getByte(5));
                    p.setIdUser(rs.getInt(6));
                    p.setDescription(rs.getString(7));
                    p.setIsSet(rs.getByte(8));
                    p.setCountReaction(rs.getInt(9));
                    
                    list.add(p);
                    
                }
                
            }else{
                
                throw new NotFound("No se encontraron publicaciones");
                
            }
            
        } catch (SQLException ex) {
            throw new NotFound("No se encontraron publicaciones");
        } finally{
            cerrarConexiones();
        }
        return list;
        
    }
    
    
    @Override
    public List<Post> all(int id) throws NotFound {
    
        List<Post> list = null;
        
        try {
            
            
            conn = ConnectionMysql.getInstance();
            
            pr = conn.getConnection().prepareStatement("select * from vposts where idUser = ? order by isSet desc , date desc");
            
            pr.setInt(1, id);
            
            rs = pr.executeQuery();
            
            if(rs.next()){
                
                rs.previous();
                
                list = new ArrayList<>();
                
                Post p;
                
                while(rs.next()){
                    
                    p = new Post();
                    p.setId(rs.getInt(1));
                    p.setTitle(rs.getString(2));
                    p.setUrlImage(rs.getString(3));
                    p.setDate(new java.util.Date(rs.getTimestamp(4).getTime()));
                    p.setIdState(rs.getByte(5));
                    p.setIdUser(rs.getInt(6));
                    p.setDescription(rs.getString(7));
                    p.setIsSet(rs.getByte(8));
                    p.setCountReaction(rs.getInt(9));
                    
                    list.add(p);
                    
                }
                
            }else{
                
                throw new NotFound("No se encontraron publicaciones");
                
            }
            
        } catch (SQLException ex) {
            throw new NotFound("No se encontraron publicaciones");
        } finally{
            cerrarConexiones();
        }
        return list;
        
    }
    
    private void cerrarConexiones() {
        
        if(rs!=null){
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(pr!=null){
            try {
                pr.close();
                pr = null;
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(cl!=null){
            try {
                cl.close();
                cl = null;
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        conn.CloseConnection();
        this.conn = null;
    
    }
    
}
