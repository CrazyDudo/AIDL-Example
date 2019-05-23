package com.example.clientapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aidlexample.IMyAidlInterface;
import com.example.aidlexample.entity.RequestEntity;
import com.example.aidlexample.entity.ResponseEntity;
import com.example.aidlexample.listener.IResultListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String ACTION_BIND_SERVICE = "com.example.intent.action.START";
    @BindView(R.id.btn_basic)
    Button btnBasic;
    @BindView(R.id.btn_entity)
    Button btnEntity;
    @BindView(R.id.btn_listener)
    Button btnListener;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.btn_bind)
    Button btnBind;
    private MyConnection myConnection;
    private IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_basic, R.id.btn_entity, R.id.btn_listener, R.id.btn_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bind:
                bindService();
                break;
            case R.id.btn_basic:
                //基础类型参数
                try {
                    iMyAidlInterface.basicTypes(777, 22l, true, 2, 2.1, "basic params test from client app");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btn_entity:
                RequestEntity entity = new RequestEntity();
                entity.setRequestMsg("hi,service,this msg from client");
                entity.setRequestCode("00");
                try {
                    iMyAidlInterface.objectTypes(entity);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btn_listener:
                try {
                    iMyAidlInterface.callbackTypes(listener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;

        }
    }

    private IResultListener listener = new IResultListener.Stub() {
        @Override
        public void onResult(ResponseEntity responseEntity) throws RemoteException {
                tvResult.setText("result msg=="+responseEntity.getResultMsg());
        }
    };

    public void bindService() {
        Intent intent = new Intent();
        //利用intent中的Action区分查找要绑定服务
        intent.setAction(ACTION_BIND_SERVICE);
//		intent.setPackage("put package name");
        final Intent eintent = new Intent(createExplicitFromImplicitIntent(this, intent));
        myConnection = new MyConnection();
        bindService(eintent, myConnection, BIND_AUTO_CREATE);
    }


    class MyConnection implements ServiceConnection {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //必须使用IService中的静态方法转换，不能强转
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            tvResult.setText("onServiceConnected:" + name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
        }

    }

    /**
     * createExplicitFromImplicitIntent
     * 根据acton找到对应包名
     *
     * @param context
     * @param implicitIntent
     * @return
     */
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        Log.i("TTT", "resolveInfo=" + resolveInfo + "\n resolveInfo.size()=" + resolveInfo.size());
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return new Intent();
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
