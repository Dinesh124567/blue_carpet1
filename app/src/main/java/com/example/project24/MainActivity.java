package com.example.project24;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth a=FirebaseAuth.getInstance();




    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser cu=a.getCurrentUser();
        if(cu!=null) {
            Intent intent1 = new Intent(getBaseContext(), user.class);
            startActivity(intent1);

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

//---------------------------------------Declaration------------------------------------------
        final EditText us =(EditText) findViewById(R.id.editText4);
        final EditText pa =(EditText) findViewById(R.id.editText5);
        TextView r =       (TextView) findViewById(R.id.textView8);
        TextView reset =       (TextView) findViewById(R.id.textView2);
        ImageView lo=      (ImageView) findViewById(R.id.logo) ;
        CheckBox sp=       (CheckBox) findViewById(R.id.sp);
        final Button login=findViewById(R.id.button);
        final Toast t = Toast.makeText(getApplicationContext(), "Error..!", Toast.LENGTH_SHORT);
        final Toast to = Toast.makeText(getApplicationContext(), "login Successful..!", Toast.LENGTH_SHORT);
        final Toast toa = Toast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_SHORT);
        final Toast toas = Toast.makeText(getApplicationContext(), "Check ur mail", Toast.LENGTH_SHORT);


//-----------------------------------Password view----------------------------------------------
        sp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    pa.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pa.setSelection(pa.getText().length());
                } else {
                    pa.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pa.setSelection(pa.getText().length());
                }

            }});

//----------------------------------------------------------------------------------------------
              r.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getBaseContext(), Reg.class);
               startActivity(intent);

           }
       });
//--------------------------------------------------------------------------------------------

            final ProgressBar pBar = (ProgressBar) findViewById(R.id.progressBar);

            pBar.setVisibility(View.GONE);
        FirebaseUser cu=a.getCurrentUser();


            login.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {login.setVisibility(View.GONE);
                    pBar.setVisibility(View.VISIBLE);
                    if(a.getCurrentUser()!=null) return;
                    if(us.getText().length()!=0 && pa.getText().length()!=0 ){
                    a.signInWithEmailAndPassword(us.getText().toString(), pa.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()  ) {
                                    to.show();
                                    login.setVisibility(View.GONE);
                                    pa.setText("");
                                    finishAndRemoveTask();
                                    Intent intent1 = new Intent(getBaseContext(), user.class);
                                    startActivity(intent1);
                                    pBar.setVisibility(View.GONE);
                                }
                                else{ Toast.makeText(getApplicationContext(),"auth fail",Toast.LENGTH_SHORT).show();
                                    pBar.setVisibility(View.GONE);login.setVisibility(View.VISIBLE);}
                        }
                    });}
                    else{if(us.getText().length()==0) Toast.makeText(getApplicationContext(),"enter the mail",Toast.LENGTH_SHORT).show();
                            else Toast.makeText(getApplicationContext(),"ENTER PASS",Toast.LENGTH_SHORT).show();
                        pBar.setVisibility(View.GONE);login.setVisibility(View.VISIBLE);

                    }
                }
            });


//------------------------------------------------------------
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(us.getText()!= null)
                {
                    a.sendPasswordResetEmail(us.getText().toString());
                    toas.show();
                }
                else{
                    toa.show();
                }
            }
        });

    }
}