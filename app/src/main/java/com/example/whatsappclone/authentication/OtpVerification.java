package com.example.whatsappclone.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsappclone.databinding.ActivityOtpVerificationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpVerification extends AppCompatActivity {

    //    this binding variable is used to access the views without using the findViewById
    private ActivityOtpVerificationBinding binding;
    private String verificationId;
    private String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hiding the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        verificationId = getIntent().getStringExtra("verificationId");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
//        taking the input form the user
        editTextInput();
        binding.verifyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    if (binding.edt1.getText().toString().trim().isEmpty() ||
                            binding.edt2.getText().toString().trim().isEmpty() ||
                            binding.edt3.getText().toString().trim().isEmpty() ||
                            binding.edt4.getText().toString().trim().isEmpty() ||
                            binding.edt5.getText().toString().trim().isEmpty() ||
                            binding.edt6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OtpVerification.this, "Enter the correct Otp!!!", Toast.LENGTH_SHORT).show();
                } else {
                    if (verificationId != null) {
                        String code = binding.edt1.getText().toString().trim() +
                                binding.edt2.getText().toString().trim() +
                                binding.edt3.getText().toString().trim() +
                                binding.edt4.getText().toString().trim() +
                                binding.edt5.getText().toString().trim() +
                                binding.edt6.getText().toString().trim();
                        binding.verificationProgressBar.setVisibility(View.VISIBLE);
                        binding.verifyButton.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    binding.verificationProgressBar.setVisibility(View.INVISIBLE);
                                    binding.verifyButton.setVisibility(View.VISIBLE);
//                                    in the next activity we will save the data of the user in firebase

                                    Intent intent = new Intent(OtpVerification.this, AboutSection.class);
                                    String id = task.getResult().getUser().getUid();
                                    intent.putExtra("id", id);
                                    Log.d("useridHere", "" + id);
                                    intent.putExtra("phoneNumber", phoneNumber);
                                    startActivity(intent);

                                } else {
                                    binding.verificationProgressBar.setVisibility(View.INVISIBLE);
                                    binding.verifyButton.setVisibility(View.VISIBLE);
                                    Toast.makeText(OtpVerification.this, "Enter the correct Otp!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                }
            }
        });


    }

    private void editTextInput() {
//here we can take the input of otp easily for user convience

        binding.edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.edt1.getText().toString().trim().length() != 0) {
                    binding.edt2.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.edt2.getText().toString().trim().length() == 0) {
                    binding.edt1.requestFocus();
                } else {
                    binding.edt3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.edt3.getText().toString().trim().length() == 0) {
                    binding.edt2.requestFocus();
                } else {

                    binding.edt4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.edt4.getText().toString().trim().length() == 0) {
                    binding.edt3.requestFocus();
                } else {

                    binding.edt5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edt5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.edt5.getText().toString().trim().length() == 0) {
                    binding.edt4.requestFocus();
                } else {

                    binding.edt6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edt6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.edt6.getText().toString().trim().length() == 0) {
                    binding.edt5.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}