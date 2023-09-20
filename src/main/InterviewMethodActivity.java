package com.example.fypmypinterview;

import static android.Manifest.permission.INTERNET;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisCancellationDetails;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class InterviewMethodActivity extends AppCompatActivity {
    private static final int MY_SOCKET_TIMEOUT_MS = 20;
    private ImageButton videoRecording_btn, audioRecording_btn;
    private Button PostContent;
    public static String JDPublicString;
    private TextView normalQTextView,questionBrandTextView;
    private static String speechSubscriptionKey = "f8e09f1e6dbf4bc5b7a4e7939af9188a";
    //Replace below with your own service region (e.g., "westus").
    private static String serviceRegion = "eastus";
    private SpeechConfig speechConfig;
    private SpeechSynthesizer synthesizer;
    public static List <String> brandList;
    public static List <String> industyKList;
    public static List <String> JDKEYWORDList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Note: we need to request the permissions
        int requestCode = 5; // unique code for the permission request
        ActivityCompat.requestPermissions(InterviewMethodActivity.this, new String[]{INTERNET}, requestCode);
        // Initialize speech synthesizer and its dependencies

        speechConfig = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion);
        assert(speechConfig != null);
        synthesizer = new SpeechSynthesizer(speechConfig);
        assert(synthesizer != null);

        setContentView(R.layout.activity_interview_method);
        videoRecording_btn =findViewById(R.id.camera_recording_btn);
        audioRecording_btn = findViewById(R.id.voice_recording_btn);
        normalQTextView = findViewById(R.id.normalQTextView);
        questionBrandTextView = findViewById(R.id.questionBrandTextView);
        PostContent = findViewById(R.id.post_btn);

      JDContentToAzureButton();
        VideoRecordingBtn();
        AudioRecordingBtn();


        Bundle bundle = getIntent().getExtras();

        String question_tv = bundle.getString("job_description");
        question_tv.toLowerCase();
        JDPublicString = question_tv.replaceAll("\n", "");

    }
    //Json data we need to post
    // {\"id\":\"documentId\",\"text\":\"Client DescriptionWell-known e-Commerce and Internet companyJob DescriptionCandidate with less experience will be considered as Senior Mobile EngineerResponsibilitiesActing as senior developer, involving in mobile development processPlanning and monitor team member overall performanceResponsible for studying and training team new mobile technology and frameworkResponsible for business development related to mobile technologyProviding advices and consultancy regarding team development and strategiesDesigning and improving features of our mobile applicationsJob RequirementsDegree holder of Computer Science, Mobile Application or equivalent8+ years of technical development experience focusing on Android1-2 years in team leader positionSolid development using Android Studio and GIT, knowledge and experience in Kotlin is a plusSolid experience on integrating RESTful APIExperience using social network APIsSolid development using 3rd party frameworks & Android librariesExperience in end-to-end troubleshooting with complex distributed backend systems is a plusDevelopment experience on products for mass markets, creative thinking and innovative ideas\",\"language\":\"en\"}]}

    private void JDContentToAzureButton() {
        PostContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PostJDContentToAzure();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void PostJDContentToAzure() throws JSONException {

        // Json data
        // "{ documents: [{ id: \"1\", language:\"en\", text: \"I had a wonderful trip to Seattle last week.\"}]}"
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://otterpinterviewtextta.cognitiveservices.azure.com/text/analytics/v3.1/entities/recognition/general";

        String json = "{documents: [{ id: \"1\", language:\"en\", text: \""+JDPublicString+"\"}]}";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(json),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("documents")
                                                            .getJSONObject(0)
                                                            .getJSONArray("entities");

                            //Creating keywordList and adding the data to it
                            List<String> EntitiesList = new ArrayList<String>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject entities = jsonArray.getJSONObject(i);

                                String text = entities.getString("text");

                                EntitiesList.add(text);

                            }

                            int size = EntitiesList.size();
                            String[] keywordArray = EntitiesList.toArray(new String[size]);
                            JDKEYWORDList = new ArrayList<>();
                            JDKEYWORDList = Arrays.asList(keywordArray);

                            for (String s: keywordArray) {
                                System.out.println("Output String array will be like:" + s);
                            }

                            Random r  = new Random();
                            int radomKeyWordsNumber  = r.nextInt(keywordArray.length);
                            String RandomKeyWordsName = keywordArray[radomKeyWordsNumber];
                            normalQTextView.setText("Can you introduce yourself? ");
                            questionBrandTextView.setText("Do you have Skill  on or what is your Person Type or do you have experience on:  " + RandomKeyWordsName + " ?");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InterviewMethodActivity.this, "API not work", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Ocp-Apim-Subscription-Key","b46bde480f8a4ffd8151405a71ab0268");
                return headers;
            }
        };
        queue.add(jsonRequest);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release speech synthesizer and its dependencies
        synthesizer.close();
        speechConfig.close();
    }

    public void onSpeechButtonClicked(View v) {

        TextView outputMessage = this.findViewById(R.id.outputMessage);


        try {
            // Note: this will block the UI thread, so eventually, you want to register for the event
            SpeechSynthesisResult result = synthesizer.SpeakText(normalQTextView.getText().toString() + questionBrandTextView.getText().toString());
            assert(result != null);

            if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                outputMessage.setText("Speech synthesis succeeded.");
            }
            else if (result.getReason() == ResultReason.Canceled) {
                String cancellationDetails =
                        SpeechSynthesisCancellationDetails.fromResult(result).toString();
                outputMessage.setText("Error synthesizing. Error detail: " +
                        System.lineSeparator() + cancellationDetails +
                        System.lineSeparator() + "Did you update the subscription info?");
            }

            result.close();
        } catch (Exception ex) {
            Log.e("SpeechSDKDemo", "unexpected " + ex.getMessage());
            assert(false);
        }
    }

    private void AudioRecordingBtn() {
        videoRecording_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InterviewMethodActivity.this,VideoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void VideoRecordingBtn() {
        audioRecording_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InterviewMethodActivity.this, VoiceRecordingActivity.class);
                startActivity(intent);
            }
        });
    }






}
