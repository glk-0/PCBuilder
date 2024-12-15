package activities.Assembler;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import java.util.ArrayList;
import java.util.List;

import DataBase.DataBaseHelper;
import inventory.Order;

public class ViewPendingOrdersActivity extends AppCompatActivity {
    private ListView lvPendingOrders;
    private Button btnCancel8;
    private DataBaseHelper dbHelper;
    private List<Order> orders, orders2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_pending_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        checkNullElement();
        setOnClickListeners();
        populateLv();
    }
    public void initViews(){
        lvPendingOrders = findViewById(R.id.lvPendingOrders);
        btnCancel8 = findViewById(R.id.btnCancel8);

        dbHelper = DataBaseHelper.getInstance(this);
        orders = dbHelper.getOrdersByStatus("waiting to be accepted");
        orders2 = dbHelper.getOrdersByStatus("Accepted, assembling in progress");
        for(Order o : orders2){
            orders.add(o);
        }


    }
    public void setOnClickListeners(){
        btnCancel8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPendingOrdersActivity.this, AssemblerActivity.class);
                startActivity(intent);
            }
        });
    }
    public void checkNullElement(){
        if (orders == null || orders.isEmpty()) {
            Toast.makeText(this, "No pending orders.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }
    public void populateLv(){


        // Create an ArrayAdapter using the orders list
        ArrayAdapter<Order> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,  // Using a built-in layout for simplicity
                orders
        );

        // Set the adapter for the ListView
        lvPendingOrders.setAdapter(adapter);

    }
}