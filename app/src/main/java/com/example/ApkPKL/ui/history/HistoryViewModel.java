package com.example.ApkPKL.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ini merupakan aplikasi untuk mempermudah user untuk mengetahui lokasi angkot\n" +
                "\n" +
                "copyright by\n" +
                "\n" +
                "Krisna Dwi Anggara - 165150200111035\n" +
                "Musthafani Akhyar  - 145150200111169");
    }

    public LiveData<String> getText() {
        return mText;
    }
}