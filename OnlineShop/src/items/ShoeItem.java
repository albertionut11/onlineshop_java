package items;

public class ShoeItem extends Item {
    private String material, shoe_type;
    private double heel_height;

    public ShoeItem(String item_name, Brand brand, double price, String gender, String size, String material, double heel_height, String shoe_type) {
        super(item_name, brand, price, gender, size);
        this.material = material;
        this.heel_height = heel_height;
        this.shoe_type = shoe_type;
    }

    public ShoeItem(int item_id, String item_name, Brand brand, double price, String gender, String size, String material, double heel_height, String shoe_type) {
        super(item_id, item_name, brand, price, gender, size);
        this.material = material;
        this.heel_height = heel_height;
        this.shoe_type = shoe_type;
    }
    public ShoeItem(){}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nShoeItem: ").append(getItemId())
                .append("\nItem Name: ").append(getItemName())
                .append("\nBrand: ").append(getBrand().getBrandName())
                .append("\nPrice: ").append(getPrice())
                .append("\nGender: ").append(getGender())
                .append("\nSize: ").append(getSize())
                .append("\nMaterial: ").append(material)
                .append("\nHeel Height: ").append(heel_height)
                .append("\nShoe Type: ").append(shoe_type);
        return sb.toString();
    }

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
