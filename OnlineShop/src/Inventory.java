import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private static int next_id = 1;
    private int inventory_id, capacity;
    private List<ItemWrapper> item_list;
    private String inventory_name, manager, warehouse;

    public Inventory(String inventory_name, int capacity, String manager, String warehouse) {
        this.inventory_name = inventory_name;
        this.inventory_id = next_id;
        this.item_list = new ArrayList<ItemWrapper>();
        this.capacity = capacity;
        this.manager = manager;
        this.warehouse = warehouse;
        next_id++;
    }

    public String getInventoryName() {
        return inventory_name;
    }

    public void setInventoryName(String inventory_name) {
        this.inventory_name = inventory_name;
    }

    public int getInventoryId() {
        return inventory_id;
    }

    public List<ItemWrapper> getItemList() {
        return item_list;
    }
    public void setItemList(List<ItemWrapper> item_list){
        this.item_list = item_list;
    }

    public void addItem(Item item, int quantity) {
        if (item_list.size() < capacity) {
            ItemWrapper it = new ItemWrapper(item, quantity);
            item_list.add(it);
        } else {
            System.out.println("Inventory is full, cannot add more items.");
        }
    }

    public void removeItem(ItemWrapper item) {
        item_list.remove(item);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public boolean hasItem(Item item) {
        for (ItemWrapper i : item_list) {
            if (i.getItem().equals(item)) {
                return true;
            }
        }
        return false;
    }

    public void DecreaseQuantity(Item item, int sale_quantity){
        for(ItemWrapper it : item_list){
            if (item.equals(it.getItem()) && sale_quantity <= it.getQuantity()){
                it.setQuantity(it.getQuantity() - sale_quantity);
            }
            else{
                String output = "There are not enough items for you to purchase!\nItem:";
                output += item.getItemName() + '\n' + "Quantity:" + it.getQuantity() + '\n';
                output += "Requested Quantity:" + sale_quantity +'\n';
                System.out.println(output);
            }
        }
    }

    public void ShowInventory(){
        System.out.println("\n");
        for(ItemWrapper it : item_list){
            String output = "Item: " + it.getItem().getItemName() + " ---- Brand: " + it.getItem().getBrand().getBrandName() + " ---- Quantity: " + it.getQuantity() + " ---- Price: " + it.getItem().getPrice();
            System.out.println(output);
        }
        System.out.println("\n");
    }
}
