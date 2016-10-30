package com.example.nicholashall.todo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoViewFragment extends Fragment {

    private ToDo.Category categoryObject;

    public ToDoViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_to_do_view, container, false);

        TextView name = (TextView) fragmentLayout.findViewById(R.id.viewToDoName);
        TextView message = (TextView) fragmentLayout.findViewById(R.id.viewToDoBody);
        TextView category = (TextView) fragmentLayout.findViewById(R.id.viewCategory) ;

        Intent intent = getActivity().getIntent();
        categoryObject = (ToDo.Category)intent.getSerializableExtra(MainActivity.TODO_CATEGORY_EXTRA);
        name.setText(intent.getExtras().getString(MainActivity.TODO_NAME_EXTRA));
        message.setText(intent.getExtras().getString(MainActivity.TODO_MESSAGE_EXTRA));
        category.setText(categoryObject.toString());



//        ToDo.Category todoCat =(ToDo.Category) intent.getSerializableExtra(MainActivity.TODO_CATEGORY_EXTRA);



        // Inflate the layout for this fragment
        return fragmentLayout;
    }

}
