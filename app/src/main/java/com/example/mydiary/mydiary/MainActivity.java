package com.example.mydiary.mydiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);

        Button new_diary = (Button) this.findViewById(R.id.plus_button);
        new_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit_intent = new Intent(MainActivity.this, DiaryEditActivity.class);
                MainActivity.this.startActivity(edit_intent);
            }
        });

        Spinner month_spinner = (Spinner) this.findViewById(R.id.pick_month);
        month_spinner.setSelection(m);
        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //calendar.set(Calendar.MONTH,position);
                //refreshDiaryList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner year_spinner = (Spinner) this.findViewById(R.id.pick_year);
        List<String> year_list = new ArrayList<String>();
        y = y < 1970 ? 1970 : y;
        for(int i = 1970;i <= y; ++i){
            year_list.add(i+"");
        }
        ArrayAdapter<String> year_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year_list);
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(year_adapter);
        year_spinner.setSelection(y - 1970);
        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //calendar.set(Calendar.YEAR,1970+position);
                //refreshDiaryList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        refreshDiaryList();

    }

    protected  Object getDiary(String name){
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

    protected void refreshDiaryList()
    {
        LinearLayout diary_list = (LinearLayout) this.findViewById(R.id.diary_list);
        diary_list.removeAllViews();

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
                diary_text.setGravity(Gravity.START);
                diary_text.setPadding(14,14,14,14);

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

}
