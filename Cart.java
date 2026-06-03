import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem mi, int qty) {
        items.add(new CartItem(mi, qty));
    }

    public void removeItem(int idx) {
        if (idx >= 0 && idx < items.size()) {
            items.remove(idx);
        }
    }

    public int getTotalPrice() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            System.out.println((i + 1) + ". " + item.getMenuItem().getName() + 
                               " | 수량: " + item.getQuantity() + 
                               " | 소계: " + item.getSubtotal() + "원");
        }
    }

    public void clear() {
        items.clear();
    }
}