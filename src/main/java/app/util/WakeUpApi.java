package app.util;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

/**
 * Created by adamz on 30.01.2018.
 */
public interface WakeUpApi {

    @GET("/wakeUp")
    Call<Void> wakeUp();
}
