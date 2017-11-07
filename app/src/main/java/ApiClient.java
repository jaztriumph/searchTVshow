import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jayanth on 07-11-2017.
 */

public class ApiClient {
    public static String BASE_URL="https://api.themoviedb.org/3/search/";
    public static Retrofit retrofit=null;
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
