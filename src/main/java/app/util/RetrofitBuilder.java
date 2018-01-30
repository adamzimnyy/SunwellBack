package app.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitBuilder {

    public static Retrofit build(String url) {
        GsonBuilder builder = new GsonBuilder();
        OkHttpClient client = new OkHttpClient.Builder().
                readTimeout(120, TimeUnit.SECONDS).connectTimeout(120, TimeUnit.SECONDS).build();


        Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static Object getService(Class<?> clas, String url) {
        return build(url).create(clas);
    }

}
