
package com.timebanking.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.timebanking.firebase.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> emaillist;
    ArrayList<String> namelist;
    SearchAdapter searchAdapter;
    //ArrayList<String> payList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View btnAddJob = findViewById(R.id.btn_addjob);
        btnAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddJobActivity.class));
            }
        });

        View btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        search_edit_text = findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Lauoyt for Search Results
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //List to hold results
        emaillist = new ArrayList<String>();
        namelist = new ArrayList<String>();


        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    setAdapter(editable.toString());
                }else {
                    namelist.clear();
                    emaillist.clear();
                    recyclerView.removeAllViews();
                }

            }
        });


    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                namelist.clear();
                emaillist.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String uid = snapshot.getKey();
                    String email = snapshot.child("email").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);

                    if (name.toLowerCase().contains(searchedString.toLowerCase())){
                        namelist.add(name);
                        emaillist.add(email);
                        counter++;
                    }

                    if (counter == 20)
                        break;

                }
                searchAdapter = new SearchAdapter(MainActivity.this, namelist, emaillist);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
