package com.example.project24;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Reg extends AppCompatActivity {
    private FirebaseAuth a = FirebaseAuth.getInstance();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.register);


        final EditText usnam=(EditText) findViewById(R.id.editText8);

//-------------------------------------------------------------
        final CheckBox rsp = (CheckBox) findViewById(R.id.shp2);
        final EditText rp = findViewById(R.id.editText11);
        final EditText rcp = findViewById(R.id.editText12);
        final EditText Fname = findViewById(R.id.editText8);
        final EditText email = findViewById(R.id.editText9);
        final EditText ph = findViewById(R.id.editText10);
        Button can = findViewById(R.id.button3);
        Button regi = findViewById(R.id.button2);
        final Toast toast = Toast.makeText(getApplicationContext(), "not same", Toast.LENGTH_SHORT);
        final Toast toast1 = Toast.makeText(getApplicationContext(), "Registration fail", Toast.LENGTH_SHORT);
        final ProgressBar p= (ProgressBar) findViewById(R.id.progressBar2);
        p.setVisibility(View.GONE);
//-------------------------------------------------------------------


        rsp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    rp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    rp.setSelection(rp.getText().length());
                    rcp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    rcp.setSelection(rp.getText().length());
                } else {
                    rp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    rp.setSelection(rp.getText().length());
                    rcp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    rcp.setSelection(rp.getText().length());
                }

            }
        });
//-------------------------------------------------------------------------------------
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast1.show();
                finish();
            }
        });
//------------------------------------------------------------------------------
        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usn=usnam.getText().toString();
                final String phno=ph.getText().toString();
                p.setVisibility(View.VISIBLE);
                if (Fname.getText().length() != 0 || email.getText().length() != 0 || rp.getText().length() != 0 || rcp.getText().length() != 0 || ph.getText().length() != 0) {
                   if(Emailv(email.getText().toString())){
                    if (passwordV(rp.getText().toString())){
                        if (rp.getText().toString().equals(rcp.getText().toString())) {
                            a.createUserWithEmailAndPassword(email.getText().toString(), rp.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        //----------------
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(usn)
                                                .build();
                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (! task.isSuccessful()) {

                                                            Toast.makeText(getApplicationContext(),"not success",Toast.LENGTH_SHORT);
                                                        }
                                                    }
                                                });


                                        String uid=user.getUid();
                                        DatabaseReference mDbRef = mDatabase.getReference("/users");
                                        mDbRef.child(usn).setValue(uid);
                                        mDbRef=mDatabase.getReference("/users/"+usn);
                                        mDbRef.child("email").setValue(user.getEmail());
                                        mDbRef.child("phno").setValue(phno);
                                        mDbRef.child("uid").setValue(user.getUid());


                                        //---------------------


                                        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                        a.getInstance().signOut();
                                        p.setVisibility(View.GONE);
                                        finish();
                                    } else {
                                        //toast1.show();
                                        Toast.makeText(Reg.this, "Auth failed", Toast.LENGTH_SHORT).show();
                                        p.setVisibility(View.GONE);
                                    }
                                }


                            });
                        } else {
                            toast.show();
                            p.setVisibility(View.GONE);
                        }
                } else {
                    Toast.makeText(getApplicationContext(), "Check ur password", Toast.LENGTH_SHORT).show();
                        p.setVisibility(View.GONE);
                }}
                else {
                       Toast.makeText(getApplicationContext(), "Check ur Email", Toast.LENGTH_SHORT).show();
                       p.setVisibility(View.GONE);
                }
                }

                else{
                    Toast.makeText(getApplicationContext(), "Enter complete details", Toast.LENGTH_LONG).show();
                    p.setVisibility(View.GONE);
                }

            }
                                    private boolean Emailv(String emailvalid) {
                                        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(emailvalid).matches();
                                    }
                                }
        );

        //===========================================================


    }


    public boolean passwordV(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }}