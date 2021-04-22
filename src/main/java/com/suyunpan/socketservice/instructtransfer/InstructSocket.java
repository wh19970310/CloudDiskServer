package com.suyunpan.socketservice.instructtransfer;

import com.suyunpan.socketservice.filetransfer.DownloadFile;
import com.suyunpan.socketservice.filetransfer.UpLoadFile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class InstructSocket implements Runnable {
    private DataOutputStream dos;
    private DataInputStream dis;
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            String request = dis.readUTF();//期待读到一个DOWNLOAD
            if ("DOWNLOAD".equals(request)) {
                DownloadFile downloadFile = new DownloadFile();
                downloadFile.setSocket(socket);
                Thread thread = new Thread(downloadFile);
                thread.start();
            } else if ("UPLOAD".equals(request)) {
                UpLoadFile upLoadFile = new UpLoadFile();
                upLoadFile.setSocket(socket);
                new Thread(upLoadFile).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
