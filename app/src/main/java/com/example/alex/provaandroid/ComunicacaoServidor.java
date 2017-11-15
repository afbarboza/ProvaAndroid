package com.example.alex.provaandroid;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Giovanni on 15/11/2017.
 */

public class ComunicacaoServidor {
    //private static String retorno;
    public void getWord(){

        WebService webService = WebService.retrofit.create(WebService.class);
        Call<PalavraRecebida> wordCall = webService.getWordsWebService();

        wordCall.enqueue(new Callback<PalavraRecebida>() {

            @Override
            public void onResponse(Call<PalavraRecebida> call, Response<PalavraRecebida> response) {
                if (response.isSuccessful()) {
                    Log.i("prova", "funcionando:" + response.body().word);
                } else {
                    Log.i("prova", "Falha: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PalavraRecebida> call, Throwable t) {
                Log.i("prova", "Falha: " + t.getMessage());            }
        });
    }
}
