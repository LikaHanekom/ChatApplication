package com.example.chatapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(
        tableName = "message",
        foreignKeys = {
            @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "sender_id"),
            @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "receiver_id")
        },
        indices = {
            @Index("sender_id"),
            @Index("receiver_id")
        }
)
public class Message {
    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo(name = "senderId") private int senderId;
    @ColumnInfo(name = "receiverId") private int receiverId;
    @ColumnInfo(name = "content") private String content;
    @ColumnInfo(name = "timestamp") private long timestamp;


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

}

