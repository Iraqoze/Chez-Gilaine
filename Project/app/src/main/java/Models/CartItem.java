package Models;

public class CartItem {
    String name; String Url; String quantity; String amount;

    public CartItem(String name, String url, String quantity, String amount) {
        this.name = name;
        Url = url;
        this.quantity = quantity;
        this.amount = amount;
    }
public CartItem(){

}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "name='" + name + '\'' +
                ", Url='" + Url + '\'' +
                ", quantity='" + quantity + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
