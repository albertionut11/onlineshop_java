public class Item {
    private static int next_id = 1;

    private int item_id;
    private String item_name, gender, size;
    private double price;

    Brand brand;
    public Item(String item_name, Brand brand, double price, String gender, String size) {
        this.item_id = next_id++;
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
}
