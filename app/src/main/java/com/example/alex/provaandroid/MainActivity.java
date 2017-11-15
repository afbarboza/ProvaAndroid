package com.example.alex.provaandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnIniciarJogo;
    Button btnGravarAudio;
    Button btnTirarFoto;
    ImageView imgFotoUsuario;

    static final int CAMERA_PIC_REQUEST = 1;

    public void iniciaJogoForca() {
        Intent i = new Intent(MainActivity.this, GameForca.class);
        startActivity(i);
    }

    /**
     * tira uma foto do usuario.
     */
    public void tirarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
        }
    }

    public void gravarAudio() {

    }

    private void tratarFoto(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        imgFotoUsuario.setImageBitmap(thumbnail);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        byte[] byteArray = bytes.toByteArray();
        File destination = new File(Environment.getExternalStorageDirectory(), "test.jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_PIC_REQUEST:
               tratarFoto(data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciarJogo = (Button) findViewById(R.id.btn_inicia_jogo);
        btnGravarAudio = (Button) findViewById(R.id.btn_grava_audio);
        btnTirarFoto = (Button) findViewById(R.id.btn_tira_foto);
        imgFotoUsuario = (ImageView) findViewById(R.id.foto_usuario);

        btnIniciarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciaJogoForca();
            }
        });

        btnGravarAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gravarAudio();
            }
        });

        btnTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tirarFoto();
            }
        });
    }
}
