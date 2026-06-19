public class Order {
    private int orderId;
    private Cart cart;
    private String status;

    public Order(int orderId, Cart cart) {
        this.orderId = orderId;
        this.cart = cart;
        this.status = "Pending";
    }

    public void confirm() {
        System.out.println("주문이 확정되었습니다. 주문 번호: " + orderId);
        this.status = "Confirmed";
    }

    public void cancel() {
        System.out.println("주문이 취소되었습니다.");
        this.status = "Canceled";
    }

    public void displayReceipt() {
        System.out.println("===== 영수증 =====");
        System.out.println("주문 번호: " + orderId);
        cart.displayCart();
        System.out.println("합계: " + cart.getTotalPrice() + "원");
        System.out.println("주문 상태: " + status);
    }
}
