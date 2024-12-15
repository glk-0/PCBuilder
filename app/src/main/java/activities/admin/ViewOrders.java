package activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groupe_39.R;

import java.util.List;

import DataBase.DataBaseHelper;
import inventory.Order;

public class ViewOrders extends AppCompatActivity {
    private ListView lvViewOrders;
    private Button btnCancelViewOrders;
    private DataBaseHelper dbHelper;
    private List<Order> allOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        checkNullElement();
        populateLv();
        setOnclickListeners();
    }
    public void initViews(){
        lvViewOrders = findViewById(R.id.lvViewOrders);
        btnCancelViewOrders = findViewById(R.id.btnCancelViewOrders);

        dbHelper = DataBaseHelper.getInstance(this);
        allOrders = dbHelper.getAllOrders();
    }
    public void checkNullElement(){
        // Check if dbHelper is null or the database is empty
        if (dbHelper == null || dbHelper.getAllOrders().isEmpty()) {
            Toast.makeText(this, "No orders available in the database.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }
    public void populateLv() {
        // Create an ArrayAdapter using the allOrders list
        ArrayAdapter<Order> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,  // Using a built-in layout for simplicity
                allOrders
        );

        // Set the adapter for the ListView
        lvViewOrders.setAdapter(adapter);
    }

    public void setOnclickListeners(){
        btnCancelViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewOrders.this,AdminActivity.class);
                startActivity(intent);
            }
        });
    }
}