package com.example.alex.provaandroid;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class GameForca extends AppCompatActivity {
    String palavraTeste = "OlaMundo";
    EditText txtLetraDigitada;
    Button btnSubmit;
    Palavra p;
    TextView lblPalavraCifrada;
    TextView lblErros;


    void fazerTentativa() {
        txtLetraDigitada =  (EditText) findViewById(R.id.et_digitada);

        char letra = txtLetraDigitada.getText().toString().charAt(0);


        p.fazerTentativa(letra);
        String palavraCifrada = p.getPalavraCriptograda();
        lblPalavraCifrada.setText(palavraCifrada.toString());
        lblErros.setText(Integer.toString(p.getErros()));
        /*Log.i("ALEX", "Letra-" + letra);
        Log.i("ALEX", "palavra cifrada: " + palavraCifrada);
        Log.i("ALEX", "erros:" + p.getErros());*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_forca);
        p = new Palavra("OLAMUNDO");

        Log.i("ALEX", "palavrafinal" + p.getPalavraFinal());

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        lblErros = (TextView) findViewById(R.id.tv_errors);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerTentativa();
            }
        });
    }
}
