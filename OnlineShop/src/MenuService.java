import customers.Customer;
import database.DatabaseReaderService;
import database.DatabaseService;
import database.DatabaseWriterService;
import items.*;
import management.Inventory;
import management.Order;
import management.Sale;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.*;

public class MenuService {

    private static final DatabaseService dbService = new DatabaseService();
    private static final DatabaseWriterService writerService = DatabaseWriterService.getInstance();
    private static final DatabaseReaderService readerService = DatabaseReaderService.getInstance();
    private Map<String, String> CRUD = new HashMap<String, String>();

    private Map<String, String> OBJ = new HashMap<String, String>();

    private final Scanner in = new Scanner(System.in);
    private String read;

    private void setMaps() {
        CRUD.put("0", "Stop");
        CRUD.put("1", "Create");
        CRUD.put("2", "Read");
        CRUD.put("3", "Update");
        CRUD.put("4", "Delete");
        OBJ.put("0", "Stop");
        OBJ.put("1", "ClothingItem");
        OBJ.put("2", "ShoeItem");
        OBJ.put("3", "Brand");
        OBJ.put("4", "Order");
        OBJ.put("5", "Sale");
        OBJ.put("6", "Inventory");
        OBJ.put("7", "ItemWrapper");
        OBJ.put("8", "Customer");
    }

