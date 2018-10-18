package cz.tokija.tokija.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Client {
    private APIInterface client;

    public Client() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tokija-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        client = retrofit.create(APIInterface.class);
    }

    public APIInterface getClient(){return client;}
}
