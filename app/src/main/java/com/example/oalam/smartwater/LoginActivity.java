package com.example.oalam.smartwater;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextNumber;
    private LoadingButton buttonLogin;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutPhone;
    public static String mytoken;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    public static String myname;
    public static String mynumber;

    public String getMyname() {
        return myname;
    }

    public String getMytoken() {
        return mytoken;
    }

    public String getMynumber() {
        return mynumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextName = findViewById(R.id.input_name);
        editTextNumber = findViewById(R.id.input_phone);
        inputLayoutName = findViewById(R.id.input_layout_name);
        inputLayoutPhone = findViewById(R.id.input_layout_phone);
        buttonLogin = findViewById(R.id.loading_btn);

        //Shader shader = new LinearGradient(0f,0f,1000f,100f, 0xAAE53935, 0xAAFF5722, Shader.TileMode.CLAMP);
        //buttonLogin.setBackgroundShader(shader);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonLogin.startLoading();
                String number = editTextNumber.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                if (!validateName()) {
                    buttonLogin.loadingFailed();
                    buttonLogin.reset();
                    return;
                }
                if (!validateNumber()) {
                    buttonLogin.loadingFailed();
                    buttonLogin.reset();
                    return;
                }
                String phoneNumber = "+" + "92" + number;
                myname = name;
                mynumber = phoneNumber;
                Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("phonenumber", phoneNumber);
                intent.putExtra("name", name);
                buttonLogin.loadingSuccessful();
                startActivity(intent);
            }
        });

        //for gettting device token
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    mytoken = task.getResult().getToken();
                    Log.v("MainActivity Token: ", mytoken);
                } else {
                    Toast.makeText(LoginActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validateName() {
        if (editTextName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("Enter your name");
            requestFocus(editTextName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNumber() {
        String number = editTextNumber.getText().toString().trim();
        if (number.isEmpty() || number.length() < 10) {
            inputLayoutPhone.setError("Valid number is required");
            requestFocus(editTextNumber);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //checking if user is already registered
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

}
