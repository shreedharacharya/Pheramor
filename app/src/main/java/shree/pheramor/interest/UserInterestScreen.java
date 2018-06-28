package shree.pheramor.interest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import shree.pheramor.presenter.NextStepInterface;
import shree.pheramor.view.R;

public class UserInterestScreen extends Fragment implements UserInterestController {

    private Button next;
    private Button man;
    private Button woman;
    private Button both;
    private EditText minAge;
    private EditText maxAge;
    private TextView errorMessage;
    private NextStepInterface nextStepInterface;
    private UserInterestController userInterestController = this;
    private UserInterestPresenter presenter;
    private boolean[] valid = new boolean[3];
    private String gender = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_interest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new UserInterestPresenter(userInterestController, view);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            nextStepInterface = (NextStepInterface) context;
        } catch (Exception e) {
            //catch
        }
    }

    @Override
    public void onDetach() {
        nextStepInterface = null;
        super.onDetach();
    }

    @Override
    public void initializeView(View view) {
        next = view.findViewById(R.id.btn_continue);
        man = view.findViewById(R.id.btn_man);
        woman = view.findViewById(R.id.btn_woman);
        both = view.findViewById(R.id.btn_both);
        minAge = view.findViewById(R.id.et_age_start);
        maxAge = view.findViewById(R.id.et_age_end);
        errorMessage = view.findViewById(R.id.error_message);

        setSelectedItem();

        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "MAN";
                valid[2]= true;
                manSelection();
            }
        });

        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "WOMAN";
                valid[2]= true;
                womanSelection();
            }
        });

        both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "BOTH";
                valid[2]= true;
                bothSelection();
            }
        });


        minAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                presenter.checkForMinAgeValidity(editable,maxAge.getText().toString());

            }
        });

        maxAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                    presenter.checkForMaxAgeValidity(editable, minAge.getText().toString());

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterestController.onClick();
            }
        });
    }



    private void setSelectedItem() {

        if(gender.equals("MAN")){
            manSelection();
        }else if(gender.equals("WOMAN")){
            womanSelection();

        }else if(gender.equals("BOTH")){
            bothSelection();
        }
        checkForAllitem();
    }

    private void manSelection() {
        man.setBackgroundResource(R.drawable.selected_item_bg);
        woman.setBackgroundResource(R.drawable.unselected_item_bg);
        both.setBackgroundResource(R.drawable.unselected_item_bg);
    }

    private void womanSelection() {
        man.setBackgroundResource(R.drawable.unselected_item_bg);
        woman.setBackgroundResource(R.drawable.selected_item_bg);
        both.setBackgroundResource(R.drawable.unselected_item_bg);
    }

    private void bothSelection() {
        man.setBackgroundResource(R.drawable.unselected_item_bg);
        woman.setBackgroundResource(R.drawable.unselected_item_bg);
        both.setBackgroundResource(R.drawable.selected_item_bg);
    }

    @Override
    public void onClick() {
        Bundle data = new Bundle();
        data.putString("interest_gender", gender);
        String ageMin =minAge.getText().toString().trim();
        String ageMax =maxAge.getText().toString().trim();
        data.putString("interest_age_range", ageMin+"-"+ageMax);
        nextStepInterface.onNextStep(5, data);
    }

    @Override
    public void ageMinValidation(boolean valid) {
        this.valid[0]= valid;
        errorMessage(valid);
    }

    @Override
    public void ageMaxValidation(boolean valid) {

        this.valid[1]= valid;
        errorMessage(valid);
    }

    private void errorMessage(boolean valid) {
        String message= "Age should be between 18 to 99";
        if (valid) {
            checkForAllitem();
            errorMessage.setVisibility(View.GONE);
        } else {
            next.setEnabled(false);
            errorMessage.setText(message);
            errorMessage.setTextColor(getResources().getColor(R.color.colorErrorMsg));
            errorMessage.setVisibility(View.VISIBLE);

        }
    }

    private void checkForAllitem() {
        int i=0;
        for(boolean val:this.valid)
            if(val)i++;
        if(i==3){
            next.setEnabled(true);
        }
    }

}
