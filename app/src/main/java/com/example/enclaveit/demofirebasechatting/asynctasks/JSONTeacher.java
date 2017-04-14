package com.example.enclaveit.demofirebasechatting.asynctasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.enclaveit.demofirebasechatting.ActivityMain;
import com.example.enclaveit.demofirebasechatting.bean.Teacher;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by enclaveit on 04/04/2017.
 */

public class JSONTeacher extends AsyncTask<String, Void, String>{

    private ProgressDialog pDialog;
    private ActivityMain context;
    private HttpGet httpGet ;
    private HttpClient client = new DefaultHttpClient();
    private HttpResponse response;
    private HttpEntity httpEntity;
    private JSONArray jsonArrayResultDetails;
    private String message;

    List<JSONObject> objectList = new ArrayList<>();
    List<Teacher> listTeachers = new ArrayList<>();

    public interface AsyncResponse {
        void getListTeacher(List<Teacher> output);
    }

    public AsyncResponse delegateTeacher = null;

    public JSONTeacher(ActivityMain context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        httpGet = new HttpGet(url);
        try{
            response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode= statusLine.getStatusCode();
            if(statusCode == 200){
                httpEntity = response.getEntity();
                InputStream is = httpEntity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while((line = br.readLine()) != null){
                    stringBuilder.append(line);
                }
                message = stringBuilder.toString();
                return message;
            }else{
                Log.i("JSONSubject", "Error parse json from service");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return message;
    }

    @Override
    protected void onPostExecute(String result) {
        /*Convert JSON to ArrayList<Object> */
        try {
            JSONObject data = new JSONObject(result);

            Log.d("TAG",result);

            jsonArrayResultDetails = new JSONArray(data.getString("teachers"));
            Teacher teacher = new Teacher();
            for (int i = 0; i < jsonArrayResultDetails.length(); i++) {
                teacher.setTeacherID(Integer.parseInt(jsonArrayResultDetails.getJSONObject(i).getString("teacherID")));
                teacher.setTeacherName(String.valueOf(jsonArrayResultDetails.getJSONObject(i).getString("teacherName")));
                teacher.setTeacherEmail(String.valueOf(jsonArrayResultDetails.getJSONObject(i).getString("teacherEmail")));
                teacher.setTeacherPhone(String.valueOf(jsonArrayResultDetails.getJSONObject(i).getString("teacherPhone")));
                teacher.setTeacherAddress(String.valueOf(jsonArrayResultDetails.getJSONObject(i).getString("teacherAddress")));
                teacher.setTeacherBirthDate(String.valueOf(jsonArrayResultDetails.getJSONObject(i).getString("teacherBirthDate")));
                objectList.add(jsonArrayResultDetails.getJSONObject(i));
            }

            for(int j = 0; j < objectList.size(); j++){
                listTeachers.add(new Teacher(
                    Integer.parseInt(objectList.get(j).getString("teacherID")),
                    String.valueOf(objectList.get(j).getString("teacherName")),
                        String.valueOf(objectList.get(j).getString("teacherEmail")),
                        String.valueOf(objectList.get(j).getString("teacherPhone")),
                        String.valueOf(objectList.get(j).getString("teacherAddress")),
                        String.valueOf(objectList.get(j).getString("teacherBirthDate"))
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        delegateTeacher.getListTeacher(listTeachers);
        pDialog.dismiss();
    }
}
