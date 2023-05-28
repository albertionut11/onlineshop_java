import customers.Customer;
import database.DatabaseConnector;
import database.DatabaseService;
import database.DatabaseWriterService;
import items.*;
import management.Inventory;
import management.Order;
import management.Sale;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        MenuService ms = new MenuService();
        ms.Start();
    }

}
