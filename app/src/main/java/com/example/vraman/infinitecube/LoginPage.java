package com.example.vraman.infinitecube;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vraman.infinitecube.bean.LoginDataClass;
import com.example.vraman.infinitecube.helper.AppSharedPreference;
import com.example.vraman.infinitecube.retrofit.ApiService;
import com.example.vraman.infinitecube.retrofit.RetroClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginPage extends AppCompatActivity {

    EditText btn_userName,btn_userPassword;
    Button btn_submit;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        btn_userName=findViewById(R.id.btnuserlogin);
        btn_userPassword=findViewById(R.id.passwordbtn);
        btn_submit=findViewById(R.id.loginsubmit);
        progressDialog=new ProgressDialog(this);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLoginRetrofit();
            }
        });
    }

    private void userLoginRetrofit(){

        final String userName=btn_userName.getText().toString().trim();
        final String userPassword=btn_userPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName)){
            btn_userName.setError("Please enter your username");
            btn_userName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(userPassword)){
            btn_userPassword.setError("Please enter your password");
            btn_userPassword.requestFocus();
            return;
        }
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();


        HashMap<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", userPassword);

        ApiService api = RetroClient.getApiService();

        Call<LoginDataClass> call = api.getLoginData(params);
        call.enqueue(new Callback<LoginDataClass>() {
            @Override
            public void onResponse(Call<LoginDataClass> call, retrofit2.Response<LoginDataClass> response) {
                System.out.println(response);

                if (progressDialog.isShowing())
                progressDialog.dismiss();

                if (response.body().getAccessToken()!= null && response.body().getOwnerId() !=  null){
                    AppSharedPreference.getInstance(LoginPage.this).setAuthToken(response.body().getAccessToken());
                    AppSharedPreference.getInstance(LoginPage.this).setOwnerId(response.body().getOwnerId());

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginPage.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginDataClass> call, Throwable t) {
                Toast.makeText(LoginPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });

    }



}
