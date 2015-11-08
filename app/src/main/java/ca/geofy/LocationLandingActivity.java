package ca.geofy;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.winsontan520.WScratchView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationLandingActivity extends AppCompatActivity {

    private static final String EXTRA_PARTNER_ID = "rampupweekend8.coffeenews.PARTNER_ID";
    private static final String EXTRA_EDITION = "rampupweekend8.coffeenews.EDITION";

    @Bind(R.id.text_partner)
    TextView textPartner;

    @Bind(R.id.scratcher)
    WScratchView scratcher;

    @Bind(R.id.underlay)
    ImageView underlay;

    private int attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        Partner partner = getPartner();
        textPartner.setText(getResources().getString(R.string.partner_location_header, partner.description));
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
        attempts++;
        if (attempts == 2) {
            onWin();
        } else {
            onLoss();
        }
    }

    private void resetScratcher() {
        if (attempts == 1) {
            scratcher.setScratchDrawable(getResources().getDrawable(R.mipmap.ad_scratcher_2));
        } else if (attempts == 2) {
            scratcher.setScratchDrawable(getResources().getDrawable(R.mipmap.ad_scratcher_3));
        } else {
            showMaximumHitDialog();
        }

        scratcher.resetView();
        scratcher.setScratchAll(false);
        scratcher.invalidate();
    }

    private void showMaximumHitDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.max_dialog_title)
                .setMessage(R.string.max_dialog_message)
                .setPositiveButton(R.string.max_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    private void onWin() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.win_dialog_title)
                .setMessage(R.string.win_dialog_message)
                .setPositiveButton(R.string.win_dialog_retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showRedeemCode();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    private void showRedeemCode() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.win_dialog_title)
                .setView(getLayoutInflater().inflate(R.layout.redeem_dialog, null))
                .setPositiveButton(R.string.redeem_dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetScratcher();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    private void onLoss() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.loss_dialog_title)
                .setMessage(R.string.loss_dialog_message)
                .setPositiveButton(R.string.loss_dialog_retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetScratcher();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    private Partner getPartner() {
        return Partners.getPartnerById(getIntent().getLongExtra(EXTRA_PARTNER_ID, -1));
    }

    private String getEdition() {
        return getIntent().getStringExtra(EXTRA_EDITION);
    }

    public static void showContentForPartner(Activity activity, long partnerId, String edition) {
        Intent startIntent = new Intent(activity, LocationLandingActivity.class);
        startIntent.putExtra(EXTRA_PARTNER_ID, partnerId);
        startIntent.putExtra(EXTRA_EDITION, edition);
        activity.startActivity(startIntent);
    }
}
