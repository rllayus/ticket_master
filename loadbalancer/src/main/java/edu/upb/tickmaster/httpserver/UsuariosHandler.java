/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tickmaster.httpserver;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author rlaredo
 */
public class UsuariosHandler implements HttpHandler {


    public UsuariosHandler() {

    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        try {
            InputStreamReader isr = new InputStreamReader(he.getRequestBody(), StandardCharsets.UTF_8);
            String response;
            BufferedReader br = new BufferedReader(isr);
            Headers responseHeaders = he.getResponseHeaders();
            responseHeaders.add("Access-Control-Allow-Origin", "*");
            responseHeaders.add("Content-type", ContentType.JSON.toString());

            if (he.getRequestMethod().equals("POST")) {
                try {
                    JsonObject object = new JsonObject();
                    object.addProperty("nombre", "Ricardo");
                    object.addProperty("Apellido", "Laredo");
                    object.addProperty("Apellido2", "Laredos");
                    response = object.toString();
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

                response = "{\"status\": \"NOK\",\"message\": \"No se logro imprir la factura\"}";

                byte [] byteResponse = response.getBytes(StandardCharsets.UTF_8);
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
