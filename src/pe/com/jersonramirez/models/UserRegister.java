package pe.com.jersonramirez.models;

import java.util.Date;

/**
 *
 * @author Jerson
 */
public class UserRegister {
    
    private int idUser;
    private Date fecha;
    private String token;
    private int activate;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getActivate() {
        return activate;
    }

    public void setActivate(int activate) {
        this.activate = activate;
    }
    
    
    
}
