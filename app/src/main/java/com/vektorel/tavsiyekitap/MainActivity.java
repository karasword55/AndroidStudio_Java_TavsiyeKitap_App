package com.vektorel.tavsiyekitap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText txtpassword,txtemail;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        firebaseAuth=FirebaseAuth.getInstance();

        txtemail=findViewById(R.id.txtemail);
        txtpassword=findViewById(R.id.txtpassword);

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            Intent intent=new Intent(MainActivity.this,Girdi.class);
            startActivity(intent);
            finish();
        }
        
    }
    public void kayitol(View view){
        String email=txtemail.getText().toString();
        String password=txtpassword.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this,"kullanıcı başarıyla kaydedildi!",
                        Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this,Girdi.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getLocalizedMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    public  void girisyap(View view){
        String email=txtemail.getText().toString();
        String password=txtpassword.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent=new Intent(MainActivity.this,Girdi.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getLocalizedMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}