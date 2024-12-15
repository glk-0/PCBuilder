package activities.Requester;

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
import inventory.Order;

public class RequesterActivity extends AppCompatActivity {
    private Button btnStartNewOrder, btnDeleteOrder, btnLogout, btnViewOrder;
    private TextView txtHello;
    private EditText edtTxtDeleteOrder;
    private String requesterId;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_requester);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //getting the instance of requester from the MainActivity

        Intent intent = getIntent();
        requesterId = intent.getStringExtra("requesterId");
        initViews();
        setOnClickListeners();
    }
    public void initViews() {
        btnStartNewOrder = findViewById(R.id.btnStartNewOrder);
        btnDeleteOrder = findViewById(R.id.btnRejectOrder);
        btnLogout = findViewById(R.id.btnLogout);
        btnViewOrder = findViewById(R.id.btnViewOrder);
        txtHello = findViewById(R.id.txtHello);
        edtTxtDeleteOrder = findViewById(R.id.edtTxtDeleteOrder);

        dbHelper = DataBaseHelper.getInstance(this);
    }

    public void setOnClickListeners() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequesterActivity.this, MainActivity.class);
                intent.putExtra("requesterId", requesterId);
                startActivity(intent);
            }
        });
        btnStartNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequesterActivity.this, NewOrder2.class);
                intent.putExtra("requesterId", requesterId);
                startActivity(intent);
            }
        });
        btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequesterActivity.this, ViewOrdersRequester.class);
                intent.putExtra("requesterId", requesterId);
                startActivity(intent);

            }
        });
        btnDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int orderNumber = Integer.parseInt(edtTxtDeleteOrder.getText().toString());
                    Order orderToDelete = dbHelper.getOrderByOrderNumber(orderNumber);
                    if(orderToDelete.getRequesterId()!= requesterId){
                        Toast.makeText(RequesterActivity.this, "this order was placed by another requester!", Toast.LENGTH_SHORT).show();
                    }
                    else if(dbHelper.deleteOrderByOrderNumber(orderNumber)){
                        Toast.makeText(RequesterActivity.this, "Order deleted Successfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RequesterActivity.this, "Invalid order number. Failed to delete order", Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException e) {
                    // Handle invalid input (not an integer or contains decimals)
                    Toast.makeText(RequesterActivity.this, "Invalid input. Please enter a valid whole number!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}