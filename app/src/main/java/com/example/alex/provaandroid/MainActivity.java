package com.example.alex.provaandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Permission;
import java.util.Random;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import android.Manifest.permission.*;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    String userName;
    Button btnIniciarJogo;
    Button btnGravarAudio;
    Button btnPararGravarAudio;
    Button btnTirarFoto;
    ImageView imgFotoUsuario;
    MediaRecorder mRecorder;

    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;

    static final int CAMERA_PIC_REQUEST = 1;

    public void iniciaJogoForca() {
        Intent i = new Intent(MainActivity.this, GameForca.class);
        startActivity(i);
    }


    public void getUserName() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Usuario");
        alertDialog.setMessage("Digite o nome do jogador: ");


        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        alertDialog.setView(input);

        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        userName = input.getText().toString();
                    }
                });

        alertDialog.show();
    }

    public void toastMessage(String message) {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
        if(checkPermission()) {

            AudioSavePathInDevice =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                            CreateRandomAudioFileName(5) + "AudioRecording.3gp";

            MediaRecorderReady();
            Log.i("LOG", "FILE: " + AudioSavePathInDevice);

        try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(MainActivity.this, "Recording started",
                        Toast.LENGTH_LONG).show();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                toastMessage("EXPECTION: IllegalStateException");
            } catch (IOException e) {
                e.printStackTrace();
                toastMessage("EXCEPTION: IOException");
            }
        } else {
            requestPermission();
        }
    }

    public void pararGravarAudio() {
        mediaRecorder.stop();

        Toast.makeText(MainActivity.this, "Recording Completed",
                Toast.LENGTH_LONG).show();

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(AudioSavePathInDevice);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
        Toast.makeText(MainActivity.this, "Recording Playing",
                Toast.LENGTH_LONG).show();
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
        btnPararGravarAudio = (Button) findViewById(R.id.parar_gravacao);

        btnGravarAudio.setVisibility(View.VISIBLE);
        btnPararGravarAudio.setVisibility(View.INVISIBLE);

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
                btnGravarAudio.setVisibility(View.INVISIBLE);
                btnPararGravarAudio.setVisibility(View.VISIBLE);
            }
        });

        btnTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tirarFoto();
            }
        });

        btnPararGravarAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pararGravarAudio();
                btnGravarAudio.setVisibility(View.VISIBLE);
                btnPararGravarAudio.setVisibility(View.INVISIBLE);
            }
        });

        getUserName();
    }

    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){

        return "maejoana";
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return ((result == PackageManager.PERMISSION_GRANTED) &&
                (result1 == PackageManager.PERMISSION_GRANTED));
    }

}
