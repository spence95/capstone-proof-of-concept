package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;

import java.util.ArrayList;


public class ApiCall {


    public ArrayList<String> httpReturns;





    public ApiCall() {
        httpReturns = new ArrayList<String>();
    }




    public String httpGet (String URL, final int ListNumber) {

        httpReturns.add(ListNumber, "");

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl(URL);
        httpRequest.setHeader("Content-Type", "application/json");

        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println("handle");
                String successValue = httpResponse.getResultAsString();
                System.out.println(successValue);
                if (successValue.contains("\"total_count\": 0"))//wrong credentials
                {
                    httpReturns.set(ListNumber, "EMPTY");
                } else//there was a match yo! should probably have a unique conststraint on username. too hard eff it
                {
                    httpReturns.set(ListNumber, successValue);

                }
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("failed");
                httpReturns.set(ListNumber, "FAILED");
                System.out.println(t.toString());
            }

            @Override
            public void cancelled() {

                System.out.println("cancelled");
                httpReturns.set(ListNumber, "CANCELLED");
            }
        });

        while (httpReturns.get(ListNumber).length() < 1)//while its empty because the HTTP method hasnt returned yet
        {

        }

        return httpReturns.get(ListNumber);

    }

    //gdx's .net doesn't have a patch method
    //no worky
//    public String httpPatch (String targetURL, String Body) {
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL(targetURL);
//            connection = (HttpURLConnection)url.openConnection();
//            connection.setRequestMethod("PATCH");
//            connection.setRequestProperty("Content-Type",
//                    "application/json");
//
//            connection.setUseCaches(false);
//            connection.setDoOutput(true);
//
//            //Send request
//            DataOutputStream wr = new DataOutputStream(
//                    connection.getOutputStream());
//            wr.writeBytes(Body);
//            wr.close();
//
//            //Get Response
//            InputStream is = connection.getInputStream();
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//            StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
//            String line;
//            while((line = rd.readLine()) != null) {
//                response.append(line);
//                response.append('\r');
//                System.out.println("appending");
//            }
//            rd.close();
//            return response.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if(connection != null) {
//                connection.disconnect();
//            }
//        }
//    }

    public String httpPostOrPatch (String URL, String Body, final int ListNumber, boolean isPatch) {


        httpReturns.add(ListNumber, "");

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(URL);
        httpRequest.setHeader("Content-Type", "application/json");
        //override to patch method
        if(isPatch)
            httpRequest.setHeader("X-HTTP-Method-Override", "PATCH");


        httpRequest.setContent(Body);


        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println("handle");
                String successValue = httpResponse.getResultAsString();
                System.out.println(successValue);
                if (successValue.contains("\"total_count\": 0"))//wrong credentials
                {
                    httpReturns.set(ListNumber, "EMPTY");
                } else//there was a match yo! should probably have a unique conststraint on username. too hard eff it
                {
                    httpReturns.set(ListNumber, successValue);

                    if (successValue.length() < 1)
                    {
                        httpReturns.set(ListNumber, "SUCCESSFUL POST");
                    }

                }
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("failed");
                httpReturns.set(ListNumber, "FAILED");
                System.out.println(t.toString());
            }

            @Override
            public void cancelled() {

                System.out.println("cancelled");
                httpReturns.set(ListNumber, "CANCELLED");
            }
        });

        while (httpReturns.get(ListNumber).length() < 1)//while its empty because the HTTP method hasnt returned yet
        {

        }

        return httpReturns.get(ListNumber);

    }





}