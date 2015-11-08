package ca.geofy;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static Pattern URL_PATTERN = Pattern.compile("http://rampupweekend.com/coffeenews\\?edition=(.+)");

    @Bind(R.id.code_scanner)
    ZXingScannerViewFixed scanner;

    private GoogleApiClient googleApi;
    private Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        googleApi = createGoogleLocationServicesApi();
        googleApi.connect();
        scanner.setFormats(Arrays.asList(BarcodeFormat.QR_CODE));

        startService(new Intent(this, NotificationService.class));
        showNotification();
    }

    private void showNotification() {
        PendingIntent scan = PendingIntent.getActivity(this, 1, new Intent(this, MainActivity.class), 0);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.geofy_large_notification_icon);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        //opts.

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.geofy_small_notification_icon)
                .setContentText("You are in a Coffee News location.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("You are in a Coffee News location. Play for a chance to win!"))
                .setContentIntent(scan)
                .build();

        NotificationManager notifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifyMgr.notify(1337, notification);
    }

    private GoogleApiClient createGoogleLocationServicesApi() {
        return new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    // Google Play Services

    @Override
    public void onConnected(Bundle bundle) {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApi);
    }

    @Override
    public void onConnectionSuspended(int i) {
        // TODO
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // what does this mean? it seems bad
    }

    @Override
    public void handleResult(Result result) {
        Matcher matcher = URL_PATTERN.matcher(result.getText());
        if (!matcher.matches()) {
            showInvalidCodeDialog();
        } else {
            String edition = matcher.group(1);
            tryShowContentForEdition(edition);
        }
        result.getText();
    }

    private void tryShowContentForEdition(String edition) {
        if (!hasLocation()) {
            showNoLocationDialog();
            return;
        }

        //Partner partner = Partners.getPartnerAtLocation(lastLocation.getLatitude(), lastLocation.getLongitude());
        Partner partner = Partners.PARTNERS[1];
        if (partner == null) {
            showNotAtPartnerDialog();
        }

        LocationLandingActivity.showContentForPartner(this, partner.id, edition);
    }

    private boolean hasLocation() {
        return true;
        //return lastLocation != null;
    }

    private void showInvalidCodeDialog() {
        DialogUtil.showInfoDialog(this, R.string.code_not_recognized_title, R.string.code_not_recognized_message, R.string.code_try_again);
    }

    private void showNoLocationDialog() {
        DialogUtil.showInfoDialog(this, R.string.enable_location_services_title, R.string.enable_location_services_message, R.string.okay);
    }

    private void showNotAtPartnerDialog() {
        DialogUtil.showInfoDialog(this, R.string.not_at_partner_location_title, R.string.not_at_partner_location_message, R.string.okay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner.setResultHandler(this);
        scanner.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanner.stopCamera();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
