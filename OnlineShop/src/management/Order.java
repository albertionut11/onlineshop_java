package management;

import database.DatabaseReaderService;
import items.Item;
import items.ItemWrapper;

import java.util.*;

public class Order {
    private static int next_id = 1;
    private int order_id, customer_id;
    private ArrayList<ItemWrapper> items;
    private String shipping_address, status, payment_method;
    private Date date;

    public Order(int customer_id, String shipping_address, String payment_method) {
        this.order_id = DatabaseReaderService.getInstance().getMaxOrderId() + 1;
        this.customer_id = customer_id;
        this.shipping_address = shipping_address;
        this.status = "Processing";
        this.payment_method = payment_method;
        this.date = new Date();
        this.items = new ArrayList<>();
    }

    public Order(int order_id, int customer_id, String shipping_address, String payment_method) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.shipping_address = shipping_address;
        this.status = "Processing";
        this.payment_method = payment_method;
        this.date = new Date();
        this.items = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(order_id).append("\n");
        sb.append("Customer ID: ").append(customer_id).append("\n");
        sb.append("Customer Name: ").append(DatabaseReaderService.getInstance().getCustomerById(customer_id).getCustomerName()).append('\n');
        sb.append("Shipping Address: ").append(shipping_address).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Payment Method: ").append(payment_method).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("Items:\n");

        for (ItemWrapper itemWrapper : items) {
            sb.append("- ").append(itemWrapper.getItem().getItemName())
                    .append(" ").append(itemWrapper.getItem().getBrand().getBrandName())
                    .append(" (Quantity: ").append(itemWrapper.getQuantity()).append(")\n");
        }

        return sb.toString();
    }


    public int getOrderId() {
        return order_id;
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

    public void addItem(Item item, int quantity) {
        items.add(new ItemWrapper(item,quantity));
    }

    public String getShippingAddress() {
        return shipping_address;
    }

    public void setShippingAddress(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return payment_method;
    }

    public void setPaymentMethod(String payment_method) {
        this.payment_method = payment_method;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }
    public void setItems(List<ItemWrapper> items) {
        this.items.addAll(items);
    }
    public List<Item> sortItemsByPrice() {
        List<Item> sortedItems = new ArrayList<>();
        for (ItemWrapper i : items){
            sortedItems.add(i.getItem());
        }
        sortedItems.sort(new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                if (item1.getPrice() < item2.getPrice()) {
                    return -1;
                } else if (item1.getPrice() > item2.getPrice()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return sortedItems;
    }
}
