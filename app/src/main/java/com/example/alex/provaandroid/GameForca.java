package com.example.alex.provaandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

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


    /**
     * vibrar - faz o celular vibrar por 400 ms
     */
    public void vibrar() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(400);

    }

    /**
     * playAudioGanhou - reproduz audio de vitoria
     */
    public void playAudioGanhou() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.ganhou1);
        mp.start();
    }

    /**
     * playAudioPerdeu - reproduz audio de derrota
     */
    public void playAudioPerdeu() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.perdeu1);
        mp.start();
    }

    /**
     * toastMessage - faz aparecer uma caixa de mensagens na tela para o usuario
     * @param message: mensagem a ser mostrada para o usuario.
     */
    public void toastMessage(String message) {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * atualizaLayout - atualiza o layout com o nro corrente de erros e com a
     *                  string criptograda mais recente.
     */
    public void atualizaLayout() {
        String palavraCifrada = p.getPalavraCriptograda();
        lblPalavraCifrada.setText(palavraCifrada.toString());
        lblErros.setText(Integer.toString(p.getErros()));
        txtLetraDigitada.setText("");
    }

    /**
     * fazerTentativa - captura a letra digitada pelo usuario e testa se a letra existe na string,
     *                  atualizando o layout e modelo de acordo.
     *
     */
    public void fazerTentativa() {
        /* captura a letra digitada pelo usuario e faz uma tentativa com ela */
        char letra = txtLetraDigitada.getText().toString().charAt(0);
        boolean acertou = p.fazerTentativa(letra);

        if (p.checarFimJogo()) {
            /* testa se o usuario ganhou a rodada e redireciona para a tela apropriada */
            if (p.ganhouJogada()) {
                toastMessage("Voce ganhou essa rodada! :-) ");
                playAudioGanhou();
                vibrar();
                /* todo: redirecionar para a tela de ganhou */

            } else {
                toastMessage("Voce perdeu essa rodada! :-( ");
                playAudioPerdeu();
                vibrar();
                /* todo: redirecionar para a tela de perdeu */
            }
            reinicializaJogo("ALEXBARBOZA");
        }

        /* atualiza o status do jogo para o usuario na tela */
        atualizaLayout();
    }

    public void reinicializaJogo(String novaPalavra) {
        p = new Palavra(novaPalavra);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_forca);
        p = new Palavra("OLAMUNDO");

        /* inicializa componentes graficos */
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        lblErros = (TextView) findViewById(R.id.tv_errors);
        lblPalavraCifrada = (TextView) findViewById(R.id.tv_palavra);
        txtLetraDigitada =  (EditText) findViewById(R.id.et_digitada);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerTentativa();
            }
        });
    }
}
