package cz.tokija.tokija.client;

import java.util.List;

import cz.tokija.tokija.client.model.Bin;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Author: jaroslavmedek on 08/10/2018.
 */
public interface APIInterface {
    @Headers({
            "X-User-Email: info@tokija.cz",
            "X-User-Token: 5Sbupp-Sk5wnDYZqvpz_"
    })
@GET("bins.json")
    Call<List<Bin>> getBins();

}
