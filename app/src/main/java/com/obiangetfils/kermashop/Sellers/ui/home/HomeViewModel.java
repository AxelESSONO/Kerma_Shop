package com.obiangetfils.kermashop.Sellers.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ici c'est l'accueil du commerçant");
    }

    public LiveData<String> getText() {
        return mText;
    }
}