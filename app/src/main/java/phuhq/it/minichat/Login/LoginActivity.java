package phuhq.it.minichat.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import phuhq.it.minichat.MainActivity;
import phuhq.it.minichat.Model.User;
import phuhq.it.minichat.R;
import phuhq.it.minichat.Signup.SignUp;

public class LoginActivity extends AppCompatActivity {
    //region AVAILABLE
    private EditText edUserName, edPassWord;
    //endregion

    //region FROM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mainLoad();
    }
    //region

    //region ADD CONTROLS AND MAIN LOAD
    public void addControls() {
        edUserName = findViewById(R.id.edtUsername);
        edPassWord = findViewById(R.id.edtPassword);
    }

    public void mainLoad() {
        addControls();
    }
    //endregion

    //region LOGIN
    public void Login(View view) {
        try {
            final String userName = edUserName.getText().toString().trim();
            final String passWord = edPassWord.getText().toString().trim();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference users = database.getReference("Users").child(userName);
            // Check user exist
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Don't exits
                    if (dataSnapshot.getValue() == null) {
                        Toast.makeText(LoginActivity.this, "Username don't exist", Toast.LENGTH_SHORT).show();
                    } else {
                        // Set data from firebase to Model
                        User user = new User();
                        user = dataSnapshot.getValue(User.class);

                        // Check password
                        if (user.getPassWord().equals(passWord)) {
                            Intent main = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(main);
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //endregion
    //region SIGN UP
    public void SignUp(View view) {
        try {
            Intent intent = new Intent(LoginActivity.this, SignUp.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
