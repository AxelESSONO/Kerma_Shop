package com.obiangetfils.kermashop.utills.KermaUtil;

import com.obiangetfils.kermashop.models.UserOBJ;

public class KermaAuth {

    public static UserOBJ CURRENT_USER;



    public static UserOBJ getCurrentUser(String typeUser) {
        return CURRENT_USER;
    }

    public static void setCurrentUser(UserOBJ currentUser) {
        CURRENT_USER = currentUser;
    }
}
