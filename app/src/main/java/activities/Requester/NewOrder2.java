package activities.Requester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import inventory.Component;
import inventory.Order;

public class NewOrder2 extends AppCompatActivity {
    private Button btnShowOptions,btnSaveSelections,btnCreateOrder1,btnCancel6;
    private Spinner spComponentSelect;
    private RelativeLayout checkboxLayout;

    private String requesterId;
    private DataBaseHelper dbHelper;
    private Order order;



    //components by type list
    List<List<Component>> allComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_order2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //getting the instance of requester from the RequesterActivity
        Intent intent = getIntent();
        requesterId = intent.getStringExtra("requesterId");

        initViews();
        setOnClickListeners();
        populateSpinners();
    }
    public void initViews() {
        btnSaveSelections = findViewById(R.id.btnSaveSelections);
        btnCreateOrder1 = findViewById(R.id.btnCreateOrder1);  // Initialize Create Order button
        btnCancel6 = findViewById(R.id.btnCancel6);  // Initialize Cancel button
        btnShowOptions = findViewById(R.id.btnShowOptions);
        spComponentSelect = findViewById(R.id.spComponentSelect);
        checkboxLayout = findViewById(R.id.checkboxLayout);

        dbHelper = DataBaseHelper.getInstance(this);
        allComponents = dbHelper.getComponentsByType();

        //Create new Order to be edited later on
        order = new Order(requesterId,null,null, null,null,null,
                null,null,null,null,null,null,null,null,null);
    }
    public void populateSpinners() {
        String[] subTypes = {"PC case", "Motherboard", "RAM", "Storage", "Monitor", "Input Device", "Web Browser", "Office", "IDE"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subTypes);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spComponentSelect.setAdapter(adapter);
    }
    public void setOnClickListeners(){
        btnShowOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the previous checkboxes
                checkboxLayout.removeAllViews();

                // Find the corresponding components from the allComponents list
                List<Component> selectedComponents = null;

                // Loop through the allComponents to find the correct list of components
                String[] subTypes = {"PC case", "Motherboard", "RAM", "Storage", "Monitor", "Input Device", "Web Browser", "Office", "IDE"};
                for (int i = 0; i < subTypes.length; i++) {
                    if (subTypes[i].equals(spComponentSelect.getSelectedItem().toString())) {
                        selectedComponents = allComponents.get(i);
                        break;
                    }
                }

                // Create checkboxes with quantity input and add/remove buttons for each component
                if (selectedComponents != null) {
                    int increment = 0;
                    int marginTop = 64; // Margin between checkboxes

                    for (Component component : selectedComponents) {
                        // Create a parent layout to hold checkbox, quantity, and buttons
                        LinearLayout itemLayout = new LinearLayout(NewOrder2.this);
                        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);

                        // Set margins (left, top, right, bottom)
                        layoutParams.setMargins(64, marginTop * increment, 0, 0); // Adjust the margins as needed

                        // Apply layoutParams to itemLayout
                        itemLayout.setLayoutParams(layoutParams);
                        // Create the CheckBox
                        CheckBox checkBox = new CheckBox(NewOrder2.this);
                        checkBox.setText(component.getTitle());  // Set the title of the component as the label for the checkbox
                        checkBox.setTag(component);  // Optionally, store the component in the checkbox tag
                        itemLayout.addView(checkBox);

                        // Create TextView for quantity
                        TextView qtyTextView = new TextView(NewOrder2.this);
                        qtyTextView.setText("1");  // Default quantity is 1
                        qtyTextView.setPadding(16, 0, 16, 0);
                        qtyTextView.setWidth(50);
                        itemLayout.addView(qtyTextView);

                        // Create ImageView for "Add" button
                        ImageView addButton = new ImageView(NewOrder2.this);
                        addButton.setImageResource(R.drawable.ic_plus_foreground);  // Add icon
                        addButton.setLayoutParams(new LinearLayout.LayoutParams(64, 64));
                        itemLayout.addView(addButton);

                        // Create ImageView for "Remove" button
                        ImageView removeButton = new ImageView(NewOrder2.this);
                        removeButton.setImageResource(R.drawable.ic_minus_foreground);  // Remove icon
                        removeButton.setLayoutParams(new LinearLayout.LayoutParams(64, 64));
                        itemLayout.addView(removeButton);

                        // Add the item layout to the checkbox layout
                        checkboxLayout.addView(itemLayout);

                        // Handling Add Button Click
                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int currentQty = Integer.parseInt(qtyTextView.getText().toString());
                                if(validateQty(spComponentSelect.getSelectedItem().toString(),currentQty,true)){
                                    qtyTextView.setText(String.valueOf(currentQty + 1));
                                }else{
                                    Toast.makeText(NewOrder2.this, "Invalid quantity for this component", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        // Handling Remove Button Click
                        removeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int currentQty = Integer.parseInt(qtyTextView.getText().toString());
                                if (validateQty(spComponentSelect.getSelectedItem().toString(),currentQty,false) ) {
                                    qtyTextView.setText(String.valueOf(currentQty - 1));
                                }else{
                                    Toast.makeText(NewOrder2.this, "Invalid quantity for this component", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        increment++;
                    }
                }
            }
        });
        btnCancel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewOrder2.this,RequesterActivity.class );
                intent.putExtra("requesterId", requesterId);
                startActivity(intent);
            }
        });
        btnSaveSelections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateSelections()){
                    Toast.makeText(NewOrder2.this, "Selection Saved!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnCreateOrder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateOrderFields()){
                    if(dbHelper.addOrder(order)){
                        Toast.makeText(NewOrder2.this, "Order Successfully created!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(NewOrder2.this, "Failed to create the Order", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
    public boolean validateQty(String subType, int qty, boolean isAdding) {
        switch (subType) {
            case "PC case":
                // Exactly 1 PC case
                return isAdding ? qty < 1 : qty > 0;

            case "Motherboard":
                // Exactly 1 motherboard
                return isAdding ? qty < 1 : qty > 0;

            case "RAM":
                // 1 to 4 RAM sticks allowed
                return isAdding ? qty < 4 : qty > 1;

            case "Storage":
                // 1 to 2 hard drives allowed
                return isAdding ? qty < 2 : qty > 1;

            case "Monitor":
                // 1 to 3 monitors allowed
                return isAdding ? qty < 3 : qty > 1;

            case "Input Device":
                // Exactly 1 input device (keyboard + mouse combo)
                return isAdding ? qty < 1 : qty > 0;

            case "Web Browser":
                // Exactly 1 web browser
                return isAdding ? qty < 1 : qty > 0;

            case "Office":
                // 0 or 1 office suite allowed
                return isAdding ? qty < 1 : qty >= 1;

            case "IDE":
                // 0 or 3 IDE tools allowed (all different)
                return isAdding ? qty < 1 : qty > 0;

            default:
                // For any other component type, return false (invalid)
                return false;
        }

    }
    public boolean validateSelections() {
        int checkedCount = 0;
        int totalQty = 0;

        // Loop through all child views in checkboxLayout
        for (int i = 0; i < checkboxLayout.getChildCount(); i++) {
            View itemView = checkboxLayout.getChildAt(i);

            // Ensure the itemView is a LinearLayout (or has the expected structure)
            if (itemView instanceof LinearLayout) {
                LinearLayout itemLayout = (LinearLayout) itemView;

                // Check if the first child is a CheckBox
                if (itemLayout.getChildCount() > 0 && itemLayout.getChildAt(0) instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) itemLayout.getChildAt(0);

                    // If the CheckBox is checked
                    if (checkBox.isChecked()) {
                        checkedCount++;

                        // Retrieve and sum the quantity
                        if (itemLayout.getChildCount() > 1 && itemLayout.getChildAt(1) instanceof TextView) {
                            TextView qtyTextView = (TextView) itemLayout.getChildAt(1);
                            try {
                                int qty = Integer.parseInt(qtyTextView.getText().toString());
                                totalQty += qty;
                            } catch (NumberFormatException e) {
                                Toast.makeText(this, "Invalid quantity for " + checkBox.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        }

        // Display the results for validation
        switch (spComponentSelect.getSelectedItem().toString()) {
            case "PC case":
                if (checkedCount > 1) {
                    Toast.makeText(this, "You cannot select more than one PC case.", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(checkedCount==0){
                    Toast.makeText(this, "You need to make a selection", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                List<List<String>> selectedItems = getSelectedItems();
                order.setCaseType(selectedItems.get(0).get(0));
                }
                break;

            case "Motherboard":
                if (checkedCount > 1) {
                    Toast.makeText(this, "You cannot select more than one motherboard.", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(checkedCount==0){
                    Toast.makeText(this, "You need to make a selection", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                List<List<String>> selectedItems1 = getSelectedItems();
                order.setMotherboard(selectedItems1.get(0).get(0));
                }
                break;

            case "RAM":
                if (checkedCount > 1) {
                    Toast.makeText(this, "You cannot select more than one type of RAM sticks.", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(checkedCount==0){
                    Toast.makeText(this, "You need to make a selection", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                List<List<String>> selectedItems2 = getSelectedItems();
                order.setMemory(selectedItems2.get(0).get(0));
                order.setQtyRAM(selectedItems2.get(1).get(0));
                }
                break;

            case "Storage":
                if (checkedCount > 2) {
                    Toast.makeText(this, "You cannot select more than two storage devices.", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(totalQty>2){
                    Toast.makeText(this, "Total Qty cannot be more than 2", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(checkedCount==0){
                    Toast.makeText(this, "You need to make a selection", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                List<List<String>> selectedItems3 = getSelectedItems();
                order.setStorage(selectedItems3.get(0));
                order.setQtyStorage(selectedItems3.get(1));
                }

                break;

            case "Monitor":
                if (checkedCount > 1) {
                    Toast.makeText(this, "You cannot select more than one type of monitor.", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(checkedCount==0){
                    Toast.makeText(this, "You need to make a selection", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                List<List<String>> selectedItems4 = getSelectedItems();
                order.setDisplay(selectedItems4.get(0).get(0));
                order.setQtyMonitor(selectedItems4.get(1).get(0));
                }
                break;

            case "Input Device":
                if (checkedCount > 1) {
                    Toast.makeText(this, "You can only select one input device (keyboard + mouse combo).", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else if(checkedCount==0){
                    Toast.makeText(this, "You need to make a selection", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    List<List<String>> selectedItems5 = getSelectedItems();
                    order.setInputPeripherals(selectedItems5.get(0).get(0));
                    }
                break;

            case "Web Browser":
                if (checkedCount > 1) {
                    Toast.makeText(this, "You can only select one web browser.", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(checkedCount==0){
                    Toast.makeText(this, "You need to make a selection", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else{
                    List<List<String>> selectedItems6 = getSelectedItems();
                    order.setWebBrowser(selectedItems6.get(0).get(0));
                }
                break;

            case "Office":
                if (checkedCount > 1) {
                    Toast.makeText(this, "You can only select up to one office suite.", Toast.LENGTH_SHORT).show();
                    return false;
                }else if( checkedCount==0){
                    return true;

                } else{
                    List<List<String>> selectedItems7 = getSelectedItems();
                    order.setOfficeSuite(selectedItems7.get(0).get(0));
                    order.setQtyOffice(selectedItems7.get(1).get(0));
                    }
                break;

            case "IDE": //TODO: allow selection of 0 IDE tools
                if (checkedCount != 0 && checkedCount != 3) {
                    Toast.makeText(this, "You must select either 0 or 3 different IDE tools.", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    List<List<String>> selectedItems8 = getSelectedItems();
                    order.setDevelopmentTools(selectedItems8.get(0));
                    order.setQtyIDE(selectedItems8.get(1));

                }
                break;



            default:
                Toast.makeText(this, "Invalid component selection.", Toast.LENGTH_SHORT).show();
                return false;
        }
        return true;



    }
    public List<List<String>> getSelectedItems(){
        List<String> checkedElements = new ArrayList<>();
        List<String> qtys = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        // Loop through all child views in checkboxLayout
        for (int i = 0; i < checkboxLayout.getChildCount(); i++) {
            View itemView = checkboxLayout.getChildAt(i);

            // Ensure the itemView is a LinearLayout (or has the expected structure)
            if (itemView instanceof LinearLayout) {
                LinearLayout itemLayout = (LinearLayout) itemView;

                // Check if the first child is a CheckBox
                if (itemLayout.getChildCount() > 0 && itemLayout.getChildAt(0) instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) itemLayout.getChildAt(0);

                    // If the CheckBox is checked
                    if (checkBox.isChecked()) {
                        String checkBoxText = checkBox.getText().toString();
                        checkedElements.add(checkBoxText);

                        // Retrieve and sum the quantity
                        if (itemLayout.getChildCount() > 1 && itemLayout.getChildAt(1) instanceof TextView) {
                            TextView qtyTextView = (TextView) itemLayout.getChildAt(1);
                            qtys.add(qtyTextView.getText().toString());
                        }
                    }
                }
            }
        }
    result.add(checkedElements);
        result.add(qtys);
    return result;

    }
    public boolean validateOrderFields() {
        // Check if essential fields are null or empty for validation
        if (order.getCaseType() == null || order.getCaseType().isEmpty()) {
            Toast.makeText(this, "PC Case is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getMotherboard() == null || order.getMotherboard().isEmpty()) {
            Toast.makeText(this, "Motherboard is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getMemory() == null || order.getMemory().isEmpty()) {
            Toast.makeText(this, "RAM is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getQtyRAM() == null || order.getQtyRAM().isEmpty()) {
            Toast.makeText(this, "RAM quantity is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getStorage() == null || order.getStorage().isEmpty()) {
            Toast.makeText(this, "Storage is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getQtyStorage() == null || order.getQtyStorage().isEmpty()) {
            Toast.makeText(this, "Storage quantity is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getDisplay() == null || order.getDisplay().isEmpty()) {
            Toast.makeText(this, "Monitor is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getQtyMonitor() == null || order.getQtyMonitor().isEmpty()) {
            Toast.makeText(this, "Monitor quantity is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getInputPeripherals() == null || order.getInputPeripherals().isEmpty()) {
            Toast.makeText(this, "Input device is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getWebBrowser() == null || order.getWebBrowser().isEmpty()) {
            Toast.makeText(this, "Web Browser is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getOfficeSuite() == null || order.getOfficeSuite().isEmpty()) {
            Toast.makeText(this, "Office Suite is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getDevelopmentTools() == null || order.getDevelopmentTools().isEmpty()) {
            Toast.makeText(this, "IDE tools are missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (order.getQtyIDE() == null || order.getQtyIDE().isEmpty()) {
            Toast.makeText(this, "IDE quantity is missing.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // If all fields are validated
        Toast.makeText(this, "Order fields are valid!", Toast.LENGTH_SHORT).show();
        return true;
    }

}







