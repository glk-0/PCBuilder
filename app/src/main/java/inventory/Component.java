package inventory;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Component implements Serializable {
    private final String type;       // Hardware or Software, non-modifiable
    private final String subType;    // e.g., motherboard, case, software type, non-modifiable
    private final String title;      // Unique descriptive title, non-modifiable
    private int quantity;            // Modifiable quantity
    private String comment;          // Optional comment, modifiable
    private final LocalDateTime createdAt;   // Automatically set at creation, non-modifiable
    private LocalDateTime updatedAt;         // Automatically updated on changes

    // Constructor
    public Component(String type, String subType, String title, int quantity, String comment) {
        this.type = type;
        this.subType = subType;
        this.title = title;
        this.quantity = quantity;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters for non-modifiable fields
    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Getters and setters for modifiable fields
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        setUpdatedAt(); // Update the modification timestamp when quantity changes
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        setUpdatedAt(); // Update the modification timestamp when comment changes
    }

    // Method to set the updatedAt timestamp
    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now(); // Update the modification timestamp
    }

    // Method to set updated dates from the database
    public void setModificationDate(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt; // Allow setting updatedAt from DB
    }

    @Override
    public String toString() {
        return
                "type=" + type + '\n' +
                "subType=" + subType + '\n' +
                "title=" + title + '\n' +
                "quantity=" + quantity ;
    }
}
