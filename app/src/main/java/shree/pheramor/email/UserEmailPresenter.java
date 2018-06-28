package shree.pheramor.email;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

public class UserEmailPresenter {

  private UserEmailController userEmailController;


     public UserEmailPresenter(UserEmailController userEmailController, View view) {
        this.userEmailController = userEmailController;
        this.userEmailController.initializeView(view);
    }

    private boolean verifyEmail(Editable editable) {
        return !TextUtils.isEmpty(editable) && Patterns.EMAIL_ADDRESS.matcher(editable).matches();

    }

    public void checkForEmailValidity(Editable editable) {
        userEmailController.emailValidation(verifyEmail(editable));
    }
}
