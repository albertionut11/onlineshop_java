package items;

import database.DatabaseReaderService;

public class Brand {
    private static int next_id = 1;
    private String brand_name, keywords, description;
    private int brand_id;

    public Brand(String brand_name, String keywords, String description) {
        this.brand_name = brand_name;
        this.brand_id = DatabaseReaderService.getInstance().getMaxBrandId() + 1;
        this.keywords = keywords;
        this.description = description;
    }

    public Brand(int brand_id, String brand_name, String keywords, String description) {
        this.brand_name = brand_name;
        this.brand_id = brand_id;
        this.keywords = keywords;
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nBrand ID: ").append(brand_id)
                .append("\nBrand Name: ").append(brand_name)
                .append("\nKeywords: ").append(keywords)
                .append("\nDescription: ").append(description);
        return sb.toString();
    }

    public String getBrandName() {
        return brand_name;
    }

    public void setBrandName(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getBrandId() {
        return brand_id;
    }

    public void setBrandId(int brandId) { this.brand_id = brand_id; }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
