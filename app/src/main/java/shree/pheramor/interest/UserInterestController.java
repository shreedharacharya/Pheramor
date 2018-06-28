package shree.pheramor.interest;

import android.view.View;

public interface UserInterestController {

    void initializeView(View view);
    void onClick();
    void ageMinValidation(boolean b);
    void ageMaxValidation(boolean b);
}
