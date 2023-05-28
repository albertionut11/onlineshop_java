package database;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class DatabaseService {
    private final DatabaseConnector databaseConnector;

    public DatabaseService(){
        databaseConnector = new DatabaseConnector();
    }

    public void createTables() throws SQLException {
        Connection connection = null;

        try {
            connection = DatabaseConnector.getConnection();
            /// #1 Item Table
            String createItemTableSQL = "CREATE TABLE IF NOT EXISTS Item ("
                    + "item_id INT PRIMARY KEY,"
                    + "item_name VARCHAR(100),"
                    + "Brand VARCHAR(100),"
                    + "price DECIMAL(10,2),"
                    + "gender VARCHAR(100),"
                    + "size VARCHAR(10)"
                    + ");";
            databaseConnector.executeStatement(createItemTableSQL);
            /// #2 ClothingItem Table
            String createClothingItemTableSQL = "CREATE TABLE IF NOT EXISTS ClothingItem (" +
                    "    item_id INT PRIMARY KEY," +
                    "    fabric VARCHAR(100)," +
                    "    color VARCHAR(50)," +
                    "    style VARCHAR(50)," +
                    "    season VARCHAR(50)," +
                    "    FOREIGN KEY (item_id) REFERENCES Item(item_id)" +
                    ");";
            databaseConnector.executeStatement(createClothingItemTableSQL);
            /// #3 ShoeItem Table
            String createShoeItemTableSQL = "CREATE TABLE IF NOT EXISTS ShoeItem (" +
                    "    item_id INT PRIMARY KEY," +
                    "    material VARCHAR(100)," +
                    "    heel_height DECIMAL(5,2)," +
                    "    shoe_type VARCHAR(50)," +
                    "    FOREIGN KEY (item_id) REFERENCES Item(item_id)" +
                    ");";
            databaseConnector.executeStatement(createShoeItemTableSQL);
            /// #4 ItemWrapper Table
            String createItemWrapperTableSQL = "CREATE TABLE IF NOT EXISTS ItemWrapper (" +
                    "    iw_id INT PRIMARY KEY," +
                    "    item_id INT," +
                    "    inventory_id INT," +
                    "    quantity INT," +
                    "    FOREIGN KEY (item_id) REFERENCES Item(item_id)," +
                    "    FOREIGN KEY (inventory_id) REFERENCES Inventory(inventory_id)" +
                    ");";

            databaseConnector.executeStatement(createItemWrapperTableSQL);
            /// #5 Brand Table
            String createBrandTableSQL = "CREATE TABLE IF NOT EXISTS Brand (" +
                    "    brand_id INT PRIMARY KEY," +
                    "    brand_name VARCHAR(100)," +
                    "    keywords VARCHAR(255)," +
                    "    description VARCHAR(255)" +
                    ");";
            databaseConnector.executeStatement(createBrandTableSQL);
            /// #6 Inventory Table
            String createInventoryTableSQL = "CREATE TABLE IF NOT EXISTS Inventory (" +
                    "    inventory_id INT PRIMARY KEY," +
                    "    inventory_name VARCHAR(100)," +
                    "    capacity INT," +
                    "    manager VARCHAR(100)," +
                    "    warehouse VARCHAR(100)" +
                    ");";
            databaseConnector.executeStatement(createInventoryTableSQL);
            /// #7 Customer Table
            String createCustomerTableSQL = "CREATE TABLE IF NOT EXISTS Customer (" +
                    "    customer_id INT PRIMARY KEY," +
                    "    customer_name VARCHAR(100)," +
                    "    age INT," +
                    "    gender VARCHAR(10)," +
                    "    phone VARCHAR(20)," +
                    "    mail VARCHAR(100)," +
                    "    points INT" +
                    ");";
            databaseConnector.executeStatement(createCustomerTableSQL);
            /// #8 Sale Table

            String createSaleTableSQL = "CREATE TABLE IF NOT EXISTS Sale (" +
                    "    sale_id INT PRIMARY KEY," +
                    "    order_id INT, " +
                    "    customer_id INT," +
                    "    date DATE," +
                    "    price DECIMAL(10,2)," +
                    "    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)," +
                    "    FOREIGN KEY (order_id) REFERENCES OrderTable(order_id)" +
                    ");";
            databaseConnector.executeStatement(createSaleTableSQL);
            /// #9 Order Table
            String createOrderTableSQL = "CREATE TABLE IF NOT EXISTS OrderTable (" +
                    "    order_id INT PRIMARY KEY," +
                    "    customer_id INT," +
                    "    date DATE," +
                    "    shipping_address VARCHAR(255)," +
                    "    status VARCHAR(50)," +
                    "    payment_method VARCHAR(50)," +
                    "    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)" +
                    ");";
            databaseConnector.executeStatement(createOrderTableSQL);
            /// #10 Order-Items Associative Table

            String dropTableSQL = "DROP TABLE IF EXISTS order_items";
            databaseConnector.executeStatement(dropTableSQL);

            String createOrderItemsTableSQL = "CREATE TABLE IF NOT EXISTS order_items (" +
                    "    order_id INT," +
                    "    iw_id INT," +
                    "    FOREIGN KEY (order_id) REFERENCES OrderTable(order_id)," +
                    "    FOREIGN KEY (iw_id) REFERENCES ItemWrapper(iw_id)" +
                    ");";
            databaseConnector.executeStatement(createOrderItemsTableSQL);

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error closing database resources: " + e.getMessage(), e);
            }
        }
    }
}
