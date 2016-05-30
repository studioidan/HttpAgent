package studioidan.com.httpagent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.view.Display;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by PopApp_laptop on 02/12/2015.
 */
public class U {

    public static int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        //int width = size.x;
        int height = size.y;
        return height;
    }

    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        //int height = size.y;
        return width;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(EditText editText) {
        return editText == null || isEmpty(editText.getText().toString());
    }

    public static boolean isEmpty(EditText... editTexts) {
        for (EditText et : editTexts) {
            if (isEmpty(et))
                return false;
        }
        return true;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(ArrayList arrayList) {
        return arrayList == null || arrayList.size() == 0;
    }

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String value(EditText editText) {
        return isEmpty(editText) ? "" : editText.getText().toString();
    }

    public static void okDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })*/
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
