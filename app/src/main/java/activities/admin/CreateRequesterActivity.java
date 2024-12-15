package activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groupe_39.MainActivity;
import com.example.groupe_39.R;

import java.util.ArrayList;
import java.util.List;

import entities.Requester;

public class CreateRequesterActivity extends AppCompatActivity {
    private EditText edtTxtFirstName,edtTxtLastName,edtTxtEmail,edtTxtPassword1;
    private Button btnAddRequester1,btnCancel;

    // List to store the requesters in memory until the database is created
    public static List<Requester> requesterList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_requester);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setOnClickListeners();
    }
    public void initViews(){
        edtTxtFirstName = findViewById(R.id.edtTxtFirstName);
        edtTxtLastName = findViewById(R.id.edtTxtLastName);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtPassword1 = findViewById(R.id.edtTxtPassword1);
        btnAddRequester1 = findViewById(R.id.btnAddRequester1);
        btnCancel = findViewById(R.id.btnCancel);

    }
    public void setOnClickListeners(){
        btnAddRequester1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRequester();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRequesterActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

    }
    private void addRequester() {
        String firstName = edtTxtFirstName.getText().toString().trim();
        String lastName = edtTxtLastName.getText().toString().trim();
        String email = edtTxtEmail.getText().toString().trim();
        String password = edtTxtPassword1.getText().toString().trim();

        // Validate input
        if (email.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email is unique
        for (Requester requester : requesterList) {
            if (requester.getEmail().equalsIgnoreCase(email)) {
                Toast.makeText(this, "Email must be unique", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Create the requester
        Requester requester = new Requester(
                requesterList.size() + 1,
                firstName,
                lastName,
                email,
                password,
                "Requester"
        );

        requesterList.add(requester);
        Toast.makeText(this, requester.getEmail() +" added successfully", Toast.LENGTH_SHORT).show();


        //update the admin's requester list and add it to the userManager
        MainActivity.userManager.addUser(requester);
        MainActivity.admin.setRequesterList(requesterList);
        if(MainActivity.dbHelper.addRequester(requester)){
            Toast.makeText(this, "Requester added to Database", Toast.LENGTH_SHORT).show();
        }


    }


    public static List<Requester> getRequesterList() {
        return requesterList;
    }
}