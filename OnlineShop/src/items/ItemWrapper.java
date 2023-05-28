package items;

import database.DatabaseReaderService;

public class ItemWrapper {
    private Item item;
    private int quantity, item_id, iw_id, inventory_id;

    public ItemWrapper(Item item, int quantity) {
        this.item_id = item.getItemId();
        this.item = item;
        this.quantity = quantity;
        this.iw_id = DatabaseReaderService.getInstance().getMaxIWId() + 1;
    }

    public ItemWrapper(int iw_id, Item item, int quantity) {
        this.item_id = item.getItemId();
        this.item = item;
        this.quantity = quantity;
        this.iw_id = iw_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nIW ID: ").append(iw_id)
                .append("\nItem ID: ").append(item_id)
                .append("\nItem: ").append(item.toString())
                .append("\nQuantity: ").append(quantity)
                .append("\nInventory ID: ").append(inventory_id);
        return sb.toString();
    }


    public int getIw_id(){return iw_id;}
    public Item getItem() {
        return item;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getInventory_id(){return inventory_id;}
    public int getItem_id(){return item_id;}
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setItem(Item item){
        this.item = item;
    }
    public void setInventory_id(int inventory_id) {this.inventory_id = inventory_id;}
    public void setIw_id(int iw_id){this.iw_id = iw_id;}
    public void setItem_id(int itemId){this.item_id = itemId;}
}
