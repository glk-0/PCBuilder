package com.example.groupe_39;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import DataBase.DataBaseHelper;
import Utils.CSVImporter;
import activities.admin.AdminActivity;
import activities.Assembler.AssemblerActivity;
import activities.Requester.RequesterActivity;
import activities.storeKeeper.StoreKeeperActivity;
import entities.Administrator;
import entities.Assembler;
import entities.Requester;
import entities.StoreKeeper;
import Utils.UserManager;
import entities.User;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView imgUOttawaLogo;
    private Button btnLogin;
    private TextView txtTitle;
    private EditText edtTxtID,edtTxtPassword;

    public static UserManager userManager;
    public static Administrator admin;
    public static DataBaseHelper dbHelper;
    private static MainActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        createDefaultEntities();
        loadUsersToMemory();

        // Load CSV data only if it’s the first launch
        loadCsvDataIfNeeded();
        setOnClickListeners();

    }
    //Initialzing the Views of the main activity by their ID
    public void initViews(){
        txtTitle = findViewById(R.id.txtTitle);
        imgUOttawaLogo= findViewById(R.id.imgUOLogo);
        btnLogin= findViewById(R.id.btnLogin);
        edtTxtID= findViewById(R.id.edtTxtID);
        edtTxtPassword= findViewById(R.id.edtTxtPassword);
        userManager = userManager.getInstance();
        dbHelper = DataBaseHelper.getInstance(this);
    }
    public void loadCsvDataIfNeeded(){
        SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean("isFirstLaunch", true);

        if (isFirstLaunch) {
            loadFilesToDatabase();

            // Mark as not first launch
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstLaunch", false);
            editor.apply();
        }
    }
    //Initializing the default admin, StoreKeeper,and assembler and adding them to userManager for login management
    public void createDefaultEntities(){
        // Retrieve values from strings.xml
        String adminEmail = getString(R.string.admin_email);
        String adminPassword = getString(R.string.admin_password);
        String storeKeeperEmail = getString(R.string.storeKeeper_email);
        String storeKeeperPassword = getString(R.string.storeKeeper_password);
        String assemblerEmail = getString(R.string.assembler_email);
        String assemblerPassword = getString(R.string.assembler_password);
        // Create the default users
        admin = new Administrator( 1," ", " ", adminEmail, adminPassword, "Administrator");
        StoreKeeper storeKeeper = new StoreKeeper( 2," ", " ", storeKeeperEmail, storeKeeperPassword, "StoreKeeper");
        Assembler assembler = new Assembler( 3," ", " ", assemblerEmail, assemblerPassword, "Assembler");

        //Add users the database
        dbHelper.addUser(admin);
        dbHelper.addUser(storeKeeper);
        dbHelper.addUser(assembler);
    }
    public void loadUsersToMemory(){
        List<User> dbUser = dbHelper.getAllUsers();
        List<Requester> dbRequester = dbHelper.getAllRequesters();
        for(User user : dbUser){
            userManager.addUser(user);
        }
        for(Requester requester : dbRequester){
            userManager.addUser(requester);
        }

    }
    public void loadFilesToDatabase(){
        //Importing default requesters and components
        try {
            CSVImporter.importRequesters(this,"requesters.csv");
            CSVImporter.importComponents(this,"components.csv");
            CSVImporter.importOrders(this,"orders.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Setting the logic for the buttons when clicked
    public void setOnClickListeners(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail = edtTxtID.getText().toString();
                String inputPassword = edtTxtPassword.getText().toString();

                User user = userManager.authenticate(inputEmail, inputPassword);
                if (user != null) {
                    // Redirect based on user type
                    if (user instanceof Administrator) {
                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(intent);
                    } else if (user instanceof StoreKeeper) {
                        Intent intent = new Intent(MainActivity.this, StoreKeeperActivity.class);
                        startActivity(intent);
                    } else if (user instanceof Assembler) {
                        Intent intent = new Intent(MainActivity.this, AssemblerActivity.class);
                        startActivity(intent);
                    }
                    else if (user instanceof Requester) {
                        Intent intent = new Intent(MainActivity.this, RequesterActivity.class);
                        intent.putExtra("requesterId", user.getEmail());
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    //.

}