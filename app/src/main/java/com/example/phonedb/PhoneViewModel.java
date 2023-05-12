package com.example.phonedb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {

    private PhoneRepository mRepository;
    private LiveData<List<Phone>> mAllPhones;

    public PhoneViewModel(Application application) {
        super(application);
        mRepository = new PhoneRepository(application);
        mAllPhones = mRepository.getAllPhones();
    }

    LiveData<List<Phone>> getAllPhones() {
        return mAllPhones;
    }

    public void insert(Phone phone) {
        mRepository.insert(phone);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deletePhone(Phone phone) {
        mRepository.deletePhone(phone);
    }

    public void update(Phone phone) {
        mRepository.update(phone);
    }
}