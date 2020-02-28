package pe.com.jersonramirez.models;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pe.com.jersonramirez.enumerates.ETypeUser;

/**
 *
 * @author Jerson
 */
public class User {
    
    private int idUser;
    private String username;
    private String password;
    private ETypeUser TypeUser;
    private int idState;
    private String email;
    private String name;
    private String lastname;
    private Date fecha;
    private String urlImage;
    private String fb;
    private String tw;
    private String ins;
    private String youtube;
    private String description;
    private String urlPortada;
    private Date happyDate;
    private Date dateRegister;

    public User() {
    }
    
    public User(int idUser, String username, String password, ETypeUser TypeUser, int idState, String email, String name, String lastname, Date fecha, String urlImage, String fb, String tw, String ins, String youtube, String description, String urlPortada) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.TypeUser = TypeUser;
        this.idState = idState;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.fecha = fecha;
        this.urlImage = urlImage;
        this.fb = fb;
        this.tw = tw;
        this.ins = ins;
        this.youtube = youtube;
        this.description = description;
        this.urlPortada = urlPortada;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }
    
    

    public Date getHappyDate() {
        return happyDate;
    }

    public void setHappyDate(Date happyDate) {
        this.happyDate = happyDate;
    }
    
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ETypeUser getTypeUser() {
        return TypeUser;
    }

    public void setTypeUser(ETypeUser TypeUser) {
        this.TypeUser = TypeUser;
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getTw() {
        return tw;
    }

    public void setTw(String tw) {
        this.tw = tw;
    }

    public String getIns() {
        return ins;
    }

    public void setIns(String ins) {
        this.ins = ins;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlPortada() {
        return urlPortada;
    }

    public void setUrlPortada(String urlPortada) {
        this.urlPortada = urlPortada;
    }

    
    
}
