package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppinglist.DataBase.UserDatabase;
import com.example.shoppinglist.DataBase.UserRepository;

public class SettingsActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private EditText usernameEditText;
    private Button applyButton, logoutButton;
    private Session session;
    UserRepository repository;
    private String LOG_TAG = "SettingsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);
        
        session = new Session(getApplicationContext());
        repository = new UserRepository(getApplication());
        usernameTextView = findViewById(R.id.username_textview);
        usernameTextView.setText(session.getUsername());
        usernameEditText = findViewById(R.id.username_textinputedittext);
        applyButton = findViewById(R.id.apply_button);
        logoutButton = findViewById(R.id.logout_button);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUsername(usernameEditText)){
                    String usernameString = usernameEditText.getText().toString().trim();
                    usernameTextView.setText(usernameString);
                    session.setUsername(usernameString);
                    repository.updateUsername(usernameString, session.getUserId());
                    Toast.makeText(getApplicationContext(), getString(R.string.username_correctly_updated), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), getString(R.string.type_username), Toast.LENGTH_SHORT).show();
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLoginStatus(false);
                Intent i = new Intent(getApplicationContext(), LoginSignupActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    /**
     * Initialize the contents of the Activity's standard options menu
     * @param menu The options menu in which you place your items.
     * @return true for the menu to be displayed; if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        menu.findItem(R.id.app_bar_search).setVisible(false);
        menu.findItem(R.id.app_bar_settings).setVisible(false);
        return true;
    }

    private boolean validateUsername(EditText usernameEditText){
        if(usernameEditText.getText()!=null){
            return usernameEditText.getText().toString().trim().length() > 0;
        } else {
            return false;
        }
    }

}
