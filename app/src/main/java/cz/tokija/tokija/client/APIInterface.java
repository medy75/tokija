package cz.tokija.tokija.client;

import java.util.List;

import cz.tokija.tokija.client.model.Bin;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Author: jaroslavmedek on 08/10/2018.
 */
public interface APIInterface {

    @GET("bins.json")
    Call<List<Bin>> getBins();

    @GET("bins/{binId}.json")
    Call<Bin> getBin(@Path("binId") int binId);

}
