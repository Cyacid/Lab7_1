package com.example.lab7_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private  int rab = 0,tur = 0;
    private SeekBar seekBar,seekBar2;
    private Button btn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar2);
        seekBar2 = findViewById(R.id.seekBar3);
        btn_start = findViewById(R.id.button);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_start.setEnabled(false);
                rab = 0;
                tur = 0;
                seekBar.setProgress(0);
                seekBar2.setProgress(0);
                runThread();
                runAsyncTask();
            }
        });
    }
    private void runAsyncTask(){
        new AsyncTask<Void,Integer,Boolean>(){
            @Override
            protected Boolean doInBackground(Void... voids) {
                while(tur <= 100 && rab <100){
                    try{
                        Thread.sleep(100);
                        tur += (int)(Math.random() * 3);
                        publishProgress(tur);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                return true;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                seekBar.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if(tur >= 100 && rab <100){
                    Toast.makeText(MainActivity.this,
                            "烏龜勝利",Toast.LENGTH_SHORT).show();
                    btn_start.setEnabled(true);
                }
            }
        }.execute();
    }
    private void runThread(){new Thread(new Runnable() {
        @Override
        public void run() {
            while(rab<=100 && tur<=100){
                try{
                    Thread.sleep(100);
                    rab += (int)(Math.random() * 3);
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }).start();
    }
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message meg) {
            switch (meg.what){
                case 1:
                    seekBar2.setProgress(rab);
                    break;
            }
            if(rab >= 100 && tur <100){
                Toast.makeText(MainActivity.this,
                        "兔子獲勝",Toast.LENGTH_SHORT).show();
                btn_start.setEnabled(true);
            }
            return false;
        }
    });
}