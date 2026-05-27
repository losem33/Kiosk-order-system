public abstract class MenuItem {

    // 공통 필드 (모든 메뉴가 가지는 정보)
    private String name;
    private int price;
    private String description;

    // 생성자 - 자식 클래스에서 super()로 호출
    public MenuItem(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // Getter 메서드
    public String getName()        { return name; }
    public int    getPrice()       { return price; }
    public String getDescription() { return description; }

    // 추상 메서드 - 자식 클래스가 반드시 구현해야 함
    public abstract String getCategory();
    public abstract void displayInfo();
}