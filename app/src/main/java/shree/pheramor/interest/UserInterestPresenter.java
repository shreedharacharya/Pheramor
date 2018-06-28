package shree.pheramor.interest;

import android.text.Editable;
import android.view.View;

public class UserInterestPresenter {

    private UserInterestController userInterestController;

    public UserInterestPresenter(UserInterestController userInterestController,View view) {
        this.userInterestController = userInterestController;
        this.userInterestController.initializeView(view);
    }

    private boolean verifyMinAge(Editable editable, String maxAge) {

        if(!editable.toString().isEmpty()){
            int minAge =Integer.parseInt(editable.toString());

            if(!maxAge.isEmpty()){
                return minAge>17 && minAge<100 && minAge<=Integer.valueOf(maxAge);
            }
            return minAge>17 && minAge<100;
        }
        return false;
    }

    private boolean verifyMaxAge(Editable editable, String minAge) {
        if(!editable.toString().isEmpty()){
            int maxAge =Integer.parseInt(editable.toString());

            if(!minAge.isEmpty()){
                return maxAge>17 && maxAge<100 && maxAge>=Integer.valueOf(minAge);
            }
            return maxAge>17 && maxAge<100;
        }
       return false;
    }

    public void checkForMinAgeValidity(Editable editable,String maxAge) {
        userInterestController.ageMinValidation(verifyMinAge(editable, maxAge));
    }



    public void checkForMaxAgeValidity(Editable editable, String minAge) {

        userInterestController.ageMaxValidation(verifyMaxAge(editable, minAge));
    }



}
