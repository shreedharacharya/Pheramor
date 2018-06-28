package shree.pheramor.name;

import android.content.Context;
import android.os.Bundle;
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

public class UserNameScreen extends Fragment implements UserNameController{

    private EditText name;
    private EditText zip;
    private EditText height;
    private Button next;
    private TextView errorMessage;
    private NextStepInterface nextStepInterface;
    private UserNameController userNameController = this;
    private UserNamePresenter presenter;
    private boolean[] valid = new boolean[3];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_name, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new UserNamePresenter(userNameController, view);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                presenter.checkForNameValidity(editable);
            }
        });

        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                presenter.checkForHeightValidity(editable);
            }
        });

        zip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                presenter.checkForZipValidity(editable);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameController.onClick();
            }
        });

    }

    @Override
    public void initializeView(View view) {
        name = view.findViewById(R.id.f_name);
        height = view.findViewById(R.id.height);
        zip = view.findViewById(R.id.zip);
        errorMessage = view.findViewById(R.id.error_message);
        next= view.findViewById(R.id.btn_continue);

    }

    @Override
    public void onClick() {

        Bundle data = new Bundle();
        data.putString("u_name", name.getText().toString().trim());
        data.putString("u_zip", zip.getText().toString().trim());
        data.putString("u_height", height.getText().toString().trim());
        nextStepInterface.onNextStep(2, data);
    }

    @Override
    public void nameValidation(boolean valid) {

        this.valid[0]= valid;
        String message =getString(R.string.name_validation);
        errorMessage(valid,message);
    }


    @Override
    public void heightValidation(boolean valid) {

        this.valid[1]= valid;
        String message =getString(R.string.height_validation);
        errorMessage(valid,message);
    }

    @Override
    public void zipValidation(boolean valid) {

        this.valid[2]= valid;
        String message =getString(R.string.zip_validation);
        errorMessage(valid,message);
    }

    private void errorMessage(boolean valid, String message) {
        if (valid) {
            int i=0;
            for(boolean val:this.valid)
                if(val)i++;
            if(i==3)next.setEnabled(true);
            errorMessage.setVisibility(View.GONE);
        } else {
            next.setEnabled(false);
            errorMessage.setText(message);
            errorMessage.setTextColor(getResources().getColor(R.color.colorErrorMsg));
            errorMessage.setVisibility(View.VISIBLE);

        }
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

}
