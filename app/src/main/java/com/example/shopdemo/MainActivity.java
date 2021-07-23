package com.example.shopdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private static final int MENU_history = Menu.FIRST, MENU_use = Menu.FIRST + 1,
            MENU_about = Menu.FIRST + 2, MENU_Delete = Menu.FIRST + 3, MENU_Login = Menu.FIRST + 4;
    private String[] fruit_name;//清空用
    private Dialog LogAndCreate, CreateMassange; //登入用
    EditText username, userpassword;//登入帳號
    //Frag
    private FruitBox fruitbox;
    private ImportedFruit importedfruit;
    private SeasonFruit seasonfruit;
    private history his;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bottom
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationbotton);
        navigation.setOnNavigationItemSelectedListener(fruitchange);
        //fragment
        seasonfruit = new SeasonFruit();
        fruitbox = new FruitBox();
        importedfruit = new ImportedFruit();
        getSupportFragmentManager().beginTransaction().add(R.id.fralay, seasonfruit).add(R.id.fralay, fruitbox)
                .add(R.id.fralay, importedfruit).show(seasonfruit).hide(importedfruit).hide(fruitbox).commit();
        Intent a  = getIntent();
        String record = a.getStringExtra("Fruit");
    }
    public  void  x()
    {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, MENU_history, 1, "瀏覽紀錄");
        menu.add(0, MENU_use, 2, "使用說明");
        menu.add(0, MENU_about, 3, "關於").setIcon(android.R.drawable.ic_dialog_info);
        menu.add(0, MENU_Delete, 4, "刪除紀錄").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        menu.add(0, MENU_Login, 5, "登入").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_history:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, history.class);
                startActivity(intent);
                return true;
            case MENU_use: //用灰體用music0
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("使用說明")
                        .setMessage("此程式為購物軟體的基礎程式，使用了資料庫與Android studio以及JAVA製作。")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.star_big_on)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                return true;
            case MENU_about:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("關於我們")
                        .setMessage("這是程式設計第三組製作之程式。")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.star_big_on)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                return true;
            case MENU_Delete:

                return true;
            //
            case MENU_Login:
                LogAndCreate = new Dialog(MainActivity.this);
                LogAndCreate.setCancelable(true);
                LogAndCreate.setContentView(R.layout.login_show);
                Button loginok = LogAndCreate.findViewById(R.id.LoginOK);
                loginok.setOnClickListener(X -> {
                    username = LogAndCreate.findViewById(R.id.UserName_login);
                    userpassword = LogAndCreate.findViewById(R.id.UserPassword_login);
                    MSSQL m = new MSSQL();
                    if (m.isOpened()) {
                        try {
                            Connection con = m.connection;
                            String SQLLOGINUSERNAME = "SELECT *FROM Userdata WHERE 使用者名稱 in (trim('" + username.getText() + "'))";
                            String SQLLOGINUSERPASSWORD = "SELECT *FROM Userdata WHERE 使用者帳號 in (trim('" + userpassword.getText() + "'))";
                            Statement stmt = con.createStatement();
                            Statement ww = con.createStatement();
                            ResultSet sqlloginusername = ww.executeQuery(SQLLOGINUSERNAME);
                            ResultSet sqlloginuserpassword = stmt.executeQuery(SQLLOGINUSERPASSWORD);
                            if (sqlloginusername.next()) {
                                if (sqlloginuserpassword.next()) {
                                    new AlertDialog.Builder(MainActivity.this).setTitle("成功!").setMessage("歡迎登入" + username.getText()).
                                            setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LogAndCreate.dismiss();
                                                }
                                            }).show();
                                    LogAndCreate.dismiss();
                                    //登入切換
//123
                                } else {
                                    new AlertDialog.Builder(MainActivity.this).setTitle("錯誤!").setMessage("帳號輸入錯誤").
                                            setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LogAndCreate.dismiss();
                                                }
                                            }).show();
                                    LogAndCreate.dismiss();
                                }
                            } else {
                                new AlertDialog.Builder(MainActivity.this).setTitle("錯誤!").setMessage("無此帳號").
                                        setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                LogAndCreate.dismiss();
                                            }
                                        }).show();
                                LogAndCreate.dismiss();
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });

                Button logincancel = LogAndCreate.findViewById(R.id.LoginCancel);
                logincancel.setOnClickListener(X -> {
                    new AlertDialog.Builder(MainActivity.this).setTitle("取消!").setMessage("登入取消").
                            setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    LogAndCreate.dismiss();
                                }
                            }).show();
                    LogAndCreate.dismiss();
                });
                Button logintocreate = LogAndCreate.findViewById(R.id.LoginToCreate);
                logintocreate.setOnClickListener(x ->
                {
                    CreateMassange = new Dialog(MainActivity.this);
                    CreateMassange.setCancelable(true);
                    CreateMassange.setContentView(R.layout.create_show);
                    CreateMassange.show();
                    LogAndCreate.dismiss();
                    Button createok = CreateMassange.findViewById(R.id.CreateOK);
                    createok.setOnClickListener(X -> {
                        username = CreateMassange.findViewById(R.id.UserName_create);
                        userpassword = CreateMassange.findViewById(R.id.UserPassword_create);
                        MSSQL m = new MSSQL();
                        if (m.isOpened()) {
                            try {
                                Connection con = m.connection;
                                String SQLCREATEUSERNAME = "SELECT *FROM Userdata WHERE 使用者名稱 =trim('" + username.getText() + "')";
                                Statement stmt = con.createStatement();
                                ResultSet sqlcreateusername = stmt.executeQuery(SQLCREATEUSERNAME);
                                if (!sqlcreateusername.next()) {
                                    String SQLCREATEACCOUNT = "INSERT INTO Userdata(使用者名稱,使用者帳號) VALUES ('" + username.getText() + "','" + userpassword.getText() + "')";
                                    stmt.executeUpdate(SQLCREATEACCOUNT);
                                    new AlertDialog.Builder(MainActivity.this).setTitle("成功!").setMessage("帳號創建成功").
                                            setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    CreateMassange.dismiss();
                                                }
                                            }).show();
                                    CreateMassange.dismiss();
                                } else {
                                    new AlertDialog.Builder(MainActivity.this).setTitle("錯誤!").setMessage("帳號已被使用").
                                            setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    CreateMassange.dismiss();
                                                }
                                            }).show();
                                    CreateMassange.dismiss();
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    });
                    Button createcancel = CreateMassange.findViewById(R.id.CreateCancel);
                    createcancel.setOnClickListener(X ->
                    {
                        new AlertDialog.Builder(MainActivity.this).setTitle("取消!").setMessage("帳號創建取消").
                                setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        CreateMassange.dismiss();
                                    }
                                }).show();
                        CreateMassange.dismiss();
                    });
                });
                LogAndCreate.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener fruitchange = (item) -> {
        switch (item.getItemId()) {
            case R.id.navigationbotton_season:
                getSupportFragmentManager().beginTransaction().show(seasonfruit).hide(importedfruit).hide(fruitbox).commit();
                break;
            case R.id.navigationbotton_imported:
                getSupportFragmentManager().beginTransaction().hide(seasonfruit).show(importedfruit).hide(fruitbox).commit();
                break;
            case R.id.navigationbotton_box:
                getSupportFragmentManager().beginTransaction().hide(seasonfruit).hide(importedfruit).show(fruitbox).commit();
                break;
        }
        return false;
    };
}