package activities.admin;

import android.content.Intent;
import android.content.res.AssetManager;
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

import java.io.IOException;

import DataBase.DataBaseHelper;
import Utils.CSVImporter;
import entities.Requester;
import inventory.Order;

public class ResetDatabaseActivity extends AppCompatActivity {
    private Button btnResetDatabase,btnResetStock,btnCancel3;
    private EditText edtTxtRequesterCSV,edtTxtComponentCSVDb,edtTxtComponentCSVStock, edtTxtOrdersCSV;

    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_database);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setOnClickListeners();
    }
    public void initViews() {
        btnResetDatabase = findViewById(R.id.btnResetDatabase);
        btnResetStock = findViewById(R.id.btnResetStock);
        btnCancel3 = findViewById(R.id.btnCancel3);

        edtTxtRequesterCSV = findViewById(R.id.edtTxtRequesterCSV);
        edtTxtComponentCSVDb = findViewById(R.id.edtTxtComponentCSVDb);
        edtTxtComponentCSVStock = findViewById(R.id.edtTxtComponentCSVStock);
        edtTxtOrdersCSV = findViewById(R.id.edtTxtOrdersCSV);

        dbHelper = DataBaseHelper.getInstance(this);
    }
    public void setOnClickListeners(){
        btnResetDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get entered file names 
                String requestersCSV = edtTxtRequesterCSV.getText().toString();
                String componentsCSV = edtTxtComponentCSVDb.getText().toString();
                String ordersCSV = edtTxtOrdersCSV.getText().toString();
                if(requestersCSV.trim().isEmpty() || componentsCSV.trim().isEmpty() || ordersCSV.trim().isEmpty()){
                    Toast.makeText(ResetDatabaseActivity.this, "One or more file names missing!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check if the files exist in assets
                if (!fileExistsInAssets(requestersCSV) || !fileExistsInAssets(componentsCSV)|| !fileExistsInAssets(ordersCSV)) {
                    Toast.makeText(ResetDatabaseActivity.this, "One or more file names do not exist in assets!", Toast.LENGTH_SHORT).show();
                    return; // Exit if any file does not exist
                }
                //clear database
                dbHelper.clearInventory();
                dbHelper.clearAllRequesters();
                dbHelper.clearOrders();
                Order.resetOrderNumber();
                // Load new data
                try {
                    Toast.makeText(ResetDatabaseActivity.this, "Database Reset Was Successful!", Toast.LENGTH_SHORT).show();
                    loadData(requestersCSV, componentsCSV,ordersCSV);
                } catch (IOException e) {
                    Toast.makeText(ResetDatabaseActivity.this, "Failed to reset database", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
        btnResetStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String componentsCSV = edtTxtComponentCSVStock.getText().toString();
                //clear all orders to be implemented
                //clear inventory
                dbHelper.clearInventory();
                try {
                    loadInventory(componentsCSV);
                    Toast.makeText(ResetDatabaseActivity.this, "Inventory Reset Was Successful!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(ResetDatabaseActivity.this, "Inventory Reset Was Successful!", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }

            }
        });
        btnCancel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetDatabaseActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }
    private void loadInventory(String componentsCSV) throws IOException {
        CSVImporter.importComponents(this,componentsCSV);
    }
    private void loadData(String requestersFileName, String componentsFileName,String ordersFileName) throws IOException {
        CSVImporter.importRequesters(this, requestersFileName);
        CSVImporter.importComponents(this, componentsFileName);
        CSVImporter.importOrders(this, ordersFileName);
    }

    private boolean fileExistsInAssets(String fileName) {
        AssetManager assetManager = getAssets();
        try {
            String[] files = assetManager.list("");
            if (files != null) {
                for (String file : files) {
                    if (file.equals(fileName)) {
                        return true; // File exists
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // File does not exist
    }


}