package com.example.nicholashall.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    public static final String TODO_ID_EXTRA = "com.example.nicholashall.todo.ToDo Identifier";
    public static final String TODO_NAME_EXTRA ="com.example.nicholashall.todo.ToDo Name";
    public static final String TODO_MESSAGE_EXTRA ="com.example.nicholashall.todo.ToDo Message";
    public static final String TODO_CATEGORY_EXTRA ="com.example.nicholashall.todo.ToDo Category";
    public static final String TODO_FRAGMENT_TO_LOAD_EXTRA ="com.example.nicholashall.todo.Fragment_To_Load";
    public enum FragmentToLaunch{ VIEW, EDIT, CREATE}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.new_todo){
            Intent intent = new Intent(this, ToDoDetailActivity.class);
            intent.putExtra(MainActivity.TODO_FRAGMENT_TO_LOAD_EXTRA, FragmentToLaunch.CREATE);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
