package com.example.shoppinglist;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.shoppinglist.DataBase.UserRepository;

import java.util.List;
//TODO implementare anche il login dopo essersi registrati
public class SignupFragment extends Fragment {

    private static final String LOG_TAG = "SignupFragment";

    private Button b1;
    private EditText usernameText, emailText, passwordText, repeatPasswordText;
    private final UserRepository repository;
    private List<String> userEmailList;


    public SignupFragment(Application application){
        repository = new UserRepository(application);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userEmailList = repository.getUserList();
        return inflater.inflate(R.layout.fragment_signup,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity != null){
            //Do stuff
            b1 = view.findViewById(R.id.signup_button);
            usernameText = view.findViewById(R.id.username_text);
            emailText = view.findViewById(R.id.email_text);
            passwordText = view.findViewById(R.id.password_text);
            repeatPasswordText = view.findViewById(R.id.repassword_text);


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setEmail(emailText.getText().toString());
                    userEntity.setPassword(passwordText.getText().toString());
                    userEntity.setName(usernameText.getText().toString());
                    if(validateInput(userEntity)){
                        //Tutti i campi sono stati scritti
                        //Controllo che le due password siano uguali
                        if(checkPasswords(passwordText, repeatPasswordText)){
                            //Controllo che l'utente non sia già registrato
                            if(checkEmail(emailText.getText().toString())){
                                Toast.makeText(activity.getApplicationContext(), "Email già in uso", Toast.LENGTH_SHORT).show();
                            } else{
                                //L'email non è presente nel database, quindi posso registrare l'utente
                                Log.e(LOG_TAG, "Email is valid");
                                repository.registerUser(userEntity);
                                Toast.makeText(activity.getApplicationContext(), "User registered correctly", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(activity.getApplicationContext(), "Check your passwords", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity.getApplicationContext(), "Please, fill in all the fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Log.e(LOG_TAG, "Activity is null");
        }
    }

    private Boolean checkPasswords(EditText p1, EditText p2){
        if(p1.getText().toString().equals(p2.getText().toString())){
            return true;
        }
        return false;
    }

    private Boolean validateInput(UserEntity userEntity){
        if(userEntity.getEmail().isEmpty() || userEntity.getName().isEmpty() || userEntity.getPassword().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    synchronized private Boolean checkEmail(String email){
        Log.e(LOG_TAG, "Email to lookup: " + email);
        userEmailList = repository.getUserList();
        if (userEmailList !=null){
            if(userEmailList.contains(email)){
                Log.e(LOG_TAG, "Email was found in database at index" + userEmailList.indexOf(email));
                return true;
            } else {
                Log.e(LOG_TAG, "OK! Email List does not contain " + email);
                return false;
            }
        } else {
            Log.e(LOG_TAG, "ERROR! Query result for " + email + " was null");
            return false;
        }

    }
}
