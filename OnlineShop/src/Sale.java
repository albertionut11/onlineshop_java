import java.util.Date;
import java.util.List;

public class Sale {
    private static int next_id = 1;
    private int sale_id, customer_id;
    private List<ItemWrapper> items;
    private Date date;
    private double price;

    public Sale(List<ItemWrapper> items, int customer_id) {
        this.sale_id = next_id;
        this.customer_id = customer_id;
        this.items = items;
        this.date = new Date();
        next_id++;
    }

    public int getSaleId() {
        return sale_id;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public List<ItemWrapper> getItems() {
        return items;
    }

    public void setItems(List<ItemWrapper> items) {
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Sale ID: ").append(sale_id).append("\n");
        receipt.append("Customer ID: ").append(customer_id).append("\n");
        receipt.append("Date: ").append(date).append("\n");
        receipt.append("Items: \n");
        for (ItemWrapper it : items) {
            receipt.append("- ").append(it.getQuantity()).append("x ").append(it.getItem().getItemName()).append(": $").append(it.getItem().getPrice()*it.getQuantity()).append("\n");
        }
        receipt.append("Total price: $").append(price).append("\n");
        return receipt.toString();
    }

}
