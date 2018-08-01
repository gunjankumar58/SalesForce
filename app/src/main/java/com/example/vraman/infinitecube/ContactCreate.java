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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vraman.infinitecube.bean.ContactDataClass;
import com.example.vraman.infinitecube.bean.CreateContactDataClass;
import com.example.vraman.infinitecube.helper.AppSharedPreference;
import com.example.vraman.infinitecube.retrofit.ApiService;
import com.example.vraman.infinitecube.retrofit.RetroClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class ContactCreate extends AppCompatActivity {
    Button btn_submit,btn_cancle;
    AlertDialog dialog,mdialog;
    String contactUrl;
    EditText btn_firstName,btn_lastName,btn_phoneNumber,btn_emailId,btn_address,btn_stateName,btn_cityName,btn_pinCode;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_create);
        btn_submit=findViewById(R.id.submit);
        btn_cancle=findViewById(R.id.cancle);
        btn_stateName=findViewById(R.id.state);



        btn_firstName=findViewById(R.id.firstname);
        btn_lastName=findViewById(R.id.lastname);
        btn_phoneNumber=findViewById(R.id.phonenumber);
        btn_emailId=findViewById(R.id.emailid);
        btn_address=findViewById(R.id.address);
        btn_cityName=findViewById(R.id.btncity);
        btn_pinCode=findViewById(R.id.zipcode);



        progressDialog=new ProgressDialog(this);

        final AlertDialog.Builder mbuilder=new AlertDialog.Builder(ContactCreate.this);
        View mview=getLayoutInflater().inflate(R.layout.dialogcreatcontact,null);
        Button cancle_btn,btn_ok;
        cancle_btn=mview.findViewById(R.id.btn_cancel1);
        btn_ok=mview.findViewById(R.id.btn_ok);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ContactCreate.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreatecontactFromRetrofit();

            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ContactCreate.this,MainActivity.class);
                btn_cancle.setTextColor(Color.parseColor("#FFFFFF"));
                btn_cancle.setBackgroundResource(R.drawable.colorshape);
                startActivity(intent);
                finish();
            }
        });
        btn_stateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mbuilder=new AlertDialog.Builder(ContactCreate.this);
                View pickerview=getLayoutInflater().inflate(R.layout.statetextpicker,null);
                NumberPicker textpicker=(NumberPicker)pickerview.findViewById(R.id.textpicker);
                TextView btn_done=(TextView)pickerview.findViewById(R.id.btndone);

                //Populate NumberPicker values from String array values
                //final String[] values= {"AL","AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID"};
                  final String[] values=getResources().getStringArray(R.array.category_state);
                textpicker.setMinValue(0);
                textpicker.setMaxValue(values.length-1);
                textpicker.setDisplayedValues(values);
                textpicker.setWrapSelectorWheel(true);
                textpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        btn_stateName.setText(values[i1]);
                    }
                });
                btn_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mdialog.cancel();
                    }
                });

                mbuilder.setView(pickerview);
                mdialog=mbuilder.create();
                mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mdialog.show();
            }
        });

    }
    private void CreatecontactFromRetrofit(){

        final String firstName,lastName,phoneNumber,emailId,address,cityName,stateName,pinCode;
        firstName=btn_firstName.getText().toString().trim();
        lastName=btn_lastName.getText().toString().trim();
        phoneNumber=btn_phoneNumber.getText().toString().trim();
        emailId=btn_emailId.getText().toString().trim();
        address=btn_address.getText().toString().trim();
        cityName=btn_cityName.getText().toString().trim();
        stateName=btn_stateName.getText().toString().trim();
        pinCode=btn_pinCode.getText().toString().trim();
        if (TextUtils.isEmpty(firstName)){
            btn_firstName.setError("Please enter your first name");
            btn_firstName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastName)){
            btn_lastName.setError("Please enter your last name");
            btn_lastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)){
            btn_phoneNumber.setError("Please enter your email id");
            btn_phoneNumber.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(emailId)) {
            btn_emailId.setError("Please enter your email");
            btn_emailId.requestFocus();
            return;
        }
          if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            btn_emailId.setError("Enter a valid email");
            btn_emailId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(address)){
            btn_address.setError("Please enter your address");
            btn_address.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pinCode)){
            btn_pinCode.setError("Please enter your address");
            btn_pinCode.requestFocus();
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
        params.put("city", cityName);
        params.put("city", cityName);
        params.put("state", stateName);
        params.put("country", "United States");
        params.put("zipcode", pinCode);
        params.put("action", "create");
        params.put("access_token", AppSharedPreference.getInstance(ContactCreate.this).getAuthToken());

        ApiService api = RetroClient.getApiService();

        Call<CreateContactDataClass> call = api.createContactData(params);
        call.enqueue(new Callback<CreateContactDataClass>() {
            @Override
            public void onResponse(Call<CreateContactDataClass> call, retrofit2.Response<CreateContactDataClass> response) {
                System.out.println(response);

                if (response.body().isSuccess()){
                    AppSharedPreference.getInstance(ContactCreate.this).setContactId(response.body().getId());

                    Intent intent1=new Intent(ContactCreate.this,LeadCreate.class);
                    btn_submit.setTextColor(Color.parseColor("#FFFFFF"));
                    btn_submit.setBackgroundResource(R.drawable.colorshape);
                    startActivity(intent1);
                    finish();

                }else {
                    Toast.makeText(ContactCreate.this, "Duplicate Entry.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateContactDataClass> call, Throwable t) {
                System.out.println(t);
            }
        });




    }
}
