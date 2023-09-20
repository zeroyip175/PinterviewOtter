package com.example.fypmypinterview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JobMain extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        jobList = new ArrayList<>();
        fetchJobs();



    }

    private void fetchJobs() {

        String url = "https://parsehub.com/api/v2/runs/trb_B8Gchocz/data?api_key=tv3cV_YP10To&format=json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {



                    try {
                        Log.d("JsonObject:", "OnResponse" +response);
                        JSONArray jsonArray = response.getJSONArray("jobposts");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobposts = jsonArray.getJSONObject(i);
                            if (jobposts.has("job_description")){
                                String job_description = jobposts.getString("job_description");


                            String jobTitle = jobposts.getString("job_title");
                            String company_name = jobposts.getString("company_name");
                            String location = jobposts.getString("location");
                            String salary = jobposts.getString("salary");


                            Jobs jobs = new Jobs(jobTitle, company_name, location,salary,job_description);
                            jobList.add(jobs);
                            Log.d("Detail","response");

                            }
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    JobAdapter adapter = new JobAdapter(JobMain.this, jobList);

                    recyclerView.setAdapter(adapter);
                }





        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: " + error.getStackTrace().toString());
                Toast.makeText(JobMain.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        requestQueue.add(jsonObjectRequest);

    }



}







