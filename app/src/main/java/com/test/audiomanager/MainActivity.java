package com.test.audiomanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.audiomanager.AudioListener;
import com.github.audiomanager.AudioTools;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View btRegister = findViewById(R.id.btRegister);
        btRegister.setOnClickListener(this);

        View btUnRegister = findViewById(R.id.btUnRegister);
        btUnRegister.setOnClickListener(this);

        View btRegisterSecond = findViewById(R.id.btRegisterSecond);
        btRegisterSecond.setOnClickListener(this);

        View btUnRegisterSecond = findViewById(R.id.btUnRegisterSecond);
        btUnRegisterSecond.setOnClickListener(this);


        AudioTools.get().init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioTools.get().unInit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btRegister:
                AudioTools.get().addListener(1, new AudioListener() {
                    @Override
                    public void onMusicVolumeChange(int preVolume, int volume) {
                        Log.i("=====",preVolume+"=====onVolumeChange111"+volume);
                    }
                });
                break;
            case R.id.btUnRegister:
                AudioTools.get().removeListener(1);
                break;
            case R.id.btRegisterSecond:
                AudioTools.get().addListener(2, new AudioListener() {
                    @Override
                    public void onMusicVolumeChange(int preVolume, int volume) {
                        Log.i("=====",preVolume+"=====onVolumeChange111"+volume);
                    }
                });
                break;
            case R.id.btUnRegisterSecond:
                AudioTools.get().removeListener(2);
                break;
        }
    }
}