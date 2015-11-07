package ca.geofy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationLandingActivity extends AppCompatActivity {

    private static final String EXTRA_PARTNER_ID = "rampupweekend8.coffeenews.PARTNER_ID";
    private static final String EXTRA_EDITION = "rampupweekend8.coffeenews.EDITION";

    @Bind(R.id.text_partner)
    TextView textPartner;

    @Bind(R.id.webview_edition)
    WebView webViewEdition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        Partner partner = getPartner();
        textPartner.setText(getResources().getString(R.string.partner_location_header, partner.description));

        // TODO: ugly hardcoding!
        switch (getEdition()) {
            case "1": // Winnipeg Male Chorus
                webViewEdition.loadData(getResources().getString(R.string.edition_1_male_chorus), "text/html", null);
                break;
            case "2": // Main Cover
                webViewEdition.loadData(getResources().getString(R.string.edition_2_main_cover), "text/html", null);
                break;
        }
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