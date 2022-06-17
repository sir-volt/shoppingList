package com.example.shoppinglist;

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

public class SignupFragment extends Fragment {

    private static final String LOG_TAG = "SignupFragment";

    private Button b1;
    private EditText ed1, ed2, ed3, ed4;

    public SignupFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity != null){
            //Do stuff
            b1 = view.findViewById(R.id.signup_button);
            ed1 = view.findViewById(R.id.username_text);
            ed2 = view.findViewById(R.id.email_text);
            ed3 = view.findViewById(R.id.password_text);
            ed4 = view.findViewById(R.id.repassword_text);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Controlli sull'unicità della email
                    //Non va forse devo usare .equals o qualcosa del genere
                    if(ed3.getText().toString().equals(ed4.getText().toString())){
                        Toast.makeText(activity.getApplicationContext(), "Correct Passwords", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(activity.getApplicationContext(), "Check your passwords", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Log.e(LOG_TAG, "Activity is null");
        }
    }
}
