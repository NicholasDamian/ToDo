package com.example.nicholashall.todo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {
    private ArrayList<ToDo> toDos;
    private ToDoAdapter toDoAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

//        String[] values = new String[]{"Android","Iphone","Windows"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , values);
//
//        setListAdapter(adapter);


        ToDoDBAdapter dbAdapter = new ToDoDBAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        toDos = dbAdapter.getAllToDos();
        dbAdapter.close();


        toDoAdapter = new ToDoAdapter(getActivity(), toDos);

        setListAdapter(toDoAdapter);
        registerForContextMenu(getListView());

    }

    @Override
    public void onListItemClick(ListView l, View v, int position,long id){
        super.onListItemClick(l, v ,position ,id);

        launchToDoDetailActivity(MainActivity.FragmentToLaunch.VIEW,position);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v , menuInfo );

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu,menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

//        grab the item position of the todo i long pressed on
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition = info.position;
//        returns to us id of what ever menu item we selected

        ToDo toDo = (ToDo) getListAdapter().getItem(rowPosition);

        switch (item.getItemId()){
//            if we press edit
            case R.id.edit:
//                do something here
                launchToDoDetailActivity(MainActivity.FragmentToLaunch.EDIT, rowPosition);
                Log.d("Menu Clicks", "We pressed edit!");
                return true;
            case R.id.delete:
                ToDoDBAdapter dbAdapter = new ToDoDBAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteToDo(toDo.getId());

                toDos.clear();
                toDos.addAll(dbAdapter.getAllToDos());
                toDoAdapter.notifyDataSetChanged();
                dbAdapter.close();
        }

        return super.onContextItemSelected(item);
    }


    private void launchToDoDetailActivity(MainActivity.FragmentToLaunch ftl,int position){

//      grab the todo information associated with whatever todo item we clicked on
        ToDo toDo =(ToDo) getListAdapter().getItem(position);

//        create a new intent that launches our todoDetainActivity
        Intent intent = new Intent(getActivity(), ToDoDetailActivity.class);

//        pass along the information of the todo we clicked on to our todoDetainActivity
        intent.putExtra(MainActivity.TODO_NAME_EXTRA, toDo.getName());
        intent.putExtra(MainActivity.TODO_MESSAGE_EXTRA, toDo.getMessage());
        intent.putExtra(MainActivity.TODO_CATEGORY_EXTRA, toDo.getCategory());
        intent.putExtra(MainActivity.TODO_ID_EXTRA, toDo.getId());

        switch (ftl){
            case VIEW:
                intent.putExtra(MainActivity.TODO_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(MainActivity.TODO_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.EDIT);
                break;
        }


        startActivity(intent);
    }


}
