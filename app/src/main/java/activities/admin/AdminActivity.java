package activities.admin;

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

import java.util.List;

import DataBase.DataBaseHelper;
import entities.Requester;
import inventory.Order;
//TODO: Reset order database, load order.csv, delete orders when deleting requester
public class AdminActivity extends AppCompatActivity {
    private Button btnAddRequester,btnDelete,btnViewOrders,btnClearAll,btnLogout, btnViewRequesters,btnResetDatabaseAdmin ;
    private TextView txtHello;
    private EditText edtTxtIDDelete ;

    private List<Requester> requestersToDelete;
    private DataBaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setOnClickListeners();
    }

    public void initViews(){
        btnAddRequester = findViewById(R.id.btnAddRequester);
        btnDelete = findViewById(R.id.btnDelete);

        btnViewOrders = findViewById(R.id.btnViewOrders);
        btnClearAll = findViewById(R.id.btnClearAll);
        btnLogout = findViewById(R.id.btnLogout);
        txtHello = findViewById(R.id.txtHello);
        edtTxtIDDelete = findViewById(R.id.edtTxtIDDelete);
        btnViewRequesters = findViewById(R.id.btnViewRequesters);
        btnResetDatabaseAdmin = findViewById(R.id.btnResetDatabaseAdmin);

        dbHelper = DataBaseHelper.getInstance(this);

    }
    public void setOnClickListeners(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnAddRequester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, CreateRequesterActivity.class);
                startActivity(intent);
            }
        });
        btnViewRequesters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ViewRequestersActivity.class);
                startActivity(intent);
            }
        });
        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear all the requesters from the Database
                requestersToDelete = MainActivity.dbHelper.getAllRequesters();
                if( dbHelper.clearAllRequesters() || MainActivity.userManager.removeRequesters(requestersToDelete)){
                    Toast.makeText(AdminActivity.this, "Requesters cleared from Database successfully!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AdminActivity.this, "Failed to Delete Requesters from Database", Toast.LENGTH_SHORT).show();
                }
                if(dbHelper.clearInventory()){
                    Toast.makeText(AdminActivity.this, "Inventory cleared from Database successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminActivity.this, "Failed to Delete Inventory from Database", Toast.LENGTH_SHORT).show();
                }
                if(dbHelper.clearOrders()){
                    Order.resetOrderNumber();
                    Toast.makeText(AdminActivity.this, "Orders cleared from Database successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminActivity.this, "Failed to Delete Orders from Database", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnResetDatabaseAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ResetDatabaseActivity.class);
                startActivity(intent);
            }
        });
        btnViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ViewOrders.class);
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requesterId = edtTxtIDDelete.getText().toString().trim();
                if(dbHelper.deleteRequesterByEmail(requesterId)){
                    Toast.makeText(AdminActivity.this, requesterId+" successfully deleted!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminActivity.this, "Invalid requester email!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}