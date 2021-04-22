package com.suyunpan.socketservice.filetransfer;

import com.suyunpan.appdata.AppData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;

public class DownloadFile implements Runnable {
    private DataOutputStream dos;
    private DataInputStream dis;
    private String path = "D:/FileStore/";
    private Socket socket;
    private RandomAccessFile rad;//方便跳着读文件
    private byte[] buf = new byte[2 * 1024];//分片，32KB
    private String fileMD5;

    @Override
    public void run() {
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF("DOWNLOAD_OK");
            dos.flush();
            fileMD5 = dis.readUTF();//期待读到文件的MD5
            AppData.downLoadRateMap.put(fileMD5, "0%");
            path = path + fileMD5;
            rad = new RandomAccessFile(path, "r");
            dos.writeUTF("OK_FILEMD5");
            dos.flush();
            Long hasDownSize = dis.readLong();
            long offset = hasDownSize;//字节偏移量
            int length;
            if (offset < rad.length()) {
                rad.seek(offset);
                while ((length = rad.read(buf)) > 0) {
                    dos.write(buf, 0, length);
                    dos.flush();
                    hasDownSize += length;
                    AppData.downLoadRateMap.put(fileMD5, (int) (((hasDownSize + 0.0) / rad.length()) * 100) + "%");
                }
                dis.close();
                dos.close();
                rad.close();
                socket.close();
            } else {
                AppData.downLoadRateMap.put(fileMD5, "100%");
                dis.close();
                dos.close();
                rad.close();
                socket.close();
            }
        } catch (IOException e) {
            AppData.downLoadRateMap.put(fileMD5, "pause");
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
