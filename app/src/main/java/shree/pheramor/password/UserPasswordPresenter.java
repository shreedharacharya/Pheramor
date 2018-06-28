package shree.pheramor.password;

import android.text.Editable;
import android.view.View;

public class UserPasswordPresenter {

   private UserPasswordController controller;

    public UserPasswordPresenter(UserPasswordController controller, View view) {
        this.controller = controller;
        this.controller.initializeView(view);
    }

    private boolean verifyPassword(Editable editable) {
        String password = editable.toString();

        int min =8;
        int max=16;
        int digit=0;
        int special=0;
        int upCount=0;
        int loCount=0;

        if(password.length()>=min&&password.length()<=max){
            for(int i =0;i<password.length();i++){
                char c = password.charAt(i);
                if(Character.isUpperCase(c)){
                    upCount++;
                }
                if(Character.isLowerCase(c)){
                    loCount++;
                }
                if(Character.isDigit(c)){
                    digit++;
                }
                if(c>=33&&c<=46||c==64){
                    special++;
                }
            }
            return special >= 1 && loCount >= 1 && upCount >= 1 && digit >= 1;

        }
        return false;

    }

    public void checkForPasswordValidity(Editable editable) {
        controller.passwordValidation(verifyPassword(editable));
    }

}
