package cz.tokija.tokija.client;

import java.util.List;

import cz.tokija.tokija.client.model.Bin;
import cz.tokija.tokija.client.model.Firm;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Author: jaroslavmedek on 08/10/2018.
 */
public interface APIInterface {

    @GET("bins.json")
    Call<List<Bin>> getBins();

    @GET("bins/{binId}.json")
    Call<Bin> getBin(@Path("binId") int binId);

    @PATCH("bins/{binId}.json")
    Call<Bin> updateBin(@Path("binId") int binId, @Body Bin bin);

    @GET("firms.json")
    Call<List<Firm>> getFirms();

    @GET("firms/{firmId}.json")
    Call<Firm> getFirm(@Path("firmId") int firmId);



}
