package com.example.chatapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.adapters.ConversationPartnerAdapter;
import com.example.chatapp.database.ChatDatabaseHelper;
import com.example.chatapp.database.MessageDao;
import com.example.chatapp.database.User;
import com.example.chatapp.database.UserDao;

import java.util.ArrayList;
import java.util.List;

public class ConversationListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConversationPartnerAdapter adapter;
    private List<User> partnerList = new ArrayList<>();
    private MessageDao messageDao;
    private UserDao userDao;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        currentUserId = getIntent().getIntExtra("userId", -1);
        messageDao = ChatDatabaseHelper.getInstance(this).messageDao();
        userDao = ChatDatabaseHelper.getInstance(this).userDao();

        recyclerView = findViewById(R.id.partnerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ConversationPartnerAdapter(partnerList, partner -> {
            Intent intent = new Intent(ConversationListActivity.this, ConversationActivity.class);
            intent.putExtra("userId", currentUserId);
            intent.putExtra("receiverId", partner.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        Button conversationButton = findViewById(R.id.btn_new_chat);


        loadPartners();

        conversationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loadAllUsers();
            }
        });
    }

    private void loadPartners() {
        new Thread(() -> {
            List<Integer> partnerIds = messageDao.getConversationPartners(currentUserId);

            List<User> users = new ArrayList<>();
            for (int id : partnerIds) {
                User user = userDao.getUserById(id);
                if (user != null) {
                    users.add(user);
                }
            }

            runOnUiThread(() -> {
                partnerList.addAll(users);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void loadAllUsers() {
        new Thread(() -> {
            List<User> users = userDao.getAllUsersExcept(currentUserId);

            runOnUiThread(() -> {
                partnerList.clear();
                partnerList.addAll(users);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }
}