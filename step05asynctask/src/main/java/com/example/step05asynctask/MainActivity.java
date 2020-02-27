package com.example.step05asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //진행 시작, 진행 과정, 결과를 표시할 TextView
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView의 참조값을 필드에 저장하기
        textView=findViewById(R.id.textView);

    }
    //작업하기 버튼을 눌렀을 때 호출되는 메서드
    public void start(View v){
        /*
            버튼을 누르면 여기에 실행순서가 들어온다.
            그 스레드는 UI 스레드 (main 스레드) 이다.
            UI 스레드에서 시간이 오래 걸리거나 언제 끝날지 모르는
            불확실한 작업을 하면 안된다.
            UI 의 업데이트는 UI 스레드에서만 가능하다.  (doInBackground 에서는 UI를 건드릴 수 없다!! 그냥 다른 스레드일 뿐.)

            오래걸리게 되면 운영체제에서 다운되었다고 판단하고 앱을 종료 시켜버린다. (임의로 스레드를 20초 정도 잡아놓고 테스트 해보면 종료되는 것을 알 수 있다.)
            => 새로운 스레드에서 작업을 진행해야 한다.
         */
        //비동기 작업의 시작은 객체를 생성하고
        CounterTask task=new CounterTask();
        //execute() 메서드를 호출하면 된다.
        task.execute("김구라", "해골", "원숭이");   //execute(String ...) => 인자로 받는 곳이 ... 으로 되어 있으면 갯수에 상관 없이 배열로 받아줄 수 있다.
    }

    /*
        extends AsyncTask<전달받는 type, 진행중 반환하는 type, 결과 type>

        type 이 필요 없으면 Void type 을 사용하면 된다.

        extends AsyncTask<String, Void, Void>
     */
    public class CounterTask extends AsyncTask<String, Integer, String>{//비동기방식 작업 AsyncTask

        //publishProgress() 메서드를 호출하면 아래의 메서드가 호출된다.
        @Override   //여기가 UI 스레드. (잠깐 끌고와서 사용함)
        protected void onProgressUpdate(Integer... values) {    //... 은 동적으로 갯수를 받는다. 배열의 개념으로 생각하기 values[xx] ex) Integer가 하나, 둘, 셋 ..
            super.onProgressUpdate(values);
            //여기는 UI 스레드 이기 때문에 UI 업데이트 가능
            //publishProgress() 메서드에 전달된 인자가 이 메서드의 인자로 전달된다.
            int count=values[0];    //Integer 배열의 0번방에 값이 들어있다.
            textView.setText(Integer.toString(count));

        }

        // doInBackground() 메서드가 리턴되면(작업이 끝나고 나면) 아래의 메서드가 호출된다. (리턴한게 s로 들어온다.)
        @Override   //여기도 UI 스레드 이다.
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //여기는 UI 스레드 이기 때문에 UI 업데이트 가능
            textView.setText(s);
        }
        //doInBackground() 메서드가 호출되기 직전에 호출된다.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //여기는 UI 스레드 이기 때문에 UI 업데이트 가능
            textView.setText("숫자 세기를 시작합니다.");
        }



        @Override
        protected String doInBackground(String... strings) {
            String name1=strings[0]; //김구라
            String name2=strings[1]; //해골
            String name3=strings[2]; //원숭이


            int count=0;
            //백그라운드(새로운 스레드) 에서 작업할 내용을 여기서 하면 된다.
            for(int i=0; i<10; i++){
                try{
                    Thread.sleep(1000);

                }catch (Exception e){}
                count ++;
                //count 값을 TextView 에 출력하기?
                //textView.setText(Integer.toString(count)); 여기서는 직접 출력이 불가능 하다!! UI 스레드가 아니기 때문에   (Only the original thread that created a view hierarchy can touch its views. 오류 메세지.)
                publishProgress(count);
            }
            String result="숫자세기 성공!";

            return result;
        }
    }
}
