package shree.pheramor.gender;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import shree.pheramor.presenter.NextStepInterface;
import shree.pheramor.view.R;

public class UserGenderScreen extends Fragment {

    private Button next;
    private Button man;
    private Button woman;
    private NextStepInterface nextStepInterface;
    private String[] gender = new String[]{""};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_gender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next = view.findViewById(R.id.btn_continue);
        man = view.findViewById(R.id.btn_man);
        woman = view.findViewById(R.id.btn_woman);
        if(gender[0]!=null) next.setEnabled(true);
        setSelectedItem();


        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender[0] = "MAN";
                manSelection();
                next.setEnabled(true);
            }
        });

        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender[0] = "WOMAN";
                womanSelection();
                next.setEnabled(true);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putString("u_gender", gender[0]);
                nextStepInterface.onNextStep(3, data);
            }
        });
    }

    private void womanSelection() {
        man.setBackgroundResource(R.drawable.unselected_item_bg);
        woman.setBackgroundResource(R.drawable.selected_item_bg);
    }

    private void manSelection() {
        man.setBackgroundResource(R.drawable.selected_item_bg);
        woman.setBackgroundResource(R.drawable.unselected_item_bg);
    }

    private void setSelectedItem() {
        if(gender[0].equals("MAN")){
            manSelection();
        }else if(gender[0].equals("WOMAN")){
            womanSelection();
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
