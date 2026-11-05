package com.example.chatapp.activities;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.List;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatapp.database.Message;
import com.example.chatapp.database.MessageDao;
import com.example.chatapp.adapters.MessageAdapter;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.Manifest;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.chatapp.R;
import com.example.chatapp.database.ChatDatabaseHelper;
import com.example.chatapp.database.User;


import java.io.File;
import java.io.IOException;



public class ConversationActivity extends AppCompatActivity {

    private Uri photoUri;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();
    private MessageDao messageDao;
    private int currentUserId;
    private int receiverId;

    private final ActivityResultLauncher<String> requestCameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    launchCamera();
                } else {
                    Toast.makeText(this, "Camera permission is required to take photos", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<Uri> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), success -> {
                if (success) {
                    Toast.makeText(this, "Photo attached — tap Send to share it", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Photo capture cancelled", Toast.LENGTH_SHORT).show();
                    photoUri = null;
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);// load XML layout onto the screen
        messageDao = ChatDatabaseHelper.getInstance(this).messageDao();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentUserId = getIntent().getIntExtra("userId", -1);
        receiverId = getIntent().getIntExtra("receiverId", -1);
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);

        fetchMessageFromDb();

        EditText messageEditText = findViewById(R.id.message);
        ImageButton cameraButton = findViewById(R.id.btn_camera);
        Button sendButton = findViewById(R.id.btn_send);

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String message = messageEditText.getText().toString();

                if(message.isEmpty()){
                    Toast.makeText(ConversationActivity.this, "Your message is empty, please type a message before sending", Toast.LENGTH_SHORT).show();
                } else {
                    if (currentUserId == -1) {
                        Toast.makeText(ConversationActivity.this, "Error: user not identified", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (receiverId == -1) {
                        Toast.makeText(ConversationActivity.this, "No conversation partner selected", Toast.LENGTH_SHORT).show();
                        return;
                    }
                        new Thread(() -> {
                        Message newMessage = new Message();
                        newMessage.setContent(message);
                        newMessage.setSenderId(currentUserId);
                        newMessage.setReceiverId(receiverId);
                        newMessage.setTimestamp(System.currentTimeMillis());
                        if (photoUri != null) {
                            newMessage.setImageUri(photoUri.toString());
                        }
                        messageDao.insertMessage(newMessage);

                        runOnUiThread(() -> {
                            messageEditText.setText("");
                            messageList.add(newMessage);
                            messageAdapter.notifyDataSetChanged();
                            photoUri = null;
                        });
                    }).start();
                }
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (ContextCompat.checkSelfPermission(ConversationActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    launchCamera();
                } else {
                    requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
                }
            }
        });
    }
    private void launchCamera() {
        File photoFile;
        try {
            String fileName = "chat_photo_" + System.currentTimeMillis() + ".jpg";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            photoFile = File.createTempFile(fileName, ".jpg", storageDir);
        } catch (IOException e) {
            Toast.makeText(this, "Could not create image file", Toast.LENGTH_SHORT).show();
            return;
        }

        photoUri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".fileprovider",
                photoFile
        );

        takePictureLauncher.launch(photoUri);
    }

    private void fetchMessageFromDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Fetch from Room DAO
                final List<Message> messages = messageDao.getMessagesBetween(currentUserId, receiverId);

                // Update UI on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageList.addAll(messages);
                        messageAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

}
