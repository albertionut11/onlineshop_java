public class ShoeItem extends Item {
    private String material, shoe_type;
    private double heel_height;

    public ShoeItem(String item_name, Brand brand, double price, String gender, String size, String material, double heel_height, String shoe_type) {
        super(item_name, brand, price, gender, size);
        this.material = material;
        this.heel_height = heel_height;
        this.shoe_type = shoe_type;
    }
    public ShoeItem(){}

    // getters and setters for each additional attribute
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getHeelHeight() {
        return heel_height;
    }

    public void setHeelHeight(double heel_height) {
        this.heel_height = heel_height;
    }

    public String getShoeType() {
        return shoe_type;
    }

    public void setShoeType(String shoe_type) {
        this.shoe_type = shoe_type;
    }
}
