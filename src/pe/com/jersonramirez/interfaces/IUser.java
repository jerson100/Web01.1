package pe.com.jersonramirez.interfaces;

import pe.com.jersonramirez.exceptions.NotUpdated;
import pe.com.jersonramirez.models.User;

/**
 *
 * @author Jerson
 */
public interface IUser extends ICrud<User>{
 
    User login(String username,String password);
    String validateToken(String token);
    void updateImageProfile(int id,String path) throws NotUpdated;
}
