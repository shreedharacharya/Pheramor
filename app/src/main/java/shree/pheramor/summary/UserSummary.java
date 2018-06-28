package shree.pheramor.summary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import shree.pheramor.presenter.NextStepInterface;
import shree.pheramor.registration.UserDetails;
import shree.pheramor.view.R;

public class UserSummary extends Fragment {

    private TextView email;
    private TextView password;
    private TextView name;
    private TextView zip;
    private TextView height;
    private TextView gender;
    private TextView dob;
    private TextView interestGender;
    private TextView interestAge;
    private TextView race;
    private TextView religion;
    private CircleImageView profilePic;
    private Button submit;
    private NextStepInterface nextStepInterface;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.tv_email);
        password = view.findViewById(R.id.tv_password);
        name = view.findViewById(R.id.tv_name);
        zip = view.findViewById(R.id.tv_zip);
        height = view.findViewById(R.id.tv_height);
        gender = view.findViewById(R.id.tv_gender);
        dob = view.findViewById(R.id.tv_dob);
        interestGender = view.findViewById(R.id.tv_interest);
        interestAge = view.findViewById(R.id.tv_interest_age);
        race = view.findViewById(R.id.tv_race);
        religion = view.findViewById(R.id.tv_religion);
        profilePic = view.findViewById(R.id.profile_pic);
        submit = view.findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStepInterface.onNextStep(8,null);
            }
        });
    }




    public void setDetails(UserDetails userDetails,String imagePath) {


        email.setText(userDetails.getEmail());
        password.setText(userDetails.getPassword());
        name.setText(userDetails.getFullName());
        zip.setText(userDetails.getZipCode());
        height.setText(userDetails.getHeight());
        gender.setText(userDetails.getGender());
        dob.setText(userDetails.getDob());
        interestGender.setText(userDetails.getGender());
        interestAge.setText(userDetails.getInterestAgeRange());
        race.setText(userDetails.getRace().isEmpty()? "Not Provided": userDetails.getRace() );
        religion.setText(userDetails.getReligion().isEmpty()? "Not Provided": userDetails.getReligion());
        System.out.println(imagePath);

        Picasso.with(profilePic.getContext())

                .load(new File(imagePath))
                .fit().centerInside()
                .error(R.drawable.user_image)
                .placeholder(R.drawable.user_image)
                .into(profilePic);


    }

    @Override
    public void onDetach() {
        nextStepInterface = null;
        super.onDetach();
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
}
