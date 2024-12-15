package activities.Requester;

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

public class ViewOrdersRequester extends AppCompatActivity {
    private ListView lvViewOrdersRequester;
    private Button btnCancelViewOrdersRequester;
    private DataBaseHelper dbHelper;
    private List<Order> allRequesterOrders;
    private String requesterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_orders_requester);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        requesterId = intent.getStringExtra("requesterId");

        initViews();
        checkNullElement();
        setOnclickListeners();
        populateLv();

    }
    public void initViews(){
        lvViewOrdersRequester = findViewById(R.id.lvViewOrdersRequester);
        btnCancelViewOrdersRequester = findViewById(R.id.btnCancelViewOrdersRequester);

        dbHelper = DataBaseHelper.getInstance(this);
        // Check if requesterId is null
        if (requesterId == null) {
            Toast.makeText(this, "Requester ID is missing. Unable to fetch orders.", Toast.LENGTH_SHORT).show();
            finish(); // End the activity if requesterId is null
            return;
        }
        allRequesterOrders = dbHelper.getOrdersByRequesterID(requesterId);
    }
    public void checkNullElement(){
        // Check if dbHelper is null or the database is empty
        if (dbHelper == null || dbHelper.getAllOrders().isEmpty()) {
            Toast.makeText(this, "No Orders available in the database.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }
    public void populateLv() {
        if (allRequesterOrders == null || allRequesterOrders.isEmpty()) {
            Toast.makeText(this, "No orders found for this requester.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an ArrayAdapter using the allOrders list
        ArrayAdapter<Order> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,  // Using a built-in layout for simplicity
                allRequesterOrders
        );

        // Set the adapter for the ListView
        lvViewOrdersRequester.setAdapter(adapter);
    }


    public void setOnclickListeners(){
        btnCancelViewOrdersRequester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewOrdersRequester.this, RequesterActivity.class);
                intent.putExtra("requesterId", requesterId);
                startActivity(intent);
            }
        });
    }
}