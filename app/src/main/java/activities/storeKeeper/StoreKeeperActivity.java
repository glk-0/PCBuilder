package activities.storeKeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groupe_39.MainActivity;
import com.example.groupe_39.R;

import DataBase.DataBaseHelper;
import activities.admin.AdminActivity;
import inventory.Component;

public class StoreKeeperActivity extends AppCompatActivity {
    private Button btnAddElement, btnViewStock, btnLogout, btnEdit,btnDeleteComponent;
    private TextView txtHello;
    private EditText edtTxtTitleEdit, edtTxtTitleDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_store_keeper);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setOnClickListeners();
    }
    public void initViews() {
        btnAddElement = findViewById(R.id.btnAddElement);
        btnViewStock = findViewById(R.id.btnViewStock);
        btnLogout = findViewById(R.id.btnLogout);
        txtHello = findViewById(R.id.txtHello);
        edtTxtTitleEdit = findViewById(R.id.edtTxtTitleEdit);
        btnEdit = findViewById(R.id.btnEdit);
        edtTxtTitleDelete = findViewById(R.id.edtTxtTitleDelete);
        btnDeleteComponent = findViewById(R.id.btnDeleteComponent);
    }

    public void setOnClickListeners() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreKeeperActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnAddElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreKeeperActivity.this, CreateComponentActivity.class);
                startActivity(intent);
            }
        });
        btnViewStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreKeeperActivity.this, ViewInventoryActivity.class);
                startActivity(intent);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTxtTitleEdit.getText().toString().trim();

                if (title.isEmpty()) {
                    // Show an error if no title is entered
                    Toast.makeText(StoreKeeperActivity.this, "Please enter a title", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the singleton instance of the database helper
                DataBaseHelper dbHelper = DataBaseHelper.getInstance(StoreKeeperActivity.this);
                Component component = dbHelper.getComponentByTitle(title);

                if (component != null) {
                    // If the component is found, display its details (could be in a new activity or directly here)
                    Intent intent = new Intent(StoreKeeperActivity.this, EditComponentActivity.class);
                    // Pass the component details to the new activity
                    intent.putExtra("component", component);
                    startActivity(intent);
                } else {
                    // If no component is found, show a message
                    Toast.makeText(StoreKeeperActivity.this, "No component found with the given title", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDeleteComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleToDelete = edtTxtTitleDelete.getText().toString();
                DataBaseHelper dbHelper = DataBaseHelper.getInstance(StoreKeeperActivity.this);
                if(dbHelper.deleteComponentByTitle(titleToDelete)){
                    Toast.makeText(StoreKeeperActivity.this, titleToDelete+ " Deleted successfully!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(StoreKeeperActivity.this, "Failed to delete "+ titleToDelete, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }}