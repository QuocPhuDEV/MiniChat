package phuhq.it.minichat.Signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import phuhq.it.minichat.Model.User;
import phuhq.it.minichat.R;

public class SignUp extends AppCompatActivity {
    //region AVAILABLE
    public EditText edFistName, edLastName, edUserName, edPassword;
    //endregion

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mainLoad();
    }
    //endregion

    //region ADD CONTROLS AND MAIN LOAD
    public void addControls() {
        edFistName = findViewById(R.id.edtFirstName);
        edLastName = findViewById(R.id.edtLastName);
        edUserName = findViewById(R.id.edtUsername);
        edPassword = findViewById(R.id.edtPassword);
    }

    public void mainLoad() {
        addControls();
    }
    //endregion

    //region Sign Up
    public void signUp(View view) {
        final String firstName = edFistName.getText().toString().trim();
        final String lastName = edLastName.getText().toString().trim();
        final String userName = edUserName.getText().toString().trim();
        final String passWord = edPassword.getText().toString().trim();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference users = database.getReference("Users").child(userName);
        // Check user exist
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Don't exist
                if (dataSnapshot.getValue() == null) {
                    User user = new User();
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setPassWord(passWord);

                    // Add user
                    users.setValue(user, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            Toast.makeText(SignUp.this, "Add user successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(SignUp.this, "User is exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //endregion
}
