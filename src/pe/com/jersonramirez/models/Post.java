package pe.com.jersonramirez.models;

import java.util.Date;

/**
 *
 * @author Jerson
 */
public class Post {
    private int id;
    private String title;
    private String urlImage;
    private Date date;
    private byte idState;
    private int countReaction;
    private int idUser;
    private String description;
    private byte isSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte getIdState() {
        return idState;
    }

    public void setIdState(byte idState) {
        this.idState = idState;
    }

    public int getCountReaction() {
        return countReaction;
    }

    public void setCountReaction(int countReaction) {
        this.countReaction = countReaction;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getIsSet() {
        return isSet;
    }

    public void setIsSet(byte isSet) {
        this.isSet = isSet;
    }
    
}
