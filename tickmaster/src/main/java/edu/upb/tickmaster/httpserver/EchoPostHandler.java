/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tickmaster.httpserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

            if (he.getRequestMethod().equals("POST")) {
                try (InputStream is = he.getRequestBody()) {

                    // Read request body
                    Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name());
                    String requestBody = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";

                    // Parse JSON
                    JsonObject jsonRequest = new com.google.gson.JsonParser().parse(requestBody).getAsJsonObject();
                    String codigo = jsonRequest.get("codigo").getAsString();

                    // Insert into database

                    // Send response
                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("status", "OK");
                    response = jsonResponse.toString();

                    he.sendResponseHeaders(Integer.parseInt(Status._200.name().substring(1, 4)), response.length());
                } catch (Exception e) {
                    response = "{\"status\": \"NOK\",\"message\": \"No se logro imprir la factura\"}";
                    he.sendResponseHeaders(Integer.parseInt(Status._200.name().substring(1, 4)), response.length());

                }
                OutputStream os = he.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;

            }
            if (he.getRequestMethod().equals("GET")) {

                //response = "{\"status\": \"NOK\",\"message\": \"No se logro imprir la factura\"}";

                JsonArray jsonResponse = new JsonArray();

                JsonObject jsonResponseObject = new JsonObject();
                jsonResponseObject.addProperty("status", "OK");
                jsonResponseObject.addProperty("message", "No se logro imprir la factura");
                jsonResponse.add(jsonResponseObject);

                jsonResponseObject.addProperty("status2", "OK");
                jsonResponseObject.addProperty("message2", "No se logro imprir la factura");
                jsonResponse.add(jsonResponseObject);

                response = jsonResponse.toString();

                byte[] byteResponse = response.getBytes(StandardCharsets.UTF_8);
                he.sendResponseHeaders(Integer.parseInt(Status._200.name().substring(1, 4)), byteResponse.length);
                OutputStream os = he.getResponseBody();
                os.write(byteResponse);
                os.close();
                return;
            }

            if (he.getRequestMethod().equals("OPTIONS")) {
                response = "{\"status\": \"OK\",\"message\": \"Factura impreso correctamente\"}";
                he.sendResponseHeaders(Integer.parseInt(Status._200.name().substring(1, 4)), response.length());
            } else {
                response = "{\"status\": \"NOK\",\"message\": \"Methodo no soportado\"}";
                he.sendResponseHeaders(Integer.parseInt(Status._404.name().substring(1, 4)), response.length());
            }
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
