package ca.geofy;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by doug on 2015-11-07.
 */
public class DialogUtil {
    static void showInfoDialog(Activity activity, final int resTitle, final int resMessage, final int resButton) {
        createOkDialog(activity, resTitle, resMessage, resButton).show();
    }

    //private static AlertDialog createOkDialog(Activity activity, int resTitle, int resMessage, int resButton) {

    //}

    private static AlertDialog createOkDialog(Activity activity, int resTitle, int resMessage, int resButton) {
        return new AlertDialog.Builder(activity)
                .setTitle(resTitle)
                .setMessage(resMessage)
                .setPositiveButton(resButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .create();
    }
}
