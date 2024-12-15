package DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import Utils.CSVImporter;
import entities.User;
import entities.Administrator;
import entities.Assembler;
import entities.StoreKeeper;
import entities.Requester;
import inventory.Component;
import inventory.Order;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USERS_TABLE = "USERS";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_ROLE = "ROLE";
    public static final String COLUMN_CREATED_AT = "CREATED_AT";
    public static final String COLUMN_UPDATED_AT = "UPDATED_AT";

    public static final String REQUESTER_TABLE = "REQUESTER";
    public static final String COLUMN_REQUESTER_ID = "REQUESTER_ID";

    private static DataBaseHelper instance;
    private Context context;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSSSSS]");

    public DataBaseHelper(@Nullable Context context) {
        super(context, "PCBuilder_db", null, 8);
        this.context = context;
    }

    // Method to get the singleton instance
    public static DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create USERS table
        String createUsersTable = "CREATE TABLE " + USERS_TABLE + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_ROLE + " TEXT, " +
                COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                COLUMN_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

        db.execSQL(createUsersTable);

        // Create REQUESTER table
        String createRequesterTable = "CREATE TABLE " + REQUESTER_TABLE + " (" +
                COLUMN_REQUESTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_ROLE + " TEXT NOT NULL, " +  // Add ROLE column
                COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                COLUMN_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

        db.execSQL(createRequesterTable);

        // Create INVENTORY table
        String createInventoryTable = "CREATE TABLE inventory (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT NOT NULL, " +
                "subType TEXT NOT NULL, " +
                "title TEXT UNIQUE NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "comment TEXT, " +
                "createdAt DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP);";

        db.execSQL(createInventoryTable);

        String createOrderTable = "CREATE TABLE orders (" +
                "orderID INTEGER PRIMARY KEY AUTOINCREMENT, " + // Unique order ID
                "requesterID TEXT NOT NULL, " + // Foreign key referencing Requester table
                "caseType TEXT, " + // Case type
                "qtyCaseType TEXT, " + // Quantity of case type
                "motherboard TEXT, " + // Motherboard
                "qtyMotherboard TEXT, " + // Quantity of motherboard
                "memory TEXT, " + // Memory
                "qtyRAM TEXT, " + // Quantity of memory (RAM)
                "storage TEXT, " + // Storage (store as a delimited string if needed)
                "qtyStorage TEXT, " + // Quantity of storage (as a delimited string if needed)
                "display TEXT, " + // Display
                "qtyMonitor TEXT, " + // Quantity of display (monitor)
                "inputPeripherals TEXT, " + // Input peripherals
                "qtyInputPeriphals TEXT, " + // Quantity of input peripherals
                "webBrowser TEXT, " + // Web browser
                "qtyWebBrowser TEXT, " + // Quantity of web browser
                "officeSuite TEXT, " + // Office suite
                "qtyOffice TEXT, " + // Quantity of office suite
                "developmentTools TEXT, " + // Development tools (store as a delimited string if needed)
                "qtyIDE TEXT, " + // Quantity of development tools (as a delimited string if needed)
                "orderNumber INTEGER NOT NULL, " + // Order number
                "orderStatus TEXT NOT NULL, " + // Status of the order
                "orderDetails TEXT, " + // Additional order details (optional)
                "createdAt DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP);";

        db.execSQL(createOrderTable);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REQUESTER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS inventory");
        db.execSQL("DROP TABLE IF EXISTS orders"); // Drop the orders table

        // Recreate the tables
        onCreate(db);

    }



    // Helper method to get current date and time
    private String getCurrentDateTime() {
        return LocalDateTime.now().format(dtf);
    }

    // Add user
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, user.getFirstName());
        cv.put(COLUMN_LAST_NAME, user.getLastName());
        cv.put(COLUMN_EMAIL, user.getEmail());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_ROLE, user.getRole());
        cv.put(COLUMN_CREATED_AT, getCurrentDateTime());
        cv.put(COLUMN_UPDATED_AT, getCurrentDateTime());

        long insert = db.insert(USERS_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    // Add requester
    public boolean addRequester(Requester requester) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, requester.getFirstName());
        cv.put(COLUMN_LAST_NAME, requester.getLastName());
        cv.put(COLUMN_EMAIL, requester.getEmail());
        cv.put(COLUMN_PASSWORD, requester.getPassword());
        cv.put(COLUMN_ROLE, requester.getRole());  // Add role value
        cv.put(COLUMN_CREATED_AT, getCurrentDateTime());
        cv.put(COLUMN_UPDATED_AT, getCurrentDateTime());

        long insert = db.insert(REQUESTER_TABLE, null, cv);
        db.close();
        return insert != -1;
    }
    // Add a new component to the inventory
    public boolean addComponent(Component component) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("type", component.getType());
        cv.put("subType", component.getSubType());
        cv.put("title", component.getTitle());
        cv.put("quantity", component.getQuantity());
        cv.put("comment", component.getComment());
        cv.put("createdAt", component.getCreatedAt().toString());
        cv.put("updatedAt", component.getUpdatedAt().toString());

        long insert = db.insert("inventory", null, cv);
        db.close();
        return insert != -1;
    }


    // Authenticate user
    public User authenticate(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USERS_TABLE + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            String firstName = cursor.getString(1);
            String lastName = cursor.getString(2);
            String role = cursor.getString(5);
            cursor.close();
            db.close();

            switch (role) {
                case "Administrator":
                    return new Administrator(userId, firstName, lastName, email, password, role);
                case "StoreKeeper":
                    return new StoreKeeper(userId, firstName, lastName, email, password, role);
                case "Assembler":
                    return new Assembler(userId, firstName, lastName, email, password, role);
                case "Requester":
                    return new Requester(userId, firstName, lastName, email, password, role);
                default:
                    return null;
            }
        } else {
            cursor.close();
            db.close();
            return null;
        }
    }

    // Update user
    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, user.getFirstName());
        cv.put(COLUMN_LAST_NAME, user.getLastName());
        cv.put(COLUMN_EMAIL, user.getEmail());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_ROLE, user.getRole());
        cv.put(COLUMN_UPDATED_AT, getCurrentDateTime());

        int result = db.update(USERS_TABLE, cv, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getUserId())});
        db.close();
        return result > 0;
    }
    // Update the quantity and comment of a component
    public boolean updateComponentDetails(String title, int newQuantity, String newComment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Update the quantity and comment
        cv.put("quantity", newQuantity);
        cv.put("comment", newComment);

        // Update the modification timestamp
        cv.put("updatedAt", LocalDateTime.now().format(dtf));

        // Update the database entry where the title matches
        int result = db.update("inventory", cv, "title = ?", new String[]{title});
        db.close();

        // Return true if at least one row was updated
        return result > 0;
    }



    // Delete user by ID
    public boolean deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(USERS_TABLE, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
        return result > 0;
    }
    // Delete a component by title
    public boolean deleteComponentByTitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Execute delete and return true if any row was deleted
        int result = db.delete("inventory", "title = ?", new String[]{title});
        db.close();

        return result > 0;
    }


    // Retrieve all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String email = cursor.getString(3);
                String password = cursor.getString(4);
                String role = cursor.getString(5);

                User user;
                switch (role) {
                    case "Administrator":
                        user = new Administrator(userId, firstName, lastName, email, password, role);
                        break;
                    case "StoreKeeper":
                        user = new StoreKeeper(userId, firstName, lastName, email, password, role);
                        break;
                    case "Assembler":
                        user = new Assembler(userId, firstName, lastName, email, password, role);
                        break;
                    case "Requester":
                        user = new Requester(userId, firstName, lastName, email, password, role);
                        break;
                    default:
                        continue;
                }
                users.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return users;
    }

    // Get all requesters
    public List<Requester> getAllRequesters() {
        List<Requester> requesters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + REQUESTER_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                int requesterId = cursor.getInt(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String email = cursor.getString(3);
                String password = cursor.getString(4);
                String role = cursor.getString(5);

                Requester requester = new Requester(requesterId, firstName, lastName, email, password, role);
                requesters.add(requester);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return requesters;
    }
    // Retrieve all components from the inventory
    public List<Component> getAllComponents() {
        List<Component> components = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM inventory", null);

        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(1);
                String subType = cursor.getString(2);
                String title = cursor.getString(3);
                int quantity = cursor.getInt(4);
                String comment = cursor.getString(5);
                LocalDateTime createdAt = LocalDateTime.parse(cursor.getString(6), dtf);
                LocalDateTime updatedAt = LocalDateTime.parse(cursor.getString(7), dtf);

                Component component = new Component(type, subType, title, quantity, comment);
                components.add(component);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return components;
    }

    // Clear all requesters
    public boolean clearAllRequesters() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete all requesters from the database
        int result = db.delete(REQUESTER_TABLE, null, null);
        db.close();

        return result > 0;  // Returns true if any rows were deleted
    }
    // Clear all components from the inventory table
    public boolean clearInventory() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete all components from the inventory
        int result = db.delete("inventory", null, null);
        db.close();

        return result > 0;
    }
    //Check if the component title already exists in the DataBase
    public boolean isComponentTitleUnique(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Using the table name "inventory" directly like in the getAllComponents() method
        Cursor cursor = db.rawQuery("SELECT * FROM inventory WHERE title = ?", new String[]{title});

        boolean isUnique = !cursor.moveToFirst(); // If cursor does not move to the first row, the title is unique
        cursor.close();
        db.close();

        return isUnique;
    }
    public Component getComponentByTitle(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM inventory WHERE title = ?", new String[]{title});

        Component component = null;
        if (cursor.moveToFirst()) {
            String type = cursor.getString(1);
            String subType = cursor.getString(2);
            int quantity = cursor.getInt(4);
            String comment = cursor.getString(5);
            String creationDateStr = cursor.getString(6);
            String modificationDateStr = cursor.getString(7);

            // Convert database timestamps to LocalDateTime
            LocalDateTime creationDate = LocalDateTime.parse(creationDateStr);
            LocalDateTime modificationDate = LocalDateTime.parse(modificationDateStr);

            // Create the component object
            component = new Component(type, subType, title, quantity, comment);

            // Manually set the modification dates
            component.setModificationDate(modificationDate);  // Set last modified date from DB
        }

        cursor.close();
        db.close();
        return component;
    }
    public List<List<Component>> getComponentsByType() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<List<Component>> categorizedComponents = new ArrayList<>();

        // Define the list of subtypes
        String[] subTypes = {"PC case", "Motherboard", "RAM", "Storage", "Monitor", "Input Device", "Web Browser", "Office", "IDE"};

        for (String subType : subTypes) {
            List<Component> componentsOfSubType = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM inventory WHERE subType = ?", new String[]{subType});

            if (cursor.moveToFirst()) {
                do {
                    String type = cursor.getString(1);
                    String subType1 = cursor.getString(2);
                    String title = cursor.getString(3);

                    int quantity = cursor.getInt(4);
                    String comment = cursor.getString(5);
                    String createdAt = cursor.getString(6);
                    String updatedAt = cursor.getString(7);
                    Component component = new Component(type, subType1, title, quantity, comment);
                    // Convert database timestamps to LocalDateTime
                    LocalDateTime creationDate = LocalDateTime.parse(createdAt);
                    LocalDateTime modificationDate = LocalDateTime.parse(updatedAt);
                    component.setModificationDate(modificationDate); // Set modification date

                    componentsOfSubType.add(component);
                } while (cursor.moveToNext());
            }

            cursor.close();
            categorizedComponents.add(componentsOfSubType);
        }

        db.close();
        return categorizedComponents;
    }

    // Method to get all orders
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch all orders from the orders table
        Cursor cursor = db.rawQuery("SELECT * FROM orders", null);

        // Iterate through the cursor and create Order objects
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve the values from each column using index positions
                String requesterId = cursor.getString(1); // Column index 1: requesterID
                String caseType = cursor.getString(2); // Column index 2: caseType
                String qtyCaseType = cursor.getString(3); // Column index 3: qtyCaseType

                String motherboard = cursor.getString(4); // Column index 4: motherboard
                String qtyMotherboard = cursor.getString(5); // Column index 5: qtyMotherboard

                String memory = cursor.getString(6); // Column index 6: memory
                String qtyRAM = cursor.getString(7); // Column index 7: qtyRAM

                List<String> storage = convertStringToList(cursor.getString(8)); // Column index 8: storage
                List<String> qtyStorage = convertStringToList(cursor.getString(9)); // Column index 9: qtyStorage

                String display = cursor.getString(10); // Column index 10: display
                String qtyMonitor = cursor.getString(11); // Column index 11: qtyMonitor

                String inputPeripherals = cursor.getString(12); // Column index 12: inputPeripherals
                String qtyInputPeriphals = cursor.getString(13); // Column index 13: qtyInputPeriphals

                String webBrowser = cursor.getString(14); // Column index 14: webBrowser
                String qtyWebBrowser = cursor.getString(15); // Column index 15: qtyWebBrowser

                String officeSuite = cursor.getString(16); // Column index 16: officeSuite
                String qtyOffice = cursor.getString(17); // Column index 17: qtyOffice

                List<String> developmentTools = convertStringToList(cursor.getString(18)); // Column index 18: developmentTools
                List<String> qtyIDE = convertStringToList(cursor.getString(19)); // Column index 19: qtyIDE

                int orderNumber = cursor.getInt(20); // Column index 20: orderNumber
                String orderStatus = cursor.getString(21); // Column index 21: orderStatus
                String orderDetails = cursor.getString(22); // Column index 22: orderDetails

                Order order = new Order(
                        requesterId,
                        caseType,
                        motherboard,
                        memory,
                        storage,
                        display,
                        inputPeripherals,
                        webBrowser,
                        officeSuite,
                        developmentTools,
                        qtyRAM,
                        qtyStorage,
                        qtyMonitor,
                        qtyOffice,
                        qtyIDE
                );

                // Optionally, set other fields like orderNumber, orderStatus, and orderDetails
                order.setCurrentOrderNumber(orderNumber);
                order.setStatus(orderStatus);
                order.setDetails(orderDetails);
                order.setQtyOffice(qtyOffice);
                order.setOfficeSuite(officeSuite);

                orderList.add(order);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return orderList;
    }



    // Helper method to convert a comma-separated string to a List<String>
    private List<String> convertStringToList(String str) {
        List<String> list = new ArrayList<>();
        if (str != null && !str.isEmpty()) {
            String[] items = str.split(",");
            for (String item : items) {
                list.add(item.trim());
            }
        }
        return list;
    }
    // Add a new order to the orders table
    public boolean addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Add order details, including quantities
        cv.put("requesterID", order.getRequesterId());
        cv.put("caseType", order.getCaseType());
        cv.put("qtyCaseType", order.getQtyCaseType());
        cv.put("motherboard", order.getMotherboard());
        cv.put("qtyMotherboard", order.getQtyMotherboard());
        cv.put("memory", order.getMemory());
        cv.put("qtyRAM", order.getQtyRAM());
        cv.put("storage", listToString(order.getStorage())); // Convert list to string if necessary
        cv.put("qtyStorage", listToString(order.getQtyStorage())); // Convert list to string if necessary
        cv.put("display", order.getDisplay());
        cv.put("qtyMonitor", order.getQtyMonitor());
        cv.put("inputPeripherals", order.getInputPeripherals());
        cv.put("qtyInputPeriphals", order.getQtyInputPeriphals());
        cv.put("webBrowser", order.getWebBrowser());
        cv.put("qtyWebBrowser", order.getQtyWebBrowser());
        cv.put("officeSuite", order.getOfficeSuite());
        cv.put("qtyOffice", order.getQtyOffice());
        cv.put("developmentTools", listToString(order.getDevelopmentTools())); // Convert list to string if necessary
        cv.put("qtyIDE", listToString(order.getQtyIDE())); // Convert list to string if necessary
        cv.put("orderNumber", order.getCurrentOrderNumber()); // Ensure this is unique and managed (incremented)
        cv.put("orderStatus", order.getStatus());
        cv.put("orderDetails", order.getDetails());
        cv.put("createdAt", getCurrentDateTime());
        cv.put("updatedAt", getCurrentDateTime());

        // Insert order into the orders table
        long insert = db.insert("orders", null, cv);
        db.close();
        return insert != -1; // Return true if insertion was successful
    }
    // Convert List<String> to a comma-separated String
    public String listToString(List<String> list) {
        return TextUtils.join(",", list);
    }
    public List<Order> getOrdersByRequesterID(String requesterId) {
        List<Order> ordersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to retrieve orders based on the requester ID
        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE requesterID = ?", new String[]{requesterId});

        if (cursor.moveToFirst()) {
            do {
                // Extracting data using index positions
                String caseType = cursor.getString(2);
                String qtyCaseType = cursor.getString(3);
                String motherboard = cursor.getString(4);
                String qtyMotherboard = cursor.getString(5);
                String memory = cursor.getString(6);
                String qtyRAM = cursor.getString(7);
                List<String> storage = convertStringToList(cursor.getString(8));
                List<String> qtyStorage = convertStringToList(cursor.getString(9));
                String display = cursor.getString(10);
                String qtyMonitor = cursor.getString(11);
                String inputPeripherals = cursor.getString(12);
                String qtyInputPeriphals = cursor.getString(13);
                String webBrowser = cursor.getString(14);
                String qtyWebBrowser = cursor.getString(15);
                String officeSuite = cursor.getString(16);
                String qtyOffice = cursor.getString(17);
                List<String> developmentTools = convertStringToList(cursor.getString(18));
                List<String> qtyIDE = convertStringToList(cursor.getString(19));
                int orderNumber = cursor.getInt(20);
                String orderStatus = cursor.getString(21);
                String orderDetails = cursor.getString(22);

                // Creating an Order object with extracted data
                Order order = new Order(
                        requesterId,
                        caseType,
                        motherboard,
                        memory,
                        storage,
                        display,
                        inputPeripherals,
                        webBrowser,
                        officeSuite,
                        developmentTools,
                        qtyRAM,
                        qtyStorage,
                        qtyMonitor,
                        qtyOffice,
                        qtyIDE
                );

                // Set additional fields for order
                order.setQtyCaseType(qtyCaseType);
                order.setQtyMotherboard(qtyMotherboard);
                order.setQtyInputPeriphals(qtyInputPeriphals);
                order.setQtyWebBrowser(qtyWebBrowser);
                order.setStatus(orderStatus);
                order.setDetails(orderDetails);
                order.setCurrentOrderNumber(orderNumber); // Set the static order number

                // Add the order to the list
                ordersList.add(order);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ordersList;
    }
    public Order getOrderByOrderNumber(int orderNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE orderNumber = ?", new String[]{String.valueOf(orderNumber)});

        Order order = null;
        if (cursor.moveToFirst()) {
            // Extracting values from the cursor based on column indexes
            String requesterId = cursor.getString(1);
            String caseType = cursor.getString(2);
            String qtyCaseType = cursor.getString(3);
            String motherboard = cursor.getString(4);
            String qtyMotherboard = cursor.getString(5);
            String memory = cursor.getString(6);
            String qtyRAM = cursor.getString(7);
            List<String> storage = convertStringToList(cursor.getString(8));
            List<String> qtyStorage = convertStringToList(cursor.getString(9));
            String display = cursor.getString(10);
            String qtyMonitor = cursor.getString(11);
            String inputPeripherals = cursor.getString(12);
            String qtyInputPeriphals = cursor.getString(13);
            String webBrowser = cursor.getString(14);
            String qtyWebBrowser = cursor.getString(15);
            String officeSuite = cursor.getString(16);
            String qtyOffice = cursor.getString(17);
            List<String> developmentTools = convertStringToList(cursor.getString(18));
            List<String> qtyIDE = convertStringToList(cursor.getString(19));
            String orderStatus = cursor.getString(21);
            String orderDetails = cursor.getString(22);
            String createdAtStr = cursor.getString(23);
            String updatedAtStr = cursor.getString(24);

            // Convert database timestamps to LocalDateTime
            LocalDateTime creationDate = LocalDateTime.parse(createdAtStr);
            LocalDateTime modificationDate = LocalDateTime.parse(updatedAtStr);

            // Create the Order object
            order = new Order(
                    requesterId,
                    caseType,
                    motherboard,
                    memory,
                    storage,
                    display,
                    inputPeripherals,
                    webBrowser,
                    officeSuite,
                    developmentTools,
                    qtyRAM,
                    qtyStorage,
                    qtyMonitor,
                    qtyOffice,
                    qtyIDE
            );

            // Set additional order fields
            order.setQtyCaseType(qtyCaseType);
            order.setQtyMotherboard(qtyMotherboard);
            order.setQtyInputPeriphals(qtyInputPeriphals);
            order.setQtyWebBrowser(qtyWebBrowser);
            order.setStatus(orderStatus);
            order.setDetails(orderDetails);
            order.setUpdatedAt(modificationDate);  // Set last modified date from DB
            order.setCurrentOrderNumber(orderNumber); // Set the static order number
        }

        cursor.close();
        db.close();
        return order;
    }
    public boolean deleteOrderByOrderNumber(int orderNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Execute delete operation and return true if any row was deleted
        int result = db.delete("orders", "orderNumber = ?", new String[]{String.valueOf(orderNumber)});
        db.close();

        return result > 0;
    }
    public boolean clearOrders() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete all orders from the orders table
        int result = db.delete("orders", null, null);
        db.close();

        return result > 0;
    }
    public List<Order> getOrdersByStatus(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE orderStatus = ?", new String[]{status});

        List<Order> orders = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // Extracting values from the cursor based on column indexes
                String requesterId = cursor.getString(1);
                String caseType = cursor.getString(2);
                String qtyCaseType = cursor.getString(3);
                String motherboard = cursor.getString(4);
                String qtyMotherboard = cursor.getString(5);
                String memory = cursor.getString(6);
                String qtyRAM = cursor.getString(7);
                List<String> storage = convertStringToList(cursor.getString(8));
                List<String> qtyStorage = convertStringToList(cursor.getString(9));
                String display = cursor.getString(10);
                String qtyMonitor = cursor.getString(11);
                String inputPeripherals = cursor.getString(12);
                String qtyInputPeriphals = cursor.getString(13);
                String webBrowser = cursor.getString(14);
                String qtyWebBrowser = cursor.getString(15);
                String officeSuite = cursor.getString(16);
                String qtyOffice = cursor.getString(17);
                List<String> developmentTools = convertStringToList(cursor.getString(18));
                List<String> qtyIDE = convertStringToList(cursor.getString(19));
                int currentOrderNumber = cursor.getInt(20);
                String orderStatus = cursor.getString(21);
                String orderDetails = cursor.getString(22);
                String createdAtStr = cursor.getString(23);
                String updatedAtStr = cursor.getString(24);

                // Convert database timestamps to LocalDateTime
                LocalDateTime creationDate = LocalDateTime.parse(createdAtStr);
                LocalDateTime modificationDate = LocalDateTime.parse(updatedAtStr);

                // Create the Order object
                Order order = new Order(
                        requesterId,
                        caseType,
                        motherboard,
                        memory,
                        storage,
                        display,
                        inputPeripherals,
                        webBrowser,
                        officeSuite,
                        developmentTools,
                        qtyRAM,
                        qtyStorage,
                        qtyMonitor,
                        qtyOffice,
                        qtyIDE
                );

                // Set additional order fields
                order.setQtyCaseType(qtyCaseType);
                order.setCurrentOrderNumber(currentOrderNumber);
                order.setQtyMotherboard(qtyMotherboard);
                order.setQtyInputPeriphals(qtyInputPeriphals);
                order.setQtyWebBrowser(qtyWebBrowser);
                order.setStatus(orderStatus);
                order.setDetails(orderDetails);
                order.setUpdatedAt(modificationDate);  // Set last modified date from DB

                // Add order to the list
                orders.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return orders;
    }
    public int getComponentQuantityByTitle(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT quantity FROM inventory WHERE title = ?", new String[]{title});

        int quantity = -1; // Default value if the component is not found
        if (cursor.moveToFirst()) {
            quantity = cursor.getInt(0); // Get the quantity from the first column
        }

        cursor.close();
        db.close();
        return quantity;
    }
    public List<Integer> getComponentQuantityListByTitle(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT quantity FROM inventory WHERE title = ?", new String[]{title});

        List<Integer> quantities = new ArrayList<>();
        if (cursor.moveToFirst()) {
            String quantityString = cursor.getString(0); // Get the comma-separated quantity string
            if (quantityString != null && !quantityString.isEmpty()) {
                String[] quantityArray = quantityString.split(","); // Split by commas
                for (String quantity : quantityArray) {
                    quantities.add(Integer.parseInt(quantity.trim())); // Parse each quantity and add to the list
                }
            }
        }

        cursor.close();
        db.close();
        return quantities;
    }
    public void updateComponentQuantityByTitle(String title, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("quantity", newQuantity); // Assuming the column name for quantity is "quantity"

        // Update the row where the title matches
        int rowsAffected = db.update("inventory", values, "title = ?", new String[]{title});

        if (rowsAffected > 0) {
            Log.d("DatabaseHelper", "Stock updated for " + title + ". New quantity: " + newQuantity);
        } else {
            Log.e("DatabaseHelper", "Failed to update stock for " + title);
        }

        db.close(); // Close the database connection
    }
    public boolean updateOrderStatusByOrderNumber(int orderNumber, String newStatus) {
        boolean updated = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("orderStatus", newStatus);

        // Update the row where the order number matches
        int rowsAffected = db.update("orders", values, "orderNumber = ?", new String[]{String.valueOf(orderNumber)});

        if (rowsAffected > 0) {
            updated = true;
            Log.d("DatabaseHelper", "Order status updated for orderNumber " + orderNumber + ". New status: " + newStatus);
        } else {
            Log.e("DatabaseHelper", "Failed to update status for orderNumber " + orderNumber);
        }

        db.close();
        return updated;// Close the database connection
    }
    public Requester getRequesterByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + REQUESTER_TABLE +
                        " WHERE " + COLUMN_EMAIL + " = ?",
                new String[]{email}
        );

        Requester requester = null;
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            String firstName = cursor.getString(1);
            String lastName = cursor.getString(2);
            String requesterEmail = cursor.getString(3);
            String password = cursor.getString(4);
            String role = cursor.getString(5);
            String createdAt = cursor.getString(6);
            String updatedAt = cursor.getString(7);

            // Create a Requester object
            requester = new Requester(userId, firstName, lastName, requesterEmail, password, role);
            requester.setCreatedAt(createdAt);
            requester.setUpdatedAt(updatedAt);
        }

        cursor.close();
        db.close();
        return requester;
    }
    public boolean deleteRequesterByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Execute delete and return true if any row was deleted
        int result = db.delete(REQUESTER_TABLE, COLUMN_EMAIL + " = ?", new String[]{email});
        db.close();

        return result > 0;
    }








}
