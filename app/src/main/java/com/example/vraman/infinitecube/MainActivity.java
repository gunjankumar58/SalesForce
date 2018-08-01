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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vraman.infinitecube.bean.ContactDataClass;
import com.example.vraman.infinitecube.bean.LoginDataClass;
import com.example.vraman.infinitecube.helper.AppSharedPreference;
import com.example.vraman.infinitecube.retrofit.ApiService;
import com.example.vraman.infinitecube.retrofit.RetroClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    Button btn_submit;
    EditText btn_represent;
    AlertDialog dialog;

    private EditText et_firstName, et_lastName;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_submit=findViewById(R.id.submit1);
        btn_represent=findViewById(R.id.editText3);

        et_firstName = findViewById(R.id.firstname);
        et_lastName = findViewById(R.id.lastname);

        progressDialog=new ProgressDialog(this);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callContactAPI();

            }
        });

    btn_represent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final AlertDialog.Builder mbuilder=new AlertDialog.Builder(MainActivity.this);
            View mview=getLayoutInflater().inflate(R.layout.representdialog,null);
            TextView btn_done=(TextView)mview.findViewById(R.id.btn_done);
            btn_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
            mbuilder.setView(mview);
            dialog=mbuilder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    });
    }

    private void callContactAPI(){

        final String firstName=et_firstName.getText().toString().trim();
        final String lastName=et_lastName.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)){
            et_firstName.setError("Please enter first name");
            et_firstName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastName)){
            et_lastName.setError("Please enter last name");
            et_lastName.requestFocus();
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
        params.put("access_token", AppSharedPreference.getInstance(MainActivity.this).getAuthToken());
        params.put("owner_id", AppSharedPreference.getInstance(MainActivity.this).getOwnerId());
        params.put("action", "search");

        ApiService api = RetroClient.getApiService();

        Call<ContactDataClass> call = api.getContactData(params);
        call.enqueue(new Callback<ContactDataClass>() {
            @Override
            public void onResponse(Call<ContactDataClass> call, retrofit2.Response<ContactDataClass> response) {
                System.out.println(response);

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                if (response.body().getTotalSize()!= 0){

                    AppSharedPreference.getInstance(MainActivity.this).setContactId(response.body().getRecords().get(0).getId());

                    startActivity(new Intent(MainActivity.this, LeadCreate.class));
                    finish();


                }else {
                    Toast.makeText(MainActivity.this, "Contact not found", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,ContactCreate.class);
                    btn_submit.setTextColor(Color.parseColor("#FFFFFF"));
                    btn_submit.setBackgroundResource(R.drawable.colorshape);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<ContactDataClass> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });

    }
}
