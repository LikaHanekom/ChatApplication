package com.example.chatapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(Message message);

    @Query("SELECT * FROM message ORDER BY timestamp ASC")
    List<Message> getAllMessages();

    @Query ("SELECT DISTINCT CASE WHEN senderId = :currentUserId THEN receiverId ELSE senderId END AS otherUserId FROM message WHERE senderId = :currentUserId OR receiverId = :currentUserId")
    List<Integer> getConversationPartners(int currentUserId);

    @Query("SELECT * FROM message WHERE (senderId = :userA AND receiverId = :userB) OR (senderId = :userB AND receiverId = :userA) ORDER BY timestamp ASC")
    List<Message> getMessagesBetween(int userA, int userB);
}
