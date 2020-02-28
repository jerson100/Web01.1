package pe.com.jersonramirez.utils;

import java.util.ArrayList;
import java.util.List;

import pe.com.jersonramirez.enumerates.ETypeUser;
import pe.com.jersonramirez.models.TypeUser;

public class TypeUserIcon {
	public static List<TypeUser> getListIconTypeUser(ETypeUser typeUser) {
    	List<TypeUser> l = null;
    	TypeUser l1 = new TypeUser("Logo de usuario de administración","imgs/svg/rey.svg");
    	TypeUser l2 = new TypeUser("Logo del creador de la página","imgs/svg/logo.svg");
    	TypeUser l3 = new TypeUser("Logo del creador de la página","imgs/svg/moderator.svg");
        switch (typeUser) {
            case ADM:
            	l = new ArrayList<>();
            	l.add(l1);
            	l.add(l2);
                break;
            case MOD:
            	l = new ArrayList<>();
            	l.add(l3);
                break;
            case US:
        }
        return l;
	}
}
