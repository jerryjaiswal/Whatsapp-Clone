package com.example.whatsappclone.authentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsappclone.R;
import com.example.whatsappclone.databinding.ActivitySignUpBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {


    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callBacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // hiding the action bar
        getSupportActionBar().hide();

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.sendOtpButton.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.INVISIBLE);

        binding.sendOtpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (binding.phoneNumber.getText().toString().trim().isEmpty() ||
                        binding.phoneNumber.getText().toString().trim().length() != 10
                ) {
                    Toast.makeText(SignUp.this, "Enter the correct number!", Toast.LENGTH_SHORT).show();
                } else {

                    new AlertDialog.Builder(SignUp.this)
                            .setTitle("+91 " + binding.phoneNumber.getText().toString().trim())
                            .setMessage("Is this OK, or would you like to edit the number?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    binding.sendOtpButton.setVisibility(View.INVISIBLE);
                                    binding.progressBar.setVisibility(View.VISIBLE);
                                    otpSend();
                                }
                            }).setNeutralButton("EDIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();

                }
            }
        });


    }


    private void otpSend() {


        callBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
//for any of the reason the otp could not sent to the user
//            and for that we will use this method
//            to give any message

                binding.sendOtpButton.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SignUp.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println(e.getLocalizedMessage());
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
//here we will call the intent where we will match the verificationId and the otp

                binding.sendOtpButton.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.INVISIBLE);

                Toast.makeText(SignUp.this, "Otp sent successfully!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUp.this, OtpVerification.class);
                intent.putExtra("verificationId", verificationId);
                Log.d("otp", "otp sent successfully");
                intent.putExtra("phoneNumber", binding.phoneNumber.getText().toString().trim());
                startActivity(intent);
            }
        };


//        all these code are help to send the otp to the user and after sending the otp we will look after the callBacks
//for next step
            Log.d("code", binding.ccp.getSelectedCountryCode());
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+" + binding.ccp.getSelectedCountryCode() + binding.phoneNumber.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(callBacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


}