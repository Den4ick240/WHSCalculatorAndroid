package ru.zhigalov.whscalculator.ui.main;


import android.text.Editable;
import android.text.TextWatcher;

public class MyTextWatcher implements TextWatcher {
    public interface OnTextChanged {
        void onTextChanged(String text);
    }

    public MyTextWatcher(OnTextChanged onTextChanged) {
        this.onTextChanged = onTextChanged;
    }

    private final OnTextChanged onTextChanged;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        onTextChanged.onTextChanged(s.toString());
    }
}
