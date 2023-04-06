import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // create some brands
        Brand nike = new Brand("Nike", "sports", "Just do it.");
        Brand adidas = new Brand("Adidas", "sports", "Impossible is nothing.");
        Brand zara = new Brand("Zara", "fashion", "Fashion made simple.");

        // create some items
        ClothingItem shirt = new ClothingItem("Shirt", nike, 30, "Male", "L", "Cotton", "White", "Casual", "Summer");
        ShoeItem sneakers = new ShoeItem("Sneakers", adidas, 100, "Male", "42", "Leather", 2.2, "Running");
        ClothingItem pants = new ClothingItem("Pants", zara, 50, "Female", "M", "Denim", "Blue", "Slim-fit", "Fall");

        // create an inventory
        List<ItemWrapper> itemList = new ArrayList<>();
        itemList.add(new ItemWrapper(shirt,100));
        itemList.add(new ItemWrapper(sneakers,200));
        itemList.add(new ItemWrapper(pants, 400));
        Inventory inventory = new Inventory("Sports and Fashion", 100,  "Mary Smith", "Main Warehouse");
        inventory.setItemList(itemList);
        inventory.ShowInventory();

        // create a customer
        Customer customer = new Customer("Vasile Cumparatoru", 24,  "Male", "0741231231", "vasile.cumparatoru@example.com");

        // create an order
        Order order = new Order(customer.getCustomerId(), "Strada Cumparatorilor 420", "Credit Card");
        order.addItem(shirt,10);
        order.addItem(sneakers,20);
        order.addItem(pants, 40);
        // create a sale
        Sale sale = new Sale(order.getItems(), customer.getCustomerId());

        // create a service and perform some operations
        Service service = new Service();
        System.out.println("Total inventory value: " + service.calculateInventoryValue(inventory));
        System.out.println("Customer points before purchase: " + customer.getPoints());
        service.processSale(customer, sale, inventory);
        inventory.ShowInventory();
        System.out.println("Customer points after purchase: " + customer.getPoints());
        System.out.println("Number of male clothing items in inventory: " + service.countMaleClothingItems(inventory));
        System.out.println("Number of Adidas items in inventory: " + service.countBrandItems(inventory, adidas));
        System.out.println("Items in order sorted by price:");
        List<Item> sortedItems = service.sortItemsByPrice(itemList);
        for (Item item : sortedItems) {
            System.out.println(item.getItemName() + ": $" + item.getPrice());
        }
    }
}
