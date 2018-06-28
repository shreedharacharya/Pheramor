package shree.pheramor.race;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import shree.pheramor.presenter.NextStepInterface;
import shree.pheramor.view.R;

public class UserRaceScreen extends Fragment {

    private Spinner sRace;
    private Spinner sReligion;
    private Button next;
    NextStepInterface nextStepInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_race_relegion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        next = view.findViewById(R.id.btn_continue);
        sRace = view.findViewById(R.id.s_race);
        sReligion = view.findViewById(R.id.s_religion);

        next.setEnabled(true);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String race = sRace.getSelectedItem().toString();
                String religion = sReligion.getSelectedItem().toString();
                if(race.equals("Race")) race = "";
                if(religion.equals("Religion")) religion = "";
                Bundle data = new Bundle();
                data.putString("u_race",race );
                data.putString("u_religion", religion);
                nextStepInterface.onNextStep(6, data);
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

}
