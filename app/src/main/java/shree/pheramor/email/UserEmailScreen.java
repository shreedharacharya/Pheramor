package shree.pheramor.email;

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

public class UserEmailScreen extends Fragment implements UserEmailController {

    private EditText email;
    private Button next;
    private TextView errorMessage;
    NextStepInterface nextStepInterface;
    UserEmailController userEmailController = this;
    UserEmailPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_email, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new UserEmailPresenter(userEmailController, view);


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.checkForEmailValidity(editable);
            }


        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmailController.onClick();
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
        email = view.findViewById(R.id.u_email);
        next = view.findViewById(R.id.btn_continue);
        errorMessage = view.findViewById(R.id.error_message);

    }


    @Override
    public void onClick() {

        Bundle data = new Bundle();
        data.putString("u_email", email.getText().toString().trim());
        nextStepInterface.onNextStep(0, data);
    }

    @Override
    public void emailValidation(boolean valid) {
        if (valid) {
            next.setEnabled(true);
            errorMessage.setVisibility(View.GONE);
        } else {
            next.setEnabled(false);
            errorMessage.setText(R.string.email_error_message);
            errorMessage.setTextColor(getResources().getColor(R.color.colorErrorMsg));
            errorMessage.setVisibility(View.VISIBLE);

        }
    }

}
