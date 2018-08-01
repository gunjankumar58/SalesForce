package com.example.vraman.infinitecube;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vraman.infinitecube.bean.CreateContactDataClass;
import com.example.vraman.infinitecube.bean.CreateLeadDataClass;
import com.example.vraman.infinitecube.helper.AppSharedPreference;
import com.example.vraman.infinitecube.retrofit.ApiService;
import com.example.vraman.infinitecube.retrofit.RetroClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadCreate extends AppCompatActivity {

Button btn_create,btn_cancel;
AlertDialog dialog;

private EditText et_firstName, et_lastName, et_phoneNumber, et_email;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_create);


        btn_create=(Button)findViewById(R.id.button2);
        btn_cancel=(Button)findViewById(R.id.button);

        et_firstName = findViewById(R.id.et_firstName);
        et_lastName = findViewById(R.id.et_lastName);
        et_phoneNumber = findViewById(R.id.et_phoneNumber);
        et_email = findViewById(R.id.et_email);

        progressDialog=new ProgressDialog(this);

        final AlertDialog.Builder mbuilder=new AlertDialog.Builder(LeadCreate.this);
        View mview=getLayoutInflater().inflate(R.layout.createleaddialog,null);
        Button btn_ok;
        btn_ok=(Button)mview.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        mbuilder.setView(mview);
        dialog=mbuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


       btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_create.setTextColor(Color.parseColor("#FFFFFF"));
                btn_create.setBackgroundResource(R.drawable.colorshape);

                createLead();

            }
        });


       btn_cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(getApplicationContext(),"You have Cancel Lead",Toast.LENGTH_SHORT).show();
           }
       });
    }


    private void createLead(){

        final String firstName,lastName,phoneNumber,emailId ;

        firstName=et_firstName.getText().toString().trim();
        lastName=et_lastName.getText().toString().trim();
        phoneNumber=et_phoneNumber.getText().toString().trim();
        emailId=et_email.getText().toString().trim();
        if (TextUtils.isEmpty(firstName)){
            et_firstName.setError("Please enter your first name");
            et_firstName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastName)){
            et_lastName.setError("Please enter your last name");
            et_lastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)){
            et_phoneNumber.setError("Please enter your email id");
            et_phoneNumber.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(emailId)) {
            et_email.setError("Please enter your email");
            et_email.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            et_email.setError("Enter a valid email");
            et_email.requestFocus();
            return;
        }


        progressDialog.setMessage("Please Wait.....");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        HashMap<String, String> params = new HashMap<>();
        params.put("first_name", firstName);
        params.put("last_name", lastName);
        params.put("email", emailId);
        params.put("phone", phoneNumber);
        params.put("company", "TCS");
        params.put("access_token", AppSharedPreference.getInstance(LeadCreate.this).getAuthToken());
        params.put("owner_id", AppSharedPreference.getInstance(LeadCreate.this).getOwnerId());
        params.put("contact_id", AppSharedPreference.getInstance(LeadCreate.this).getContactId());

        ApiService api = RetroClient.getApiService();

        retrofit2.Call<CreateLeadDataClass> call = api.createLeadData(params);

        call.enqueue(new Callback<CreateLeadDataClass>() {
            @Override
            public void onResponse(Call<CreateLeadDataClass> call, Response<CreateLeadDataClass> response) {

                if (progressDialog.isShowing())
                progressDialog.dismiss();
                if (response.body().isSuccess()){

                    AppSharedPreference.getInstance(LeadCreate.this).setLeadId(response.body().getId());

                    final AlertDialog.Builder mbuilder=new AlertDialog.Builder(LeadCreate.this);
                    View mview=getLayoutInflater().inflate(R.layout.createleadpopup,null);
                    Button btn_ok;
                    btn_ok=(Button)mview.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            startActivity(new Intent(LeadCreate.this, MainActivity.class));
                            dialog.cancel();
                            finish();
                        }
                    });
                    mbuilder.setView(mview);
                    dialog=mbuilder.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }

            }

            @Override
            public void onFailure(Call<CreateLeadDataClass> call, Throwable t) {
                Toast.makeText(LeadCreate.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
