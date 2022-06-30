package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoppinglist.DataBase.UserRepository;

public class LoginFragment extends Fragment {

    private static final String LOG_TAG = "LoginFragment";

    private EditText emailText, passwordText;
    private final UserRepository repository;
    private final Session session;
    private UserEntity loggedUser;
    private String email, password;

    public LoginFragment(Application application){
        repository = new UserRepository(application);
        session = new Session(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container,false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity != null) {
            Button b1 = view.findViewById(R.id.login_button);
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
                            Toast.makeText(activity.getApplicationContext(), "Welcome, " + session.getUsername() , Toast.LENGTH_SHORT).show();
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
        session.setAllUserInfos(loggedUser.getName(), loggedUser.getEmail());
    }
}