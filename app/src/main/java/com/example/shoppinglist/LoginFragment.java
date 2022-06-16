package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    private Button b1,b2;
    private EditText ed1,ed2;
    private TextView text1;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        b1 = (Button) findViewById(R.id.login_button);
        ed1 = findViewById(R.id.email_text);
        ed2 = findViewById(R.id.pw_text);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed1.getText().toString().equals("admin") &&
                    ed2.getText().toString().equals("admin")){
                    Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                }
            }
        });*/
    public LoginFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container,false);
    }
}