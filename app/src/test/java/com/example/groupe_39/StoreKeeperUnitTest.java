package com.example.groupe_39;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import inventory.Component;

public class StoreKeeperUnitTest {

    private List<Component> stockList;

    @Before
    public void setUp() {
        // Initialize stock list before each test
        stockList = new ArrayList<>();
    }

    // Test 1: Create and add a new component to stock
    @Test
    public void testAddComponent() {
        Component component = new Component("Hardware","RAM", "16GB DDR4", 10, "High-speed RAM module");
        stockList.add(component);
        assertTrue(stockList.contains(component));
    }

    // Test 2: Modify an existing component's details
    @Test
    public void testModifyComponent() {
        Component component = new Component("Hardware","SSD", "256GB SSD", 5, "High-speed storage");
        stockList.add(component);
        // Update details
        component.setQuantity(15);
        component.setComment("Updated storage description");
        assertEquals(15, component.getQuantity());
        assertEquals("Updated storage description", component.getComment());
    }

    // Test 3: Delete a component from stock
    @Test
    public void testDeleteComponent() {
        Component component = new Component("Hardware","CPU", "Intel i7", 3, "High-performance CPU");
        stockList.add(component);
        stockList.remove(component);
        assertFalse(stockList.contains(component));
    }

}

