package com.example.chessgame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.chessgame.databinding.ActivityLoginBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.Arrays;
import java.util.zip.Inflater;

public class Login extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    ImageView fbBtn;
    ImageView twBtn;
    TextView textView1, forgotpass;
    CallbackManager callbackManager;
    FirebaseAuth firebaseAuth;

    private AppBarConfiguration appBarConfiguration;
    private ActivityLoginBinding binding;

    public Login() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View vi = View.inflate(this,R.layout.activity_main2,null);
        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        if (accessToken!=null && !accessToken.isExpired()){
//            navigateToSecondActivity();
//            finish();
//            Toast.makeText(getApplicationContext(), "Already signed in", Toast.LENGTH_SHORT).show();
//        }

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        navigateToSecondActivity();
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        googleBtn = findViewById(R.id.google_btn);
        fbBtn = findViewById(R.id.fb_btn);
        twBtn = findViewById(R.id.twt_btn);
        forgotpass = findViewById(R.id.forgotpass);
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        MaterialButton loginbtn = findViewById(R.id.loginbtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//        if(acct!=null){
//            navigateToSecondActivity();
//        }

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,OtpSend.class));
            }
        });

        fbBtn.setOnClickListener(v -> LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile")));

        googleBtn.setOnClickListener(v -> signIn());

        loginbtn.setOnClickListener(v -> {
            if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                //correct
                Toast.makeText(Login.this,"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();
            }else
                //incorrect
                Toast.makeText(Login.this,"LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
        });

        twBtn.setOnClickListener(v -> {
            OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
            provider.addCustomParameter("lang", "fr");
            Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
            if (pendingResultTask != null) {
                // There's something already here! Finish the sign-in for your user.
                pendingResultTask
                        .addOnSuccessListener(
                                authResult -> {
                                    navigateToSecondActivity();
                                    Toast.makeText(Login.this,"Login Success", Toast.LENGTH_SHORT);
                                })
                        .addOnFailureListener(
                                e -> Toast.makeText(Login.this,"Login Failed"+e.getMessage(),Toast.LENGTH_SHORT));
            } else {
                firebaseAuth
                        .startActivityForSignInWithProvider(/* activity= */ Login.this, provider.build())
                        .addOnSuccessListener(
                                authResult -> {
                                    navigateToSecondActivity();
                                    Toast.makeText(Login.this,"Login Success", Toast.LENGTH_SHORT);

                                })
                        .addOnFailureListener(
                                e -> Toast.makeText(Login.this,"Login Failed"+e.getMessage(),Toast.LENGTH_SHORT));
            }
        });
    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }





//    @Override
//    protected void onActivityResult2(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_login);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(Login.this,MainActivity2.class);
        startActivity(intent);
    }
}