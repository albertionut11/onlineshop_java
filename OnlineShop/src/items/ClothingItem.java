package items;

public class ClothingItem extends Item {
    private String fabric, color, style, season;
    public ClothingItem(String item_name, Brand brand, double price, String gender, String size, String fabric, String color, String style, String season) {
        super(item_name, brand, price, gender, size);
        this.fabric = fabric;
        this.color = color;
        this.style = style;
        this.season = season;
    }
    public ClothingItem(int item_id, String item_name, Brand brand, double price, String gender, String size, String fabric, String color, String style, String season) {
        super(item_id, item_name, brand, price, gender, size);
        this.fabric = fabric;
        this.color = color;
        this.style = style;
        this.season = season;
    }
    public ClothingItem(){}

    @Override
    public String toString() {
        return "\nClothingItem ID: " + getItemId() +
                "\nItem Name: " + getItemName() +
                "\nBrand: " + getBrand().getBrandName() +
                "\nPrice: " + getPrice() +
                "\nGender: " + getGender() +
                "\nSize: " + getSize() +
                "\nFabric: " + fabric +
                "\nColor: " + color +
                "\nStyle: " + style +
                "\nSeason: " + season;
    }

    // getters and setters for each additional attribute
    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
