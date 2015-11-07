package rampupweekend8.coffeenews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoffeeNewsActivity extends AppCompatActivity {

    private static final String EXTRA_PARTNER_ID = "rampupweekend8.coffeenews.PARTNER_ID";
    private static final String EXTRA_EDITION = "rampupweekend8.coffeenews.EDITION";

    @Bind(R.id.text_partner)
    TextView textPartner;

    @Bind(R.id.text_edition)
    TextView textEdition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        Partner partner = getPartner();
        textPartner.setText(getResources().getString(R.string.partner_location_header, partner.description));

        // TODO: ugly!
        switch (getEdition()) {
            case "1": // Winnipeg Male Chorus
                textEdition.setText(Html.fromHtml(getResources().getString(R.string.edition_1_male_chorus)));
                break;
            case "2": // Main Cover
                textEdition.setText(Html.fromHtml(getResources().getString(R.string.edtion_2_main_cover)));
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
        Intent startIntent = new Intent(activity, CoffeeNewsActivity.class);
        startIntent.putExtra(EXTRA_PARTNER_ID, partnerId);
        startIntent.putExtra(EXTRA_EDITION, edition);
        activity.startActivity(startIntent);
    }
}
