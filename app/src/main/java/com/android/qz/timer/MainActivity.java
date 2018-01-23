package com.android.qz.timer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekbar;
    TextView timeLeftTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        timerSeekbar.setEnabled(true);
        timerSeekbar.setProgress(30);
        timeLeftTextView.setText("0:30");
        countDownTimer.cancel();
        controllerButton.setText("Go");
        counterIsActive = false;
    }

    public void controlTimer(View view) {
        if (!counterIsActive) {
            counterIsActive = true;
            timerSeekbar.setEnabled(false);
            controllerButton.setText("STOP");
            countDownTimer = new CountDownTimer(timerSeekbar.getProgress()*1000,1000){

                @Override
                public void onTick(long millsUntilFinished) {
                    updateTimer((int) millsUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.air);
                    mediaPlayer.start();
                }
            }.start();
        } else {
            resetTimer();
        }
    }

    public void updateTimer(int secondsLeft) {
        int min = (int) secondsLeft/60;
        int sec = secondsLeft % 60;
        String secondsInString = Integer.toString(sec);
        String minutesInString = Integer.toString(min);

        if (sec <= 9) {
            secondsInString = "0" + sec;
        }

        timeLeftTextView.setText(minutesInString + ":" + secondsInString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controllerButton = (Button) findViewById(R.id.controllerButton);

        timerSeekbar = (SeekBar) findViewById(R.id.seekBar);

        timeLeftTextView = (TextView) findViewById(R.id.timerTextView);

        timerSeekbar.setMax(600);

        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
                counterIsActive = false;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
