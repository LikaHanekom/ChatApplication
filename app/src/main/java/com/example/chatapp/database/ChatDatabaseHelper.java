package com.example.chatapp.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Message.class}, version = 1)

public abstract class ChatDatabaseHelper extends RoomDatabase{
    private static final String DB_NAME = "chat_database";
    private static ChatDatabaseHelper instance;
    public abstract UserDao userDao();
    public abstract MessageDao messageDao();

    public static synchronized ChatDatabaseHelper getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ChatDatabaseHelper.class, DB_NAME).build();
        }
        return instance;
    }
}


