package com.example.project24;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.net.URLEncoder;

public class user extends AppCompatActivity {

    private FirebaseDatabase dbs= FirebaseDatabase.getInstance();
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private StorageReference srf;

    String email = user.getEmail();
    @Override
    public void onStart(){
        super.onStart();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_bookings, R.id.nav_offers,
                R.id.nav_nearfunctns, R.id.nav_logout, R.id.nav_sugg)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.user_nam);
        TextView em = (TextView) headerView.findViewById(R.id.textView);
        try{

            username.setText(user.getDisplayName());
            em.setText(user.getEmail());
            }
        catch(Exception e){
            username.setText("No name");
        }

       // srf= FirebaseStorage.getInstance().getReference();

        ImageView img=  headerView.findViewById(R.id.imageView2);
        String str_url="https://firebasestorage.googleapis.com/v0/b/project24-d91d5.appspot.com/o/data%2Ffunction%20halls%2Fsai%20functionhall%2Fsai%20func.jpg?alt=media&token=c6fadb26-46ff-42b3-b397-cc989aeb3e2e";
        String str_parameters = "param123=abc|test&param=abc test";
       try{ String encodedparams = URLEncoder.encode(str_parameters,"UTF-8");

        String str_finalurl=str_url+encodedparams;
        Glide.with(getApplicationContext()).load(str_finalurl).into(img);}

        catch(Exception e){  TextView tw=findViewById(R.id.textView3); tw.setText(e.toString()+"");}

    }

    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void alertbox(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to Sign Out");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
