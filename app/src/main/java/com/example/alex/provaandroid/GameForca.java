package com.example.alex.provaandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameForca extends AppCompatActivity {
    /* objeto representando a palavra corrente sendo jogada */
    Palavra p;

    /* letra a ser testada pra ver se existe na palavra atual */
    EditText txtLetraDigitada;

    /* botao para submeter mais uma tentativa */
    Button btnSubmit;

    /* mostra ao usuario a palavra cifrada atual */
    TextView lblPalavraCifrada;

    /* informa ao usuario a quantidade de tentativas restantes */
    TextView lblErros;

    /* armazena um array de palavras ja jogadas pelo usuario */
    ArrayList<Palavra> palavrasJogadas;

    public void newWord() {
        WebService webService = WebService.retrofit.create(WebService.class);
        Call<PalavraRecebida> wordCall = webService.getWordsWebService();
        wordCall.enqueue(new Callback<PalavraRecebida>() {
            @Override
            public void onResponse(Call<PalavraRecebida> call, Response<PalavraRecebida> response) {
                if (response.isSuccessful()) {
                    p = new Palavra(response.body().id, response.body().getWord());
                } else {
                    Toast.makeText(getApplicationContext(), "Não foi possível se conectar ao servidor", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<PalavraRecebida> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Não foi possível se conectar ao servidor", Toast.LENGTH_SHORT);

            }
        });
    }

    public void toastMessage(String message) {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    public void atualizaLayout() {
        String palavraCifrada = p.getPalavraCriptograda();
        lblPalavraCifrada.setText(palavraCifrada.toString());
        lblErros.setText(Integer.toString(p.getErros()));
        txtLetraDigitada.setText("");
    }

    public void fazerTentativa() {
        /* captura a letra digitada pelo usuario e faz uma tentativa com ela */
        char letra = txtLetraDigitada.getText().toString().charAt(0);
        boolean acertou = p.fazerTentativa(letra);

        if (p.checarFimJogo()) {
            /* testa se o usuario ganhou a rodada e redireciona para a tela apropriada */
            if (p.ganhouJogada()) {
                toastMessage("Voce ganhou essa rodada! :-) ");
                /* todo: redirecionar para a tela de ganhou */

            } else {
                toastMessage("Voce perdeu essa rodada! A palavra era " + p.getPalavraFinal() + " :-( ");
                /* todo: redirecionar para a tela de perdeu */
            }
            newWord();
        }

        /* atualiza o status do jogo para o usuario na tela */
        atualizaLayout();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_forca);

        newWord();

        /* inicializa componentes graficos */
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        lblErros = (TextView) findViewById(R.id.tv_errors);
        lblPalavraCifrada = (TextView) findViewById(R.id.tv_palavra);
        txtLetraDigitada = (EditText) findViewById(R.id.et_digitada);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerTentativa();
            }
        });
    }
}
