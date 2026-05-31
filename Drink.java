public class Drink extends MenuItem {
    private String size;
    private boolean isCold;

    public Drink(String name, int price, String desc, String size, boolean isCold) {
        super(name, price, desc);
        this.size = size;
        this.isCold = isCold;
    }

    @Override
    public String getCategory() { return "drink"; }

    @Override
    public void displayInfo() {
        System.out.println("[drink] " + getName() + " | " + getPrice() + "원" + " | " + size + (isCold ? " ❄ 아이스" : " ☕ 핫"));
    }
}
