package activities.storeKeeper;

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

import com.example.groupe_39.R;

import DataBase.DataBaseHelper;
import inventory.Component;

public class CreateComponentActivity extends AppCompatActivity {
    private EditText edtTxtType, edtTxtSubType, edtTxtTitle, edtTxtQuantity, edtTxtComment;
    private Button btnIncrement, btnDecrement, btnCreate, btnCancel2;

    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_component);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        dbHelper = DataBaseHelper.getInstance(this); // Get singleton instance of database helper
        setOnClickListeners();
    }

    public void initViews() {
        edtTxtType = findViewById(R.id.edtTxtType);
        edtTxtSubType = findViewById(R.id.edtTxtSubType);
        edtTxtTitle = findViewById(R.id.edtTxtTitle);
        edtTxtQuantity = findViewById(R.id.edtTxtQuantity);
        edtTxtComment = findViewById(R.id.edtTxtComment);

        btnIncrement = findViewById(R.id.btnIncrement);
        btnDecrement = findViewById(R.id.btnDecrement);
        btnCreate = findViewById(R.id.btnSave);
        btnCancel2 = findViewById(R.id.btnCancel2);

        //Set the quntity to 0
        edtTxtQuantity.setText("0");
    }

    public void setOnClickListeners() {
        btnIncrement.setOnClickListener(v -> {
            String quantity = edtTxtQuantity.getText().toString();
            edtTxtQuantity.setText(Integer.toString(Integer.parseInt(quantity) + 1));
        });

        btnDecrement.setOnClickListener(v -> {
            String quantity = edtTxtQuantity.getText().toString();
            int qty = Integer.parseInt(quantity);
            if (qty > 0) {
                edtTxtQuantity.setText(Integer.toString(qty - 1));
            } else {
                edtTxtQuantity.setText("0");
            }
        });
        btnCancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateComponentActivity.this, StoreKeeperActivity.class);
                startActivity(intent);
            }
        });

        btnCreate.setOnClickListener(v -> {
            // Validate required fields
            String type = edtTxtType.getText().toString().trim();
            String subType = edtTxtSubType.getText().toString().trim();
            String title = edtTxtTitle.getText().toString().trim();
            String quantityStr = edtTxtQuantity.getText().toString().trim();
            String comment = edtTxtComment.getText().toString().trim();

            if (type.isEmpty() || subType.isEmpty() || title.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(CreateComponentActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert quantity to an integer
            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
            } catch (NumberFormatException e) {
                Toast.makeText(CreateComponentActivity.this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if the title is unique
            if (!isTitleUnique(title)) {
                Toast.makeText(CreateComponentActivity.this, "Title must be unique", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save the component to the database
            saveComponentToDatabase(type, subType, title, quantity, comment);

            // Provide feedback to the user
            Toast.makeText(CreateComponentActivity.this, "Component created successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CreateComponentActivity.this, StoreKeeperActivity.class);
            startActivity(intent);
        });
    }

    // Check if title is unique
    public boolean isTitleUnique(String title) {
        return dbHelper.isComponentTitleUnique(title);
    }

    // Save component to the database
    public void saveComponentToDatabase(String type, String subType, String title, int quantity, String comment) {
        Component component = new Component(type, subType, title, quantity, comment);
        dbHelper.addComponent(component);
    }
}
