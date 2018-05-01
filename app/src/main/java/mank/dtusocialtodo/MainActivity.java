package mank.dtusocialtodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static mank.dtusocialtodo.RecyclerAdapter.list;

public class MainActivity extends AppCompatActivity {

    static RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    String URL = "http://10.0.2.2:8080/DTUSocial/users/usertodo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Button button = findViewById(R.id.delete);

        this.setTitle("My Todo");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.remove(recyclerView.getChildLayoutPosition(view));

            }
        });

        getResponse();
    }


    public void getResponse() {
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e("Rest Response: ", response.toString());
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson gson = gsonBuilder.create();

                        List<TodoList> list = Arrays.asList(gson.fromJson(response.toString(), TodoList[].class));

                        adapter = new RecyclerAdapter(list);
                        recyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Rest Response: ", error.toString());

                    }
                }
                ){

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> params = new HashMap<String, String>();
                Intent i = getIntent();
                String token = i.getStringExtra("token");
                String json = new Gson().fromJson(token, String.class);
                params.put("Authorization", json);
                params.put("Content-Type", "application/json");

                return params;
            }


        };


        AppSingleton.getAppInstance(this).addToRequestQueue(arrayRequest);
    }


}
