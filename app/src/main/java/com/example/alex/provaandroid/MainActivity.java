package com.example.alex.provaandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    Button iniciarJogo;

    public void iniciaJogoForca() {
        Intent i = new Intent(MainActivity.this, GameForca.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarJogo = (Button) findViewById(R.id.btn_inicia_jogo);
        iniciarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciaJogoForca();
            }
        });
    }
}
