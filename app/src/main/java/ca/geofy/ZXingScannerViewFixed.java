package ca.geofy;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Catches the exceptions that onPreviewFrame likes to throw.
 */
public class ZXingScannerViewFixed extends ZXingScannerView {

    public ZXingScannerViewFixed(Context context) {
        super(context);
    }

    public ZXingScannerViewFixed(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        try {
            super.onPreviewFrame(data, camera);
        } catch (IndexOutOfBoundsException e) {
            Log.w("ZXingScannerViewFixed", "Prevented the scanner view.", e);
        }
    }
}
