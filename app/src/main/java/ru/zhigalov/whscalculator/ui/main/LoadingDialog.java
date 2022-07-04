package ru.zhigalov.whscalculator.ui.main;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import ru.zhigalov.whscalculator.R;

public class LoadingDialog {
    private final Context context;
    private AlertDialog alertDialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void start() {
        alertDialog = new MaterialAlertDialogBuilder(context)
                .setView(R.layout.dialog_loading)
                .setCancelable(false)
                .create();

        alertDialog.show();
    }

    public void dismiss() {
        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();
    }
}
