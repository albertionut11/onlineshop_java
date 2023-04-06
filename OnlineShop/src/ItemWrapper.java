public class ItemWrapper {
    private Item item;
    private int quantity;

    public ItemWrapper(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setItem(Item item){
        this.item = item;
    }
}
