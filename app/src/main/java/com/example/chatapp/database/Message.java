package com.example.chatapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(
        tableName = "message",
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "senderId"),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "receiverId")
        },
        indices = {
                @Index("senderId"),
                @Index("receiverId")
        }
)
public class Message {
    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo(name = "senderId") private int senderId;
    @ColumnInfo(name = "receiverId") private int receiverId;
    @ColumnInfo(name = "content") private String content;
    @ColumnInfo(name = "timestamp") private long timestamp;
    @ColumnInfo(name = "imageUri") private String imageUri;


        public Message(){

        }

        public int getId(){
            return id;
        }

        public void setId(int id){
            this.id = id;
        }

        public int getSenderId(){
            return senderId;
        }

        public void setSenderId(int senderId){
            this.senderId = senderId;
        }

        public int getReceiverId(){
            return receiverId;
        }

        public void setReceiverId(int receiverId){
            this.receiverId = receiverId;
        }


        public String getContent(){
            return content;
        }

        public void setContent(String content){
            this.content = content;
        }

        public long getTimestamp(){
            return timestamp;
        }

        public void setTimestamp(long timestamp){
            this.timestamp = timestamp;
        }

        public String getImageUri(){
            return imageUri;
        }

        public void setImageUri(String imageUri){
            this.imageUri = imageUri;
        }

    }

