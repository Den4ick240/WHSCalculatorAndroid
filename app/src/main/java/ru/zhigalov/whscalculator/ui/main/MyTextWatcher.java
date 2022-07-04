package ru.zhigalov.whscalculator.ui.main;


import android.text.Editable;
import android.text.TextWatcher;

import ru.zhigalov.whscalculator.ui.main.courses.newcourse.NewCourseFragment;

public class MyTextWatcher implements TextWatcher {
    public interface OnTextChanged {
        void onTextChanged();
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
        onTextChanged.onTextChanged();
    }
}
