package com.example.oalam.smartwater;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.dx.dxloadingbutton.lib.AnimationType;
import com.dx.dxloadingbutton.lib.LoadingButton;
import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class VerifyPhoneActivity extends AppCompatActivity {
    private String verificationId;
    private View animateView;
    private LoadingButton mLoadingBtn;
    //private ProgressBar progressBar;
    //private EditText editText;
    private Pinview pinView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        mAuth = FirebaseAuth.getInstance();
        pinView = findViewById(R.id.pinview);
        mLoadingBtn = findViewById(R.id.loading_btn);
        animateView = findViewById(R.id.animate_view);

        //Shader shader = new LinearGradient(0f,0f,1000f,100f, 0xAAE53935, 0xAAFF5722, Shader.TileMode.CLAMP);
        // mLoadingBtn.setBackgroundShader(shader);


//while login failed, reset view to button with animation
        mLoadingBtn.setResetAfterFailed(true);
        mLoadingBtn.setAnimationEndAction(new Function1<AnimationType, Unit>() {
            @Override
            public Unit invoke(AnimationType animationType) {
                if (animationType == AnimationType.SUCCESSFUL) {
                    startActivity(new Intent(VerifyPhoneActivity.this, MainActivity.class));
                }
                return Unit.INSTANCE;
            }
        });


        String phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);
        mLoadingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String code = pinView.getValue().trim();
//                if (code.isEmpty() || code.length() < 6) {
//                    Toast.makeText(VerifyPhoneActivity.this, "Enter Code", Toast.LENGTH_SHORT).show();
//                    pinView.requestFocus();
//                    return;
//                }
//                // TODO: 19-Dec-18 network check
//                verifyCode(code);

                animateVerify();


            }
        });
    }

    private void animateVerify() {

        final String code2 = pinView.getValue().trim();
        mLoadingBtn.startLoading();
        mLoadingBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                //mEditUserName.setEnabled(true);
                //mEditPassword.setEnabled(true);
                if (code2.isEmpty() || code2.length() < 6) {
                    //login failed
                    mLoadingBtn.loadingFailed();
                    Toast.makeText(getApplicationContext(), "Please enter code", Toast.LENGTH_SHORT).show();
                    pinView.requestFocus();
                    return;
                } else {
                    //login success
                    if (isOnline()) {
                        verifyCode(code2);
                        mLoadingBtn.loadingSuccessful();
                        mLoadingBtn.setAnimationEndAction(new Function1<AnimationType, Unit>() {
                            @Override
                            public Unit invoke(AnimationType animationType) {
                                toNextPage();
                                return Unit.INSTANCE;
                            }
                        });
                    } else {
                        mLoadingBtn.loadingFailed();
                        Toast.makeText(VerifyPhoneActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
                        pinView.requestFocus();
                        return;
                    }


                }
            }
        }, 3000);
    }

    //add a demo activity transition animation,this is a demo implement
    private void toNextPage() {

        int cx = (mLoadingBtn.getLeft() + mLoadingBtn.getRight()) / 2;
        int cy = (mLoadingBtn.getTop() + mLoadingBtn.getBottom()) / 2;

        Animator animator = ViewAnimationUtils.createCircularReveal(animateView, cx, cy, 0, getResources().getDisplayMetrics().heightPixels * 1.2f);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());
        animateView.setVisibility(View.VISIBLE);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(VerifyPhoneActivity.this, MainActivity.class));
                mLoadingBtn.reset();
                animateView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


    private void sendVerificationCode(String number) {
        // progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(VerifyPhoneActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//            String code = phoneAuthCredential.getSmsCode();
//            if (code != null) {
//               // editText.setText(code);
//                verifyCode(code);
//            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnline() {
        if (isNetworkAvailable()) {
            return true;
        } else {
            return false;
        }
    }

}


