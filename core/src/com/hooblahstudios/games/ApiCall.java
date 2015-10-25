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
import java.util.List;


public class ApiCall {


    public ArrayList<String> httpReturns;
    public World.playerActionRetrievalCallback callback;


    public ApiCall(){
        httpReturns = new ArrayList<String>();
    }

    public ApiCall(World.playerActionRetrievalCallback callback)
    {
        this.callback = callback;
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
                String successValue = httpResponse.getResultAsString();
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


    public String httpPostPutOrPatch (String URL, String Body, final int ListNumber, boolean isPatch, boolean isPut) {


        httpReturns.add(ListNumber, "");
        Net.HttpRequest httpRequest;
        if(isPut){
            httpRequest = new Net.HttpRequest(Net.HttpMethods.PUT);
        } else {
            httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        }
        httpRequest.setUrl(URL);
        httpRequest.setHeader("Content-Type", "application/json");
        //override to patch method
        if(isPatch)
            httpRequest.setHeader("X-HTTP-Method-Override", "PATCH");


        httpRequest.setContent(Body);


        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String successValue = httpResponse.getResultAsString();
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


    public void httpGetAndRunPlayers (String URL, final String playerUrl, final int index) {


        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl(URL);
        httpRequest.setHeader("Content-Type", "application/json");

        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String successValue = httpResponse.getResultAsString();
                if (successValue.contains("\"total_count\": 0"))//wrong credentials
                {
                    //do something insightful here

                } else//there was a match yo! should probably have a unique conststraint on username. too hard eff it
                {
                    callback.setPlayersForRunning(successValue, playerUrl, index);
                }
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("failed");
                System.out.println(t.toString());
            }

            @Override
            public void cancelled() {
                System.out.println("cancelled");
            }
        });

    }





}