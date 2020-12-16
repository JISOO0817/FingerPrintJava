package com.example.fingerprintjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

     Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.loginBtn);


        //biometricManager

        BiometricManager biometricManager = BiometricManager.from(this);
        switch(biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:
               // Toast.makeText(this, "지문인식으로 로그인을 할 수 있어요!", Toast.LENGTH_SHORT).show();
                break;
                
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "지문인식 센서가 없습니다!", Toast.LENGTH_SHORT).show();
                loginBtn.setVisibility(View.GONE);
                break;
                
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "지문인식센서를 사용할 수 없어요!", Toast.LENGTH_SHORT).show();
                loginBtn.setVisibility(View.GONE);
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "지문인식을 저장할 수 없어요, 설정에 가서 확인해주세요!", Toast.LENGTH_SHORT).show();
                break;
        }


        Executor executors = ContextCompat.getMainExecutor(this);

        final BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this, executors, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                finish();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });  //결과 알려줌

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("로그인")
                .setDescription("지문을 인식해주세요.")
                .setNegativeButtonText("취소")
                .build();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                biometricPrompt.authenticate(promptInfo);
            }
        });




    }



}