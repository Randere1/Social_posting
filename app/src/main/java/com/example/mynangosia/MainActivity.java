package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity  implements  NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    private RecyclerView picpostRecycler;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference users, Alcohols;
    String currentUserId;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private CircleImageView navprofileimage;
    private TextView navprofilename, navid;
    mainAD discpostAd;
    ArrayList<alcoholGs> discposts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Alcoholdisplay();

        mToolbar = findViewById(R.id.main_page_bar);
        drawerLayout = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.main_nav);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        users = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        Alcohols = FirebaseDatabase.getInstance().getReference().child("Alcohols");


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(getResources().getColor(R.color.colorNavigation));

        picpostRecycler = findViewById(R.id.Posts);
        picpostRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        picpostRecycler.setLayoutManager(linearLayoutManager);



        navprofilename = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_user);
        navid = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_id);
    }

    private void Alcoholdisplay() {

        discposts = new ArrayList<>();
        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("Alcohols");
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                discposts = new ArrayList<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    alcoholGs pp = eventSnapshot.getValue(alcoholGs.class);

                    discposts.add(pp);
                }
                discpostAd = new mainAD(MainActivity.this ,discposts);
                picpostRecycler.setAdapter(discpostAd);
                discpostAd.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser onlineuser = mAuth.getCurrentUser();
        if (onlineuser == null) {
            DirectUserToLoginActivity();
        } else {
            isUserInDataBase();
        }
    }

    private void isUserInDataBase() {


        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String  full = dataSnapshot.child("username").getValue().toString();
                    String  p ="not set";
                    if (full.equals(p) ){
                        userMovedToSetUpActivity();
                    }if (!full.equals(p)){
                        String  b = dataSnapshot.child("username").getValue().toString();

                    }
                }else
                {
                    HashMap picpostmap = new HashMap();
                    picpostmap.put("username", "not set");
                    picpostmap.put("Admin", "user");
                    picpostmap.put("ProfileImage", "https://firebasestorage.googleapis.com/v0/b/post-32466.appspot.com/o/Profile%20Images%2FiZbhgkZQJfhaPZn9guneOsv9Jw62.jpg?alt=media&token=dc24a261-3e2e-42b9-925e-bd5581c7f02b");
                    users.updateChildren(picpostmap);

                    userMovedToSetUpActivity();
                    Toast.makeText(MainActivity.this, "Set your profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void userMovedToSetUpActivity() {
        Intent ra = new Intent(MainActivity.this, set.class);
        startActivity(ra);
        finish();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Menu menu =navigationView.getMenu();

        switch (item.getItemId()) {

            case R.id.nav_log:
                mAuth.signOut();
                DirectUserToLoginActivity();
                break;
            case R.id.nav_Rooms:
                Intent a = new Intent(MainActivity.this, room.class);
                startActivity(a);

                break;
            case R.id.nav_Admin:

                users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String  full = dataSnapshot.child("Admin").getValue().toString();
                            String  p ="user";
                            if (full.equals(p) ){
                                 Toast.makeText(MainActivity.this, "You are not an Admin", Toast.LENGTH_SHORT).show();
                            }else {

                                 Intent g = new Intent(MainActivity.this, admin.class);
                                startActivity(g);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;

            case R.id.nav_Contact:
                Intent u = new Intent(MainActivity.this, contact.class);
                startActivity(u);

                break;

            case R.id.nav_Acc:
                Intent ui = new Intent(MainActivity.this, profile.class);
                startActivity(ui);


            default:
                break;
        }

        return false;
    }

    private void DirectUserToLoginActivity() {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}