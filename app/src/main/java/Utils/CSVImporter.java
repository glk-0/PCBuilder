package Utils;

import static com.example.groupe_39.MainActivity.dbHelper;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Requester;
import inventory.Component;
import inventory.Order;

public class CSVImporter {

    private static Context context;

    public CSVImporter(Context context) {
        this.context = context;
    }

    public static void importRequesters(Context context,String fileName) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(fileName);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            // Traiter chaque ligne du CSV et ajouter à la base de données
            // Assumant que les données sont séparées par des virgules
            String[] data = line.split(",");

            // Assurez-vous que le nombre de colonnes dans le CSV correspond à vos attributs
            if (data.length >= 5) { // Vérifiez que vous avez assez de colonnes
                int userId = Integer.parseInt(data[0]); // Assuming the first column is userId
                String firstName = data[1].trim();
                String lastName = data[2].trim();
                String email = data[3].trim();
                String password = data[4].trim();
                String role = "Requester"; // Assuming role is constant for Requesters

                // Créez l'objet Requester
                Requester requester = new Requester(userId, firstName, lastName, email, password, role);

                // Insérer le Requester dans la base de données
                // Supposons que vous avez une méthode dans votre DatabaseHelper pour ajouter un Requester
                boolean isInserted = dbHelper.addRequester(requester); // Ajoutez cette méthode dans votre DatabaseHelper
                if (isInserted) {
                    Log.d("CSVImporter", "Requester added: " + requester);
                } else {
                    Log.e("CSVImporter", "Failed to add requester: " + requester);
                }
            } else {
                Log.e("CSVImporter", "Invalid CSV format at line: " + line);
            }
        }

        reader.close();
        inputStream.close();

    }
    public static void importComponents(Context context,String fileName) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(fileName);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {

            // Process each line
            String[] data = line.split(",");
            String type = data[0].trim();
            String subType = data[1].trim();
            String title = data[2].trim();
            int quantity = Integer.parseInt(data[3]);
            String comment = data[4].trim();

            // Create a Component object
            Component component = new Component(type, subType, title, quantity, comment);

            boolean isInserted = dbHelper.addComponent(component);
            if (isInserted) {
                Log.d("CSVImporter", "Component added: " + component);
            } else {
                Log.e("CSVImporter", "Failed to add component: " + component);
            }
        }

        reader.close();
        inputStream.close();

    }
    // Method to import Orders
    public static void importOrders(Context context, String fileName) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(fileName);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int lineCount = 0;

        while ((line = reader.readLine()) != null) {
            lineCount++;

            // Process each line using regex to split while preserving brackets
            String[] data = line.split(",");

            try {
                // Map the CSV data to Order attributes
                String requesterId = data[0];
                String caseType = data[1];
                String qtyCaseType = data[2];
                String motherboard = data[3];
                String qtyMotherboard = data[4];
                String memory = data[5];
                String qtyRAM = data[6];
                List<String> storage = parseList(data[7]);
                List<String> qtyStorage = parseList(data[8]);
                String display = data[9];
                String qtyMonitor = data[10];
                String inputPeripherals = data[11];
                String qtyInputPeriphals = data[12];
                String webBrowser = data[13];
                String qtyWebBrowser = data[14];
                String officeSuite = data[15];
                String qtyOffice = data[16];
                List<String> developmentTools = parseList(data[17]);
                List<String> qtyIDE = parseList(data[18]);
                int orderNumber = Integer.parseInt(data[19]);
                String orderStatus = data[20];
                String orderDetails = data[21];

                // Create an Order object
                Order order = new Order(
                        requesterId, caseType, motherboard, memory,
                        storage, display, inputPeripherals,
                        webBrowser, officeSuite, developmentTools,
                        qtyRAM, qtyStorage, qtyMonitor, qtyOffice, qtyIDE
                );

                // Set additional properties
                order.setQtyCaseType(qtyCaseType);
                order.setQtyMotherboard(qtyMotherboard);
                order.setQtyInputPeriphals(qtyInputPeriphals);
                order.setQtyWebBrowser(qtyWebBrowser);
                order.setCurrentOrderNumber(orderNumber);
                order.setStatus(orderStatus);
                order.setDetails(orderDetails);

                // Insert the order into the database
                boolean isInserted = dbHelper.addOrder(order);
                if (isInserted) {
                    Log.d("CSVImporter", "Order added: " + order);
                } else {
                    Log.e("CSVImporter", "Failed to add order: " + order);
                }
            } catch (Exception e) {
                Log.e("CSVImporter", "Line " + lineCount + ": Error processing line: " + e.getMessage());
            }
        }

        reader.close();
        inputStream.close();
    }


    private static List<String> parseList(String input) {
        List<String> result = new ArrayList<>();
        if (input != null && !input.trim().isEmpty()) {
            // Remove the brackets
            input = input.replaceAll("[\\[\\]]", "").trim();

            // Split by comma
            String[] items = input.split(";");

            for (String item : items) {
                result.add(item.trim()); // Add each item to the list after trimming
            }
        }
        return result;
    }


}
