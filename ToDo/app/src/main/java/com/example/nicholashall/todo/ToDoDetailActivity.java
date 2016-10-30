package com.example.nicholashall.todo;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ToDoDetailActivity extends AppCompatActivity {
    public static final String NEW_TODO_EXTRA = "New ToDo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_detail);
        createAndAddFragment();
    }

    private void createAndAddFragment(){

//        grab intent and fragment to launch from our main activity list fragment
        Intent intent = getIntent();
        MainActivity.FragmentToLaunch fragmentToLaunch =
                (MainActivity.FragmentToLaunch) intent.getSerializableExtra(MainActivity.TODO_FRAGMENT_TO_LOAD_EXTRA);

//        grabbing our fragment manager and our fragment transaction so that we can add our edit or view fragment dynamically
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


//        choose the correct fragment to load
        switch (fragmentToLaunch){
            case EDIT:
//                create and add todo edit fragment to todo detail activity if that's what we want to launch
                ToDoEditFragment toDoEditFragment = new ToDoEditFragment();
                setTitle(R.string.edit_fragment_title);
                fragmentTransaction.add(R.id.activity_container, toDoEditFragment, "TODO_EDIT_FRAGMENT");
                break;
            case VIEW:
//                create and add todo view fragment to todo detail activity if that's what we want to launch
                ToDoViewFragment toDoViewFragment = new ToDoViewFragment();
                setTitle(R.string.view_fragment_title);
                fragmentTransaction.add(R.id.activity_container, toDoViewFragment, "TODO_VIEW_FRAGMENT");
                break;
            case CREATE:
                ToDoEditFragment todoCreateFragment = new ToDoEditFragment();
                setTitle("Create ToDo");

                Bundle bundle = new Bundle();
                bundle.putBoolean(NEW_TODO_EXTRA, true);
                todoCreateFragment.setArguments(bundle);

                fragmentTransaction.add(R.id.activity_container, todoCreateFragment, "TODO_CREATE_FRAGMENT");
                break;

        }



//        commit our changes so everything works
        fragmentTransaction.commit();
    }
}
