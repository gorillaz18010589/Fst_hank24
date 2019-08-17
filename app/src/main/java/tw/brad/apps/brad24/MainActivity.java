package tw.brad.apps.brad24;
//目的:撥放音樂
//activity以後就寫uxui的東西,網路東西就給Service
//只要這件事情是可以在背景中處理就用Service
//前景是觸發Service
//你可以透過推撥去叫出程式
//1.

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);


        //廣播接收
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("brad");
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);//當結束時取消廣播
        super.onDestroy();
    }

    //啟動連接到severice頁面
    public void test1(View view){
        Intent intent = new Intent(this,MyService.class);//從這個頁面到Server頁面
        startService(intent);//開始連線到那個Servire

    }
        //改變i值，產生亂數
    public void test2(View view){
        Intent intent = new Intent(this,MyService.class);
        intent.putExtra("data",(int)( Math.random()*1000));//這邊給值給servire
        startService(intent);//開始連線到那個Servire

    }
        //關閉
    public void test3(View view){
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);//關閉連線到Servire
    }

    //接收廣播的i值,並且顯示在頁面上
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            int i = intent.getIntExtra("i", -1);
            tv.setText("" + i) ;
        }
    }

}