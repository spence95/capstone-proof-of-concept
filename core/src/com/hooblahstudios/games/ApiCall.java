package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
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

    public String httpPost (String URL, String body, final int ListNumber) {

        httpReturns.add(ListNumber, "");

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(URL);
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setContent(body);

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





}