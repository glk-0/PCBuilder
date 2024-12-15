package com.example.groupe_39;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

import activities.Requester.NewOrder2;
import inventory.Order;

public class OrderUnitTest {

    private Order order;

    @Before
    public void setUp() {
        // Initialize the Order object with some sample data before each test
        order = new Order(
                "francis.james@example.com", "Case1", "motherboard1", "16GB",
                Arrays.asList("SSD 1TB", "SSD 512GB"), "8K Monitor",
                "Wireless Keyboard and Mouse1", "Chrome", "LibreOffice",
                Arrays.asList("Visual Studio Code"),
                "3", Arrays.asList("2", "1"), "2", "1", Arrays.asList("3")
        );
        //Setting the Office type and qty because it is initially not existent (0 Office selected possible)
        order.setOfficeSuite("LibreOffice");
        order.setQtyOffice("1");
    }

    // Test 1: Verify Order Initialization (Check if fields are set correctly)
    @Test
    public void testOrderInitialization() {
        assertEquals("francis.james@example.com", order.getRequesterId());
        assertEquals("Case1", order.getCaseType());
        assertEquals("motherboard1", order.getMotherboard());
        assertEquals("16GB", order.getMemory());
        assertEquals(Arrays.asList("SSD 1TB", "SSD 512GB"), order.getStorage());
        assertEquals("8K Monitor", order.getDisplay());
        assertEquals("Wireless Keyboard and Mouse1", order.getInputPeripherals());
        assertEquals("Chrome", order.getWebBrowser());
        assertEquals("LibreOffice", order.getOfficeSuite());
        assertEquals(Arrays.asList("Visual Studio Code"), order.getDevelopmentTools());
        assertEquals("3", order.getQtyRAM());
        assertEquals(Arrays.asList("2", "1"), order.getQtyStorage());
        assertEquals("2", order.getQtyMonitor());
        assertEquals("1", order.getQtyOffice());
    }

    // Test 2: Ensure the order number is incremented correctly
    @Test
    public void testOrderNumberIncrement() {
        int initialOrderNumber = order.getCurrentOrderNumber();
        new Order("requester2", "Case2", "motherboard2", "8GB",
                Arrays.asList("SSD 256GB"), "4K Monitor", "Wireless Keyboard and Mouse2",
                "Microsoft Edge", "OfficePro", Arrays.asList("IntelliJ"), "2",
                Arrays.asList("1"), "1", "1", Arrays.asList("2"));
        assertEquals(initialOrderNumber + 1, order.getCurrentOrderNumber());
    }


// Test 3: Add valid RAM quantity (check that RAM is added correctly)
@Test
public void testAddValidRAM() {
    order.setQtyRAM("4");
    assertEquals("4", order.getQtyRAM());
}

// Test 4: Add invalid RAM quantity (negative or zero)
@Test
public void testAddInvalidRAM() {
    order.setQtyRAM("-1");
    assertNotEquals("-1", order.getQtyRAM());  // Assuming negative RAM quantity is invalid
}
    // Test 5: Add valid Monitor quantity (check if monitors are added correctly)
    @Test
    public void testAddValidMonitor() {
        order.setQtyMonitor("3");
        assertEquals("3", order.getQtyMonitor());
    }

    // Test 6: Add invalid Monitor quantity (check if an invalid quantity is rejected)
    @Test
    public void testAddInvalidMonitor() {
        order.setQtyMonitor("0");
        assertNotEquals("0", order.getQtyMonitor());  // Assuming quantity 0 is invalid
    }
    // Test 7: Check if adding storage works correctly (set a list of storage devices)
    @Test
    public void testAddValidStorage() {
        order.setQtyStorage(Arrays.asList("3", "2"));
        assertEquals(Arrays.asList("3", "2"), order.getQtyStorage());
    }

    // Test 8: Verify the status is set to "waiting to be accepted" upon initialization
    @Test
    public void testOrderStatus() {
        assertEquals("waiting to be accepted", order.getStatus());
    }

    // Test 9: Test creation date (ensure it's set to the current date)
    @Test
    public void testCreationDate() {
        assertNotNull(order.getCreationDate());
    }

    // Test 10: Verify that setting a web browser works correctly
    @Test
    public void testSetWebBrowser() {
        order.setWebBrowser("Firefox");
        assertEquals("Firefox", order.getWebBrowser());
    }
    // Test 11: Verify that adding a comment works correctly
    @Test
    public void testSetDetails() {
        order.setDetails("This is a sample order for testing.");
        assertEquals("This is a sample order for testing.", order.getDetails());
    }

    // Test 12: Verify the order creation date is set correctly (non-null)
    @Test
    public void testUpdatedAtIsNotNull() {
        assertNotNull(order.getUpdatedAt());
    }


}