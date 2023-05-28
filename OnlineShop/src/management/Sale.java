package management;

import database.DatabaseReaderService;
import database.DatabaseWriterService;
import items.ItemWrapper;

import java.util.Date;
import java.util.List;

public class Sale {
    private static int next_id = 1;
    private int sale_id, customer_id, order_id;
    private List<ItemWrapper> items;
    private Date date;
    private double price;

    public Sale(List<ItemWrapper> items, int customer_id, int order_id) {
        this.sale_id = DatabaseReaderService.getInstance().getMaxSaleId() + 1;
        this.customer_id = customer_id;
        this.items = items;
        this.date = new Date();
        this.order_id = order_id;
        Order o = DatabaseReaderService.getInstance().getOrderById(order_id);
        o.setStatus("Done");
        DatabaseWriterService.getInstance().updateOrder(o , items);
    }

    public Sale(int sale_id, List<ItemWrapper> items, int customer_id, int order_id) {
        this.sale_id = sale_id;
        this.customer_id = customer_id;
        this.items = items;
        this.date = new Date();
        this.order_id = order_id;
        Order o = DatabaseReaderService.getInstance().getOrderById(order_id);
        o.setStatus("Done");
        DatabaseWriterService.getInstance().updateOrder(o , items);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sale ID: ").append(sale_id).append("\n");
        sb.append("Customer Name: ").append(DatabaseReaderService.getInstance().getCustomerById(customer_id).getCustomerName()).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("Items:\n");

        for (ItemWrapper itemWrapper : items) {
            sb.append("- ").append(itemWrapper.getQuantity()).append("x ")
                    .append(itemWrapper.getItem().getItemName()).append(" ").append(itemWrapper.getItem().getBrand().getBrandName()).append(": ")
                    .append(itemWrapper.getItem().getPrice() * itemWrapper.getQuantity())
                    .append("\n");
            this.price += itemWrapper.getItem().getPrice() * itemWrapper.getQuantity();
        }

        sb.append("Total price: ").append(price).append("$\n");

        return sb.toString();
    }

    public int getSaleId() {
        return sale_id;
    }

    public int getCustomerId() {
        return customer_id;
    }
    public void setCustomerId(int customerId) {
        this.customer_id = customerId;
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

    public void setOrderId(int order_id){ this.order_id = order_id;}
    public int getOrderId(){return order_id;}

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

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

}
