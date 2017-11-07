import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jayanth on 07-11-2017.
 */

public class movieInfo {

    @SerializedName("results")
    private ArrayList<movie> results = new ArrayList<movie>();

    public ArrayList<movie> getResults() {
        return results;
    }
}

class movie {
    @SerializedName("name")
    String name;
    @SerializedName("original_name")
    String originalName;
}
