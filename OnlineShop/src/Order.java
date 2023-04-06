import java.util.*;

public class Order {
    private static int next_id = 1;
    private int order_id, customer_id;
    private Set<ItemWrapper> items;
    private String shipping_address, status, payment_method;
    private Date date;

    public Order(int customer_id, String shipping_address, String payment_method) {
        this.order_id = next_id;
        this.customer_id = customer_id;
        this.shipping_address = shipping_address;
        this.status = "Processing";
        this.payment_method = payment_method;
        this.date = new Date();
        this.items = new HashSet<>();
        next_id++;
    }

    public int getOrderId() {
        return order_id;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public List<ItemWrapper> getItems() {
        return new ArrayList<>(items);
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
