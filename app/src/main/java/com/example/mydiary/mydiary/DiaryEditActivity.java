package com.example.mydiary.mydiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class DiaryEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);

        TextView year = (TextView) this.findViewById(R.id.edit_year);
        TextView month = (TextView) this.findViewById(R.id.edit_month);
        TextView date = (TextView) this.findViewById(R.id.edit_date);
        TextView day = (TextView) this.findViewById(R.id.edit_day);

        final EditText diary_text = (EditText) this.findViewById(R.id.diary_text);

        final Calendar calendar = Calendar.getInstance();
        final int y = calendar.get(Calendar.YEAR);
        final int m = calendar.get(Calendar.MONTH) + 1;  //得到的月份是0-11 所以要+1
        final int d = calendar.get(Calendar.DATE);
        final String s = Calculate.countDay(calendar.get(Calendar.DAY_OF_WEEK));

        year.setText(y+getResources().getString(R.string.year_mark));
        month.setText(m+getResources().getString(R.string.month_mark));
        date.setText(d+getResources().getString(R.string.date_mark));
        day.setText(s);

        final DiaryCopy diaryCopy = (DiaryCopy) getDiary(y+"-"+m+"-"+d);
        if (diaryCopy.getYear() != ""){
            diary_text.setText(diaryCopy.getText());
        }
        else{
            diary_text.setText("");
        }

        Button done = (Button) this.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaryCopy.setData(y+"", m+"", d+"", s, diary_text.getText().toString());
                saveDiary(y+"-"+m+"-"+d, diaryCopy);
                DiaryEditActivity.this.finish();
            }
        });

        Button clock = (Button) this.findViewById(R.id.clock);
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diary_text.setText(diary_text.getText().toString()+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));
            }
        });

    }

    void saveDiary(String name, DiaryCopy diary){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(diary);
        } catch (Exception e) {
            e.printStackTrace();  //这里是保存文件产生异常
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {  //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {  //oos流关闭异常
                    e.printStackTrace();
                }
            }
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
