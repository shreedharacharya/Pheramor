package shree.pheramor.dob;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import shree.pheramor.presenter.NextStepInterface;
import shree.pheramor.view.R;

public class UserDobScreen extends Fragment implements DatePickerDialog.OnDateSetListener{

    private EditText pickDob;
    private Button next;
    private NextStepInterface nextStepInterface;
    private static final String DATEPICKER_TAG = "datepicker";
    private String date="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_dob, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next = view.findViewById(R.id.btn_continue);
        pickDob = view.findViewById(R.id.u_dob);

        pickDob.setOnTouchListener(dobTouchListener);

       if (!date.isEmpty()) next.setEnabled(true);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle data = new Bundle();
                data.putString("u_dob", date);
                nextStepInterface.onNextStep(4, data);
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


    View.OnTouchListener dobTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                showDatePickerDialog();
            }
            return false;
        }
    };

    private void showDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setYearRange(1919,2000);

        dpd.show(getActivity().getFragmentManager(), DATEPICKER_TAG);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
         date =  dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        next.setEnabled(true);
        pickDob.setText(date);
    }
}
