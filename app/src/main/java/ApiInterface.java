import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jayanth on 07-11-2017.
 */

public interface ApiInterface {
    @GET("/tv?api_key=876ba1f8052dc2ee75cb845c11e9e531&language=en-US&query={movie}&page=1")
    Call<List<movieInfo>> getMovies(@Path("movie") String movie);
}
