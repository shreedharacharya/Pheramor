package shree.pheramor.registration;

public class ApiUtils {
    private ApiUtils() {}

    private static final String BASE_URL = "https://external.dev.pheramor.com";

    public static ApiService getApiService() {

        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);

    }
}
