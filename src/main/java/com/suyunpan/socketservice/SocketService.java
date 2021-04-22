package com.suyunpan.socketservice;

import com.suyunpan.socketservice.instructtransfer.InstructSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketService extends Thread {
    private ServerSocket server = null;
    private int port = 9999;

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            while (true) {
                Socket socket = server.accept();
                InstructSocket instructSocket = new InstructSocket();
                instructSocket.setSocket(socket);
                Thread thread = new Thread(instructSocket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
