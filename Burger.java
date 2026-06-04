public class Burger extends MenuItem {
    private boolean isSpicy;

    public Burger(String name, int price, String desc, boolean isSpicy) {
        super(name, price, desc);
        this.isSpicy = isSpicy;
    }

    @Override
    public String getCategory() { return "burger"; }

    @Override
    public void displayInfo() {
        System.out.println("[버거] " + getName() + " | " + getPrice() + "원" + (isSpicy ? " | 매움" : ""));
    }
}
