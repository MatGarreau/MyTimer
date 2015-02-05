package com.bigmat.MyTimer;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    private TextView inputDuration;
    private TextView tvCdTimer;
    private Button start;
    CountDownTimer myCdTimer;
    NumberPicker npDuration;
    CheckBox isRepeat;
    TextView tvRepeat;
    int nbRepeat;
    int count;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        inputDuration = (TextView) findViewById(R.id.tvInputDuration);
        tvCdTimer = (TextView) findViewById(R.id.tvCdTimer);
        start = (Button) findViewById(R.id.btStart);
        npDuration = (NumberPicker) findViewById(R.id.npDuration);
        npDuration.setMaxValue(60);
        npDuration.setMinValue(0);
        isRepeat = (CheckBox) findViewById(R.id.cbRepeat);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                tvRepeat = (TextView) findViewById(R.id.nbRepeat);
                if (tvRepeat.getText().toString().length() > 0) {
                    nbRepeat = Integer.parseInt(tvRepeat.getText().toString());
                } else {
                    nbRepeat = 1;
                }
                runTimer();
            }
        });
    }



    public void runTimer(){

        Log.i("CDT", "value of npDuration is : "+npDuration.getValue());

        if (npDuration.getValue() > 0 && nbRepeat > 0) {

                // create a timer
                myCdTimer = new CountDownTimer(npDuration.getValue()*1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        tvCdTimer.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        tvCdTimer.setText("Done ! ");
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        count++;
                        //condition

                        if (count < nbRepeat) {
                            Log.i("MyTimer", "value of nbRepeat :" + nbRepeat + " value of count : " + count);
                            myCdTimer.cancel();
                            myCdTimer.start();
                        }
                    }
                }.start();
        } else {
            Toast.makeText(getApplicationContext(), "Timer is set on 0 seconds !!!", Toast.LENGTH_LONG).show();
        }
    }
}
