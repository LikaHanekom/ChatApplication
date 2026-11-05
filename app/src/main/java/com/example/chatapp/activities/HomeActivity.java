package com.example.chatapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);   // load my XML layout onto the screen
        int currentUserId = getIntent().getIntExtra("userId", -1);
        Button conversationButton = findViewById(R.id.btn_conversations);
        Button cameraButton = findViewById(R.id.btn_camera);

        conversationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(HomeActivity.this, ConversationListActivity.class);
                intent.putExtra("userId", currentUserId);
                startActivity(intent);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(HomeActivity.this, "Open a conversation to use the camera", Toast.LENGTH_SHORT).show();
            }
        });
    }
}