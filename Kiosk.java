import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kiosk {
    private List<MenuItem> menuList;
    private Cart cart;

    public Kiosk() {
        this.menuList = new ArrayList<>();
        this.cart = new Cart();

        menuList.add(new Burger("불고기버거", 5500, "달콤한 불고기 패티", false));
        menuList.add(new Burger("스파이시버거", 6000, "매콤한 패티", true));
        menuList.add(new Drink("콜라", 2000, "시원한 코카콜라", "M", true));
        menuList.add(new Drink("아메리카노", 2500, "깊고 진한 에스프레소", "S", false));
        menuList.add(new Side("감자튀김", 2000, "바삭한 감자", 150));
    }

    public void start() {
        System.out.println("키오스크 시스템을 시작합니다.");
        handleOrder();
    }

    public void showMainMenu() {
        System.out.println("\n[ KIOSK MAIN MENU ]");
        for (int i = 0; i < menuList.size(); i++) {
            System.out.print((i + 1) + ". ");
            menuList.get(i).displayInfo();
        }
        System.out.println("0. 주문 확정");
    }

    public void handleOrder() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            showMainMenu();
            System.out.print("\n메뉴 번호를 선택하세요 (0: 주문 확정): ");
            int choice = sc.nextInt();

            if (choice == 0) break;

            if (choice < 1 || choice > menuList.size()) {
                System.out.println("잘못된 번호입니다. 다시 선택해 주세요.");
                continue;
            }

            MenuItem selected = menuList.get(choice - 1);
            System.out.print("수량을 입력하세요: ");
            int qty = sc.nextInt();
            cart.addItem(selected, qty);
            System.out.println(selected.getName() + " " + qty + "개 장바구니에 추가되었습니다.");

            System.out.println("\n[ 현재 장바구니 ]");
            cart.displayCart();
            System.out.println("현재 합계: " + cart.getTotalPrice() + "원");
        }

        if (cart.isEmpty()) {
            System.out.println("장바구니가 비어 있어 주문을 종료합니다.");
            sc.close();
            return;
        }

        System.out.println("\n[ 최종 주문 내역 ]");
        cart.displayCart();
        System.out.println("총 합계: " + cart.getTotalPrice() + "원");
        System.out.print("주문하시겠습니까? (1: 확정 / 2: 취소): ");
        int confirm = sc.nextInt();

        Order order = new Order(1001, cart);
        if (confirm == 1) {
            order.confirm();
            order.displayReceipt();
        } else {
            order.cancel();
        }

        sc.close();
    }

    public static void main(String[] args) {
        Kiosk kiosk = new Kiosk();
        kiosk.start();
    }
}
