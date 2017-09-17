package my_parser;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import my_parser.api.hhApi;

public class app {

    private static hhApi hhApi;
    private Retrofit retrofit;

    public void RetrofitCreation() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.hh.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        hhApi = retrofit.create(hhApi.class);
    }

    public static void main(){
        System.out.println("123");
    }

    public static hhApi getApi() {
        return hhApi;
    }
}