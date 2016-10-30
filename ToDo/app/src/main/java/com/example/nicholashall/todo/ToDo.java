package com.example.nicholashall.todo;

import java.util.Date;

/**
 * Created by nicholashall on 10/25/16.
 */

public class ToDo {
    private String name, message;
    private long noteId, dateCreatedMilli;
    private Category category;

    public enum Category{ PERSONAL, WORK, HOUSE, BILL }

    public ToDo(String name, String message, Category category){
        this.name = name;
        this.message = message;
        this.category = category;
        this.noteId = 0;
        this.dateCreatedMilli = 0;
    }

    public ToDo(String name, String message, Category category, long noteId, long dateCreatedMilli){
        this.name = name;
        this.message = message;
        this.category = category;
        this.noteId = noteId;
        this.dateCreatedMilli = dateCreatedMilli;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public Category getCategory() {
        return category;
    }



    public long getDate() {
        return dateCreatedMilli;
    }

    public long getId() {
        return noteId;
    }

    @Override
    public String toString() {
        return "ID: " + noteId + " Name: " + name + " Message: " + message + " IconID: " + category.name() + " Date: ";
    }


//    public String getAssociatedTextView(){
//        return categoryToTextView(category);
//    }
//
//    public static String categoryToTextView(Category noteCategory){
//        switch (noteCategory){
//            case PERSONAL:
//                return "Personal";
//            case WORK:
//                return "Work";
//            case HOUSE:
//                return "House";
//            case BILL:
//                return "Bill";
//        }
//        return "hi";
//    }

}
