package shree.pheramor.registration;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {


    @POST("/")
    Call<ResponseBody> postData(
            @Body UserDetails userDetails
    );

    @Multipart
    @POST("/")
    Call<ResponseBody> postImage(
            @Part MultipartBody.Part file
    );

}
