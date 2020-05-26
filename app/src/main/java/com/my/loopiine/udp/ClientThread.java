package com.my.loopiine.udp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.my.loopiine.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by chenjy on 2017/3/14.
 */
public class ClientThread extends Thread {

    private final String LOG_TAG = "ClientThread";
    private final int PACK_HEAD_LENGTH = 16;

    private boolean running = true;
    private Context mContext;
    private Socket mSocket;

    private int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }

    public ClientThread(Socket socket, Context context) {
        this.mContext = context;
        this.mSocket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStream os = mSocket.getOutputStream();
            while (running) {
                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.a);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                byte[] array= out.toByteArray();
//                os.write(array,0,array.length);
                send_img_byte(array);

            }
        } catch (IOException e) {
            running = false;
            Log.e(LOG_TAG, "+++++ ClientThread IOException="+e.getStackTrace());
            e.printStackTrace();
        }
    }


    public synchronized void send_img_byte(byte[] tmp){
        ByteBuffer buffer = ByteBuffer.allocate(tmp.length+16);
        buffer.putInt(tmp.length+8);
        buffer.putInt(tmp.length);
        buffer.put(tmp);
        dealResponse(buffer.array());
    }


    public synchronized void dealResponse(byte[] buf) {
        try {
            if(mSocket==null ||mSocket.isClosed()){
                //Log.e(TAG, "+++++ mSocket isClosed ");
                return;
            }
            //_sendLock.lock();
            OutputStream outputStream = mSocket.getOutputStream();
            outputStream.flush();
            outputStream.write(buf);
            outputStream.flush();
            //_sendLock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


}
