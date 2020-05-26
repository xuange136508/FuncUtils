package com.my.loopiine.test;

import android.app.Activity;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.my.loopiine.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;

import dalvik.system.DexClassLoader;

/**
 * Created by xx on 2017/5/4.
 */

public class TestActivity3 extends Activity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test3);

        registerService();
        discoverService();
        initResolveListener( );

    }

    private void toast(String str){
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }


    public NsdManager.RegistrationListener nsRegListener;
    public  NsdManager.DiscoveryListener nsDicListener;
    public NsdManager.ResolveListener nsResolveListener;
    public NsdManager nsdManager;

    public void registerService() {
        // 注意：注册网络服务时不要对端口进行硬编码，通过如下这种方式为你的网络服务获取
        // 一个可用的端口号.
        int port = 0;
        try {
            ServerSocket sock = new ServerSocket(0);
            port = sock.getLocalPort();
            sock.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "can not set port", Toast.LENGTH_SHORT);
        }

        // 注册网络服务的名称、类型、端口
        NsdServiceInfo nsdServiceInfo = new NsdServiceInfo();
        nsdServiceInfo.setServiceName("NSD_Test_Program");
        nsdServiceInfo.setServiceType("_http._tcp.");
        nsdServiceInfo.setPort(port);

        // 实现一个网络服务的注册事件监听器，监听器的对象应该保存起来以便之后进行注销
       nsRegListener = new NsdManager.RegistrationListener() {
            @Override
            public void onUnregistrationFailed(NsdServiceInfo arg0, int arg1) {
                Toast.makeText(getApplicationContext(), "Unregistration Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                Toast.makeText(getApplicationContext(), "Service Unregistered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceRegistered(NsdServiceInfo arg0) {
                Toast.makeText(getApplicationContext(), "Service Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo arg0, int arg1) {
                Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        };

        // 获取系统网络服务管理器，准备之后进行注册
        nsdManager = (NsdManager) getApplicationContext().getSystemService(Context.NSD_SERVICE);
        nsdManager.registerService(nsdServiceInfo, NsdManager.PROTOCOL_DNS_SD, nsRegListener);
    }



    public void discoverService() {
       nsDicListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Toast.makeText(getApplicationContext(), "Stop Discovery Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Toast.makeText(getApplicationContext(),
                        "Start Discovery Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceLost(NsdServiceInfo serviceInfo) {
                Toast.makeText(getApplicationContext(), "Service Lost", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceFound(NsdServiceInfo serviceInfo) {
                // 发现网络服务时就会触发该事件
                // 可以通过switch或if获取那些你真正关心的服务
                Toast.makeText(getApplicationContext(), "Service Found", Toast.LENGTH_SHORT).show();
                if (nsdManager != null) {
                    nsdManager.resolveService(serviceInfo, nsResolveListener);
                }
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Toast.makeText(getApplicationContext(), "Discovery Stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDiscoveryStarted(String serviceType) {
                Toast.makeText(getApplicationContext(), "Discovery Started", Toast.LENGTH_SHORT).show();
            }
        };
        NsdManager nsdManager = (NsdManager) getApplicationContext().getSystemService(Context.NSD_SERVICE);
        nsdManager.discoverServices("_http._tcp", NsdManager.PROTOCOL_DNS_SD,nsDicListener);
    }


    public void initResolveListener( ) {
        nsResolveListener = new NsdManager.ResolveListener() {
            @Override
            public void onServiceResolved(NsdServiceInfo info) {
                // 可以再这里获取相应网络服务的地址及端口信息，然后决定是否要与之建立连接。
                // 之后就是一些socket操作了
                String str =info.getServiceName()+"--"+info.getServiceType()+"--"+info.getHost()+"--"+info.getPort();
                toast(str);
                Log.i("test",str);
            }

            @Override
            public void onResolveFailed(NsdServiceInfo arg0, int arg1) {
            }
        };
    }


    public void unregisterService() {
        NsdManager nsdManager = (NsdManager) getApplicationContext().getSystemService(Context.NSD_SERVICE);
        nsdManager.stopServiceDiscovery(nsDicListener); // 关闭网络发现
        nsdManager.unregisterService(nsRegListener);    // 注销网络服务
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterService();
    }
}
