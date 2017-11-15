package com.example.alex.provaandroid;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Giovanni on 15/11/2017.
 */

public interface WebService {
    @GET("getWord")
    Call<PalavraRecebida> getWordsWebService();

    @POST("reportScore")
    Call<>

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://infinite-depths-57886.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
