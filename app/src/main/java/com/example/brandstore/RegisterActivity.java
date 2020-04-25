package com.example.brandstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextMobile;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextMobile = findViewById(R.id.edt_register);
        mAuth= FirebaseAuth.getInstance();

//        mAuth.signInAnonymously()
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
////                            String phonenumber = getIntent().getStringExtra("mobile");
////                            sendVerificationCode(phonenumber);
//                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
//                            assert firebaseUser != null;
//                            String userId = firebaseUser.getUid();
//                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
//
//
//
//                            HashMap<String, String> map = new HashMap<>();
//                            map.put("id", userId);
//                            reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()){
//                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                                        startActivity(intent);
//                                        Toast.makeText(RegisterActivity.this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//                            startActivity(intent);
//
//                        } else {
//                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                        }
//
//                        // ...
//                    }
//                });

//        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String mobile = editTextMobile.getText().toString().trim();
//                String code = "+996";
//
//                if(mobile.isEmpty() || mobile.length() < 9){
//                    editTextMobile.setError("Enter a valid mobile");
//                    editTextMobile.requestFocus();
//                    return;
//                }
//                String phoneNumber = code + mobile;
//                Intent intent = new Intent(RegisterActivity.this, VerifyPhoneActivity.class);
//                intent.putExtra("mobile", phoneNumber);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
