package com.werbenjagermanjensenstudios.charitychamps;

/**
 * Created by spence95 on 9/4/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

import java.util.ArrayList;


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
                    System.out.println("IN GET");
                    System.out.println(successValue);
                    System.out.println(httpResponse.getStatus().getStatusCode());
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
//            System.out.println("waiting for server");
        }

        return httpReturns.get(ListNumber);

    }


    public String httpPostPutOrPatch (String URL, String Body, final int ListNumber, boolean isPatch, boolean isPut) {


        //a put is to update 1 thing, a patch is how to post a bunch of things
        //oh, so when you do a put I guess you cant do a put on like a ..api/v1/playermatch/?player=2&match=3&format=json
        //instead, you have to do a get on that or just keep the ID it created (for the playermatch) so you can instead do
        //...api/v1/playermatch/6/?format=json and do a PUT on that.

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