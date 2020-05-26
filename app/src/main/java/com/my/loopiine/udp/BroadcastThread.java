package com.my.loopiine.udp;

import android.os.Environment;
import android.os.SystemClock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZWF on 2016/1/26.
 *
 * 广播线程
 *
 * 3/23 广播数据后面加上#盒子类型
 */
public class BroadcastThread {

    private static BroadcastThread mInstance;

    private static final String BROADCAST_IP = "255.255.255.255";

//    private static final int BROADCAST_PORT = 20087;
    //测试广播端口
    private static final int BROADCAST_PORT = 20099;

    private static final int TIME = 3 * 1000;

    private static final String LOG_TAG = BroadcastThread.class.getSimpleName();

    private String mBroadcastData;
    private boolean mRunning;
    private BroadcastRunnable mBroadcastRunnable;
    private Thread mThread;

    public void setmRunning(boolean mRunning) {
        this.mRunning = mRunning;
    }

    private BroadcastThread(){
    }

    public static BroadcastThread getInstance(){
        if (mInstance == null){
            mInstance = new BroadcastThread();
        }
        return mInstance;
    }

    private static class BroadcastRunnable implements Runnable {

        private String mBroadcastData;
        private boolean running;
        private Thread mThread;

        public void setBroadcastData(String mBroadcastData) {
            this.mBroadcastData = mBroadcastData;
        }

        public void setRunning(boolean mRunning) {
            this.running = mRunning;
        }

        public void setmThread(Thread mThread) {
            this.mThread = mThread;
        }

        public BroadcastRunnable(String data, boolean running, Thread thread){
            this.mBroadcastData = data;
            this.running = running;
            this.mThread = thread;

        }

        @Override
        public void run() {
            try {
                DatagramSocket datagramSocket = new DatagramSocket();
                while (running) {
                    byte[] buf = mBroadcastData.getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length,
                            InetAddress.getByName(BROADCAST_IP), BROADCAST_PORT);
                    datagramSocket.send(datagramPacket);
                    SystemClock.sleep(TIME);
                    //Log.e(LOG_TAG, "++ BroadcastThread "+mThread.toString()+"-->>"+mBroadcastData);
                }
                datagramSocket.close();
            } catch (SocketException e) {
                e.printStackTrace();
                running = false;
                getInstance().setmRunning(false);
//                logTest(e.getMessage());
            } catch (UnknownHostException e) {
                e.printStackTrace();
                running = false;
                getInstance().setmRunning(false);
//                logTest(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
                getInstance().setmRunning(false);
//                logTest(e.getMessage());
            }
        }
    }

    public void setBroadcastData(String broadcastData) {
        this.mBroadcastData = broadcastData;
    }

    public void startBroadcast() {
        startBroadcast("com.yumeng.tvservice");
    }

    public void startBroadcast(String broadcastData) {
        this.mBroadcastData = broadcastData;
        if (mBroadcastRunnable == null){
            mBroadcastRunnable = new BroadcastRunnable(mBroadcastData, mRunning, mThread);
        }
        mBroadcastRunnable.setBroadcastData(broadcastData);
        if (!mRunning){
            mRunning = true;
            mBroadcastRunnable.setRunning(mRunning);
            mThread = new Thread(mBroadcastRunnable);
            mBroadcastRunnable.setmThread(mThread);
            mThread.start();
        }
    }

    public void stopBroadcast() {
        mRunning = false;
    }

    private static void logTest(String erre){
        String path1 = Environment.getExternalStorageDirectory().getPath() + "/9i9i9i";
        File file1 = new File(path1);
        if (!file1.exists()) {
            file1.mkdirs();
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-mm-ss");
            String format = simpleDateFormat.format(new Date());
            String path = Environment.getExternalStorageDirectory().getPath() + "/9i9i9i/" + format + ".txt";
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buf = erre.getBytes();
            fileOutputStream.write(buf);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
