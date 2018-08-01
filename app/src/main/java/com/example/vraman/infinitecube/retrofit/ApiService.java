package com.example.vraman.infinitecube.retrofit;

import com.example.vraman.infinitecube.bean.ContactDataClass;
import com.example.vraman.infinitecube.bean.CreateContactDataClass;
import com.example.vraman.infinitecube.bean.CreateLeadDataClass;
import com.example.vraman.infinitecube.bean.LoginDataClass;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Abhi on 06 Sep 2017 006.
 */

public interface ApiService {


    @POST("login")
    Call<LoginDataClass> getLoginData( @Body HashMap<String, String> params);

    @POST("contact")
    Call<ContactDataClass> getContactData(@Body HashMap<String, String> params);

    @POST("contact")
    Call<CreateContactDataClass> createContactData(@Body HashMap<String, String> params);

    @POST("lead")
    Call<CreateLeadDataClass> createLeadData(@Body HashMap<String, String> params);

}