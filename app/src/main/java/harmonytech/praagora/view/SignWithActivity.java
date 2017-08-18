package harmonytech.praagora.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

import harmonytech.praagora.R;
import harmonytech.praagora.controller.domain.User;
import harmonytech.praagora.controller.util.FirebaseHelper;
import harmonytech.praagora.controller.util.Utility;

public class SignWithActivity extends AppCompatActivity implements View.OnClickListener{

    private CallbackManager callbackManager;

    private FirebaseAuth mAuth;

    private ProgressDialog dialog;

    private DatabaseReference mDatabase;

    private String database;

    String Uid, name , email, password, profile_pic;

    EditText etEmail, etPassword;

    SharedPreferences sharedPreferences;

    Button btEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        database = getIntent().getStringExtra(Utility.KEY_CONTENT_EXTRA_DATABASE);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_sign_with);

        btEntrar = (Button) findViewById(R.id.button_login);
        btEntrar.setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.email_login);
        etPassword = (EditText) findViewById(R.id.password_login);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Collections.singletonList("email"));
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        dialog = ProgressDialog.show(SignWithActivity.this,"",
                                SignWithActivity.this.getResources().getString(R.string.processing_login), true, false);

                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(SignWithActivity.this, R.string.error_facebook_login_canceled, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(SignWithActivity.this, R.string.error_facebook_login_unknown_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.button_login:
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                loginUser();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, UsersCategoryActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void createUser(){
        if(!Utility.verifyEmptyField(email, password)) {
            dialog = ProgressDialog.show(SignWithActivity.this,"",
                    SignWithActivity.this.getResources().getString(R.string.creating_user), true, false);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnFailureListener(SignWithActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            if (e instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(SignWithActivity.this,
                                        getResources().getString(R.string.error_password_too_small),
                                        Toast.LENGTH_SHORT).show();
                                etPassword.setText("");
                                etPassword.requestFocus();
                            } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SignWithActivity.this,
                                        getResources().getString(R.string.error_invalid_email),
                                        Toast.LENGTH_SHORT).show();
                                etEmail.setText("");
                                etEmail.requestFocus();
                            } else if (e instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignWithActivity.this,
                                        getResources().getString(R.string.error_failed_signin_email_exists),
                                        Toast.LENGTH_LONG).show();
                                etEmail.setText("");
                                etEmail.requestFocus();
                            } else {
                                Toast.makeText(SignWithActivity.this,
                                        getResources().getString(R.string.error_unknown_error),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnSuccessListener(SignWithActivity.this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(SignWithActivity.this,
                                    getResources().getString(R.string.user_created_successfully),
                                    Toast.LENGTH_SHORT).show();

                            FirebaseAuth mAuth = FirebaseAuth.getInstance();

                            FirebaseUser user = mAuth.getCurrentUser();

                            finishLogin(user);
                        }
                    })
                    .addOnCompleteListener(SignWithActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                Intent intent = new Intent(SignWithActivity.this, MainActivity.class);
                                intent.putExtra(Utility.KEY_CONTENT_EXTRA_DATABASE, database);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }else{
            Toast.makeText(SignWithActivity.this,
                    getResources().getString(R.string.error_all_fields_required),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void loginUser(){

        if(!Utility.verifyEmptyField(email, password)){
            dialog = ProgressDialog.show(SignWithActivity.this,"",
                    SignWithActivity.this.getResources().getString(R.string.processing_login), true, false);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnFailureListener(SignWithActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            if(e instanceof FirebaseAuthInvalidUserException){
                                createUser();
                            }else if(e instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(SignWithActivity.this,
                                        getResources().getString(R.string.error_user_password_incorrect),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnSuccessListener(SignWithActivity.this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();

                            FirebaseUser user = mAuth.getCurrentUser();

                            finishLogin(user);

                            finish();
                        }
                    })
                    .addOnCompleteListener(SignWithActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                Intent intent = new Intent(SignWithActivity.this, MainActivity.class);
                                intent.putExtra(Utility.KEY_CONTENT_EXTRA_DATABASE, database);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }else{
            Toast.makeText(SignWithActivity.this, getResources().getString(R.string.error_all_fields_required), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnFailureListener(SignWithActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        if(e instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(SignWithActivity.this, R.string.error_failed_signin_email_exists,
                                    Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(SignWithActivity.this, R.string.error_unknown_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnSuccessListener(SignWithActivity.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();

                        FirebaseUser user = mAuth.getCurrentUser();

                        finishLogin(user);
                    }
                })
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Intent intent = new Intent(SignWithActivity.this, MainActivity.class);
                            intent.putExtra(Utility.KEY_CONTENT_EXTRA_DATABASE, database);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    public void finishLogin(FirebaseUser user){

        Uid = user.getUid();
        name = user.getDisplayName();
        email = user.getEmail();
        //profile_pic = user.getPhotoUrl().toString();

        mDatabase.child(database).child(Uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {

                            FirebaseHelper.writeNewUser(mDatabase, database, Uid, name, email, profile_pic);

                            sharedPreferences = getSharedPreferences(Utility.LOGIN_SHARED_PREF_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("database", database);
                            editor.putString("id", Uid);
                            editor.putString("name", name);
                            editor.putString("email", email);
                            editor.putString("profile_pic", profile_pic);
                            editor.apply();
                        } else {

                            sharedPreferences = getSharedPreferences(Utility.LOGIN_SHARED_PREF_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("database", database);
                            editor.putString("id", Uid);
                            editor.putString("name", name);
                            editor.putString("email", email);
                            editor.putString("profile_pic", profile_pic);
                            editor.apply();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(SignWithActivity.this, R.string.error_signin, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}