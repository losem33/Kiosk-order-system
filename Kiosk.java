import java.util.ArrayList;
import java.util.List;

public class Kiosk {
    private List<MenuItem> menuList; 
    public Kiosk() {
        this.menuList = new ArrayList<>();
        this.menuList.add(new Drink("콜라", 2000, "시원한 코카콜라", "Regular", true));
        this.menuList.add(new Drink("사이다", 2000, "청량한 스프라이트", "Regular", true));
        this.menuList.add(new Drink("아메리카노", 2500, "깊고 진한 에스프레소", "Large", false));
    }

    public void start() {
        System.out.println("키오스크 시스템을 시작합니다.");
        showMainMenu();
        handleOrder();
    }

    public void showMainMenu() {
        System.out.println("[ KIOSK MAIN MENU ]");
        
        for (int i = 0; i < menuList.size(); i++) {
            MenuItem item = menuList.get(i);
            
            System.out.print((i + 1) + ". ");
            item.displayInfo();
        }
    }

    public void handleOrder() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        
        System.out.print("\n구매할 메뉴 번호를 입력해 주세요: ");
        int choice = sc.nextInt(); 
        
        if (choice > 0 && choice <= menuList.size()) {
            MenuItem selectedItem = menuList.get(choice - 1); 
            System.out.println("\n[선택한 메뉴]");
            selectedItem.displayInfo();
            
            System.out.println("\n주문을 확정합니다...");
            Order currentOrder = new Order(1001, "Pending"); 
            currentOrder.confirm();       
            currentOrder.displayReceipt(); 
            
        } else {
            System.out.println("잘못된 번호입니다. 프로그램을 종료합니다.");
        }
        
        sc.close();
    }

    public static void main(String[] args) {
        Kiosk kiosk = new Kiosk();
        kiosk.start(); 
    }
}