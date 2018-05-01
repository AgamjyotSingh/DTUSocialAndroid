package mank.dtusocialtodo;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaCas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Swagam on 24/04/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName, edtPassWord;
    private Button login;

    String username;
    String password;


    private String URL = "http://10.0.2.2:8080/DTUSocial/login";
    AlertDialog.Builder builder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        URL = URL.replaceAll(" ", "%20");

        builder = new AlertDialog.Builder(this);

        edtUserName = findViewById(R.id.input_username);
        edtPassWord = findViewById(R.id.input_password);

        login = findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = edtUserName.getText().toString();
                password = edtPassWord.getText().toString();


                if (username.equals("") || password.equals("")){

                    builder.setTitle("Oops");
                    displayText("Incorrect username or password, please try again!");

                }
                else{
                    postResponse();

                    System.out.println(username + password);

                }
            }
        });


    }

    public void displayText(String message){

        builder.setMessage(message);
        builder.setPositiveButton("Ok baby!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                edtUserName.setText("");
                edtPassWord.setText("");

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    public void postResponse() {

        Map<String, String> params = new HashMap<String, String>();

        params.put("username", username);
        params.put("password", password);

        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("REST RESPONSE ", response);

                        System.out.println(response);

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);

                        i.putExtra("token", response);

                        startActivity(i);

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("REST RESPONSE: ", error.toString());

                        }
                }
        ) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("username", username);
                params2.put("password", password);
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };


        AppSingleton.getAppInstance(this).addToRequestQueue(jsonObjectRequest);

    }


}
