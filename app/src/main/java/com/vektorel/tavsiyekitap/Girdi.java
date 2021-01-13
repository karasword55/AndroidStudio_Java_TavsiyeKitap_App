package com.vektorel.tavsiyekitap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Girdi extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> userEmailFromFB;
    ArrayList<String> userkitapadiFromFB;
    ArrayList<String> userkitapyorumFromFB;
    ArrayList<String> userImageFromFB;
    GirdiRecyclerAdapter girdiRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girdi);
        //getSupportActionBar().hide();
        userEmailFromFB=new ArrayList<>();
        userkitapadiFromFB=new ArrayList<>();
        userkitapyorumFromFB=new ArrayList<>();
        userImageFromFB=new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getDataFromFirestore();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        girdiRecyclerAdapter=new GirdiRecyclerAdapter(userEmailFromFB,
                userkitapadiFromFB,userImageFromFB,userkitapyorumFromFB);
        recyclerView.setAdapter(girdiRecyclerAdapter);


    }
    public void getDataFromFirestore(){
        CollectionReference collectionReference=firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error!=null){
                    Toast.makeText(Girdi.this,error.getLocalizedMessage().toString(),
                            Toast.LENGTH_LONG).show();
                }

                if(value !=null){
                    for(DocumentSnapshot snapshot:value.getDocuments()){
                        Map<String,Object> data=snapshot.getData();
                        String kitapadi=(String) data.get("kitapadi");
                        String kitapyorum=(String) data.get("kitapyorumu");
                        String useremail=(String) data.get("useremail");
                        String downloadUrl=(String) data.get("downloadurl");

                        userEmailFromFB.add(useremail);
                        userImageFromFB.add(downloadUrl);
                        userkitapadiFromFB.add(kitapadi);
                        userkitapyorumFromFB.add(kitapyorum);

                        girdiRecyclerAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.option_kitap_tavsiye) {
            Intent intentToUpload = new Intent(Girdi.this, UploadActivity.class);
            startActivity(intentToUpload);
        } else if (item.getItemId() == R.id.option_cikis) {

            firebaseAuth.signOut();

            Intent intentToSignUp = new Intent(Girdi.this, MainActivity.class);
            startActivity(intentToSignUp);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}