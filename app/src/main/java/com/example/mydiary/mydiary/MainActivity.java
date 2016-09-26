package com.example.mydiary.mydiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button new_diary = (Button) this.findViewById(R.id.plus_button);
        new_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit_intent = new Intent(MainActivity.this, DiaryEditActivity.class);
                MainActivity.this.startActivity(edit_intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        LinearLayout diary_list = (LinearLayout) this.findViewById(R.id.diary_list);
        diary_list.removeAllViews();

        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;  //得到的月份是0-11 所以要+1
        int d = Calculate.countDaysOfaMonth(calendar);  //这里的d是当前月份的天数
        DiaryCopy diaryCopy;

        for(int i = 1;i <= d; ++i) {
            LinearLayout item = new LinearLayout(this);
            LinearLayout.LayoutParams lp;

            diaryCopy = (DiaryCopy) getDiary(y+"-"+m+"-"+i);
            if(diaryCopy.getYear() != ""){

                item.setBackgroundResource(R.drawable.frame);
                item.setWeightSum(8);
                item.setOrientation(LinearLayout.HORIZONTAL);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,0,14);
                item.setLayoutParams(lp);

                LinearLayout dateFrame = new LinearLayout(this);
                dateFrame.setWeightSum(3);
                dateFrame.setOrientation(LinearLayout.VERTICAL);
                dateFrame.setGravity(Gravity.CENTER);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
                dateFrame.setLayoutParams(lp);
                dateFrame.setBackgroundResource(R.drawable.frame_right);

                LinearLayout textFrame = new LinearLayout(this);
                textFrame.setOrientation(LinearLayout.VERTICAL);
                textFrame.setGravity(Gravity.CENTER_HORIZONTAL);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,7);
                textFrame.setLayoutParams(lp);
                textFrame.setBackgroundResource(R.drawable.frame_left);

                TextView day = new TextView(this);
                day.setText(diaryCopy.getDay());
                day.setGravity(Gravity.CENTER);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,2);
                day.setLayoutParams(lp);

                TextView date = new TextView(this);
                date.setText(diaryCopy.getDate());
                date.setGravity(Gravity.CENTER);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
                date.setLayoutParams(lp);

                TextView diary_text = new TextView(this);
                diary_text.setText(diaryCopy.getText());
                diary_text.setGravity(Gravity.CENTER);

                item.addView(textFrame);
                item.addView(dateFrame);
                textFrame.addView(day);
                textFrame.addView(date);
                dateFrame.addView(diary_text);
            }
            else{
                lp = new LinearLayout.LayoutParams(64, 64);
                lp.setMargins(0,0,0,14);
                item.setLayoutParams(lp);
                item.setBackgroundResource(R.drawable.dot);
            }
            diary_list.addView(item);
        }

    }

    Object getDiary(String name){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = this.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {  //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {  //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }  //读取产生异常，返回null
        return new DiaryCopy();
    }

}
