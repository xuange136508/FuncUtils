package com.my.loopiine.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.my.loopiine.R;
import com.my.loopiine.udp.BroadcastThread;
import com.my.loopiine.udp.ClientThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by xx on 2017/7/17.
 */

public class TouPinActivity extends Activity {

    private int port = 4399;
    private String currentIp = "10.17.174.236";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //广播ip地址和端口
//        sendUDPBroadcast();

        //socket连接
        String ip = getLocalIpAddress(this);
        startServer(this,currentIp,port);
    }

    private void sendUDPBroadcast() {
        String ip = getLocalIpAddress(this);
        startBroadcast9iInfo(currentIp,port,getThisWindow());
    }


    public String getThisWindow() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Point outSize = new Point();
        wm.getDefaultDisplay().getSize(outSize);
        int width = outSize.x; //wm.getDefaultDisplay().getWidth();
        int height = outSize.y;//wm.getDefaultDisplay().getHeight();
        return width + "," + height + ",1";
    }

    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void startBroadcast9iInfo(String ip,int port,String window) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ip",ip);
            jsonObject.put("serverPort", port);
            jsonObject.put("packageName", "toupin");
            jsonObject.put("tvName", window);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String broadcastInfo = jsonObject.toString();
        BroadcastThread.getInstance().startBroadcast(broadcastInfo);
    }

    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }






    public void startServer(final Context context, final String ip, final int port){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);

                    while (true){
                        Socket socket = serverSocket.accept();

                        ClientThread clientThread = new ClientThread(socket, context);
                        clientThread.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();



}









}
