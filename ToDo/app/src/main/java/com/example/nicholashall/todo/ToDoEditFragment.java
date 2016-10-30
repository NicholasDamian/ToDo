package com.example.nicholashall.todo;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoEditFragment extends Fragment {
    private EditText name,message;
    private Button changeCategory;
    private Button saveButton;

    private AlertDialog categoryDialogObject;
    private ToDo.Category saveButtonCategory;
    private Intent intent;
    private boolean newToDo = false;
    private long noteId = 0;

    public ToDoEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        grab bundle that sends along if it is creating a new note or not
        Bundle bundle = this.getArguments();
        if(bundle != null){
            newToDo = bundle.getBoolean(ToDoDetailActivity.NEW_TODO_EXTRA, false);
        }
//      inflate our fragment edit layout
        View fragmentLayout = inflater.inflate(R.layout.fragment_to_do_edit, container, false);

//        grab widget references from layout
        name = (EditText) fragmentLayout.findViewById(R.id.editToDoName);
        message = (EditText) fragmentLayout.findViewById(R.id.editToDoMessage);
        changeCategory = (Button) fragmentLayout.findViewById(R.id.editCategoryButton);
        saveButton = (Button) fragmentLayout.findViewById(R.id.saveToDo);

//        populate widgets with todo data
        intent = getActivity().getIntent();
        name.setText(intent.getExtras().getString(MainActivity.TODO_NAME_EXTRA, ""));
        message.setText(intent.getExtras().getString(MainActivity.TODO_MESSAGE_EXTRA,""));
        intent.getExtras().getString(MainActivity.TODO_CATEGORY_EXTRA);
        saveButtonCategory = (ToDo.Category)intent.getSerializableExtra(MainActivity.TODO_CATEGORY_EXTRA);

        noteId = intent.getExtras().getLong(MainActivity.TODO_ID_EXTRA);





        BuildCategoryDialog();
        onSaveButton();
        changeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialogObject.show();
            }
        });

        // Inflate the layout for this fragment
        return fragmentLayout;
    }

    private void BuildCategoryDialog(){
        final String [] categories = new String [] { "Personal", "Work", "House", "Bill"};
        AlertDialog.Builder categoryBuilder = new AlertDialog.Builder(getActivity());
        categoryBuilder.setTitle("Choose To-Do Type");

        categoryBuilder.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

//                dismissed our dialog
                categoryDialogObject.cancel();

                switch (item){
                    case 0:
                        saveButtonCategory = ToDo.Category.PERSONAL;
                        break;
                    case 1:
                        saveButtonCategory = ToDo.Category.WORK;
                        break;
                    case 2:
                        saveButtonCategory = ToDo.Category.HOUSE;
                        break;
                    case 3:
                        saveButtonCategory = ToDo.Category.BILL;
                        break;
                }
                Toast.makeText(getActivity() ," Category: "+ saveButtonCategory+ "", Toast.LENGTH_LONG).show();
            }
        });

        categoryDialogObject =categoryBuilder.create();
    }


    public void onSaveButton(){
//        Intent intent = new Intent(getActivity(),MainActivity.class);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity() ,"Name: "+name.getText() +" Message: "+message.getText()+" Category: "+ saveButtonCategory+ "", Toast.LENGTH_LONG).show();


                ToDoDBAdapter dbAdapter = new ToDoDBAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                if(newToDo){
//              if its a new todo create it in the database
                dbAdapter.createToDo(name.getText() + "", message.getText()+ "",
                        (saveButtonCategory == null)? ToDo.Category.PERSONAL : saveButtonCategory  );
                }else{
//                    otherwise it's an old note so update it in our database

                    dbAdapter.UpdateToDo(noteId, name.getText() + "", message.getText() + "", saveButtonCategory);

                }

                dbAdapter.close();

                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
    }



}
