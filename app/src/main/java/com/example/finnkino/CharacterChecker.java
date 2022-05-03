package com.example.finnkino;


// Class for checking that passwords are complex enough
public class CharacterChecker {
    private String password;

    protected CharacterChecker(String pass){
        password = pass;
    }

    public Boolean Uppercase(){
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public Boolean Lowercase(){
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public Boolean numbers(){
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }
    //~ ! @ # $ % ^ * -  = + [ { ] } / ; : , . ?
    //Source: https://reference.iam.harvard.edu/faq/what-are-password-requirements-and-why-are-they-important
    public Boolean specialCharacters(){
        for (int i = 0; i < password.length(); i++) {
            if(password.contains("~") || password.contains("!") || password.contains("@") ||
            password.contains("#") || password.contains("$") || password.contains("%") || password.contains("^") ||
            password.contains("*") || password.contains("-") || password.contains("") || password.contains("=") ||
            password.contains("+") || password.contains("[") || password.contains("{") || password.contains("]") ||
            password.contains("}") || password.contains("/") || password.contains(";") || password.contains(":") ||
            password.contains(",") || password.contains(".") || password.contains("?")){
                return true;
            }
        }
        return false;
    }

}
