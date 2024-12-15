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

import com.example.groupe_39.MainActivity;
import com.example.groupe_39.R;

import java.util.List;

import DataBase.DataBaseHelper;
import entities.Requester;

public class ViewRequestersActivity extends AppCompatActivity {
    private ListView lvRequesters;
    private Button btnBack;
    private List<Requester> requesterList;

    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_requesters);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        checkNullElement();
        setOnClickListeners();
        setListView();
    }
    public void initViews(){
        lvRequesters = findViewById(R.id.lvRequesters);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = DataBaseHelper.getInstance(this);
    }
    public void checkNullElement(){
        // Check if dbHelper is null or the database is empty
        if (dbHelper == null || dbHelper.getAllRequesters().isEmpty()) {
            Toast.makeText(this, "No requesters available in the database.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }
    public void setOnClickListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRequestersActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

    }
    public void setListView(){
        requesterList = dbHelper.getAllRequesters();

        ArrayAdapter<Requester> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, requesterList);

        // Set the adapter to the ListView
        lvRequesters.setAdapter(adapter);

    }
}