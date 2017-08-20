package com.example.bing.aidldemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.aidl.ICalculateAidlInterface;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private ICalculateAidlInterface mCalculateService;
    private IBinder mBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"onServiceConnected()...name = " + name + " service = " + service);
            //mCalculateService = ICalculateAidlInterface.Stub.asInterface(service);
            mBinder = service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG,"onServiceDisconnected()...name = " + name );
            mCalculateService = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG,"onCreate()...");
    }

    public void bindService(View view){
        Log.e(TAG,"bindService()...");
        Intent intent = new Intent();
        intent.setAction("com.example.bing.AIDL_SERVICE");
        intent.setPackage("com.example.aidlservice");
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(View view){
        Log.e(TAG,"unbindService()...");
        unbindService(mConnection);
    }

    public void addAction(View view){
        Log.e(TAG,"addAction()...");
        /*if (mCalculateService != null){
            try {
                int result = mCalculateService.add(10,20);
                Log.e(TAG,"addAction(10,20) result is " + result);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }*/
        addOperation(10,20);
    }

    public void subAction(View view){
        Log.e(TAG,"subAction()...");
        /*if (mCalculateService != null){
            try {
                int result = mCalculateService.sub(30,15);
                Log.e(TAG,"subAction(10,20) result is " + result);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }*/
        subOperation(30,15);
    }

    private void addOperation(int x,int y){
        if (mBinder != null){
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            int result;

            try{
                data.writeInterfaceToken("CalculateService");
                data.writeInt(x);
                data.writeInt(y);
                mBinder.transact(0x110,data,reply,0);
                reply.readException();
                result = reply.readInt();
                Log.e(TAG,"addOperation(x,y) result = " + result);
            }catch (RemoteException e){
                e.printStackTrace();
            }finally {
                data.recycle();
                reply.recycle();
            }
        }else {
            Log.e(TAG,"The Service is not connected,please connect first");
        }
    }

    private void subOperation(int x,int y){
        if (mBinder != null){
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            int result;

            try{
                data.writeInterfaceToken("CalculateService");
                data.writeInt(x);
                data.writeInt(y);
                mBinder.transact(0x111,data,reply,0);
                reply.readException();
                result = reply.readInt();
                Log.e(TAG,"subOperation(x,y) result = " + result);
            }catch (RemoteException e){
                e.printStackTrace();
            }finally {
                data.recycle();
                reply.recycle();
            }
        }else {
            Log.e(TAG,"The Service is not connected,please connect first");
        }
    }


}
