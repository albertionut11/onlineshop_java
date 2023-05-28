package database;

import customers.Customer;

import items.Brand;
import items.ClothingItem;
import items.Item;
import items.ItemWrapper;
import items.ShoeItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import management.Inventory;
import management.Order;
import management.Sale;

public class DatabaseReaderService {
    private static DatabaseReaderService instance;
    private Connection connection;

    private DatabaseReaderService() {
        // Initialize the connection
        connection = DatabaseConnector.getConnection();
    }

    public static DatabaseReaderService getInstance() {
        if (instance == null) {
            synchronized (DatabaseReaderService.class) {
                if (instance == null) {
                    instance = new DatabaseReaderService();
                }
            }
        }
        return instance;
    }

    // Read methods for Order class
    public Order getOrderById(int orderId) {
        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ordertable WHERE order_id = ?");
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String shippingAddress = resultSet.getString("shipping_address");
                String paymentMethod = resultSet.getString("payment_method");

                // Create the Order object
                Order order = new Order(orderId, customerId, shippingAddress, paymentMethod);
                order.setStatus(resultSet.getString("status"));
                order.setDate(resultSet.getDate("date"));

                // Retrieve and add items to the Order
                List<ItemWrapper> items = getItemsByOrderId(orderId);
                order.setItems(items);

                AuditService.registerAction("Get order by id.");
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ordertable");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int customerId = resultSet.getInt("customer_id");
                String shippingAddress = resultSet.getString("shipping_address");
                String paymentMethod = resultSet.getString("payment_method");

                // Create the Order object
                Order order = new Order(orderId, customerId, shippingAddress, paymentMethod);
                order.setStatus(resultSet.getString("status"));
                order.setDate(resultSet.getDate("date"));

                // Retrieve and add items to the Order
                List<ItemWrapper> items = getItemsByOrderId(orderId);
                order.setItems(items);

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AuditService.registerAction("Get all orders.");
        return orders;
    }

