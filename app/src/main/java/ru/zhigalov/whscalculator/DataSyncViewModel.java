package ru.zhigalov.whscalculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataSyncViewModel extends ViewModel {
    private final MutableLiveData<Void> _handicapIndexChanged = new MutableLiveData<>();
    public final LiveData<Void> handicapIndexChanged = _handicapIndexChanged;

    public void notifyHandicapIndexChanged() {
        _handicapIndexChanged.setValue(null);
    }
}
