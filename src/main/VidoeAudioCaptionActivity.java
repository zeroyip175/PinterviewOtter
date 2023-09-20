package com.example.fypmypinterview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class VidoeAudioCaptionActivity extends AppCompatActivity {
    private List videoDetailsList, audioDetailsList;
    private RequestQueue requestQueue;
    private String pass_joDescript_value = "", job_tv_str_value = "";
    private TextView pass_jobDescription_keyword, brand_tv, description_tv, audio_insight_tv;
    private Button jt_save_btn, save_interview_caption, compare_btn, audio_insight_btn, save_audio_caption_btn,video_insight_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidoe_audio_caption);
        videoDetailsList = new ArrayList<>();
        audioDetailsList = new ArrayList<>();
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        brand_tv = findViewById(R.id.brand_tv);
        pass_jobDescription_keyword = findViewById(R.id.jobDescription_keywords);
        description_tv = findViewById(R.id.description_tv);
        jt_save_btn = findViewById(R.id.jt_save_btn);
        compare_btn = findViewById(R.id.compare_btn);
        audio_insight_tv = findViewById(R.id.audio_insight_tv);
        save_interview_caption = findViewById(R.id.save_interview_caption);
        audio_insight_btn = findViewById(R.id.audio_insight_btn);
        save_audio_caption_btn = findViewById(R.id.save_audio_caption_btn);
        video_insight_btn = findViewById(R.id.video_insight_btn);
        pass_joDescript_value += InterviewMethodActivity.JDKEYWORDList + "\n";
        pass_jobDescription_keyword.setText(pass_joDescript_value);
        job_tv_str_value = JobDetailActivity.mjob_detail_tv_value;
        description_tv.setText(job_tv_str_value);


        getAudioInsight();
        getVideoInsight();
        SaveJD();
        SaveVideoCaption();
        SaveAudioCaption();
        NextPage();


    }



    private void NextPage() {
        compare_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VidoeAudioCaptionActivity.this, OnetoOneMatchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SaveAudioCaption() {
        save_audio_caption_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = TextUtils.join(",", audioDetailsList);
                AudiowriteToFile("audio_caption.txt", content);
            }

            private void AudiowriteToFile(String fileName, String content) {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsoluteFile();
                try {
                    FileOutputStream writer = new FileOutputStream(new File(path, fileName));
                    writer.write(content.getBytes());
                    writer.close();
                    Toast.makeText(getApplicationContext(), "Saved to file: " + fileName, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void SaveVideoCaption() {
        save_interview_caption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = TextUtils.join(",", videoDetailsList);
                writeToFile("video_caption.txt", content);
                System.out.println("WTF:" + content);
            }
        });
    }

    public void writeToFile(String fileName, String content) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsoluteFile();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName));
            writer.write(content.getBytes());
            writer.close();
            Toast.makeText(getApplicationContext(), "Saved to file: " + fileName, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveJD() {
        jt_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = job_tv_str_value;
                JDwriteToFile("JD.txt", content);
            }

            private void JDwriteToFile(String fileName, String content) {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsoluteFile();
                try {
                    FileOutputStream writer = new FileOutputStream(new File(path, fileName));
                    writer.write(content.getBytes());
                    writer.close();
                    Toast.makeText(getApplicationContext(), "Saved to file: " + fileName, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void getVideoInsight() {
        video_insight_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchVideosInsight();
            }
        });
    }

    private void fetchVideosInsight() {

        String url = "https://api.videoindexer.ai/trial/Accounts/e65ba8e3-80b2-4b51-a346-82223c8ff5bb/Videos/9aaa74130f/Index?language=English&reTranslate=true&includeStreamingUrls=true";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d("JsonObject:", "OnResponse" + response);
                    //JSONArray jsonArray = response.getJSONArray("videos");

                    JSONArray keywords = response.getJSONArray("videos")
                            .getJSONObject(0)
                            .getJSONObject("insights")
                            .getJSONArray("keywords");
                    for (int i = 0; i < keywords.length(); i++) {
                        JSONObject keyword = keywords.getJSONObject(i);
                        String keywords_name = keyword.getString("text");
                        videoDetailsList.add(keywords_name);
                        brand_tv.setText(videoDetailsList.toString());
                    }

                    JSONArray brands = response.getJSONArray("videos")
                            .getJSONObject(0)
                            .getJSONObject("insights")
                            .getJSONArray("brands");
                    for (int x = 0; x < brands.length(); x++) {
                        JSONObject brand = brands.getJSONObject(x);

                        String brands_name = brand.getString("name");
                        videoDetailsList.add(brands_name);
                        brand_tv.setText(videoDetailsList.toString());


                    }

                    JSONArray emotions = response.getJSONArray("videos")
                            .getJSONObject(0)
                            .getJSONObject("insights")
                            .getJSONArray("emotions");
                    for (int y = 0; y < emotions.length(); y++) {
                        JSONObject emotion = emotions.getJSONObject(y);

                        String emotions_name = emotion.getString("type");
                        videoDetailsList.add(emotions_name);
                        brand_tv.setText(videoDetailsList.toString());

                    }

                    JSONArray transcript = response.getJSONArray("videos")
                            .getJSONObject(0)
                            .getJSONObject("insights")
                            .getJSONArray("transcript");
                    for (int z = 0; z < transcript.length(); z++) {
                        JSONObject transcripts = transcript.getJSONObject(z);

                        String transcript_name = transcripts.getString("text");
                        videoDetailsList.add(transcript_name);
                        brand_tv.setText(videoDetailsList.toString());

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: " + error.getStackTrace().toString());
                Toast.makeText(VidoeAudioCaptionActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    private void getAudioInsight() {
        audio_insight_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAudioInsight();
            }

            private void fetchAudioInsight() {
                String url = "https://api.videoindexer.ai/trial/Accounts/e65ba8e3-80b2-4b51-a346-82223c8ff5bb/Videos/1a97930620/Index?language=en-US&reTranslate=true&includeStreamingUrls=true";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.d("JsonObject:", "OnResponse" + response);
                            JSONArray audiokeywords = response.getJSONArray("videos")
                                                              .getJSONObject(0)
                                                              .getJSONObject("insights")
                                                              .getJSONArray("brands");

                            for (int i = 0; i < audiokeywords.length(); i++) {
                                JSONObject audiokeyword = audiokeywords.getJSONObject(i);
                                String audiokeywords_name = audiokeyword.getString("name");
                                audioDetailsList.add(audiokeywords_name);
                                audio_insight_tv.setText(audioDetailsList.toString());

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "onErrorResponse: " + error.getStackTrace().toString());
                        Toast.makeText(VidoeAudioCaptionActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                requestQueue.add(jsonObjectRequest);


            }
        });
    }
}