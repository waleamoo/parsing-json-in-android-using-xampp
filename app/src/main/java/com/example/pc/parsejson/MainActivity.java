package com.example.pc.parsejson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button start;
    TextView textView;
    // Request queue class allows json request and all other url request to be processed
    // without using the async task
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.btnParseJSON);
        textView = (TextView) findViewById(R.id.tv_hello_world);

        requestQueue = Volley.newRequestQueue(this);

        start.setOnClickListener(new View.OnClickListener() {
            /*
            works perfectly with:
            https://androidwebapp.000webhostapp.com/student.json
             */
            @Override
            public void onClick(View view) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.33.1/student.json",

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("students");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject student = jsonArray.getJSONObject(i);
                                        String firstname = student.getString("firstname");
                                        String lastname = student.getString("lastname");
                                        String age = student.getString("age");
                                        textView.append(firstname + " " + lastname + " " + age + "\n");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("VOLLEY", "ERROR");
                            }
                        }); // this is very important struggled here...

                // after the the request then call the request queue
                requestQueue.add(jsonObjectRequest);
            }

        });

    }

}