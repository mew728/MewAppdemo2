package com.example.shopdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class history extends AppCompatActivity {
    private ListView lvShow;
    private String[] fruit_name=new String[5];
    private static final int MENU_Delete = Menu.FIRST;


    public boolean onCreateOptionsMenu(Menu menu) {


        menu.add(0, MENU_Delete, 1, "刪除紀錄").setIcon(android.R.drawable.ic_menu_close_clear_cancel);


        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case MENU_Delete:
                fruit_name=new String[5];
                findViews();
                setAdapter();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String record = i.getStringExtra("Fruit");
        fruit_name = new String[]{};

        setContentView(R.layout.activity_history);
        findViews();
        setAdapter();
        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg=fruit_name[position];
                setToast(history.this,msg);
            }
        });
    }
    public void setToast(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    private void setAdapter() {
        ArrayAdapter<String> adapter=
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fruit_name);
        lvShow.setAdapter(adapter);
    }
    private void findViews() {
        lvShow=(ListView)findViewById(R.id.lvShow);
    }
}