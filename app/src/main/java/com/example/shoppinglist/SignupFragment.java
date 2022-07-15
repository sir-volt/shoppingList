package com.example.shoppinglist;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.shoppinglist.DataBase.UserRepository;

import java.util.List;

public class SignupFragment extends Fragment {

    private static final String LOG_TAG = "SignupFragment";

    private EditText usernameText, emailText, passwordText, repeatPasswordText;
    private final UserRepository repository;
    private Session session;
    //private List<String> userEmailList;

    //TODO usare Session e il nuovo metodo di repository
    //TODO inserire controlli maggiori sulle password (lunghezza, numeri)

    public SignupFragment(Application application){
        repository = new UserRepository(application);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //userEmailList = repository.getUserList();
        return inflater.inflate(R.layout.fragment_signup,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity != null){
            //Do stuff
            session = new Session(activity.getApplicationContext());

            Button b1 = view.findViewById(R.id.signup_button);
            usernameText = view.findViewById(R.id.username_text);
            emailText = view.findViewById(R.id.email_text);
            passwordText = view.findViewById(R.id.password_text);
            repeatPasswordText = view.findViewById(R.id.repassword_text);


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserEntity userEntity = new UserEntity(emailText.getText().toString(), passwordText.getText().toString(), usernameText.getText().toString());
                    if(validateInput(userEntity)){
                        //Tutti i campi sono stati scritti
                        //Controllo che le due password siano uguali
                        if(checkPasswords(passwordText, repeatPasswordText)){
                            //Controllo che l'utente non sia già registrato
                            if(checkEmail(emailText.getText().toString())){
                                Toast.makeText(activity.getApplicationContext(), R.string.invalid_email, Toast.LENGTH_SHORT).show();
                            } else{
                                //L'email non è presente nel database, quindi posso registrare l'utente
                                Log.d(LOG_TAG, "Email is valid");
                                repository.registerUser(userEntity);

                                saveUserData(repository.login(userEntity.getEmail(), userEntity.getPassword()));
                                Toast.makeText(activity.getApplicationContext(), R.string.successful_signup, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        } else {
                            Toast.makeText(activity.getApplicationContext(), R.string.passwords_do_not_match, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity.getApplicationContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
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
            return userEntity.getEmail().trim().length() != 0 && userEntity.getName().trim().length() != 0 && userEntity.getPassword().trim().length() != 0;
        }
    }

    private Boolean checkEmail(String email){
        return repository.isTaken(email);
    }

    private void saveUserData(UserEntity loggedUser){
        Context context = getActivity().getApplicationContext();
        Log.d(LOG_TAG, "Logged User Data: " + loggedUser.toString());
        session.setAllUserInfos(loggedUser.getName(), loggedUser.getEmail(), loggedUser.getId());
    }

}
