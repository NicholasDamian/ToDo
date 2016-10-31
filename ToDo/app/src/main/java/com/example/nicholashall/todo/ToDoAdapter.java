package com.example.nicholashall.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nicholashall on 10/25/16.
 */

public class ToDoAdapter extends ArrayAdapter<ToDo> {

    public static class ViewHolder{
        TextView name;
        TextView message;
        TextView dateModified;
        TextView category;
    }

    public ToDoAdapter(Context context,ArrayList<ToDo> ToDos){
        super(context, 0, ToDos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
//        Get the data item for this position
        ToDo toDo = getItem(position);

//        create a new view holder
        ViewHolder viewHolder;

//        Check if an existing view is being reused, otherwise inflate a new view from custom row layout
        if(convertView == null){

//            If we don't have a view that is being used create one, and make sure you create a
//            view holder along with it to save our view references to.
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row,parent,false);

//            set our views to our view holder so that we no longer have to go back and use find view
//            by id everytime we have a new row
            viewHolder.category = (TextView) convertView.findViewById(R.id.category_of_todo);
            viewHolder.name = (TextView) convertView.findViewById(R.id.ToDoName);
            viewHolder.dateModified =(TextView) convertView.findViewById(R.id.date_modified);
            viewHolder.message = (TextView) convertView.findViewById(R.id.ToDoBody);


//            use setTag to remember our viewHolder which is holding our references to our widgets
            convertView.setTag(viewHolder);

        }else{
//            otherwise we already have a view so just go to our viewHolder and grab the widgets from it.
            viewHolder = (ViewHolder) convertView.getTag();
        }


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM hh:mm:ss");
        String dateString = formatter.format(new Date(toDo.getDate()));
        viewHolder.dateModified.setText(dateString);

        String categoryString = toDo.getCategory().toString();
        viewHolder.category.setText(categoryString);


        viewHolder.name.setText(toDo.getName());
        viewHolder.message.setText(toDo.getMessage());

//        Grab references of views so we can populate them with specific note row data

//        now that we modified the view to display appropriate data,
//        return it so it will be displayed.
        return convertView;

    }
}
