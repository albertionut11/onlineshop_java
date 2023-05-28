package database;

import customers.Customer;

import items.Brand;
import items.ClothingItem;
import items.Item;
import items.ItemWrapper;
import items.ShoeItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import management.Inventory;
import management.Order;
import management.Sale;

public class DatabaseWriterService {
    private static DatabaseWriterService instance;
    private Connection connection;

    private DatabaseWriterService() {
        // Initialize the connection
        connection = DatabaseConnector.getConnection();
    }

    public static DatabaseWriterService getInstance() {
        if (instance == null) {
            synchronized (DatabaseWriterService.class) {
                if (instance == null) {
                    instance = new DatabaseWriterService();
                }
            }
        }
        return instance;
    }

    // Add methods for writing to the database
    // Helper methods
    private void createItem(Item item) {
        try {
            String insertItemSQL = "INSERT INTO item (item_id, item_name, Brand, price, gender, size) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertItemSQL);
            statement.setInt(1, item.getItemId());
            statement.setString(2, item.getItemName());
            statement.setString(3, item.getBrand().getBrandName());
            statement.setDouble(4, item.getPrice());
            statement.setString(5, item.getGender());
            statement.setString(6, item.getSize());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    private void updateItem(Item item) {
        try {
            String updateItemSQL = "UPDATE item SET item_name = ?, Brand = ?, price = ?, gender = ?, size = ? WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateItemSQL);
            statement.setString(1, item.getItemName());
            statement.setString(2, item.getBrand().getBrandName());
            statement.setDouble(3, item.getPrice());
            statement.setString(4, item.getGender());
            statement.setString(5, item.getSize());
            statement.setInt(6, item.getItemId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }
    private void deleteItem(int itemId) {
        try {
            String deleteItemSQL = "DELETE FROM item WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteItemSQL);
            statement.setInt(1, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void createClothingItem(ClothingItem clothingItem) {
        try {
            // Create the item first
            createItem(clothingItem);
            AuditService.registerAction("Create ClothingItem");
            // Insert the clothing item
            String insertClothingItemSQL = "INSERT INTO clothingitem (item_id, fabric, color, style, season) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertClothingItemSQL);
            statement.setInt(1, clothingItem.getItemId());
            statement.setString(2, clothingItem.getFabric());
            statement.setString(3, clothingItem.getColor());
            statement.setString(4, clothingItem.getStyle());
            statement.setString(5, clothingItem.getSeason());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void updateClothingItem(ClothingItem clothingItem) {
        try {
            // Update the item
            updateItem(clothingItem);
            AuditService.registerAction("Update ClothingItem");
            // Update the clothing item
            String updateClothingItemSQL = "UPDATE clothingitem SET fabric = ?, color = ?, style = ?, season = ? WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateClothingItemSQL);
            statement.setString(1, clothingItem.getFabric());
            statement.setString(2, clothingItem.getColor());
            statement.setString(3, clothingItem.getStyle());
            statement.setString(4, clothingItem.getSeason());
            statement.setInt(5, clothingItem.getItemId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void deleteClothingItem(int itemId) {
        try {
            // Delete the clothing item
            AuditService.registerAction("Delete ClothingItem");
            String deleteClothingItemSQL = "DELETE FROM clothingitem WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteClothingItemSQL);
            statement.setInt(1, itemId);
            statement.executeUpdate();

            // Delete the item
            deleteItem(itemId);
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void createShoeItem(ShoeItem shoeItem) {
        try {
            // Create the item first
            createItem(shoeItem);
            AuditService.registerAction("Create ShoeItem");
            // Insert the shoe item
            String insertShoeItemSQL = "INSERT INTO shoeitem (item_id, material, heel_height, shoe_type) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertShoeItemSQL);
            statement.setInt(1, shoeItem.getItemId());
            statement.setString(2, shoeItem.getMaterial());
            statement.setDouble(3, shoeItem.getHeelHeight());
            statement.setString(4, shoeItem.getShoeType());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void updateShoeItem(ShoeItem shoeItem) {
        try {
            // Update the item
            updateItem(shoeItem);
            AuditService.registerAction("Update ShoeItem");
            // Update the shoe item
            String updateShoeItemSQL = "UPDATE shoeitem SET material = ?, heel_height = ?, shoe_type = ? WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateShoeItemSQL);
            statement.setString(1, shoeItem.getMaterial());
            statement.setDouble(2, shoeItem.getHeelHeight());
            statement.setString(3, shoeItem.getShoeType());
            statement.setInt(4, shoeItem.getItemId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void deleteShoeItem(int itemId) {
        try {
            // Delete the shoe item
            AuditService.registerAction("Delete ShoeItem");
            String deleteShoeItemSQL = "DELETE FROM shoeitem WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteShoeItemSQL);
            statement.setInt(1, itemId);
            statement.executeUpdate();

            // Delete the item
            deleteItem(itemId);
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void createBrand(Brand brand) {
        try {
            AuditService.registerAction("Create Brand");
            String insertBrandSQL = "INSERT INTO brand (brand_id, brand_name, keywords, description) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertBrandSQL);
            statement.setInt(1, brand.getBrandId());
            statement.setString(2, brand.getBrandName());
            statement.setString(3, brand.getKeywords());
            statement.setString(4, brand.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void updateBrand(Brand brand) {
        try {
            AuditService.registerAction("Update Brand");
            String updateBrandSQL = "UPDATE brand SET brand_name = ?, keywords = ?, description = ? WHERE brand_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateBrandSQL);
            statement.setString(1, brand.getBrandName());
            statement.setString(2, brand.getKeywords());
            statement.setString(3, brand.getDescription());
            statement.setInt(4, brand.getBrandId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void deleteBrand(int brandId) {
        try {
            AuditService.registerAction("Delete Brand");
            String deleteBrandSQL = "DELETE FROM brand WHERE brand_id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteBrandSQL);
            statement.setInt(1, brandId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void createInventory(Inventory inventory) {
        try {
            AuditService.registerAction("Create Inventory");
            String insertInventorySQL = "INSERT INTO inventory (inventory_id, inventory_name, capacity, manager, warehouse) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertInventorySQL);
            statement.setInt(1, inventory.getInventoryId());
            statement.setString(2, inventory.getInventoryName());
            statement.setInt(3, inventory.getCapacity());
            statement.setString(4, inventory.getManager());
            statement.setString(5, inventory.getWarehouse());
            statement.executeUpdate();

            // Save the ItemWrapper details
            List<ItemWrapper> itemWrappers = inventory.getItemList();
            for (ItemWrapper itemWrapper : itemWrappers) {
                createItemWrapper(itemWrapper, inventory.getInventoryId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void createItemWrapper(ItemWrapper itemWrapper, int inventoryId) throws SQLException {
        AuditService.registerAction("Create ItemWrapper");
        String insertItemWrapperSQL = "INSERT INTO itemwrapper (iw_id, quantity, inventory_id, item_id) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertItemWrapperSQL);
        statement.setInt(1, itemWrapper.getIw_id());
        statement.setInt(2, itemWrapper.getQuantity());
        statement.setInt(3, inventoryId);
        statement.setInt(4, itemWrapper.getItem().getItemId());
        statement.executeUpdate();
    }

    public void updateInventory(Inventory inventory) {
        try {
            AuditService.registerAction("Update Inventory");
            String updateInventorySQL = "UPDATE inventory SET inventory_name = ?, capacity = ?, manager = ?, warehouse = ? WHERE inventory_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateInventorySQL);
            statement.setString(1, inventory.getInventoryName());
            statement.setInt(2, inventory.getCapacity());
            statement.setString(3, inventory.getManager());
            statement.setString(4, inventory.getWarehouse());
            statement.setInt(5, inventory.getInventoryId());
            statement.executeUpdate();

            // Update ItemWrapper details
            List<ItemWrapper> itemWrappers = inventory.getItemList();
            for (ItemWrapper itemWrapper : itemWrappers) {
                itemWrapper.setInventory_id(inventory.getInventoryId());
                updateItemWrapper(itemWrapper);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void updateItemWrapper(ItemWrapper itemWrapper) throws SQLException {
        AuditService.registerAction("Update ItemWrapper");
        String updateItemWrapperSQL = "UPDATE itemwrapper SET quantity = ?, inventory_id = ?, item_id = ? WHERE iw_id = ?";
        PreparedStatement statement = connection.prepareStatement(updateItemWrapperSQL);
        statement.setInt(1, itemWrapper.getQuantity());
        statement.setInt(2, itemWrapper.getInventory_id());
        statement.setInt(3, itemWrapper.getItem_id());
        statement.setInt(4, itemWrapper.getIw_id());
        statement.executeUpdate();
    }

    public void deleteInventory(int inventoryId) {
        try {
            AuditService.registerAction("Delete Inventory");
            String deleteInventorySQL = "DELETE FROM inventory WHERE inventory_id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteInventorySQL);
            statement.setInt(1, inventoryId);
            statement.executeUpdate();

            // Delete associated ItemWrappers
            deleteItemWrappersByInventoryId(inventoryId);
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
        }
    }

    public void deleteItemWrapper(int itemWrapperId) throws SQLException {
        AuditService.registerAction("Delete ItemWrapper");
        String deleteItemWrapperSQL = "DELETE FROM ItemWrapper WHERE iw_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(deleteItemWrapperSQL);
            statement.setInt(1, itemWrapperId);
            statement.executeUpdate();

            System.out.println("ItemWrapper deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting ItemWrapper: " + e.getMessage());
            throw e;
        }
    }


    public void deleteItemWrappersByInventoryId(int inventoryId) throws SQLException {
        AuditService.registerAction("Delete ItemWrapper");
        String deleteItemWrappersSQL = "DELETE FROM itemwrapper WHERE inventory_id = ?";
        PreparedStatement statement = connection.prepareStatement(deleteItemWrappersSQL);
        statement.setInt(1, inventoryId);
        statement.executeUpdate();
    }

    public void createOrder(Order order, List<ItemWrapper> items) {
        AuditService.registerAction("Create Order");
        String query = "INSERT INTO ordertable (order_id, customer_id, shipping_address, status, payment_method, date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        /// Inserting into ordertable
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getOrderId());
            statement.setInt(2, order.getCustomerId());
            statement.setString(3, order.getShippingAddress());
            statement.setString(4, order.getStatus());
            statement.setString(5, order.getPaymentMethod());
            statement.setDate(6, new java.sql.Date(order.getDate().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ///Inserting into order_items
        for(ItemWrapper iw : items){
            query = "INSERT INTO order_items (order_id, iw_id) VALUES(?, ?);";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1, order.getOrderId());
                statement.setInt(2, iw.getIw_id());
                statement.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void updateOrder(Order order, List<ItemWrapper> items) {
        AuditService.registerAction("Update Order");
        String query = "UPDATE ordertable SET customer_id = ?, shipping_address = ?, status = ?, payment_method = ? WHERE order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getCustomerId());
            statement.setString(2, order.getShippingAddress());
            statement.setString(3, order.getStatus());
            statement.setString(4, order.getPaymentMethod());
            statement.setInt(5, order.getOrderId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ///Updating order_items
//        for(ItemWrapper iw : items){
//            query = "UPDATE order_items SET order_id = ?, iw_id = ?";
//            try(PreparedStatement statement = connection.prepareStatement(query)){
//                statement.setInt(1, order.getOrderId());
//                statement.setInt(2, iw.getIw_id());
//                statement.executeUpdate();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }1
    }

    public void deleteOrder(int orderId) throws SQLException {
        AuditService.registerAction("Delete Order");

        Order order = DatabaseReaderService.getInstance().getOrderById(orderId);
        List<ItemWrapper> itemWrappers = order.getItems();

        /// Deleting from order_items
        String query = "DELETE FROM order_items WHERE order_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(ItemWrapper iw : itemWrappers){
            deleteItemWrapper(iw.getIw_id());
        }

        query = "DELETE FROM ordertable WHERE order_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSale(Sale sale) {
        AuditService.registerAction("Create Sale");
        String query = "INSERT INTO sale (sale_id, order_id, customer_id, date, price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sale.getSaleId());
            statement.setInt(2, sale.getOrderId());
            statement.setInt(3, sale.getCustomerId());
            statement.setDate(4, new java.sql.Date(sale.getDate().getTime()));
            statement.setDouble(5, sale.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSale(Sale sale) {
        AuditService.registerAction("Update Sale");
        String query = "UPDATE sale SET price = ?, date = ?, order_id = ? WHERE sale_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, sale.getPrice());
            statement.setDate(2, new java.sql.Date(sale.getDate().getTime()));
            statement.setInt(3, sale.getOrderId());
            statement.setInt(4, sale.getSaleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSale(int saleId) {
        AuditService.registerAction("Delete Sale");
        String query = "DELETE FROM sale WHERE sale_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, saleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCustomer(Customer customer) {
        AuditService.registerAction("Create Customer");
        String query = "INSERT INTO customer (customer_id, customer_name, age, gender, phone, mail, points) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customer.getCustomerId());
            statement.setString(2, customer.getCustomerName());
            statement.setInt(3, customer.getAge());
            statement.setString(4, customer.getGender());
            statement.setString(5, customer.getPhone());
            statement.setString(6, customer.getMail());
            statement.setInt(7, customer.getPoints());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customer customer) {
        AuditService.registerAction("Update Customer");
        String query = "UPDATE customer SET customer_name = ?, age = ?, gender = ?, phone = ?, mail = ?, points = ? WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getCustomerName());
            statement.setInt(2, customer.getAge());
            statement.setString(3, customer.getGender());
            statement.setString(4, customer.getPhone());
            statement.setString(5, customer.getMail());
            statement.setInt(6, customer.getPoints());
            statement.setInt(7, customer.getCustomerId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int customerId) {
        AuditService.registerAction("Delete Customer");
        String query = "DELETE FROM customer WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
