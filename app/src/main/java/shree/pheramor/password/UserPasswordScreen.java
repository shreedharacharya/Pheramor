package shree.pheramor.password;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import shree.pheramor.presenter.NextStepInterface;
import shree.pheramor.view.R;

public class UserPasswordScreen extends Fragment implements UserPasswordController{

  private  EditText password;
    private Button next;
    private TextView errorMessage;
    NextStepInterface nextStepInterface;
    UserPasswordController userPasswordController = this;
    UserPasswordPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new UserPasswordPresenter(userPasswordController, view);
        password = view.findViewById(R.id.u_password);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.checkForPasswordValidity(editable);
            }


        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPasswordController.onClick();
            }
        });
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
        password = view.findViewById(R.id.u_password);
        next = view.findViewById(R.id.btn_continue);
        errorMessage = view.findViewById(R.id.error_message);

    }

    @Override
    public void onClick() {

        Bundle data = new Bundle();
        data.putString("u_password", password.getText().toString().trim());
        nextStepInterface.onNextStep(1, data);
    }

    @Override
    public void passwordValidation(boolean valid) {
        if (valid) {
            next.setEnabled(true);
            errorMessage.setVisibility(View.GONE);
        } else {
            next.setEnabled(false);
            errorMessage.setText(Html.fromHtml(getString(R.string.password_error_message)));
            errorMessage.setTextColor(getResources().getColor(R.color.colorErrorMsg));
            errorMessage.setVisibility(View.VISIBLE);

        }
    }

}
