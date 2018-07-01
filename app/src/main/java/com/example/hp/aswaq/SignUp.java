package com.example.hp.aswaq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class SignUp extends AppCompatActivity
{
    ProgressDialog progressDialog;
    EditText fname,Lname,mail,Pass;
    String F_Name,L_Name,Mail_holder,Pass_holder;
    Button Sign;
    Boolean CheckEditText ;
    HashMap<String,String> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        fname=findViewById(R.id.FN);
        Lname=findViewById(R.id.LN);
        mail=findViewById(R.id.signup_input_email);
        Pass=findViewById(R.id.signup_input_password);
        Sign=findViewById(R.id.btn_signup);
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    // If EditText is not empty and CheckEditText = True then this block will execute.

                    UserRegisterFunction(F_Name,L_Name, Mail_holder, Pass_holder);

                }
                else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(SignUp.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });

    }
    public void CheckEditTextIsEmptyOrNot(){

        F_Name = fname.getText().toString();
        L_Name = Lname.getText().toString();
        Mail_holder = mail.getText().toString();
        Pass_holder = Pass.getText().toString();


        if(TextUtils.isEmpty(F_Name) || TextUtils.isEmpty(L_Name) || TextUtils.isEmpty(Mail_holder) || TextUtils.isEmpty(Pass_holder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
    public void UserRegisterFunction(final String F_Name, final String L_Name, final String email, final String password){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(SignUp.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                if(httpResponseMsg.equals("Registration Successfully")){
                    Toast.makeText(SignUp.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUp.this,FolowingProducts.class));


                }



            }

            @Override
            protected String doInBackground(String... params) {
                RequestHandler rh = new RequestHandler();

                hashMap.put(Conf.S_Fname,params[0]);

                hashMap.put(Conf.S_Lname,params[1]);

                hashMap.put(Conf.S_mail,params[2]);

                hashMap.put(Conf.S_pass,params[3]);

             String   finalResult = rh.sendPostRequest(Conf.signup_ur,hashMap);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(F_Name,L_Name,email,password);
    }

}
