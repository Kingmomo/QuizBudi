package com.darmajayaquizz.quizbudi.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class Validate {

    public static boolean cek(EditText et) {
        View focusView = null;
        Boolean cancel = false;
        if (TextUtils.isEmpty(et.getText().toString().trim())) {
            et.setError("Inputan Harus Di Isi");
            focusView = et;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        return cancel;
    }


    public static void dismissKeyboard(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
