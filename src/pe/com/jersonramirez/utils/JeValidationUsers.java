package pe.com.jersonramirez.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jerson
 */
public class JeValidationUsers {
    
    /*
        El username de los usuarios debe contener las siguientes restricciones:
        
        1.- Solo deben contener contener letras [a-zA-Z] y números [0-9].
        2.- La longitud mínima carácteres es 9 y máximo 20.
        3.- Debe contener al menos un número.
        4.- No debe iniciar en un número.
        
    */
    
    public static boolean validateUsername(String username){
        final Pattern pattern1 = Pattern.compile("(?=\\b[^\\d]+[\\d][a-zA-Z\\d]*)[a-zA-Z\\d]{9,20}");
        final Pattern pattern2 = Pattern.compile("[a-zA-Z](?:[a-zA-Z\\d]){8,19}");
        Matcher matcher = pattern1.matcher(username);
        return matcher.matches();//jerson100
    }
    
    public static void main(String[] args) {
        
        //System.out.println(validateUsername("jerson100"));
        
        //System.out.println(validateEmail("juamkooo@gmail.com"));
        
        System.out.println(validatePassword("don5toaadoRa#a"));
        
        Pattern p = Pattern.compile(".*[a-z]");
        // (?=[d-f])bz
        Matcher m = p.matcher("sadwdwwaS*");
        while(m.find()){
            System.out.println(m.group());
        }
        System.out.println(m.matches());
    }

    public static boolean validateEmail(String email) {
    
        final Pattern pattern1 = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4}",Pattern.CASE_INSENSITIVE);
        
        Matcher matcher = pattern1.matcher(email);
        
        return matcher.matches();
    
    }

    public static boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])"
                            + "(?=.*[!@#$&*])[a-zA-Z\\d|!@#$&*]{10,20}$";
        return Pattern
                .compile(regex)
                .matcher(password)
                .matches();
    }
    
}
