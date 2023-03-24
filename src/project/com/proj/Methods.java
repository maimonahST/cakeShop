package com.proj;

import java.awt.*;

public class Methods {

    static Color selected = Color.GRAY;
    static Color notSelected = Color.white;

    public static boolean isValidEmail( String Email ){

        if( Email.contains("@") && Email.contains(".")){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidPass(String pass){
        if( pass.length() > 6 ){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidPhoneNum(String phoneNum){
        boolean digit = true;

        for (int i = 0; i < phoneNum.length(); i++) {
             if( ! Character.isDigit(phoneNum.charAt(i))){
                 digit = false;
             }
        }

        if ( phoneNum.length() == 10 && digit){
            return true;
        }

        return false;

    }
}
