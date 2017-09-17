package my_parser.api;

import java.util.List;

import my_parser.Example;
import my_parser.Item;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import my_parser.PostModel;

public interface hhApi {

    @Headers({"Cache-Control: max-age=640000", "User-Agent: HH-User-Agent"})
    @GET("/vacancies")
    Call<Example> getData(@Query("text") String resourceName, @Query("num") int count, @Query("per_page") int per_page, @Query("page") int page);
}
