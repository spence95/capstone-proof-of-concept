package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
<<<<<<< HEAD
=======
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
>>>>>>> proofOfConcept/WilsonSigninAndPosting
import java.util.ArrayList;


public class ApiCall {


    public ArrayList<String> httpReturns;


<<<<<<< HEAD
=======


>>>>>>> proofOfConcept/WilsonSigninAndPosting
    public ApiCall() {
        httpReturns = new ArrayList<String>();
    }

<<<<<<< HEAD
=======


>>>>>>> proofOfConcept/WilsonSigninAndPosting
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

<<<<<<< HEAD
    public String httpPost (String URL, String body, final int ListNumber) {
=======
    public String httpPost (String URL, String Body, final int ListNumber) {
>>>>>>> proofOfConcept/WilsonSigninAndPosting

        httpReturns.add(ListNumber, "");

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(URL);
        httpRequest.setHeader("Content-Type", "application/json");
<<<<<<< HEAD
        httpRequest.setContent(body);
=======
        httpRequest.setContent(Body);
>>>>>>> proofOfConcept/WilsonSigninAndPosting

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
<<<<<<< HEAD
=======
                    if (successValue.length() < 1)
                    {
                        httpReturns.set(ListNumber, "SUCCESSFUL POST");
                    }
>>>>>>> proofOfConcept/WilsonSigninAndPosting

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