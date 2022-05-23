package unipiloto.edu.co.appiclaje;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Validation {

    public Validation() {
    }

    public boolean isNumeric(String parameter) {
        if (parameter == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(parameter);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean isTeleTelephone(String telephone){
        if (telephone.length()< 10){
            return true;
        }else
        return false;
    }

    public boolean isEmpty(String parameter){
        return parameter.length() == 0;
    }

    public boolean isEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean correct =pattern.matcher(email).matches();
        return correct;
    }

    public boolean containSpace(String user){
        return user.contains(" ");
    }
}
