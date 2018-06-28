package shree.pheramor.name;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

class UserNamePresenter {

    UserNameController userNameController;

    public UserNamePresenter(UserNameController userNameController, View view) {
        this.userNameController = userNameController;
        this.userNameController.initializeView(view);
    }

    private boolean verifyName(Editable editable){

        return !TextUtils.isEmpty(editable) && editable.length()>=3;
    }

    private boolean verifyHeight(Editable editable){
        int height =0;
        try {
            height=Integer.parseInt(editable.toString());
        }catch (Exception e){

        }

        return !TextUtils.isEmpty(editable) && height>=80 && height<=300;

    }

    private boolean verifyZip(Editable editable){
        return editable.length()==5;
    }

    public void checkForNameValidity(Editable editable){
        userNameController.nameValidation(verifyName(editable));
    }

    public void checkForHeightValidity(Editable editable){
        userNameController.heightValidation(verifyHeight(editable));
    }

    public void checkForZipValidity(Editable editable){
        userNameController.zipValidation(verifyZip(editable));
    }

}