    // Read methods for Sale class
    public Sale getSaleById(int saleId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM sale WHERE sale_id = ?");
            statement.setInt(1, saleId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                int orderId = resultSet.getInt("order_id");
                double price = resultSet.getDouble("price");

                // Create the Sale object
                Sale sale = new Sale(saleId, getItemsBySaleId(saleId), customerId, orderId);
                sale.setPrice(price);
                sale.setDate(resultSet.getDate("date"));

                AuditService.registerAction("Get sale by id");
                return sale;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM sale");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int saleId = resultSet.getInt("sale_id");
                int customerId = resultSet.getInt("customer_id");
                int orderId = resultSet.getInt("order_id");
                double price = resultSet.getDouble("price");

                // Create the Sale object
                Sale sale = new Sale(saleId, getItemsBySaleId(saleId), customerId, orderId);
                sale.setPrice(price);
                sale.setDate(resultSet.getDate("date"));

                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AuditService.registerAction("Get all sales");
        return sales;
    }

    // Read methods for Brand class
    public Brand getBrandById(int brandId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM brand WHERE brand_id = ?");
            statement.setInt(1, brandId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String brandName = resultSet.getString("brand_name");
                String keywords = resultSet.getString("keywords");
                String description = resultSet.getString("description");

                // Create the Brand object
                Brand brand = new Brand(brandId, brandName, keywords, description);
                brand.setBrandId(brandId);

                AuditService.registerAction("Get brand by id");
                return brand;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Brand> getAllBrands() {
        List<Brand> brands = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM brand");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int brandId = resultSet.getInt("brand_id");
                String brandName = resultSet.getString("brand_name");
                String keywords = resultSet.getString("keywords");
                String description = resultSet.getString("description");

                // Create the Brand object
                Brand brand = new Brand(brandId, brandName, keywords, description);
                brand.setBrandId(brandId);

                brands.add(brand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AuditService.registerAction("Get all brands");
        return brands;
    }

    // Read methods for Inventory class
    public Inventory getInventoryById(int inventoryId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM inventory WHERE inventory_id = ?");
            statement.setInt(1, inventoryId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String inventoryName = resultSet.getString("inventory_name");
                int capacity = resultSet.getInt("capacity");
                String manager = resultSet.getString("manager");
                String warehouse = resultSet.getString("warehouse");

                // Create the Inventory object
                Inventory inventory = new Inventory(inventoryId, inventoryName, capacity, manager, warehouse);
                inventory.setInventoryId(inventoryId);

                // Retrieve and set the item list for the Inventory
                List<ItemWrapper> items = getItemsByInventoryId(inventoryId);
                inventory.setItemList(items);

                AuditService.registerAction("Get inventory by id");
                return inventory;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Inventory> getAllInventories() {
        List<Inventory> inventories = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM inventory");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int inventoryId = resultSet.getInt("inventory_id");
                String inventoryName = resultSet.getString("inventory_name");
                int capacity = resultSet.getInt("capacity");
                String manager = resultSet.getString("manager");
                String warehouse = resultSet.getString("warehouse");

                // Create the Inventory object
                Inventory inventory = new Inventory(inventoryId, inventoryName, capacity, manager, warehouse);
                inventory.setInventoryId(inventoryId);

                // Retrieve and set the item list for the Inventory
                List<ItemWrapper> items = getItemsByInventoryId(inventoryId);
                inventory.setItemList(items);

                inventories.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AuditService.registerAction("Get all inventories");
        return inventories;
    }

    public ItemWrapper getItemWrapperById(int itemWrapperId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM itemwrapper WHERE iw_id = ?");
            statement.setInt(1, itemWrapperId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                int inventoryId = resultSet.getInt("inventory_id");
                int quantity = resultSet.getInt("quantity");

                ItemWrapper itemWrapper = new ItemWrapper(itemWrapperId, getItemById(itemId), quantity);
                itemWrapper.setInventory_id(inventoryId);

                AuditService.registerAction("Get ItemWrapper by id");
                return itemWrapper;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ItemWrapper> getAllItemWrappers() {
        List<ItemWrapper> itemWrappers = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM itemwrapper");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemWrapperId = resultSet.getInt("iw_id");
                int itemId = resultSet.getInt("item_id");
                int inventoryId = resultSet.getInt("inventory_id");
                int quantity = resultSet.getInt("quantity");

                ItemWrapper itemWrapper = new ItemWrapper(itemWrapperId, getItemById(itemId), quantity);
                itemWrapper.setInventory_id(inventoryId);

                itemWrappers.add(itemWrapper);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AuditService.registerAction("Get all ItemWrappers");
        return itemWrappers;
    }

    public Item getItemById(int itemId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE item_id = ?");
            statement.setInt(1, itemId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String itemName = resultSet.getString("item_name");
                double price = resultSet.getDouble("price");
                String brand = resultSet.getString("Brand");
                String gender = resultSet.getString("gender");
                String size = resultSet.getString("size");
                String itemType = getItemTypeById(itemId);
                // Create the Item object based on its type
                if (itemType.equals("C")) {
                    statement = connection.prepareStatement("SELECT * FROM clothingitem WHERE item_id = ?");
                    statement.setInt(1, itemId);
                    resultSet = statement.executeQuery();
                    if(resultSet.next()){
                        String fabric = resultSet.getString("fabric");
                        String color = resultSet.getString("color");
                        String style = resultSet.getString("style");
                        String season = resultSet.getString("season");

                        ClothingItem clothingItem = new ClothingItem(itemId, itemName, getBrandByName(brand), price, gender, size, fabric, color, style, season);
                        clothingItem.setItemId(itemId);

                        AuditService.registerAction("Get ClothingItem by id");
                        return clothingItem;
                    }
                } else if (itemType.equals("S")) {
                    statement = connection.prepareStatement("SELECT * FROM shoeitem WHERE item_id = ?");
                    statement.setInt(1, itemId);
                    resultSet = statement.executeQuery();
                    if(resultSet.next()){
                        String shoe_type = resultSet.getString("shoe_type");
                        String material = resultSet.getString("material");
                        double heel_height = resultSet.getDouble("heel_height");

                        ShoeItem shoeItem = new ShoeItem(itemId, itemName, getBrandByName(brand), price, gender, size, material, heel_height, shoe_type);
                        shoeItem.setItemId(itemId);
                        AuditService.registerAction("Get ShoeItem by id");
                        return shoeItem;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM item");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String itemName = resultSet.getString("item_name");
                double price = resultSet.getDouble("price");
                String brand = resultSet.getString("Brand");
                String gender = resultSet.getString("gender");
                String size = resultSet.getString("size");
                String itemType = getItemTypeById(itemId);
                Item item = null;
                if (itemType.equals("C")) {
                    statement = connection.prepareStatement("SELECT * FROM clothingitem WHERE item_id = ?");
                    statement.setInt(1, itemId);
                    ResultSet resultSet1 = statement.executeQuery();
                    if(resultSet1.next()){
                        String fabric = resultSet.getString("fabric");
                        String color = resultSet.getString("color");
                        String style = resultSet.getString("style");
                        String season = resultSet.getString("season");

                        item = new ClothingItem(itemId, itemName, getBrandByName(brand), price, gender, size, fabric, color, style, season);
                        }
                    } else if (itemType.equals("S")) {
                    statement = connection.prepareStatement("SELECT * FROM shoeitem WHERE item_id = ?");
                    statement.setInt(1, itemId);
                    ResultSet resultSet1 = statement.executeQuery();
                    if(resultSet1.next()){
                        String shoeType = resultSet.getString("shoe_type");
                        String material = resultSet.getString("material");
                        double heelHeight = resultSet.getInt("heel_height");

                        item = new ShoeItem(itemId, itemName, getBrandByName(brand), price, gender, size, material, heelHeight, shoeType);
                        }
                    } else {
                    System.out.println("Unrecognived item type!");
                    continue;
                }
                assert item != null;
                item.setItemId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AuditService.registerAction("Get all items");
        return items;
    }
    public List<ClothingItem> getAllClothingItems() {
        List<ClothingItem> items = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clothingitem");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                Item it = getItemById(itemId);
                String itemName = it.getItemName();
                double price = it.getPrice();
                String brand = it.getBrand().getBrandName();
                String gender = it.getGender();
                String size = it.getSize();

                ClothingItem item = new ClothingItem();
                statement = connection.prepareStatement("SELECT * FROM clothingitem WHERE item_id = ?");
                statement.setInt(1, itemId);
                ResultSet resultSet1 = statement.executeQuery();

                if(resultSet1.next()){
                    String fabric = resultSet.getString("fabric");
                    String color = resultSet.getString("color");
                    String style = resultSet.getString("style");
                    String season = resultSet.getString("season");

                    item = new ClothingItem(itemId, itemName, getBrandByName(brand), price, gender, size, fabric, color, style, season);
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AuditService.registerAction("Get all ClothingItems");
        return items;
    }

    public ClothingItem getClothingItemById(int itemId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clothingitem WHERE item_id = ?");
            statement.setInt(1, itemId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Item it = getItemById(itemId);
                String itemName = it.getItemName();
                double price = it.getPrice();
                String brand = it.getBrand().getBrandName();
                String gender = it.getGender();
                String size = it.getSize();

                String fabric = resultSet.getString("fabric");
                String color = resultSet.getString("color");
                String style = resultSet.getString("style");
                String season = resultSet.getString("season");

                ClothingItem clothingItem = new ClothingItem(itemId, itemName, getBrandByName(brand), price, gender, size, fabric, color, style, season);

                AuditService.registerAction("Get ClothingItem by id");
                return clothingItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ShoeItem> getAllShoeItems() {
        List<ShoeItem> items = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM shoeitem");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                Item it = getItemById(itemId);
                String itemName = it.getItemName();
                double price = it.getPrice();
                String brand = it.getBrand().getBrandName();
                String gender = it.getGender();
                String size = it.getSize();

                String material = resultSet.getString("material");
                double heelHeight = resultSet.getDouble("heel_height");
                String shoeType = resultSet.getString("shoe_type");

                ShoeItem item = new ShoeItem(itemId, itemName, getBrandByName(brand), price, gender, size, material, heelHeight, shoeType);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AuditService.registerAction("Get all ShoeItems");
        return items;
    }

    public ShoeItem getShoeItemById(int itemId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM shoeitem WHERE item_id = ?");
            statement.setInt(1, itemId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Item it = getItemById(itemId);
                String itemName = it.getItemName();
                double price = it.getPrice();
                String brand = it.getBrand().getBrandName();
                String gender = it.getGender();
                String size = it.getSize();

                String material = resultSet.getString("material");
                double heelHeight = resultSet.getDouble("heel_height");
                String shoeType = resultSet.getString("shoe_type");

                ShoeItem shoeItem = new ShoeItem(itemId, itemName, getBrandByName(brand), price, gender, size, material, heelHeight, shoeType);

                AuditService.registerAction("Get ShoeItem by id");
                return shoeItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Read methods for Customer class
    public Customer getCustomerById(int customerId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE customer_id = ?");
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String customer_name = resultSet.getString("customer_name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("mail");
                String phoneNumber = resultSet.getString("phone");

                // Create the Customer object
                Customer customer = new Customer(customerId, customer_name, age, gender, phoneNumber, email);

                AuditService.registerAction("Get customer by id");
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String customer_name = resultSet.getString("customer_name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("mail");
                String phoneNumber = resultSet.getString("phone");

                // Create the Customer object
                Customer customer = new Customer(customerId, customer_name, age, gender, phoneNumber, email);

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AuditService.registerAction("Get all customers");
        return customers;
    }


    // Helper methods to retrieve items associated with Order, Sale, and Inventory
    public List<ItemWrapper> getItemsByOrderId(int orderId) {
        List<ItemWrapper> items = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_items WHERE order_id = ?");
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int iw_id = resultSet.getInt("iw_id");
                ItemWrapper itemWrapper = getItemWrapperById(iw_id);
                items.add(itemWrapper);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<ItemWrapper> getItemsBySaleId(int saleId) {
        List<ItemWrapper> items = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM sale WHERE sale_id = ?");
            statement.setInt(1, saleId);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                int order_id = resultSet.getInt("order_id");
                statement = connection.prepareStatement("SELECT * FROM order_items WHERE order_id = ?");
                statement.setInt(1, order_id);
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int iw_id =  resultSet.getInt("iw_id");
                    ItemWrapper itemWrapper = getItemWrapperById(iw_id);
                    items.add(itemWrapper);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<ItemWrapper> getItemsByInventoryId(int inventoryId) {
        List<ItemWrapper> items = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM itemwrapper WHERE inventory_id = ?");
            statement.setInt(1, inventoryId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int iw_id = resultSet.getInt("iw_id");
                ItemWrapper itemWrapper = getItemWrapperById(iw_id);
                items.add(itemWrapper);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public String getItemTypeById(int itemId) {
        String itemType = null;

        // Check in ClothingItem table
        String clothingItemQuery = "SELECT * FROM clothingitem WHERE item_id = ?";
        try (PreparedStatement clothingItemStatement = connection.prepareStatement(clothingItemQuery)) {
            clothingItemStatement.setInt(1, itemId);

            ResultSet clothingItemResult = clothingItemStatement.executeQuery();
            if (clothingItemResult.next()) {
                itemType = "C";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check in ShoeItem table if itemType is still null
        if (itemType == null) {
            String shoeItemQuery = "SELECT * FROM shoeitem WHERE item_id = ?";
            try (PreparedStatement shoeItemStatement = connection.prepareStatement(shoeItemQuery)) {
                shoeItemStatement.setInt(1, itemId);

                ResultSet shoeItemResult = shoeItemStatement.executeQuery();
                if (shoeItemResult.next()) {
                    itemType = "S";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return itemType;
    }

    public Brand getBrandByName(String brandName) {
        Brand brand = null;
        String query = "SELECT * FROM brand WHERE brand_name = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, brandName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int brandId = resultSet.getInt("brand_id");
                String keywords = resultSet.getString("keywords");
                String description = resultSet.getString("description");

                brand = new Brand(brandId, brandName, keywords, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brand;
    }

    public int getMaxItemId() {
        int maxItemId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(item_id) AS max_id FROM item");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxItemId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxItemId;
    }

    public int getMaxBrandId() {
        int maxBrandId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(brand_id) AS max_id FROM brand");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxBrandId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxBrandId;
    }

    public int getMaxIWId() {
        int maxIWId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(iw_id) AS max_id FROM itemwrapper");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxIWId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxIWId;
    }


    public int getMaxCustomerId() {
        int maxCustomerId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(customer_id) AS max_id FROM customer");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxCustomerId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxCustomerId;
    }

    public int getMaxInventoryId() {
        int maxInventoryId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(inventory_id) AS max_id FROM inventory");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxInventoryId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxInventoryId;
    }

    public int getMaxOrderId() {
        int maxOrderId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(order_id) AS max_id FROM ordertable");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxOrderId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxOrderId;
    }

    public int getMaxSaleId() {
        int maxSaleId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(sale_id) AS max_id FROM sale");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxSaleId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxSaleId;
    }

}
