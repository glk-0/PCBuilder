package com.example.groupe_39;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import entities.Administrator;
import entities.Requester;

public class AdministratorUnitTest {

    private Administrator admin;

    @Before
    public void setUp() {
        // Initialize an Administrator with some sample data
        admin = new Administrator(1, "Admin", "User", "admin@example.com", "admin123", "Administrator");
    }

    // Test 1: Add a new Requester to the list
    @Test
    public void testAddRequester() {
        Requester newRequester = new Requester(2, "John", "Doe", "john.doe@example.com", "password123", "Requester");
        admin.requesterList.add(newRequester);
        assertTrue(admin.requesterList.contains(newRequester));
    }

    // Test 2: Remove an existing Requester from the list
    @Test
    public void testRemoveRequester() {
        Requester existingRequester = new Requester(3, "Jane", "Smith", "jane.smith@example.com", "pass456", "Requester");
        admin.requesterList.add(existingRequester);
        admin.requesterList.remove(existingRequester);
        assertFalse(admin.requesterList.contains(existingRequester));
    }

    // Test 3: Modify a Requester's details
    @Test
    public void testModifyRequester() {
        Requester requester = new Requester(4, "Alex", "Johnson", "alex.j@example.com", "myPass", "Requester");
        admin.requesterList.add(requester);
        // Update details
        requester.setFirstName("Alexander");
        requester.setLastName("John");
        assertEquals("Alexander", requester.getFirstName());
        assertEquals("John", requester.getLastName());
    }

    // Test 4: Ensure unique Requester IDs
    @Test
    public void testUniqueRequesterIds() {
        Requester req1 = new Requester(5, "Chris", "Miller", "chris@example.com", "password", "Requester");
        Requester req2 = new Requester(5, "Sam", "Hill", "sam@example.com", "password123", "Requester");
        admin.requesterList.add(req1);
        boolean isUnique = admin.requesterList.stream().noneMatch(req -> req.getUserId() == req2.getUserId());
        assertFalse(isUnique);
    }
}
