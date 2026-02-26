package edu.upb.tickmaster;

import edu.upb.tickmaster.grpc.ProtoServer;
import edu.upb.tickmaster.httpserver.ApacheServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ApacheServer apacheServer = new ApacheServer();
        apacheServer.start();
        System.out.println("-------- TickMaster started 1.0 ---------");
        System.out.println("-------- Puerto:  1914 ---------");
        System.out.println("-------- Stereum Tech           ---------");

        ProtoServer protoServer = new ProtoServer();
        protoServer.start();
    }
}
