package cz.tokija.tokija.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Client {
    private APIInterface client;
    private static String token;
    private static String email;

    public Client() {
        Interceptor headerInterceptor = chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            if (token != null) {
                requestBuilder.addHeader("X-User-Email", email);
                requestBuilder.addHeader("X-User-Token", token);
            }
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tokija-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okClient)
                .build();
        client = retrofit.create(APIInterface.class);
    }

    public APIInterface getClient(){return client;}

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Client.token = token;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Client.email = email;
    }
}
