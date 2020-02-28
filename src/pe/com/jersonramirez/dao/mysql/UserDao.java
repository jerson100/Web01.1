package pe.com.jersonramirez.dao.mysql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import pe.com.jersonramirez.connections.ConnectionMysql;
import pe.com.jersonramirez.enumerates.ETypeUser;
import pe.com.jersonramirez.exceptions.NotCreated;
import pe.com.jersonramirez.exceptions.NotDeleted;
import pe.com.jersonramirez.exceptions.NotFound;
import pe.com.jersonramirez.exceptions.NotUpdated;
import pe.com.jersonramirez.interfaces.IConnection;
import pe.com.jersonramirez.interfaces.IUser;
import pe.com.jersonramirez.models.User;

/**
 *
 * @author Jerson
 */
public class UserDao implements IUser{
    
    private PreparedStatement pr;
    private CallableStatement cl;
    private ResultSet rs;
    private IConnection conn;
    
    public UserDao(){
       
    }
    
    @Override
    public void create(User e) throws NotCreated {
        
        try {
            this.conn = ConnectionMysql.getInstance();
            this.conn.getConnection().setAutoCommit(false);
            CallableStatement cl1 = this.conn.getConnection().prepareCall("call sp_insert_user(?,?,?,?,?)");
            cl1.setString(1, e.getName());
            cl1.setString(2, e.getLastname());
            cl1.setString(3, e.getUsername());
            cl1.setString(4, e.getEmail());
            cl1.setString(5, DigestUtils.md5Hex(e.getPassword()));//PASAMOS A MD5
            rs = cl1.executeQuery();
            
            rs.next();//movemos el puntero
            
            int idUser = rs.getInt(1);
                e.setIdUser(idUser);
                //java.util.Date dt = new java.util.Date();
                String token = DigestUtils.md5Hex(e.getPassword() + e.getFecha().getTime() + e.getIdUser());
                
                
            CallableStatement cl2 = this.conn.getConnection().prepareCall("call sp_insert_userregister(?,?,?)");
            cl2.setInt(1, idUser);
            cl2.setTimestamp(2, new Timestamp(e.getFecha().getTime()));
            cl2.setString(3, token);
            
            cl2.executeUpdate();
            
            this.conn.getConnection().commit();
            
        } catch (SQLException ex) {
            try {
                this.conn.getConnection().rollback();
                throw new NotCreated("No se pudo crear al usuario");
            } catch (SQLException ex1) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally{
            cerrarConexiones();
        }
        
    }
    
    
    @Override
    public User read(int id) throws NotFound {
        
        User us = null;
        
        try {
            
            conn = ConnectionMysql.getInstance();
            
            pr = conn.getConnection().prepareStatement("select * from users where idUser = ?");
            pr.setInt(1, id);
            
            rs = pr.executeQuery();
            
            if(rs.next()){
             
                us = new User();
                us.setIdUser(rs.getInt(1));
                us.setName(rs.getString(2));
                us.setLastname(rs.getString(3));
                us.setUsername(rs.getString(4));
                us.setEmail(rs.getString(5));
                String type = rs.getString(7);
                us.setTypeUser(type.equals("US")?ETypeUser.US:type.equals("ADM")?ETypeUser.ADM:ETypeUser.US);//luego modificamos
                us.setIdState(rs.getInt(8));
                us.setUrlImage(rs.getString(9));
                us.setFb(rs.getString(10));
                us.setTw(rs.getString(11));
                us.setIns(rs.getString(12));
                us.setYoutube(rs.getString(13));
                us.setDescription(rs.getString(14));
                us.setUrlPortada(rs.getString(15));
                if(rs.getDate(16) !=null){
                    us.setHappyDate(new java.util.Date(rs.getDate(16).getTime()));   
                }
                us.setDateRegister(new java.util.Date(rs.getTimestamp(17).getTime()));
            }else{
                
                throw new NotFound("No se encontró el usuario");
                
            }
            
        } catch (SQLException ex) {
            
            throw new NotFound("No se encontró el usuario");
            
        } finally{
            
            cerrarConexiones();
            
        }
        
        return us;
        
    }

    @Override
    public void update(User e) throws NotUpdated {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) throws NotDeleted {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> all() throws NotFound {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public User login(String username, String password) {
    
        User us = null;
        
        try {
            
            conn = ConnectionMysql.getInstance();
            
            pr = conn.getConnection().prepareStatement("select * from users where username=? and password=? and idState=2");
            pr.setString(1, username);
            pr.setString(2, DigestUtils.md5Hex(password));
            
            rs = pr.executeQuery();
            
            if(rs.next()){
             
                us = new User();
                us.setIdUser(rs.getInt(1));
                us.setName(rs.getString(2));
                us.setLastname(rs.getString(3));
                us.setUsername(rs.getString(4));
                us.setEmail(rs.getString(5));
                String type = rs.getString(7);
                us.setTypeUser(type.equals("US")?ETypeUser.US:type.equals("ADM")?ETypeUser.ADM:ETypeUser.US);//luego modificamos
                us.setIdState(rs.getInt(8));
                us.setUrlImage(rs.getString(9));
                us.setFb(rs.getString(10));
                us.setTw(rs.getString(11));
                us.setIns(rs.getString(12));
                us.setYoutube(rs.getString(13));
                us.setDescription(rs.getString(14));
                us.setUrlPortada(rs.getString(15));
                if(rs.getDate(16) !=null){
                    us.setHappyDate(new java.util.Date(rs.getDate(16).getTime()));   
                }
                us.setDateRegister(new java.util.Date(rs.getTimestamp(17).getTime()));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally{
            
            cerrarConexiones();
            
        }
        
        return us;
        
    }

    @Override
    public String validateToken(String token) {
        
        String response = null;
        
        try {
            
            conn = ConnectionMysql.getInstance();
            this.conn.getConnection().setAutoCommit(false);
            pr = conn.getConnection().prepareStatement("select * from UserRegister where token = ?");
            pr.setString(1, token);
            rs = pr.executeQuery();
            
            if(rs.next()){
                
                int id = rs.getInt(1);
                
                if(rs.getInt(4) == 1){
                
                    response = "La cuenta ya está activada, no es necesario el token.";
                    
                }else{
                    
                    pr = conn.getConnection().prepareStatement("update users set idState = 2 where idUser=?");
                
                    pr.setInt(1, id);

                    pr.executeUpdate();

                    pr = conn.getConnection().prepareStatement("update UserRegister set activate = 1 where idUser = ?");
                    pr.setInt(1, id);
                    pr.executeUpdate();
                    
                    response = "Su cuenta se a registrado con exito, ahora puede iniciar sesión";
                    
                }
                
            }else{
                
                response = "Token no válido";
                
            }
            
            this.conn.getConnection().commit();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                
                this.conn.getConnection().rollback();
                
            } catch (SQLException ex1) {ex1.printStackTrace();}
            
        } finally{
            
            cerrarConexiones();
            
        }
        
        return response;
        
    }

    @Override
    public void updateImageProfile(int id, String path) throws NotUpdated {
    
        try {
            conn = ConnectionMysql.getInstance();
            
            pr = conn.getConnection().prepareStatement("update users set urlImage = ? where idUser = ?");
            
            pr.setString(1, path);
            pr.setInt(2, id);
            
            if( pr.executeUpdate() == 0){
                
                throw new NotUpdated("No se pudo actualizar la foto de perfil del usuario");
                
            }
            
        } catch (SQLException ex) {
            
            throw new NotUpdated("No se pudo actualizar la foto de perfil del usuario");
            
        } finally{
            
            cerrarConexiones();
            
        }
        
    }

    @Override
    public List<User> all(int id) throws NotFound {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
