package ca.geofy;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.win_redeem)
    public void onClick() {
        showRedeemCode();
    }

    private void showRedeemCode() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.win_dialog_title)
                .setView(getLayoutInflater().inflate(R.layout.redeem_dialog, null))
                .setPositiveButton(R.string.redeem_dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showThanksScreen();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    private void showThanksScreen() {
        Intent concluding = new Intent(this, ConcludingActivity.class);
        startActivity(concluding);
        overridePendingTransition(R.anim.abc_fade_in, 0);
    }
}
