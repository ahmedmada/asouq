package com.example.hp.aswaq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Regisetration extends AppCompatActivity
{
    EditText mail,pass;
    Button Login,signup;
    private LoginButton FacebookLogin;

    private CallbackManager callbackManager;
    HashMap<String,String> hashMap = new HashMap<>();
    public static final String UserEmail = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisetration);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        AndroidNetworking.initialize(getApplicationContext());
        mail=findViewById(R.id.mailET);
        pass=findViewById(R.id.passET);
        Login=findViewById(R.id.loginBT);
        signup=findViewById(R.id.signupBT);

        final String UMail = mail.getText().toString().trim();
        final String UPass = pass.getText().toString().trim();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Regisetration.this,SignUp.class));

            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // SaveRecipt();
                Logg(UMail,UPass);

            }
        });
        FacebookLogin=findViewById(R.id.faceLogin);
        FacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                Log.e( "User ID: ",
                         loginResult.getAccessToken().getUserId()
                        + "\n" +
                        "Auth Token: "
                        + loginResult.getAccessToken().getToken());

            }

            @Override
            public void onCancel() {
                Log.e("cancel","Login attempt canceled.");

            }

            @Override
            public void onError(FacebookException e) {
                Log.e("error","Login attempt error.");

            }




            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public  void Logg(final String Mail,final String Pass)

    {

        // AndroidNetworking.get()
        class Logg extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Regisetration.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {

                super.onPostExecute(s);
                loading.dismiss();
//                if (!isNetworkAvailable()) {
//                    Toast.makeText(Regisetration.this, " No Internet Connection", Toast.LENGTH_SHORT).show();
//                }
//                //ShowSub(s);
                if(s.contains("exist")){
//
                    finish();
                    final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("Registered", true);
                    editor.putString("mail", Mail);

                    editor.apply();
                    startActivity(new Intent(Regisetration.this,FolowingProducts.class));

//                    Intent intent = new Intent(Regisetration.this, FolowingProducts.class);
//
//                    intent.putExtra("mail",Mail);
//
//                    startActivity(intent);

                }
                else{

                    Toast.makeText(Regisetration.this,s+"mmmmmmmmmmm",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params)
            {
                RequestHandler rh = new RequestHandler();
                hashMap.put(Conf.UMail,params[0]);

                hashMap.put(Conf.UPass,params[1]);





                String s = rh.sendPostRequest(Conf.login_ur,hashMap);
                return s;

            }
        }
        Logg gr = new Logg();
        gr.execute(Mail,Pass);


    }
    private boolean isNetworkAvailable()
    {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    public void SaveRecipt()
    {
        final String Mail = mail.getText().toString().trim();
        final String Pass = pass.getText().toString().trim();





        AndroidNetworking.post(Conf.login_ur)
                .addBodyParameter(Conf.UMail, Mail)
                .addBodyParameter(Conf.UPass, Pass)

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if(response.equalsIgnoreCase("exist")){
                            Toast.makeText(Regisetration.this, "Congratulation", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Regisetration.this,FolowingProducts.class));
                        }
                        else if(response.equalsIgnoreCase("nooo")){

                            Log.e("res",response);
                        }
                    }

                    @Override
                    public void onError(ANError anError)
                    {
                        Log.e("err",anError.toString());
                        Toast.makeText(Regisetration.this, "mooo"+anError, Toast.LENGTH_SHORT).show();


                    }
                });

    }
}
