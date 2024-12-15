package activities.storeKeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groupe_39.MainActivity;
import com.example.groupe_39.R;

import java.util.List;

import DataBase.DataBaseHelper;
import inventory.Component;

public class ViewInventoryActivity extends AppCompatActivity {
    private ListView lvInventory;
    private Button btnBack;
    private List<Component> componentsList; // Change to use Component entity

    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_inventory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setListView();
        setOnClickListeners();

    }

    public void initViews() {
        lvInventory = findViewById(R.id.lvInventory);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = DataBaseHelper.getInstance(this);
    }

    public void setOnClickListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewInventoryActivity.this, StoreKeeperActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setListView() {
        componentsList = MainActivity.dbHelper.getAllComponents(); // Fetch all components from the database

        ArrayAdapter<Component> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, componentsList);

        // Set the adapter to the ListView
        lvInventory.setAdapter(adapter);
    }
}