package com.example.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidl.ICalculateAidlInterface;

/**
 * Created by bing on 2017/8/19.
 */

public class CalculateService extends Service {
    private static final String TAG = "CalculateService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind()...intent = " + intent);
        //return mBinder;
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand()...intent = " + intent + " flags = " + flags + " startId = " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG,"onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy()");
    }

    private ICalculateAidlInterface.Stub mBinder = new ICalculateAidlInterface.Stub(){

        @Override
        public int add(int x, int y) throws RemoteException {
            return x + y;
        }

        @Override
        public int sub(int x, int y) throws RemoteException {
            return x - y;
        }
    };

    private MyBinder myBinder = new MyBinder();

    private static final String DESCRIPTOR = "CalculateService";
    private class MyBinder extends Binder{
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code){
                case 0x110:
                    data.enforceInterface(DESCRIPTOR);
                    int arg1 = data.readInt();
                    int arg2 = data.readInt();
                    int result = arg1 + arg2;
                    reply.writeNoException();
                    reply.writeInt(result);
                    return true;
                case 0x111:
                    data.enforceInterface(DESCRIPTOR);
                    int arg3 = data.readInt();
                    int arg4 = data.readInt();
                    int result2 = arg3 - arg4;
                    reply.writeNoException();
                    reply.writeInt(result2);
                    return true;

            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}
