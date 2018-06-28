package shree.pheramor.password;

import android.view.View;

public interface UserPasswordController {

    void initializeView(View view);
    void onClick();
    void passwordValidation(boolean b);
}
