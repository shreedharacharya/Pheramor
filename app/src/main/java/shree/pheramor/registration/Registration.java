package shree.pheramor.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shree.pheramor.dob.UserDobScreen;
import shree.pheramor.email.UserEmailScreen;
import shree.pheramor.gender.UserGenderScreen;
import shree.pheramor.interest.UserInterestScreen;
import shree.pheramor.name.UserNameScreen;
import shree.pheramor.password.UserPasswordScreen;
import shree.pheramor.presenter.NextStepInterface;
import shree.pheramor.profile.UserProfilePicScreen;
import shree.pheramor.race.UserRaceScreen;
import shree.pheramor.summary.UserSummary;
import shree.pheramor.view.R;

public class Registration extends AppCompatActivity implements NextStepInterface {

    private ViewPager viewPager;
    private SlidePagerAdapter adapter;
    private UserDetails userDetails;
    private ApiService apiService;
    private Uri fileUri;
    private ProgressDialog progressDialog;
    private int successCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewPager = findViewById(R.id.viewpager);
        userDetails = new UserDetails();
        apiService = ApiUtils.getApiService();


        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new UserEmailScreen());
        fragments.add(new UserPasswordScreen());
        fragments.add(new UserNameScreen());
        fragments.add(new UserGenderScreen());
        fragments.add(new UserDobScreen());
        fragments.add(new UserInterestScreen());
        fragments.add(new UserRaceScreen());
        fragments.add(new UserProfilePicScreen());
        fragments.add(new UserSummary());

        adapter = new SlidePagerAdapter(getSupportFragmentManager(), getApplicationContext(), fragments);
        adapter.disableAllPageExceptFirst();
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                 adapter.disableAllPageAfterCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }






    @Override
    public void onNextStep(int id, Bundle data) {

        switch (id) {
            case 0:
                userDetails.setEmail(data.getString("u_email"));
                setCurrentPage();
                break;
            case 1:
                userDetails.setPassword(data.getString("u_password"));
                setCurrentPage();
                break;
            case 2:
                userDetails.setFullName(data.getString("u_name"));
                userDetails.setZipCode(data.getString("u_zip"));
                userDetails.setHeight(data.getString("u_height"));

                setCurrentPage();
                break;

            case 3:
                userDetails.setGender(data.getString("u_gender"));

                setCurrentPage();
                break;

            case 4:
                userDetails.setDob(data.getString("u_dob"));

                setCurrentPage();
                break;

            case 5:
                userDetails.setInterestGender(data.getString("interest_gender"));
                userDetails.setInterestAgeRange(data.getString("interest_age_range"));

                setCurrentPage();
                break;

            case 6:
                userDetails.setRace(data.getString("u_race"));
                userDetails.setReligion(data.getString("u_religion"));

                setCurrentPage();
                break;

            case 8:
                postUserDetails();
                break;

        }
    }

    private void setCurrentPage() {
        adapter.setEnabled(viewPager.getCurrentItem() + 1, true);
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    private void postUserDetails() {

        successCount=0;
        showProgress();
        File file = new File(fileUri.getPath());

        String extension = MimeTypeMap.getFileExtensionFromUrl(fileUri.getPath());
        String  type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(type), file);
        MultipartBody.Part image =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);


        apiService.postData(userDetails).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                responseFromServer(true,response.message());
                //Log.v("Onsuccess", new GsonBuilder().setPrettyPrinting().create().toJson(response));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                responseFromServer(false,t.getMessage());
                //Log.e("ONFailure", t.getMessage());
            }
        });

        apiService.postImage(image).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                responseFromServer(true, response.message());
               // Log.v("OnsuccessImage", new GsonBuilder().setPrettyPrinting().create().toJson(response));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                responseFromServer(false,t.getMessage());
               // Log.e("ONFailureImage", t.getMessage());
            }
        });

    }

    private void responseFromServer(boolean success, String message) {
        if(success)successCount++;
        else {
            progressDialog.dismiss();
        }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if(successCount==2){
            progressDialog.dismiss();
            startActivity(new Intent(Registration.this, SuccessActivity.class));
            finish();
        }
    }

    private void showProgress() {

        progressDialog = new ProgressDialog(Registration.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == UserProfilePicScreen.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                fileUri = UserProfilePicScreen.fileUri;


            } else if (requestCode == UserProfilePicScreen.RESULT_LOAD_IMAGE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
               String selectedImagePath = cursor.getString(column_index);

                fileUri = Uri.parse(selectedImagePath);

            }

        }

        setCurrentPage();
        ((UserSummary) adapter.getItem(viewPager.getCurrentItem())).setDetails(userDetails, fileUri.getPath());
    }
}
