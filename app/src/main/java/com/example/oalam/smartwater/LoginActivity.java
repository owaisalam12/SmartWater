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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextNumber;
    private EditText editTextMAC;
    private EditText editTextPass;
    private LoadingButton buttonLogin;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutPhone,inputLayoutMAC,inputLayoutPass;
    public static String mytoken;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    public static String myname;
    public static String mynumber;
    public static String mymac;
    public static String mypass;

    public String getMyname() {
        return myname;
    }

    public String getMytoken() {
        return mytoken;
    }

    public String getMynumber() {
        return mynumber;
    }
    public String getMymac() {
        return mymac;
    }
    public String getMypass() {
        return mypass;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextName = findViewById(R.id.input_name);
        editTextNumber = findViewById(R.id.input_phone);
        editTextMAC = findViewById(R.id.input_mac);
        editTextPass = findViewById(R.id.input_pass);
        inputLayoutName = findViewById(R.id.input_layout_name);
        inputLayoutPhone = findViewById(R.id.input_layout_phone);
        inputLayoutMAC = findViewById(R.id.input_layout_mac);
        inputLayoutPass = findViewById(R.id.input_layout_pass);
        buttonLogin = findViewById(R.id.loading_btn);

        //Shader shader = new LinearGradient(0f,0f,1000f,100f, 0xAAE53935, 0xAAFF5722, Shader.TileMode.CLAMP);
        //buttonLogin.setBackgroundShader(shader);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonLogin.startLoading();
                String number = editTextNumber.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String mac = editTextMAC.getText().toString().trim();
                String pass = editTextPass.getText().toString().trim();
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
//                if(!validateMACempty()){
//                    buttonLogin.loadingFailed();
//                    buttonLogin.reset();
//                    return;
//                }
                if(!validateMAC2(editTextMAC.getText().toString())){
                    buttonLogin.loadingFailed();
                    buttonLogin.reset();
                    return;
                }
                if(!validatePass()){
                    buttonLogin.loadingFailed();
                    buttonLogin.reset();
                    return;
                }
                String phoneNumber = "+" + "92" + number;
                myname = name;
                mynumber = phoneNumber;
                mymac=mac;
                mypass=pass;
                Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("phonenumber", phoneNumber);
                intent.putExtra("name", name);
                intent.putExtra("mac", mac);
                intent.putExtra("pass", pass);
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


private boolean validateMACempty(){
    if (editTextMAC.getText().toString().trim().isEmpty()) {
        inputLayoutMAC.setError("Enter MAC Address");
        requestFocus(editTextMAC);
        return false;
    }else{
        inputLayoutMAC.setErrorEnabled(false);

    }
    return true;
}

    private boolean validateMAC(String mac) {
            Pattern p = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
            Matcher m = p.matcher(mac);
            return m.find();
    }

    private boolean validateMAC2(String mac2){

        if (editTextMAC.getText().toString().trim().isEmpty()) {
            inputLayoutMAC.setError("Enter MAC Address");
            requestFocus(editTextMAC);
            return false;
        }else{
            inputLayoutMAC.setErrorEnabled(false);

        }

        if(!validateMAC(mac2)){
            inputLayoutMAC.setError("Valid MAC is required");
            requestFocus(editTextMAC);
            return false;
        }else{
            inputLayoutMAC.setErrorEnabled(false);

        }
        return true;

    }
    private boolean validatePass() {
        if (editTextPass.getText().toString().trim().isEmpty()) {
            inputLayoutPass.setError("Enter Password");
            requestFocus(editTextPass);
            return false;
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }

        if(!editTextPass.getText().toString().trim().equals("Admin")){
            inputLayoutPass.setError("Invalid Password");
            requestFocus(editTextPass);
            return false;
        }else{
            inputLayoutPass.setErrorEnabled(false);
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
