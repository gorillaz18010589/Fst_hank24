package tw.brad.apps.brad24;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

    public class MyService extends Service {
        private Timer timer;
        private int i;
    @Override
    public IBinder onBind(Intent intent) { //這個方法是抽象方法需要實做,用再綁定型上面
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented"); 一開始就拋出例外,但我沒有要處理榜定型,所已注解
        return null;
    }

    //3個狀態看一下
    @Override
    public void onCreate() { //只有第一次創造時會叫到
        super.onCreate();
        Log.v("brad","onCreate");
        timer = new Timer();
        timer.schedule(new MyTask(), 0, 1*1000); //執行這方法每秒都執行
    }

    //創建一個一秒一值家的任務,如果除已10時
    private  class  MyTask extends TimerTask{
        @Override
        public void run() {
            if(i %10 == 0){ //如果被10整除,把事情芳生出去,用廣播方式
                Intent intent = new Intent("brad");
                intent.putExtra("i",i); //把這個i掛上去
                sendBroadcast(intent); //廣播發送出去

            }

            Log.v("brad","i =" + i++);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//主要都在玩這個帶參數
//        String name = intent.getStringExtra("data");
//        Log.v("brad","onStartCommand");

        //抓到Activity的i值
        int newi = intent.getIntExtra("data",-1);
        if(newi != -1){ //如果有收到值,把activity的i改變這邊的值
            i = newi;
        }


        return super.onStartCommand(intent, flags, startId);

    }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.v("brad", "onDestroy");

            if (timer != null){
                timer.cancel();
                timer.purge();
                timer = null;
            }
        }
    }
