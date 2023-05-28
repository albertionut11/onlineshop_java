package items;

import database.DatabaseReaderService;

import java.math.BigDecimal;

public class Item {
    private static int next_id = 1;

    private int item_id;
    private String item_name, gender, size;
    private double price;

    Brand brand;
    /// Constructor when actually creating a new Item
    public Item(String item_name, Brand brand, double price, String gender, String size) {
        this.item_id = DatabaseReaderService.getInstance().getMaxItemId() + 1;
        this.item_name = item_name;
        this.brand = brand;
        this.price = price;
        this.gender = gender;
        this.size = size;
    }
    /// Constructor just for copying an existing Item
    /// Same logic will apply for all the other objects
    public Item(int item_id, String item_name, Brand brand, double price, String gender, String size) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.brand = brand;
        this.price = price;
        this.gender = gender;
        this.size = size;
    }

    public Item(){}

    // getters and setters for each attribute
    public int getItemId() {
        return item_id;
    }

    public void setItemId(int itemId){ this.item_id = itemId;}

    public String getItemName() {
        return item_name;
    }

    public void setItemName(String item_name) {
        this.item_name = item_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Brand getBrand() {
        return this.brand;
    }
    public void setBrand(Brand brand){
        this.brand.setBrandId(brand.getBrandId());
        this.brand.setBrandName(brand.getBrandName());
        this.brand.setDescription(brand.getDescription());
        this.brand.setKeywords(brand.getKeywords());
    }
}
