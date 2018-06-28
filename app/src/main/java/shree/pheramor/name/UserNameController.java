package shree.pheramor.name;

import android.view.View;

interface UserNameController {

    void initializeView(View view);
    void onClick();
    void nameValidation(boolean b);
    void heightValidation(boolean b);
    void zipValidation(boolean b);
}
