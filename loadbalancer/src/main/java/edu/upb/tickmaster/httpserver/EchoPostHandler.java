/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tickmaster.httpserver;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author rlaredo
 */
public class EchoPostHandler implements HttpHandler {


    public EchoPostHandler() {

    }

    @Override
    public void handle(HttpExchange he) throws IOException {

        try {
            String response;
            Headers responseHeaders = he.getResponseHeaders();
            responseHeaders.add("Access-Control-Allow-Origin", "*");
            responseHeaders.add("Content-type", ContentType.JSON.toString());

            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://www.google.com"))
                        .headers("Accept", he.getResponseHeaders().get("Accept").getFirst())
                        .GET()
                        //.POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();


                // Enviamos la petición de forma síncrona pero eficiente
                HttpResponse<String> responses = client.send(request, HttpResponse.BodyHandlers.ofString());
                he.sendResponseHeaders(responses.statusCode(), responses.body().getBytes().length);
                OutputStream os = he.getResponseBody();
                os.write(responses.body().getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
