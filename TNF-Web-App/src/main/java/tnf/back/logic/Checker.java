package tnf.back.logic;

public class Checker {

    public static boolean isEmptyString(String string){
        return string == null || string.length() == 0 || string.equals("null");
    }

    public static boolean isEmptyStrings(String... strings){
        for (var string : strings)
            if (!isEmptyString(string))
                return false;
        return true;
    }

}