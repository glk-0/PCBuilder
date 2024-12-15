package activities.Assembler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import inventory.Order;

public class AssemblerActivity extends AppCompatActivity {
    private Button btnPendingOrders,btnValidateOrder,btnRejectOrder,btnLogout,btnApproveOrder  ;
    private TextView txtHello;
    private EditText edtTxtIdReject, edtTxtIdValidate,edtTxtIdApproveOrder;
    private DataBaseHelper dbHelper;
    private Order order;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assembler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setOnClickListeners();
    }
    public void initViews() {
        btnPendingOrders = findViewById(R.id.btnPendingOrders);
        btnRejectOrder = findViewById(R.id.btnRejectOrder);
        btnLogout = findViewById(R.id.btnLogout);
        txtHello = findViewById(R.id.txtHello);
        edtTxtIdValidate = findViewById(R.id.edtTxtIdValidate);
        btnValidateOrder = findViewById(R.id.btnValidateOrder);
        edtTxtIdReject = findViewById(R.id.edtTxtIDDelete);
        edtTxtIdApproveOrder = findViewById(R.id.edtTxtIdApproveOrder);
        btnApproveOrder = findViewById(R.id.btnApproveOrder);

        dbHelper = DataBaseHelper.getInstance(this);
    }
    public void setOnClickListeners(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AssemblerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnPendingOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AssemblerActivity.this, ViewPendingOrdersActivity.class);
                startActivity(intent);
            }
        });
        btnRejectOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderNumber = edtTxtIdReject.getText().toString().trim();

                if (orderNumber.isEmpty()) {
                    Toast.makeText(AssemblerActivity.this, "Please enter a valid order number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Parse the order number to an integer
                    int parsedOrderNumber = Integer.parseInt(orderNumber);

                    // Fetch the order using the parsed number
                    Order order = dbHelper.getOrderByOrderNumber(parsedOrderNumber);

                    // Validate the order
                    if (order == null) {
                        Toast.makeText(AssemblerActivity.this, "Order number does not exist.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!validateOrder(order)) {
                        // Reject the order
                        order.setStatus("Rejected");
                        if (dbHelper.updateOrderStatusByOrderNumber(parsedOrderNumber, "Rejected")) {
                            Toast.makeText(AssemblerActivity.this, "Order status updated", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AssemblerActivity.this, "Order Rejected, not enough stock", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Cannot reject valid orders
                        Toast.makeText(AssemblerActivity.this, "Order is valid, sufficient stock, cannot reject it!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid input (e.g., non-integer or malformed data)
                    Toast.makeText(AssemblerActivity.this, "Invalid input. Please enter a valid whole number!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // Catch any other unexpected errors
                    Toast.makeText(AssemblerActivity.this, "An unexpected error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        });
        btnValidateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderNumberStr = edtTxtIdValidate.getText().toString().trim();

                if (orderNumberStr.isEmpty()) {
                    Toast.makeText(AssemblerActivity.this, "Please enter a valid order number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Parse the order number to an integer
                    int orderNumber = Integer.parseInt(orderNumberStr);

                    // Check if the order exists
                    Order order = dbHelper.getOrderByOrderNumber(orderNumber);
                    if (order != null) {
                        // Validate the order
                        if (validateOrder(order)) {
                            // Update the order status to "Accepted"
                            order.setStatus("Accepted, assembling in progress");
                            if (dbHelper.updateOrderStatusByOrderNumber(orderNumber, "Accepted, assembling in progress")) {
                                Toast.makeText(AssemblerActivity.this, "Order Validated!", Toast.LENGTH_SHORT).show();
                                Toast.makeText(AssemblerActivity.this, "Order status updated to 'Accepted, assembling in progress'", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AssemblerActivity.this, "Failed to update order status. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Invalid order due to insufficient stock or other reasons
                            Toast.makeText(AssemblerActivity.this, "Invalid Order!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Order not found
                        Toast.makeText(AssemblerActivity.this, "Invalid Order Number!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid input that cannot be parsed to an integer
                    Toast.makeText(AssemblerActivity.this, "Invalid input. Please enter a valid whole number!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // Catch any unexpected errors
                    Toast.makeText(AssemblerActivity.this, "An unexpected error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnApproveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderNumberStr = edtTxtIdApproveOrder.getText().toString().trim();

                if (orderNumberStr.isEmpty()) {
                    Toast.makeText(AssemblerActivity.this, "Please enter a valid order number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Parse the order number to an integer
                    int orderNumber = Integer.parseInt(orderNumberStr);

                    // Fetch the order from the database
                    Order order = dbHelper.getOrderByOrderNumber(orderNumber);

                    if (order == null) {
                        Toast.makeText(AssemblerActivity.this, "Order not found. Please check the order number.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Validate the order
                    if (!validateOrder(order)) {
                        Toast.makeText(AssemblerActivity.this, "Order is not valid, cannot approve it!", Toast.LENGTH_SHORT).show();
                    } else if ("Rejected".equals(order.getStatus())) {
                        Toast.makeText(AssemblerActivity.this, "Order is rejected, cannot approve it!", Toast.LENGTH_SHORT).show();
                    } else if ("Delivered".equals(order.getStatus())) {
                        Toast.makeText(AssemblerActivity.this, "Order is already delivered, cannot approve it again!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Approve the order and update its status
                        approveOrder(order);
                        order.setStatus("Delivered");

                        if (dbHelper.updateOrderStatusByOrderNumber(orderNumber, "Delivered")) {
                            Toast.makeText(AssemblerActivity.this, "Order status updated to: Delivered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AssemblerActivity.this, "Failed to update order status. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid input that cannot be parsed to an integer
                    Toast.makeText(AssemblerActivity.this, "Invalid input. Please enter a valid whole number for the order number!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // Handle unexpected errors
                    Toast.makeText(AssemblerActivity.this, "An unexpected error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    public boolean validateOrder(Order order) {
        // Validate hardware components
        if (!validateComponent(order.getCaseType(), order.getQtyCaseType())) {
            return false;
        }
        if (!validateComponent(order.getMotherboard(), order.getQtyMotherboard())) {
            return false;
        }
        if (!validateComponent(order.getMemory(), order.getQtyRAM())) {
            return false; //qty not displaying
        }
        // Validate list components
        if (!validateListComponent(order.getStorage(), order.getQtyStorage())) {
            return false; // No stock available displayed
        }
        if (!validateComponent(order.getDisplay(), order.getQtyMonitor())) {
            return false;
        }
        if (!validateComponent(order.getInputPeripherals(), order.getQtyInputPeriphals())) {
            return false;
        }

        // Validate software components
        if (!validateComponent(order.getWebBrowser(), order.getQtyWebBrowser())) {
            return false;
        }
        if (!validateComponent(order.getOfficeSuite(), order.getQtyOffice())) {
            return false; // -1 is displayed
        }


        if (!validateListComponent(order.getDevelopmentTools(), order.getQtyIDE())) {
            return false; // No stock available displayed
        }

        // If all validations pass
        return true;
    }

    public boolean validateComponent(String title, String quantity) {
        if (title == null || title.trim().isEmpty() || quantity == null || quantity.trim().isEmpty()) {
            return true; // 0 components selected is valid
        }
        int stockQty = dbHelper.getComponentQuantityByTitle(title);

        // Create a Handler to manage delayed Toasts
        Handler handler = new Handler();
        int delay = 0; // Start with no delay

        if (stockQty > 0) {
            int requestedQty = Integer.parseInt(quantity);

            if (stockQty >= requestedQty) {
                // Sufficient stock, return true without showing a Toast
                return true;
            } else {
                // Insufficient stock, show an error message
                delay += 2000; // Add 2-second delay if needed
                String finalTitle = title.trim();
                handler.postDelayed(() -> {
                    Toast.makeText(this, "Insufficient stock for " + finalTitle + ". Requested: "
                            + requestedQty + ", Available: " + stockQty, Toast.LENGTH_SHORT).show();
                }, delay);
                return false;
            }
        } else {
            // No stock available, show an error message
            delay += 2000; // Add 2-second delay if needed
            String finalTitle = title;
            handler.postDelayed(() -> {
                Toast.makeText(this, "No stock available for: " + finalTitle, Toast.LENGTH_SHORT).show();
            }, delay);
            return false;
        }
    }



    public boolean validateListComponent(List<String> titles, List<String> quantities) {
        if (titles == null || quantities == null || titles.isEmpty() || quantities.isEmpty()) {
            return true; // 0 components selected is valid
        }

        Handler handler = new Handler();
        int delay = 0; // Start with no delay

        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i).trim();
            String quantity = quantities.get(i).trim();

            // Validate that quantity is a valid integer
            int requestedQty;
            try {
                requestedQty = Integer.parseInt(quantity);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid quantity for " + title + ": " + quantity, Toast.LENGTH_SHORT).show();
                return false;
            }

            // Fetch the stock quantity from the database
            int stockQty = dbHelper.getComponentQuantityByTitle(title);

            // Validation logic
            if (stockQty > 0) {
                if (stockQty >= requestedQty) {
                    // Sufficient stock, no Toast needed, proceed to the next item
                    continue;
                } else {
                    // Insufficient stock, schedule a warning message
                    delay += 2000; // Add delay for proper display
                    String finalTitle = title;
                    handler.postDelayed(() -> {
                        Toast.makeText(this, "Insufficient stock for " + finalTitle + ". Requested: "
                                + requestedQty + ", Available: " + stockQty, Toast.LENGTH_SHORT).show();
                    }, delay);
                    return false;
                }
            } else {
                // No stock available, schedule an error message
                delay += 2000; // Add delay for proper display
                String finalTitle = title;
                handler.postDelayed(() -> {
                    Toast.makeText(this, "No stock available for: " + finalTitle, Toast.LENGTH_SHORT).show();
                }, delay);
                return false;
            }
        }

        return true; // All components validated successfully
    }
    public void approveOrder(Order order) {
        // Update hardware components
        if (order.getCaseType() != null) {
            updateStock(order.getCaseType(), order.getQtyCaseType());
        }
        if (order.getMotherboard() != null) {
            updateStock(order.getMotherboard(), order.getQtyMotherboard());
        }
        if (order.getMemory() != null) {
            updateStock(order.getMemory(), order.getQtyRAM());
        }
        if (order.getStorage() != null && !order.getStorage().isEmpty()) {
            updateStockList(order.getStorage(), order.getQtyStorage());
        }
        if (order.getDisplay() != null) {
            updateStock(order.getDisplay(), order.getQtyMonitor());
        }
        if (order.getInputPeripherals() != null) {
            updateStock(order.getInputPeripherals(), order.getQtyInputPeriphals());
        }

        // Update software components
        if (order.getWebBrowser() != null) {
            updateStock(order.getWebBrowser(), order.getQtyWebBrowser());
        }
        if (order.getOfficeSuite() != null) {
            updateStock(order.getOfficeSuite(), order.getQtyOffice());
        }
        if (order.getDevelopmentTools() != null && !order.getDevelopmentTools().isEmpty()) {
            updateStockList(order.getDevelopmentTools(), order.getQtyIDE());
        }
    }

    private void updateStock(String title, String quantity) {
        if (title == null || title.trim().isEmpty() || quantity == null || quantity.trim().isEmpty()) {
            return ; // 0 components selected is valid
        }
        int currentStock = dbHelper.getComponentQuantityByTitle(title);
        int requestedQty = Integer.parseInt(quantity.trim());

        int newStock = currentStock - requestedQty;
        dbHelper.updateComponentQuantityByTitle(title, newStock);
    }

    private void updateStockList(List<String> titles, List<String> quantities) {
        if (titles == null || quantities == null || titles.isEmpty() || quantities.isEmpty()) {
            return ; // 0 components selected is valid
        }
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            String quantity = quantities.get(i);

            updateStock(title, quantity);
        }
    }










}