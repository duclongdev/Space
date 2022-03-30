package com.example.space.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<String> email;
    private MutableLiveData<String> passWord;
    private MutableLiveData<String> gender;
    private MutableLiveData<String> name;
    private MutableLiveData<String> age;


    public MutableLiveData<String>getEmail()
    {
        if(email==null)
        {
            email=new MutableLiveData<String>();
        }return email;
    }

    public MutableLiveData<String>getName(){
        if(name==null)
            name=new MutableLiveData<String>();
        return name;
    }
    public MutableLiveData<String>getGender(){
        if(gender==null)
            gender=new MutableLiveData<String>();
        return gender;
    }

    public MutableLiveData<String>getPassWord(){
        if(passWord==null)
            passWord=new MutableLiveData<String>();
        return passWord;
    }

    public MutableLiveData<String> getAge()
    {
        if(age==null)
            age=new MutableLiveData<String>();
        return age;
    }

    public void setGender(String gender)
    {
        this.gender.setValue(gender);
        Log.d("nhucc", this.getGender().getValue());
    }
}
