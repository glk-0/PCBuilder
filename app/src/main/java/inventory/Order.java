package inventory;

import android.content.Context;
import android.content.SharedPreferences;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    // Static variable for order number
    //TODO: fix order number increment
    private static int orderNumber = 2;
    private int currentOrderNumber ;
    private String qtyRAM,  qtyMonitor, qtyOffice,  qtyCaseType,qtyMotherboard, qtyInputPeriphals,qtyWebBrowser;
    private List<String> qtyIDE,qtyStorage;
    // Unique identifier for the requester
    private String requesterId;

    // Hardware components
    private String caseType;
    private String motherboard;
    private String memory;
    private List<String> storage;
    private String display;
    private String inputPeripherals;

    // Software components
    private String webBrowser;
    private String officeSuite;
    private List<String> developmentTools;
    //comments
    private String status;
    private String details;

    // Dates
    private final LocalDateTime creationDate;
    private LocalDateTime updatedAt;

    // Constructor with all attributes
    public Order(String requesterId, String caseType, String motherboard, String memory,
                 List<String> storage, String display, String inputPeripherals,
                 String webBrowser, String officeSuite, List<String> developmentTools,String qtyRAM,List<String> qtyStorage,String qtyMonitor,String qtyOffice, List<String> qtyIDE) {
        this.requesterId = requesterId;
        this.caseType = caseType;
        this.motherboard = motherboard;
        this.memory = memory;
        this.storage = storage;
        this.display = display;
        this.inputPeripherals = inputPeripherals;
        this.webBrowser = webBrowser;
        this.officeSuite = officeSuite; //TODO: make it " " if bugs
        this.developmentTools = developmentTools;
        this.creationDate = LocalDateTime.now();
        this.updatedAt = creationDate;
        this.qtyRAM = qtyRAM;
        this.qtyStorage = qtyStorage;
        this.qtyIDE = qtyIDE;
        this.qtyMonitor = qtyMonitor;
        this.qtyOffice = "0";
        this.qtyCaseType = "1";
        this.qtyMotherboard = "1";
        this.qtyInputPeriphals = "1";
        this.qtyWebBrowser ="1";

        // Load the persisted order number and increment it
        synchronized (Order.class) {
            orderNumber++;
            this.currentOrderNumber = orderNumber;
        }

        status = "waiting to be accepted";
        details = " ";

    }

    public String getQtyCaseType() {
        return qtyCaseType;
    }

    public String getQtyMotherboard() {
        return qtyMotherboard;
    }

    public String getQtyInputPeriphals() {
        return qtyInputPeriphals;
    }

    public String getQtyWebBrowser() {
        return qtyWebBrowser;
    }

    // Static method to get the current order number


    public int getCurrentOrderNumber() {
        return currentOrderNumber;
    }

    // Other methods (getters, setters, etc.)...
    public String getRequesterId() {
        return requesterId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    public static void resetOrderNumber(){
        orderNumber =2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCaseType() {
        return caseType;
    }

    public String getMotherboard() {
        return motherboard;
    }

    public String getMemory() {
        return memory;
    }

    public List<String> getStorage() {
        return storage;
    }

    public String getDisplay() {
        return display;
    }

    public String getInputPeripherals() {
        return inputPeripherals;
    }

    public String getWebBrowser() {
        return webBrowser;
    }

    public String getOfficeSuite() {
        return officeSuite;
    }

    public List<String> getDevelopmentTools() {
        return developmentTools;
    }

    public String getQtyRAM() {
        return qtyRAM;
    }

    public void setQtyRAM(String qtyRAM) {
        if(Integer.parseInt(qtyRAM)>0){
        this.qtyRAM = qtyRAM;}
    }

    public List<String> getQtyStorage() {
        return qtyStorage;
    }

    public void setQtyStorage(List<String> qtyStorage) {
        this.qtyStorage = qtyStorage;
    }

    public String getQtyMonitor() {
        return qtyMonitor;
    }

    public void setQtyMonitor(String qtyMonitor) {
        if(Integer.parseInt(qtyMonitor)>0){
        this.qtyMonitor = qtyMonitor;}
    }

    public String getQtyOffice() {
        return qtyOffice;
    }

    public void setQtyOffice(String qtyOffice) {
        this.qtyOffice = qtyOffice;
    }

    public void setDevelopmentTools(List<String> developmentTools) {
        this.developmentTools = developmentTools;
    }

    public List<String> getQtyIDE() {
        return qtyIDE;
    }

    public void setQtyIDE(List<String> qtyIDE) {
        this.qtyIDE = qtyIDE;
    }

    public void setQtyCaseType(String qtyCaseType) {
        this.qtyCaseType = qtyCaseType;
    }

    public void setQtyMotherboard(String qtyMotherboard) {
        this.qtyMotherboard = qtyMotherboard;
    }

    public void setQtyInputPeriphals(String qtyInputPeriphals) {
        this.qtyInputPeriphals = qtyInputPeriphals;
    }

    public void setQtyWebBrowser(String qtyWebBrowser) {
        this.qtyWebBrowser = qtyWebBrowser;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public void setStorage(List<String> storage) {
        this.storage = storage;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setInputPeripherals(String inputPeripherals) {
        this.inputPeripherals = inputPeripherals;
    }
    public void setCurrentOrderNumber(int currentOrderNumber){
        this.currentOrderNumber = currentOrderNumber;
    }

    public void setWebBrowser(String webBrowser) {
        this.webBrowser = webBrowser;
    }

    public void setOfficeSuite(String officeSuite) {
        this.officeSuite = officeSuite;
    }

    @Override
    public String toString() {
        return
                " -requesterId= " + requesterId + '\n' +
                        " -orderNumber= " + getCurrentOrderNumber() + '\n' +
                " -caseType= " + caseType +"; qty: "+getQtyCaseType()+ '\n' +
                " -motherboard= " + motherboard +"; qty: "+getQtyMotherboard()+ '\n' +
                " -memory= " + memory +"; qty: "+getQtyRAM()+'\n' +
                " -storage= " + storage +"; qty: "+getQtyStorage()+'\n' +
                " -display= " + display +"; qty: "+getQtyMonitor()+'\n' +
                " -inputPeripherals= " + inputPeripherals +"; qty: "+getQtyInputPeriphals()+ '\n' +
                " -webBrowser= " + webBrowser +"; qty: "+getQtyWebBrowser()+ '\n' +
                " -officeSuite= " + officeSuite + "; qty: "+getQtyOffice()+'\n' +
                " -developmentTools= " + developmentTools +"; qty: "+getQtyIDE()+'\n'+
                " -Status= "  +getStatus()+'\n'
                ;
    }
}
