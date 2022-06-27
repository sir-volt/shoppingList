package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppinglist.DataBase.UserRepository;

import java.util.List;

public class LoginFragment extends Fragment {

    private static final String LOG_TAG = "LoginFragment";

    private Button b1;
    private EditText emailText, passwordText;
    private final UserRepository repository;
    private UserEntity loggedUser;
    private String email, password;

    public LoginFragment(Application application){
        repository = new UserRepository(application);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container,false);
    }

    //TODO Spostare sharedpreferences in oncreateview o nel costruttore
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity != null) {
            b1 = view.findViewById(R.id.login_button);
            emailText = view.findViewById(R.id.email_text);
            passwordText = view.findViewById(R.id.pw_text);


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    email = emailText.getText().toString();
                    password = passwordText.getText().toString();
                    Log.e(LOG_TAG, "Current email and password: " + email + " : " + password);
                    if (validateInput()){
                        loggedUser = login(email, password);
                        if (loggedUser!=null){
                            saveUserData();
                            SharedPreferences sharedPreferences = activity.getApplicationContext().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
                            Toast.makeText(activity.getApplicationContext(), "Welcome, " + sharedPreferences.getString("username", null) , Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(activity.getApplicationContext(), "User Not Found in Database", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(activity.getApplicationContext(),"Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Log.e(LOG_TAG, "Activity is null");
        }
    }

    private Boolean validateInput(){
        if(email.isEmpty() || password.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private synchronized UserEntity login(String email, String password){
        UserEntity tmp = repository.login(email, password);
        if (tmp == null){
            Log.e(LOG_TAG, "ERROR: UserEntity is null!");
        } else {
            Log.e(LOG_TAG, "SUCCESS: UserEntity was found: " + tmp.toString());
        }
        return tmp;
    }

    private void saveUserData(){
        Context context = getActivity().getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", loggedUser.getName());
        editor.putString("email", loggedUser.getEmail());
        editor.commit();

    }
}