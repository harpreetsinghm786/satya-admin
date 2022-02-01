package com.official.satyaadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class verification extends AppCompatActivity {

    EditText otp;
    Button submit;
    TextView resend;
    private String number,id,username;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    TextView user_number;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        progressBar=findViewById(R.id.progress_bar);

        otp=findViewById(R.id.otp);
        submit=findViewById(R.id.submit);
        resend=findViewById(R.id.resend);
        user_number=findViewById(R.id.user_phone);
        databaseReference= FirebaseDatabase.getInstance().getReference("Userdata");
        mAuth=FirebaseAuth.getInstance();

        number=getIntent().getStringExtra("number");
        username=getIntent().getStringExtra("username");

        user_number.setText(number);


        sendVerificationCode();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(otp.getText().toString())){
                    otp.setError("OTP is required");
                }else if(otp.getText().toString().replace(" ","").length()!=6){
                    otp.setError("Enter right otp");
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id,otp.getText().toString().replace(" ",""));
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });
    }

    private void sendVerificationCode() {
        new CountDownTimer(60000,1000){
            @Override
            public void onTick(long l) {
                resend.setText("Do not receive OTP?"+" "+l/1000);
                resend.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resend.setText("Do not receive OTP? Resend");
                resend.setEnabled(true);
            }
        }.start();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String id, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        verification.this.id=id;

                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(verification.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                });        // OnVerificationStateChangedCallbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(user_number.getText().toString())){
                                        Intent i=new Intent(verification.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        Intent i=new Intent(verification.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            Toast.makeText(verification.this, "Verification Failed !", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }
}
