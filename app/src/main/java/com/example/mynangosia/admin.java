package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mynangosia.Mpesa.MpesaActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin extends AppCompatActivity implements f1.OnFragmentInteractionListener,f2.OnFragmentInteractionListener,f3.OnFragmentInteractionListener ,f4.OnFragmentInteractionListener {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private RecyclerView picpostRecycler;
    private Button d,e,f;
  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();




            d = findViewById(R.id.main_post);
            e = findViewById(R.id.main_post1);
            f = findViewById(R.id.main_post2);

        mToolbar = findViewById(R.id.msg_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("ALCOHOLS"));
        tabLayout.addTab(tabLayout.newTab().setText("ROOMS"));
        tabLayout.addTab(tabLayout.newTab().setText("OTHER PRODUCTS"));
        tabLayout.addTab(tabLayout.newTab().setText("DELIVERED"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new fAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent u = new Intent(admin.this, post.class);
                startActivity(u);

            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h = new Intent(admin.this, roomPost.class);
                startActivity(h);
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h = new Intent(admin.this, productPost.class);
                startActivity(h);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home ){
            SendUserToMain();
        }

        switch (item.getItemId()) {



            case  R.id.log_menu:

                mAuth.signOut();
                Intent b = new Intent(admin.this, login.class);
                startActivity(b);

                break;


            case  R.id.drink_menu:

                Intent c = new Intent(admin.this, people_with_orders.class);
                startActivity(c);





                return true;

            default:
        }
        return super.onOptionsItemSelected(item);



    }

    private void SendUserToMain() {

        Intent e=new Intent(admin.this,MainActivity.class);
        startActivity(e);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this , MainActivity.class));
    }
}