public class Order {
    private int orderId;
    private String status;

    public Order(int orderId, String status) {
        this.orderId = orderId;
        this.status = status;
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
        System.out.println("===== 영 수 증 =====");
        System.out.println("주문 번호: " + orderId);
        System.out.println("주문 상태: " + status);
        // 여기에 나중에 장바구니 품목 출력 기능을 추가하면 됩니다!
    }
}