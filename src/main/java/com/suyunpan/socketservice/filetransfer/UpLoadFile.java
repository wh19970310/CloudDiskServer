package com.suyunpan.socketservice.filetransfer;

import java.io.*;
import java.net.Socket;

public class UpLoadFile implements Runnable {
    private DataOutputStream dos;
    private DataInputStream dis;
    private String path = "D:/FileStore/";
    private Socket socket;
    private RandomAccessFile rad;//方便跳着读文件
    private byte[] buf = new byte[2 * 1024];//分片，每片32KB
    private String MD5;
    private long size = 0;
    private int length;

    @Override
    public void run() {
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF("UPLOAD_OK");
            dos.flush();
            MD5 = dis.readUTF();
            rad = new RandomAccessFile(path + MD5, "rw");
            File file = new File(path + MD5);
            if (file.exists() && file.isFile()) {
                size = file.length();
            } else {
                file.createNewFile();
            }
            dos.writeLong(size);
            dos.flush();
            while ((length = dis.read(buf, 0, buf.length)) > 0) {
                rad.write(buf, 0, length);
            }
            try {
                dis.close();
                dos.close();
                rad.close();
                socket.close();
            } catch (IOException e) {
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                dis.close();
                dos.close();
                rad.close();
                socket.close();
            } catch (IOException ee) {
            }
        } finally {
            try {
                dis.close();
                dos.close();
                rad.close();
                socket.close();
            } catch (IOException e) {
            }
        }
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
