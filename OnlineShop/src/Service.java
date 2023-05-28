import customers.Customer;
import items.Brand;
import items.ClothingItem;
import items.Item;
import items.ItemWrapper;
import management.Inventory;
import management.Order;
import management.Sale;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Service {

    private List<Inventory> inventories;
    private List<Customer> customers;
    private List<Sale> sales;
    private List<Order> orders;
    private List<Brand> brands;

    public Service() {
        inventories = new ArrayList<>();
        customers = new ArrayList<>();
        sales = new ArrayList<>();
        orders = new ArrayList<>();
        brands = new ArrayList<>();
    }

    // Inventory operations
    public void addInventory(Inventory inventory) {
        inventories.add(inventory);
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void removeInventory(Inventory inventory) {
        inventories.remove(inventory);
    }

    // Customer operations
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    // Sale operations
    public void addSale(Sale sale) {
        sales.add(sale);
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void removeSale(Sale sale) {
        sales.remove(sale);
    }

    // Order operations
    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    // Brand operations
    public void addBrand(Brand brand) {
        brands.add(brand);
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void removeBrand(Brand brand) {
        brands.remove(brand);
    }

    // Sorting by price
    public List<Item> sortItemsByPrice(List<ItemWrapper> items) {
        List<Item> sortedItems = new ArrayList<>();
        for (ItemWrapper i : items){
            sortedItems.add(i.getItem());
        }
        sortedItems.sort(Comparator.comparingDouble(Item::getPrice));
        return sortedItems;
    }

    // Method 1: search for an item by name
    public List<Item> searchItem(String itemName) {
        List<Item> matchingItems = new ArrayList<>();
        for (Inventory inventory : inventories) {
            for (ItemWrapper it : inventory.getItemList()) {
                if (it.getItem().getItemName().equals(itemName)) {
                    matchingItems.add(it.getItem());
                }
            }
        }
        return matchingItems;
    }

    // Method 2: calculate total sale price of a list of items
    public double calculateTotalSalePrice(List<ItemWrapper> items) {
        double totalSalePrice = 0;
        for (ItemWrapper it : items) {
            totalSalePrice += (it.getItem().getPrice() * it.getQuantity());
        }
        return totalSalePrice;
    }

    // Method 3: get customers by age
    public List<Customer> getCustomersByAge(int age) {
        List<Customer> matchingCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getAge() == age) {
                matchingCustomers.add(customer);
            }
        }
        return matchingCustomers;
    }

    // Method 5: calculate points earned by purchasing items
    public int calculatePointsEarned(List<Item> items) {
        int totalPoints = 0;
        for (Item item : items) {
            totalPoints += 10;
        }
        return totalPoints;
    }

    // Method 6: update order status
    public void updateOrderStatus(int orderId, String newStatus) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                order.setStatus(newStatus);
                break;
            }
        }
    }

    // Method 7: get inventory by name
    public Inventory getInventoryByName(String inventoryName) {
        for (Inventory inventory : inventories) {
            if (inventory.getInventoryName().equals(inventoryName)) {
                return inventory;
            }
        }
        return null;
    }

    // Method 8: get items by brand
    public List<Item> getItemsByBrand(Brand brand) {
        List<Item> matchingItems = new ArrayList<>();
        for (Inventory inventory : inventories) {
            for (ItemWrapper it : inventory.getItemList()) {
                if (it.getItem().getBrand().equals(brand)) {
                    matchingItems.add(it.getItem());
                }
            }
        }
        return matchingItems;
    }

    public double calculateInventoryValue(Inventory inventory) {
        double value = 0;
        for (ItemWrapper item : inventory.getItemList()) {
            value += item.getQuantity() * item.getItem().getPrice();
        }
        return value;
    }

    public int countMaleClothingItems(Inventory inventory) {
        int count = 0;
        for (ItemWrapper it : inventory.getItemList()) {
            if (it.getItem() instanceof ClothingItem clothingItem) {
                if (clothingItem.getGender().equalsIgnoreCase("Male")) {
                    count += it.getQuantity();
                }
            }
        }
        return count;
    }

    public int countBrandItems(Inventory inventory, Brand brand) {
        int count = 0;
        for (ItemWrapper it : inventory.getItemList()) {
            if (it.getItem().getBrand().equals(brand)) {
                count += it.getQuantity();
            }
        }
        return count;
    }
    public void processSale(Customer customer, Sale sale, Inventory inventory) {
        // Validate sale items are available in inventory
        for (ItemWrapper it : sale.getItems()) {
            if (!inventory.hasItem(it.getItem())) {
                System.out.println("Item " + it.getItem().getItemName() + " is not available in inventory.");
                return;
            }
        }

        // Calculate total price of the sale
        double totalPrice = calculateTotalSalePrice(sale.getItems());
        customer.addPoints(totalPrice);
        int customerPoints = customer.getPoints();
        if (customerPoints >= 50) {
            double discount = totalPrice * 0.1;
            totalPrice -= discount;
            System.out.println("Congratulations! You have earned a 10% discount on your purchase.");
        }

//         Update inventory and customer information
        for (ItemWrapper item : sale.getItems()) {
            inventory.DecreaseQuantity(item.getItem(), item.getQuantity());
        }

        // Record the sale and print out the receipt
        sale.setPrice(totalPrice);
        System.out.println("Thank you for your purchase! Here's your receipt:");
        System.out.println(sale.getReceipt());
    }
}

