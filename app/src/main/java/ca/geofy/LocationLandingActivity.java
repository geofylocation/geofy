package ca.geofy;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.winsontan520.WScratchView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationLandingActivity extends AppCompatActivity {

    @Bind(R.id.scratcher)
    WScratchView scratcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        scratcher.setOnScratchCallback(new WScratchView.OnScratchCallback() {

            @Override
            public void onDetach(float percentageRevealed) {
                if (percentageRevealed >= 65) {
                    onRevealed();
                }
            }
        });
    }

    private void onRevealed() {
        Intent showWin = new Intent(this, WinActivity.class);
        startActivity(showWin);
        finish();
    }

    public static void show(Activity activity) {
        Intent startIntent = new Intent(activity, LocationLandingActivity.class);
        activity.startActivity(startIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
