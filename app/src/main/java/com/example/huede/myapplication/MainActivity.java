package com.example.huede.myapplication;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3;
    EditText edt1;
    String Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt1 = (EditText) findViewById(R.id.edt1);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        new SendMessage().execute();
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), page2.class);
                startActivity(i);
                finish();
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeformatter = new SimpleDateFormat("HH:mm");
                Date curDate = new Date(System.currentTimeMillis());
                String datestr = dateformatter.format(curDate);
                String timestr = timeformatter.format(curDate);
                edt1.setText(datestr + " " + timestr + " " + Message);

            }
        });


    }

    class SendMessage extends AsyncTask<String, String, String> {


        protected String doInBackground(String... args) {


            try {
                //建立要傳送的JSON物件
                JSONObject json = new JSONObject();
                json.put("json", "huede");

                //建立POST Request
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://192.168.3.221:1082/api/cie/huede");

                //JSON物件放到POST Request
                StringEntity stringEntity = new StringEntity(json.toString());
                Log.d("data1", json.toString());
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);

                //執行POST Request
                HttpResponse httpResponse = httpClient.execute(httpPost);

                //取得回傳內容
                HttpEntity httpEntity = httpResponse.getEntity();
                String responseString = EntityUtils.toString(httpEntity, "UTF-8");

                //回傳的內容轉存為JSON物件
                JSONObject responseJSON = new JSONObject(responseString);

                //取得Message屬性
                Message = responseJSON.getString("message");

                Log.d("data2", Message);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


    }


}
