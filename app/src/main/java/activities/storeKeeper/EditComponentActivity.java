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

public class EditComponentActivity extends AppCompatActivity {
    private EditText edtTxtType, edtTxtSubType, edtTxtTitle, edtTxtQuantity, edtTxtComment;
    private Button btnIncrement, btnDecrement, btnSave, btnCancel2;

    private DataBaseHelper dbHelper;

    private Component component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_component);

        // Get the Intent that started this activity
        Intent intent = getIntent();
        // Retrieve the component object from the intent
        component = (Component) intent.getSerializableExtra("component");

        if (component != null) {
            // Use the component data to populate your UI
            populateUIWithComponentData(component);
        }
        setOnClickListeners();
    }

    private void populateUIWithComponentData(Component component) {
         edtTxtType = findViewById(R.id.edtTxtType);
         edtTxtSubType = findViewById(R.id.edtTxtSubType);
         edtTxtTitle = findViewById(R.id.edtTxtTitle);
         edtTxtQuantity = findViewById(R.id.edtTxtQuantity);
         edtTxtComment = findViewById(R.id.edtTxtComment);
        //initializing other views
        btnIncrement = findViewById(R.id.btnIncrement);
        btnDecrement = findViewById(R.id.btnDecrement);
        btnSave = findViewById(R.id.btnSave);
        btnCancel2 = findViewById(R.id.btnCancel2);
        dbHelper = DataBaseHelper.getInstance(this);



        edtTxtType.setText(component.getType());
        edtTxtSubType.setText(component.getSubType());
        edtTxtTitle.setText(component.getTitle());
        edtTxtQuantity.setText(String.valueOf(component.getQuantity()));
        edtTxtComment.setText(component.getComment());
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
                Intent intent = new Intent(EditComponentActivity.this, StoreKeeperActivity.class);
                startActivity(intent);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update the quantity and comment in the database
                int newQuantity = Integer.parseInt(edtTxtQuantity.getText().toString());
                String title = edtTxtTitle.getText().toString();
                String newComment= edtTxtComment.getText().toString();
                if(dbHelper.updateComponentDetails(title, newQuantity,newComment)){
                    Toast.makeText(EditComponentActivity.this, "Changes Saved Successfully!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditComponentActivity.this, "Failed to Save Changes!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
