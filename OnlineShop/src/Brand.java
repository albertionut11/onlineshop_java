public class Brand {
    private static int next_id = 1;
    private String brand_name, keywords, description;
    private int brand_id;

    public Brand(String brand_name, String keywords, String description) {
        this.brand_name = brand_name;
        this.brand_id = next_id++;
        this.keywords = keywords;
        this.description = description;
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