    public void Start() throws SQLException {
        setMaps();

        while (true) {

            boolean ok = false;
            while(!ok) {
                String askAction = """

                        What do you want to do?
                        0-Stop     1-Create     2-Read     3-Update     4-Delete
                        Press a key to match your action.
                        """;
                System.out.println(askAction);
                read = in.nextLine();
                if(read.equals("0") || read.equals("1") || read.equals("2") || read.equals("3") || read.equals("4"))
                    ok = true;
                else
                    System.out.println("Invalid command!");
            }
            String Action = CRUD.get(read);
            if (Action.equals("Stop")) break;

            ok = false;
            while(!ok) {
                String askObject = "What do you want to " + Action + "?\n" +
                        "0-Stop     1-ClothingItem     2-ShoeItem     3-Brand     4-Order\n" +
                        "5-Sale     6-Inventory     7-ItemWrapper     8-Customer\n" +
                        "Press a key to match your object.\n";

                System.out.println(askObject);
                read = in.nextLine();
                if(read.equals("1") || read.equals("2") || read.equals("3") || read.equals("4") ||
                        read.equals("5") || read.equals("6") || read.equals("7") || read.equals("8") || read.equals("0"))
                    ok = true;
                else
                    System.out.println("Invalid command!");
            }
            String Object = OBJ.get(read);
            if (Object.equals("Stop")) break;
            System.out.println(Action + " " + Object);

            if (Object.equals("ClothingItem")) {
                if (Action.equals("Create")) {
                    System.out.println("Item name:");
                    read = in.nextLine();
                    String item_name = read;

                    System.out.println("Choose between these brands:");
                    List<Brand> brands = readerService.getAllBrands();
                    for (Brand b : brands) {
                        System.out.println(b.getBrandName());
                    }
                    boolean found = false;
                    while (!found) {
                        System.out.println("Brand name:");
                        read = in.nextLine();
                        for (Brand b : brands) {
                            if (read.equals(b.getBrandName()))
                                found = true;
                        }
                        if (!found)
                            System.out.println("Choose an existing brand!\n");
                    }
                    Brand brand = readerService.getBrandByName(read);

                    double price;
                    while (true) {
                        try {
                            System.out.println("Price:");
                            read = in.nextLine();
                            price = Double.parseDouble(read);
                            break;
                        } catch (Exception e) {
                            System.out.println("Please write a double number that can be parsed!");
                        }
                    }
                    System.out.println("Gender:");
                    String gender = in.nextLine();
                    System.out.println("Size:");
                    String size = in.nextLine();
                    System.out.println("Fabric:");
                    String fabric = in.nextLine();
                    System.out.println("Color:");
                    String color = in.nextLine();
                    System.out.println("Style:");
                    String style = in.nextLine();
                    System.out.println("Season:");
                    String season = in.nextLine();

                    ClothingItem CI = new ClothingItem(item_name, brand, price,
                            gender, size, fabric, color, style, season);
                    writerService.createClothingItem(CI);
                } else if (Action.equals("Update")) {
                    List<ClothingItem> clothingItems = DatabaseReaderService.getInstance().getAllClothingItems();
                    HashSet<Integer> ids = new HashSet<Integer>();
                    for(Item it : clothingItems){
                        ids.add(it.getItemId());
                        String output =  "ID: " + it.getItemId() + "   " + it.getItemName() + " " + it.getBrand().getBrandName();
                        System.out.println(output);
                    }
                    int itemId = 0;
                    do{
                        try{
                            System.out.println("Enter the ClothingItem ID:");
                            itemId = Integer.parseInt(in.nextLine());
                        }catch(Exception e){
                            System.out.println("Please enter a valid number!");
                        }
                    }while(!ids.contains(itemId));

                    ClothingItem existingItem = readerService.getClothingItemById(itemId);
                    if (existingItem != null) {
                        if(YesOrNo("Item name")){
                            System.out.println("Item name:");
                            String item_name = in.nextLine();
                            existingItem.setItemName(item_name);
                        }

                        if(YesOrNo("Brand")){
                            System.out.println("Choose between these brands:\n");
                            List<Brand> brands = readerService.getAllBrands();
                            for (Brand b : brands) {
                                System.out.println(b.getBrandName());
                            }
                            boolean found = false;
                            while (!found) {
                                System.out.println("Brand name:");
                                read = in.nextLine();
                                for (Brand b : brands) {
                                    if (read.equals(b.getBrandName()))
                                        found = true;
                                }
                                if (!found)
                                    System.out.println("Choose an existing brand!\n");
                            }
                            Brand brand = readerService.getBrandByName(read);
                            existingItem.setBrand(brand);
                        }

                        if(YesOrNo("Price")){
                            double price;
                            while (true) {
                                try {
                                    System.out.println("Price:");
                                    read = in.nextLine();
                                    price = Double.parseDouble(read);
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Please write a double number that can be parsed!");
                                }
                            }
                            existingItem.setPrice(price);
                        }

                        if(YesOrNo("Gender")){
                            System.out.println("Gender:");
                            String gender = in.nextLine();
                            existingItem.setGender(gender);
                        }

                        if(YesOrNo("Size")) {
                            System.out.println("Size:");
                            String size = in.nextLine();
                            existingItem.setSize(size);
                        }

                        if(YesOrNo("Fabric")) {
                            System.out.println("Fabric:");
                            String fabric = in.nextLine();
                            existingItem.setFabric(fabric);
                        }

                        if(YesOrNo("Color")) {
                            System.out.println("Color:");
                            String color = in.nextLine();
                            existingItem.setColor(color);
                        }

                        if(YesOrNo("Style")) {
                            System.out.println("Style:");
                            String style = in.nextLine();
                            existingItem.setStyle(style);
                        }

                        if(YesOrNo("Season")) {
                            System.out.println("Season:");
                            String season = in.nextLine();
                            existingItem.setSeason(season);
                        }
                        writerService.updateClothingItem(existingItem);
                    } else {
                        System.out.println("ClothingItem not found!");
                    }
                }
                else if (Action.equals("Read")) {
                    System.out.println("Choose an option:");
                    System.out.println("1. Get all ClothingItems");
                    System.out.println("2. Get ClothingItem by ID");
                    read = in.nextLine();

                    if (read.equals("1")) {
                        List<ClothingItem> clothingItems = readerService.getAllClothingItems();
                        for (ClothingItem ci : clothingItems) {
                            System.out.println(ci);
                        }
                    } else if (read.equals("2")) {
                        System.out.println("Enter the ClothingItem ID:");
                        read = in.nextLine();
                        int itemId = Integer.parseInt(read);
                        ClothingItem ci = readerService.getClothingItemById(itemId);
                        System.out.println(ci);
                    } else {
                        System.out.println("Invalid option!");
                    }
                } else if (Action.equals("Delete")) {
                    System.out.println("Enter the ClothingItem ID:");
                    read = in.nextLine();
                    int itemId = Integer.parseInt(read);
                    writerService.deleteClothingItem(itemId);
                }
            }
            else if (Object.equals("ShoeItem")) {
                if (Action.equals("Create")) {
                    System.out.println("Item name:");
                    read = in.nextLine();
                    String item_name = read;

                    System.out.println("Choose between these brands:\n");
                    List<Brand> brands = readerService.getAllBrands();
                    for (Brand b : brands) {
                        System.out.println(b.getBrandName());
                    }
                    boolean found = false;
                    while (!found) {
                        System.out.println("Brand name:");
                        read = in.nextLine();
                        for (Brand b : brands) {
                            if (read.equals(b.getBrandName()))
                                found = true;
                        }
                        if (!found)
                            System.out.println("Choose an existing brand!\n");
                    }
                    Brand brand = readerService.getBrandByName(read);

                    double price;
                    while (true) {
                        try {
                            System.out.println("Price:");
                            read = in.nextLine();
                            price = Double.parseDouble(read);
                            break;
                        } catch (Exception e) {
                            System.out.println("Please write a double number that can be parsed!");
                        }
                    }
                    System.out.println("Gender:");
                    String gender = in.nextLine();
                    System.out.println("Size:");
                    String size = in.nextLine();
                    System.out.println("Material:");
                    String material = in.nextLine();
                    System.out.println("Shoe_type:");
                    String shoe_type = in.nextLine();

                    double heel_height;
                    while (true) {
                        try {
                            System.out.println("Heel_height:");
                            read = in.nextLine();
                            heel_height = Double.parseDouble(read);
                            break;
                        } catch (Exception e) {
                            System.out.println("Please write a double number that can be parsed!");
                        }
                    }

                    ShoeItem SI = new ShoeItem(item_name, brand, price, gender, size, material, heel_height, shoe_type);
                    writerService.createShoeItem(SI);
                } else if (Action.equals("Update")) {
                    List<ShoeItem> shoeItems = DatabaseReaderService.getInstance().getAllShoeItems();
                    HashSet<Integer> ids = new HashSet<Integer>();
                    for(Item it : shoeItems){
                        ids.add(it.getItemId());
                        String output =  "ID: " + it.getItemId() + "   " + it.getItemName() + " " + it.getBrand().getBrandName();
                        System.out.println(output);
                    }
                    System.out.println("Enter the ShoeItem ID:");
                    int itemId = 0;
                    do{
                        try{
                            itemId = Integer.parseInt(in.nextLine());
                        }catch(Exception e){
                            System.out.println("Please enter a valid number!");
                        }
                    }while(!ids.contains(itemId));

                    ShoeItem existingItem = readerService.getShoeItemById(itemId);
                    if (existingItem != null) {

                        if(YesOrNo("Item name")) {
                            System.out.println("Item name:");
                            String item_name = in.nextLine();
                            existingItem.setItemName(item_name);
                        }
                        if(YesOrNo("Brand")) {
                            System.out.println("Choose between these brands:\n");
                            List<Brand> brands = readerService.getAllBrands();
                            for (Brand b : brands) {
                                System.out.println(b.getBrandName());
                            }
                            boolean found = false;
                            while (!found) {
                                System.out.println("Brand name:");
                                read = in.nextLine();
                                for (Brand b : brands) {
                                    if (read.equals(b.getBrandName()))
                                        found = true;
                                }
                                if (!found)
                                    System.out.println("Choose an existing brand!");
                            }
                            Brand brand = readerService.getBrandByName(read);
                            existingItem.setBrand(brand);
                        }

                        if(YesOrNo("Price")) {
                            double price;
                            while (true) {
                                try {
                                    System.out.println("Price:");
                                    read = in.nextLine();
                                    price = Double.parseDouble(read);
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Please write a double number that can be parsed!");
                                }
                            }
                            existingItem.setPrice(price);
                        }

                        if(YesOrNo("Gender")) {
                            System.out.println("Gender:");
                            String gender = in.nextLine();
                            existingItem.setGender(gender);
                        }
                        if(YesOrNo("Size")) {
                            System.out.println("Size:");
                            String size = in.nextLine();
                            existingItem.setSize(size);
                        }
                        if(YesOrNo("Material")) {
                            System.out.println("Material:");
                            String material = in.nextLine();
                            existingItem.setMaterial(material);
                        }
                        if(YesOrNo("Shoe_type")) {
                            System.out.println("Shoe_type:");
                            String shoe_type = in.nextLine();
                            existingItem.setShoeType(shoe_type);
                        }
                        if(YesOrNo("Heel_height")) {
                            double heel_height;
                            while (true) {
                                try {
                                    System.out.println("Heel_height:");
                                    read = in.nextLine();
                                    heel_height = Double.parseDouble(read);
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Please write a double number that can be parsed!");
                                }
                            }
                            existingItem.setHeelHeight(heel_height);
                        }
                        writerService.updateShoeItem(existingItem);
                    } else {
                        System.out.println("ShoeItem not found!");
                    }
                }
                else if (Action.equals("Read")) {
                    System.out.println("Choose an option:");
                    System.out.println("1. Get all ShoeItems");
                    System.out.println("2. Get ShoeItem by ID");
                    read = in.nextLine();

                    if (read.equals("1")) {
                        List<ShoeItem> shoeItems = readerService.getAllShoeItems();
                        for (ShoeItem si : shoeItems) {
                            System.out.println(si);
                        }
                    } else if (read.equals("2")) {
                        System.out.println("Enter the ShoeItem ID:");
                        read = in.nextLine();
                        int itemId = Integer.parseInt(read);
                        ShoeItem si = readerService.getShoeItemById(itemId);
                        System.out.println(si);
                    } else {
                        System.out.println("Invalid option!");
                    }
                } else if (Action.equals("Delete")) {
                    System.out.println("Enter the ShoeItem ID:");
                    read = in.nextLine();
                    int itemId = Integer.parseInt(read);
                    writerService.deleteShoeItem(itemId);
                }
            }
            else if (Object.equals("Brand")) {
                if (Action.equals("Create")) {
                    System.out.println("Brand name:");
                    String brandName = in.nextLine();

                    System.out.println("Keywords:");
                    String keywords = in.nextLine();

                    System.out.println("Description:");
                    String description = in.nextLine();

                    Brand brand = new Brand(brandName, keywords, description);
                    writerService.createBrand(brand);
                } else if (Action.equals("Update")) {
                    List<Brand> brands = DatabaseReaderService.getInstance().getAllBrands();
                    HashSet<Integer> ids = new HashSet<Integer>();
                    for(Brand b : brands){
                        ids.add(b.getBrandId());
                        String output =  "ID: " + b.getBrandId() + "   " + b.getBrandName();
                        System.out.println(output);
                    }

                    System.out.println("Enter the Brand ID:");
                    int brandId = 0;
                    do{
                        try{
                            brandId = Integer.parseInt(in.nextLine());
                        }catch(Exception e){
                            System.out.println("Please enter a valid number!");
                        }
                    }while(!ids.contains(brandId));

                    Brand existingBrand = readerService.getBrandById(brandId);
                    if (existingBrand != null) {
                        if(YesOrNo("Brand name")) {
                            System.out.println("Brand name:");
                            String brandName = in.nextLine();
                            existingBrand.setBrandName(brandName);
                        }
                        if(YesOrNo("Keyword")){
                            System.out.println("Keywords:");
                            String keywords = in.nextLine();
                            existingBrand.setKeywords(keywords);
                        }
                        if(YesOrNo("Description")) {
                            System.out.println("Description:");
                            String description = in.nextLine();
                            existingBrand.setDescription(description);
                        }
                        writerService.updateBrand(existingBrand);
                    } else {
                        System.out.println("Brand not found!");
                    }
                } else if (Action.equals("Read")) {
                    System.out.println("Choose an option:");
                    System.out.println("1. Get all Brands");
                    System.out.println("2. Get Brand by ID");
                    read = in.nextLine();

                    if (read.equals("1")) {
                        List<Brand> brands = readerService.getAllBrands();
                        for (Brand brand : brands) {
                            System.out.println(brand);
                        }
                    } else if (read.equals("2")) {
                        System.out.println("Enter the Brand ID:");
                        read = in.nextLine();
                        int brandId = Integer.parseInt(read);
                        Brand brand = readerService.getBrandById(brandId);
                        System.out.println(brand);
                    } else {
                        System.out.println("Invalid option!");
                    }
                } else if (Action.equals("Delete")) {
                    System.out.println("Enter the Brand ID:");
                    read = in.nextLine();
                    int brandId = Integer.parseInt(read);
                    writerService.deleteBrand(brandId);
                }
            }
            else if (Object.equals("Customer")) {
                if (Action.equals("Create")) {
                    System.out.println("Customer name:");
                    String customerName = in.nextLine();

                    int age = 0;
                    do{
                        try{
                            System.out.println("Age:");
                            age = Integer.parseInt(in.nextLine());
                        }catch(Exception e){
                            System.out.println("Please enter a valid number!");
                        }
                    }while(age == 0);

                    System.out.println("Gender:");
                    String gender = in.nextLine();

                    System.out.println("Phone:");
                    String phone = in.nextLine();

                    System.out.println("Email:");
                    String email = in.nextLine();

                    Customer customer = new Customer(customerName, age, gender, phone, email);
                    writerService.createCustomer(customer);
                } else if (Action.equals("Update")) {
                    List<Customer> customers = DatabaseReaderService.getInstance().getAllCustomers();
                    for(Customer c : customers){
                        String output =  "ID: " + c.getCustomerId() + "   " + c.getCustomerName();
                        System.out.println(output);
                    }
                    System.out.println("Enter the Customer ID:");
                    read = in.nextLine();
                    int customerId = Integer.parseInt(read);

                    Customer existingCustomer = readerService.getCustomerById(customerId);
                    if (existingCustomer != null) {
                        if(YesOrNo("Customer name")) {
                            System.out.println("Customer name:");
                            String customerName = in.nextLine();
                            existingCustomer.setCustomerName(customerName);
                        }
                        if(YesOrNo("Age")) {
                            int age = 0;
                            do{
                                try{
                                    System.out.println("Age:");
                                    age = Integer.parseInt(in.nextLine());
                                }catch(Exception e){
                                    System.out.println("Please enter a valid number!");
                                }
                            }while(age == 0);
                            existingCustomer.setAge(age);
                        }
                        if(YesOrNo("Gender")) {
                            System.out.println("Gender:");
                            String gender = in.nextLine();
                            existingCustomer.setGender(gender);
                        }
                        if(YesOrNo("Phone")) {
                            System.out.println("Phone:");
                            String phone = in.nextLine();
                            existingCustomer.setPhone(phone);
                        }
                        if(YesOrNo("Email")) {
                            System.out.println("Email:");
                            String email = in.nextLine();
                            existingCustomer.setMail(email);
                        }
                        writerService.updateCustomer(existingCustomer);
                    } else {
                        System.out.println("Customer not found!");
                    }
                } else if (Action.equals("Read")) {
                    System.out.println("Choose an option:");
                    System.out.println("1. Get all Customers");
                    System.out.println("2. Get Customer by ID");
                    read = in.nextLine();

                    if (read.equals("1")) {
                        List<Customer> customers = readerService.getAllCustomers();
                        for (Customer customer : customers) {
                            System.out.println(customer);
                        }
                    } else if (read.equals("2")) {
                        System.out.println("Enter the Customer ID:");
                        read = in.nextLine();
                        int customerId = Integer.parseInt(read);
                        Customer customer = readerService.getCustomerById(customerId);
                        System.out.println(customer);
                    } else {
                        System.out.println("Invalid option!");
                    }
                } else if (Action.equals("Delete")) {
                    System.out.println("Enter the Customer ID:");
                    read = in.nextLine();
                    int customerId = Integer.parseInt(read);
                    writerService.deleteCustomer(customerId);
                }
            }
            else if (Object.equals("ItemWrapper")) {
                if (Action.equals("Create")) {
                    int inventoryId = 0, itemId = 0;
                    List<ClothingItem> clothingItems = readerService.getAllClothingItems();
                    List<ShoeItem> shoeItems = readerService.getAllShoeItems();
                    ok = false;
                    while(!ok) {
                        HashSet<Integer> ids = new HashSet<Integer>();
                        for(Item it : clothingItems){
                            String output =  "ID: " + it.getItemId() + "   " + it.getItemName() + " " + it.getBrand().getBrandName();
                            System.out.println(output);
                            ids.add(it.getItemId());
                        }
                        for(Item it : shoeItems){
                            String output =  "ID: " + it.getItemId() + "   " + it.getItemName() + " " + it.getBrand().getBrandName();
                            System.out.println(output);
                            ids.add(it.getItemId());
                        }
                        do {
                            try {
                                System.out.println("Item ID:");
                                itemId = Integer.parseInt(in.nextLine());
                            } catch (Exception e) {
                                System.out.println("Please enter a valid number!");
                            }
                        } while (itemId == 0);

                        if(ids.contains(itemId))
                            ok = true;
                        else
                            System.out.println("Invalid Command!");
                    }
                    List<Inventory> inventories = readerService.getAllInventories();
                    ok = false;
                    while(!ok) {
                        HashSet<Integer> ids = new HashSet<Integer>();
                        for(Inventory inv : inventories){
                            String output = "ID: " + inv.getInventoryId() + "   " + inv.getInventoryName();
                            System.out.println(output);
                            ids.add(inv.getInventoryId());
                        }
                        do {
                            try {
                                System.out.println("Inventory ID:");
                                inventoryId = Integer.parseInt(in.nextLine());
                            } catch (Exception e) {
                                System.out.println("Please enter a valid number!");
                            }
                        } while (inventoryId == 0);

                        if(ids.contains(inventoryId))
                            ok = true;
                        else
                            System.out.println("Invalid Command!");
                    }

                    int quantity;
                    while (true) {
                        try {
                            System.out.println("Quantity:");
                            read = in.nextLine();
                            quantity = Integer.parseInt(read);
                            break;
                        } catch (Exception e) {
                            System.out.println("Please write an integer number that can be parsed!");
                        }
                    }

                    ItemWrapper itemWrapper = new ItemWrapper(readerService.getItemById(itemId), quantity);
                    itemWrapper.setInventory_id(inventoryId);
                    writerService.createItemWrapper(itemWrapper, inventoryId);
                } else if (Action.equals("Update")) {
                    System.out.println("Enter the ItemWrapper ID:");
                    read = in.nextLine();
                    int itemWrapperId = Integer.parseInt(read);

                    ItemWrapper existingItemWrapper = readerService.getItemWrapperById(itemWrapperId);
                    if (existingItemWrapper != null) {
                        int itemId = 0, inventoryId = 0;
                        if(YesOrNo("Item")) {
                            List<ClothingItem> clothingItems = readerService.getAllClothingItems();
                            List<ShoeItem> shoeItems = readerService.getAllShoeItems();
                            ok = false;
                            while(!ok) {
                                HashSet<Integer> ids = new HashSet<Integer>();
                                for(Item it : clothingItems){
                                    String output =  "ID: " + it.getItemId() + "   " + it.getItemName() + " " + it.getBrand().getBrandName();
                                    System.out.println(output);
                                    ids.add(it.getItemId());
                                }
                                for(Item it : shoeItems){
                                    String output =  "ID: " + it.getItemId() + "   " + it.getItemName() + " " + it.getBrand().getBrandName();
                                    System.out.println(output);
                                    ids.add(it.getItemId());
                                }
                                System.out.println("Item ID:");
                                itemId = Integer.parseInt(in.nextLine());
                                if(ids.contains(itemId))
                                    ok = true;
                                else
                                    System.out.println("Invalid Command!");
                            }
                            existingItemWrapper.setItem_id(itemId);
                        }
                        if(YesOrNo("Inventory")){
                            List<Inventory> inventories = readerService.getAllInventories();
                            ok = false;
                            while(!ok) {
                                HashSet<Integer> ids = new HashSet<Integer>();
                                for (Inventory inv : inventories) {
                                    String output = "ID: " + inv.getInventoryId() + "   " + inv.getInventoryName();
                                    System.out.println(output);
                                    ids.add(inv.getInventoryId());
                                }
                                System.out.println("Inventory ID:");
                                inventoryId = Integer.parseInt(in.nextLine());
                                if (ids.contains(inventoryId))
                                    ok = true;
                                else
                                    System.out.println("Invalid Command!");
                            }
                            existingItemWrapper.setInventory_id(inventoryId);
                        }
                        if(YesOrNo("Quantity")) {
                            int quantity;
                            while (true) {
                                try {
                                    System.out.println("Quantity:");
                                    read = in.nextLine();
                                    quantity = Integer.parseInt(read);
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Please write an integer number that can be parsed!");
                                }
                            }
                            existingItemWrapper.setQuantity(quantity);
                        }
                        writerService.updateItemWrapper(existingItemWrapper);
                    } else {
                        System.out.println("ItemWrapper not found!");
                    }
                } else if (Action.equals("Read")) {
                    System.out.println("Choose an option:");
                    System.out.println("1. Get all ItemWrappers");
                    System.out.println("2. Get ItemWrapper by ID");
                    read = in.nextLine();

                    if (read.equals("1")) {
                        List<ItemWrapper> itemWrappers = readerService.getAllItemWrappers();
                        for (ItemWrapper itemWrapper : itemWrappers) {
                            System.out.println(itemWrapper);
                        }
                    } else if (read.equals("2")) {
                        System.out.println("Enter the ItemWrapper ID:");
                        read = in.nextLine();
                        int itemWrapperId = Integer.parseInt(read);
                        ItemWrapper itemWrapper = readerService.getItemWrapperById(itemWrapperId);
                        System.out.println(itemWrapper);
                    } else {
                        System.out.println("Invalid option!");
                    }
                } else if (Action.equals("Delete")) {
                    System.out.println("Enter the ItemWrapper ID:");
                    read = in.nextLine();
                    int itemWrapperId = Integer.parseInt(read);
                    writerService.deleteItemWrapper(itemWrapperId);
                }
            }
            else if (Object.equals("Inventory")) {
                if (Action.equals("Create")) {
                    System.out.println("Inventory Name:");
                    String inventoryName = in.nextLine();

                    System.out.println("Capacity:");
                    int capacity = Integer.parseInt(in.nextLine());

                    System.out.println("Manager:");
                    String manager = in.nextLine();

                    System.out.println("Warehouse:");
                    String warehouse = in.nextLine();

                    Inventory inventory = new Inventory(inventoryName, capacity, manager, warehouse);
                    writerService.createInventory(inventory);
                } else if (Action.equals("Update")) {
                    System.out.println("Enter the Inventory ID:");
                    read = in.nextLine();
                    int inventoryId = Integer.parseInt(read);

                    Inventory existingInventory = readerService.getInventoryById(inventoryId);
                    if (existingInventory != null) {
                        if (YesOrNo("Inventory name")) {
                            System.out.println("Inventory Name:");
                            String inventoryName = in.nextLine();
                            existingInventory.setInventoryName(inventoryName);
                        }
                        if(YesOrNo("Capacity")) {
                            System.out.println("Capacity:");
                            int capacity = Integer.parseInt(in.nextLine());
                            existingInventory.setCapacity(capacity);
                        }
                        if(YesOrNo("Manager")) {
                            System.out.println("Manager:");
                            String manager = in.nextLine();
                            existingInventory.setManager(manager);
                        }
                        if(YesOrNo("Warehouse")) {
                            System.out.println("Warehouse:");
                            String warehouse = in.nextLine();
                            existingInventory.setWarehouse(warehouse);
                        }
                        writerService.updateInventory(existingInventory);
                    } else {
                        System.out.println("Inventory not found!");
                    }
                } else if (Action.equals("Read")) {
                    System.out.println("Choose an option:");
                    System.out.println("1. Get all Inventories");
                    System.out.println("2. Get Inventory by ID");
                    read = in.nextLine();

                    if (read.equals("1")) {
                        List<Inventory> inventories = readerService.getAllInventories();
                        for (Inventory inventory : inventories) {
                            System.out.println(inventory);
                        }
                    } else if (read.equals("2")) {
                        System.out.println("Enter the Inventory ID:");
                        read = in.nextLine();
                        int inventoryId = Integer.parseInt(read);
                        Inventory inventory = readerService.getInventoryById(inventoryId);
                        System.out.println(inventory);
                    } else {
                        System.out.println("Invalid option!");
                    }
                } else if (Action.equals("Delete")) {
                    System.out.println("Enter the Inventory ID:");
                    read = in.nextLine();
                    int inventoryId = Integer.parseInt(read);
                    writerService.deleteInventory(inventoryId);
                }
            }
            else if (Object.equals("Order")) {
                if (Action.equals("Create")) {
                    int customerId = 0;
                    List<Customer> customers = readerService.getAllCustomers();
                    ok = false;
                    while(!ok) {
                        HashSet<Integer> ids = new HashSet<Integer>();
                        for(Customer c : customers){
                            String output =  "ID: " + c.getCustomerId() + "   " + c.getCustomerName();
                            System.out.println(output);
                            ids.add(c.getCustomerId());
                        }
                        do {
                            try {
                                System.out.println("Customer ID:");
                                customerId = Integer.parseInt(in.nextLine());
                            } catch (Exception e) {
                                System.out.println("Please enter a valid number!");
                            }
                        } while (customerId == 0);

                        if(ids.contains(customerId))
                            ok = true;
                        else
                            System.out.println("Invalid Command!");
                    }

                    System.out.println("Shipping Address:");
                    String shippingAddress = in.nextLine();

                    System.out.println("Payment Method:");
                    String paymentMethod = in.nextLine();

                    Order order = new Order(customerId, shippingAddress, paymentMethod);

                    ArrayList<ItemWrapper> items = new ArrayList<ItemWrapper>();
                    int n = 0;
                    do {
                        try {
                            System.out.println("How many items do you want to order?");
                            n = Integer.parseInt(in.nextLine());
                        } catch (Exception e) {
                            System.out.println("Please enter a valid number!");
                        }
                    } while (n == 0);
                    for(int i = 0; i < n; ++i){
                        int inventoryId = 0, itemId = 0;
                        List<ClothingItem> clothingItems = readerService.getAllClothingItems();
                        List<ShoeItem> shoeItems = readerService.getAllShoeItems();
                        ok = false;
                        while(!ok) {
                            HashSet<Integer> ids = new HashSet<Integer>();
                            for(Item it : clothingItems){
                                String output =  "ID: " + it.getItemId() + "   " + it.getItemName() + " " + it.getBrand().getBrandName();
                                System.out.println(output);
                                ids.add(it.getItemId());
                            }
                            for(Item it : shoeItems){
                                String output =  "ID: " + it.getItemId() + "   " + it.getItemName() + " " + it.getBrand().getBrandName();
                                System.out.println(output);
                                ids.add(it.getItemId());
                            }

                            do {
                                try {
                                    System.out.println("Item ID:");
                                    itemId = Integer.parseInt(in.nextLine());
                                } catch (Exception e) {
                                    System.out.println("Please enter a valid number!");
                                }
                            } while (itemId == 0);

                            if(ids.contains(itemId))
                                ok = true;
                            else
                                System.out.println("Invalid Command!");
                        }
                        List<Inventory> inventories = readerService.getAllInventories();
                        ok = false;
                        while(!ok) {
                            HashSet<Integer> ids = new HashSet<Integer>();
                            for(Inventory inv : inventories){
                                String output = "ID: " + inv.getInventoryId() + "   " + inv.getInventoryName();
                                System.out.println(output);
                                ids.add(inv.getInventoryId());
                            }

                            do {
                                try {
                                    System.out.println("Inventory ID:");
                                    inventoryId = Integer.parseInt(in.nextLine());
                                } catch (Exception e) {
                                    System.out.println("Please enter a valid number!");
                                }
                            } while (inventoryId == 0);

                            if(ids.contains(inventoryId))
                                ok = true;
                            else
                                System.out.println("Invalid Command!");
                        }

                        int quantity = 0;
                        while (true) {
                            try {
                                System.out.println("Quantity:");
                                read = in.nextLine();
                                quantity = Integer.parseInt(read);
                                break;
                            } catch (Exception e) {
                                System.out.println("Please write an integer number that can be parsed!");
                            }
                        }

                        ItemWrapper itemWrapper = new ItemWrapper(readerService.getItemById(itemId), quantity);
                        itemWrapper.setInventory_id(inventoryId);
                        items.add(itemWrapper);
                        writerService.createItemWrapper(itemWrapper, inventoryId);
                        System.out.println("Added to cart!");
                    }
                    writerService.createOrder(order, items);
                } else if (Action.equals("Update")) {
                    int orderId = 0;
                    HashSet<Integer> ids = new HashSet<Integer>();
                    List<Order> orders = readerService.getAllOrders();
                    for(Order o : orders){
                        if(o.getStatus().equals("Processing")) {
                            ids.add(o.getOrderId());
                            String output = "Order ID: " + o.getOrderId() + " " +
                                    "Customer Name: " + readerService.getCustomerById(o.getCustomerId()).getCustomerName();
                            List<ItemWrapper> itemWrappers = o.getItems();
                            System.out.println(output);
                            for (ItemWrapper iw : itemWrappers) {
                                String output1 = iw.getItem().getItemName() + " " +
                                        iw.getItem().getBrand().getBrandName();
                                System.out.println(output1);
                            }
                        }
                    }

                    do {
                        try {
                            System.out.println("Order ID:");
                            orderId = Integer.parseInt(in.nextLine());
                        } catch (Exception e) {
                            System.out.println("Please enter a valid number!");
                        }
                    } while (!ids.contains(orderId));

                    Order existingOrder = readerService.getOrderById(orderId);
                    if (existingOrder != null) {
                        if(YesOrNo("Customer")) {
                            int customerId = 0;

                            List<Customer> customers = readerService.getAllCustomers();
                            ok = false;
                            while(!ok) {
                                ids = new HashSet<Integer>();
                                for(Customer c : customers){
                                    String output =  "ID: " + c.getCustomerId() + "   " + c.getCustomerName();
                                    System.out.println(output);
                                    ids.add(c.getCustomerId());
                                }
                                do {
                                    try {
                                        System.out.println("Customer ID:");
                                        customerId = Integer.parseInt(in.nextLine());
                                    } catch (Exception e) {
                                        System.out.println("Please enter a valid number!");
                                    }
                                } while (customerId == 0);

                                if(ids.contains(customerId))
                                    ok = true;
                                else
                                    System.out.println("Invalid Command!");
                            }

                            existingOrder.setCustomerId(customerId);
                        }

                        if(YesOrNo("Shipping Address")) {
                            System.out.println("Shipping Address:");
                            String shippingAddress = in.nextLine();
                            existingOrder.setShippingAddress(shippingAddress);
                        }

                        if(YesOrNo("Payment Method")) {
                            System.out.println("Payment Method:");
                            String paymentMethod = in.nextLine();
                            existingOrder.setPaymentMethod(paymentMethod);
                        }
                        List<ItemWrapper> items = existingOrder.getItems();
                        if(YesOrNo("the ordered items")){
                            for(ItemWrapper existingItemWrapper : items){
                                int itemId = 0, inventoryId = 0;
                                String item = existingItemWrapper.getItem().getItemName() + " " + existingItemWrapper.getItem().getBrand().getBrandName();
                                if(YesOrNo(item)) {
                                    System.out.println("Here");
                                    List<ClothingItem> clothingItems = readerService.getAllClothingItems();
                                    List<ShoeItem> shoeItems = readerService.getAllShoeItems();
                                    ok = false;
                                    while (!ok) {
                                        ids = new HashSet<Integer>();
                                        for (Item it : clothingItems) {
                                            String output = "ID: " + it.getItemId() + "   " + it.getItemName() + " " + it.getBrand().getBrandName();
                                            System.out.println(output);
                                            ids.add(it.getItemId());
                                        }
                                        for (Item it : shoeItems) {
                                            String output = "ID: " + it.getItemId() + "   " + it.getItemName() + " " + it.getBrand().getBrandName();
                                            System.out.println(output);
                                            ids.add(it.getItemId());
                                        }
                                        System.out.println("Item ID:");
                                        itemId = Integer.parseInt(in.nextLine());
                                        if (ids.contains(itemId))
                                            ok = true;
                                        else
                                            System.out.println("Invalid Command!");
                                    }
                                    existingItemWrapper.setItem_id(itemId);

                                    if (YesOrNo("Inventory")) {
                                        List<Inventory> inventories = readerService.getAllInventories();
                                        ok = false;
                                        while (!ok) {
                                            ids = new HashSet<Integer>();
                                            for (Inventory inv : inventories) {
                                                String output = "ID: " + inv.getInventoryId() + "   " + inv.getInventoryName();
                                                System.out.println(output);
                                                ids.add(inv.getInventoryId());
                                            }
                                            System.out.println("Inventory ID:");
                                            inventoryId = Integer.parseInt(in.nextLine());
                                            if (ids.contains(inventoryId))
                                                ok = true;
                                            else
                                                System.out.println("Invalid Command!");
                                        }
                                        existingItemWrapper.setInventory_id(inventoryId);
                                    }
                                    if (YesOrNo("Quantity")) {
                                        int quantity;
                                        while (true) {
                                            try {
                                                System.out.println("Quantity:");
                                                read = in.nextLine();
                                                quantity = Integer.parseInt(read);
                                                break;
                                            } catch (Exception e) {
                                                System.out.println("Please write an integer number that can be parsed!");
                                            }
                                        }
                                        existingItemWrapper.setQuantity(quantity);
                                    }
                                    writerService.updateItemWrapper(existingItemWrapper);
                                }
                            }
                        }
                        writerService.updateOrder(existingOrder, items);

                    } else {
                        System.out.println("Order not found!");
                    }
                } else if (Action.equals("Read")) {
                    System.out.println("Choose an option:");
                    System.out.println("1. Get all Orders");
                    System.out.println("2. Get Order by ID");
                    read = in.nextLine();

                    if (read.equals("1")) {
                        List<Order> orders = readerService.getAllOrders();
                        for (Order order : orders) {
                            System.out.println(order);
                        }
                    } else if (read.equals("2")) {
                        System.out.println("Enter the Order ID:");
                        read = in.nextLine();
                        int orderId = Integer.parseInt(read);
                        Order order = readerService.getOrderById(orderId);
                        System.out.println(order);
                    } else {
                        System.out.println("Invalid option!");
                    }
                } else if (Action.equals("Delete")) {
                    System.out.println("Enter the Order ID:");
                    read = in.nextLine();
                    int orderId = Integer.parseInt(read);
                    writerService.deleteOrder(orderId);
                }
            }
            else if (Object.equals("Sale")) {
                if (Action.equals("Create")) {
                    int orderId = 0;
                    HashSet<Integer> ids = new HashSet<Integer>();
                    List<Order> orders = readerService.getAllOrders();
                    for(Order o : orders){
                        if(o.getStatus().equals("Processing")) {
                            ids.add(o.getOrderId());
                            String output = "Order ID: " + o.getOrderId() + " " +
                                    "Customer Name: " + readerService.getCustomerById(o.getCustomerId()).getCustomerName();
                            List<ItemWrapper> itemWrappers = o.getItems();
                            System.out.println(output);
                            for (ItemWrapper iw : itemWrappers) {
                                String output1 = iw.getItem().getItemName() + " " +
                                        iw.getItem().getBrand().getBrandName();
                                System.out.println(output1);
                                }
                            }
                        }
                    do {
                        try {
                            System.out.println("Order ID:");
                            orderId = Integer.parseInt(in.nextLine());
                        } catch (Exception e) {
                            System.out.println("Please enter a valid number!");
                        }
                    } while (!ids.contains(orderId));

                    List<ItemWrapper> items = readerService.getItemsByOrderId(orderId);

                    int customerId = readerService.getOrderById(orderId).getCustomerId();

                    Sale sale = new Sale(items, customerId, orderId);
                    writerService.createSale(sale);

                } else if (Action.equals("Update")) {
                    System.out.println("You cannot update something that happened in the past!");
                } else if (Action.equals("Read")) {
                    System.out.println("Choose an option:");
                    System.out.println("1. Get all Sales");
                    System.out.println("2. Get Sale by ID");
                    read = in.nextLine();

                    if (read.equals("1")) {
                        List<Sale> sales = readerService.getAllSales();
                        for (Sale sale : sales) {
                            System.out.println(sale);
                        }
                    } else if (read.equals("2")) {
                        System.out.println("Enter the Sale ID:");
                        read = in.nextLine();
                        int saleId = Integer.parseInt(read);
                        Sale sale = readerService.getSaleById(saleId);
                        System.out.println(sale);
                    } else {
                        System.out.println("Invalid option!");
                    }
                } else if (Action.equals("Delete")) {
                    System.out.println("Enter the Sale ID:");
                    read = in.nextLine();
                    int saleId = Integer.parseInt(read);
                    writerService.deleteSale(saleId);
                }
            }

        }
    }
    public boolean YesOrNo(String field){
        while(true) {
            String output = "Do you want to update " + field + " ?\n" + "1-Yes 2-No";
            System.out.println(output);
            String ans = in.nextLine();
            if (ans.equals("1"))
                return true;
            else if (ans.equals("2"))
                return false;
            else {
                System.out.println("Invalid command!");
            }
        }
    }

}