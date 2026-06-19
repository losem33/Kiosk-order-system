import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class KioskGUI extends JFrame {

    private final List<MenuItem> menuList = new ArrayList<>();
    private final Cart cart = new Cart();
    private int nextOrderId = 1001;

    private final DefaultListModel<String> cartModel = new DefaultListModel<>();
    private JList<String> cartList;
    private JLabel totalLabel;

    // ── 다크 테마 색상 ──────────────────────────────
    private static final Color BG        = new Color(15,  17,  23);
    private static final Color SURFACE   = new Color(23,  28,  36);
    private static final Color SURFACE2  = new Color(30,  37,  51);
    private static final Color BORDER    = new Color(42,  49,  64);
    private static final Color ACCENT    = new Color(79,  195, 247);
    private static final Color GREEN     = new Color(86,  211, 100);
    private static final Color ORANGE    = new Color(255, 179, 71);
    private static final Color RED       = new Color(240, 113, 120);
    private static final Color TEXT      = new Color(226, 232, 240);
    private static final Color MUTED     = new Color(136, 146, 164);

    public KioskGUI() {
        menuList.add(new Burger("불고기버거",  5500, "달콤한 불고기 패티", false));
        menuList.add(new Burger("스파이시버거", 6000, "매콤한 패티",       true));
        menuList.add(new Drink("콜라",        2000, "시원한 코카콜라",    "M", true));
        menuList.add(new Drink("아메리카노",   2500, "깊고 진한 에스프레소","S", false));
        menuList.add(new Side("감자튀김",     2000, "바삭한 감자",        150));
        buildUI();
    }

    // ── UI 구성 ─────────────────────────────────────
    private void buildUI() {
        setTitle("Kiosk Order System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 580);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        // 헤더
        JLabel header = new JLabel("KIOSK ORDER SYSTEM", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setOpaque(true);
        header.setBackground(SURFACE);
        header.setForeground(ACCENT);
        header.setBorder(new EmptyBorder(16, 0, 16, 0));
        add(header, BorderLayout.NORTH);

        // 메뉴 + 장바구니 분할
        JSplitPane split = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            buildMenuPanel(),
            buildCartPanel()
        );
        split.setDividerLocation(540);
        split.setDividerSize(3);
        split.setBorder(null);
        split.setBackground(BORDER);
        add(split, BorderLayout.CENTER);
    }

    // ── 메뉴 패널 ────────────────────────────────────
    private JPanel buildMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(SURFACE);

        JLabel title = sectionLabel("  MENU");
        panel.add(title, BorderLayout.NORTH);

        JPanel items = new JPanel();
        items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));
        items.setBackground(SURFACE);
        items.setBorder(new EmptyBorder(4, 0, 4, 0));

        String[][] categories = {{"burger", "버거"}, {"drink", "음료"}, {"side", "사이드"}};
        for (String[] cat : categories) {
            List<MenuItem> group = filterByCategory(cat[0]);
            if (group.isEmpty()) continue;

            items.add(categoryLabel(cat[1]));
            for (MenuItem m : group) {
                items.add(menuButton(m));
                items.add(Box.createRigidArea(new Dimension(0, 2)));
            }
        }
        items.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(items);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(SURFACE);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private List<MenuItem> filterByCategory(String cat) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem m : menuList) {
            if (m.getCategory().equals(cat)) result.add(m);
        }
        return result;
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(MUTED);
        label.setOpaque(true);
        label.setBackground(SURFACE);
        label.setBorder(new EmptyBorder(10, 12, 8, 0));
        return label;
    }

    private JLabel categoryLabel(String name) {
        JLabel label = new JLabel("  ─ " + name);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(ORANGE);
        label.setOpaque(true);
        label.setBackground(SURFACE);
        label.setBorder(new EmptyBorder(10, 8, 4, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JButton menuButton(MenuItem item) {
        String html = String.format(
            "<html><div style='padding:3px 6px'>"
            + "<b style='font-size:13px; color:#e2e8f0'>%s</b>"
            + "&nbsp;&nbsp;<b style='color:#4fc3f7'>%,d원</b>"
            + "<br><span style='color:#8892a4; font-size:10px'>%s</span>"
            + "</div></html>",
            item.getName(), item.getPrice(), item.getDescription());

        JButton btn = new JButton(html);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 66));
        btn.setPreferredSize(new Dimension(500, 66));
        btn.setBackground(SURFACE2);
        btn.setForeground(TEXT);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setBorder(new CompoundBorder(
            new EmptyBorder(1, 8, 1, 8),
            new MatteBorder(0, 0, 1, 0, BORDER)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(BORDER); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(SURFACE2); }
        });
        btn.addActionListener(e -> onMenuClick(item));
        return btn;
    }

    // ── 장바구니 패널 ─────────────────────────────────
    private JPanel buildCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(BG);

        JLabel title = sectionLabel("  장바구니");
        title.setBackground(BG);
        panel.add(title, BorderLayout.NORTH);

        cartList = new JList<>(cartModel);
        cartList.setBackground(BG);
        cartList.setForeground(TEXT);
        cartList.setFont(new Font("Monospaced", Font.PLAIN, 13));
        cartList.setSelectionBackground(BORDER);
        cartList.setSelectionForeground(TEXT);
        cartList.setFixedCellHeight(38);
        cartList.setBorder(new EmptyBorder(4, 8, 4, 8));

        JScrollPane scroll = new JScrollPane(cartList);
        scroll.setBorder(new MatteBorder(1, 0, 1, 0, BORDER));
        scroll.getViewport().setBackground(BG);
        panel.add(scroll, BorderLayout.CENTER);

        panel.add(buildBottomPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildBottomPanel() {
        JPanel bottom = new JPanel(new BorderLayout(0, 10));
        bottom.setBackground(BG);
        bottom.setBorder(new EmptyBorder(12, 14, 14, 14));

        totalLabel = new JLabel("합계:  0원", SwingConstants.RIGHT);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        totalLabel.setForeground(GREEN);
        bottom.add(totalLabel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 8, 0));
        btnPanel.setBackground(BG);

        JButton removeBtn = styledBtn("선택 삭제", SURFACE2, MUTED);
        JButton clearBtn  = styledBtn("전체 취소", SURFACE2, RED);
        JButton orderBtn  = styledBtn("주문 확정", new Color(0, 102, 204), Color.WHITE);

        removeBtn.addActionListener(e -> onRemove());
        clearBtn.addActionListener(e -> onClear());
        orderBtn.addActionListener(e -> onConfirm());

        btnPanel.add(removeBtn);
        btnPanel.add(clearBtn);
        btnPanel.add(orderBtn);
        bottom.add(btnPanel, BorderLayout.SOUTH);
        return bottom;
    }

    private JButton styledBtn(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(0, 38));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ── 이벤트 핸들러 ─────────────────────────────────
    private void onMenuClick(MenuItem item) {
        String input = JOptionPane.showInputDialog(
            this,
            String.format("%s  (%,d원)\n수량을 입력하세요:", item.getName(), item.getPrice()),
            "수량 입력",
            JOptionPane.PLAIN_MESSAGE
        );
        if (input == null) return;
        try {
            int qty = Integer.parseInt(input.trim());
            if (qty <= 0) throw new NumberFormatException();
            cart.addItem(item, qty);
            refreshCart();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "1 이상의 올바른 수량을 입력해 주세요.",
                "입력 오류", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onRemove() {
        int idx = cartList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "삭제할 항목을 선택해 주세요.");
            return;
        }
        cart.removeItem(idx);
        refreshCart();
    }

    private void onClear() {
        if (cart.isEmpty()) return;
        int res = JOptionPane.showConfirmDialog(
            this, "장바구니를 비우시겠습니까?", "전체 취소", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            cart.clear();
            refreshCart();
        }
    }

    private void onConfirm() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "장바구니가 비어 있습니다.");
            return;
        }
        Order order = new Order(nextOrderId, cart);
        order.confirm();

        StringBuilder sb = new StringBuilder();
        sb.append("주문 번호: ").append(nextOrderId++).append("\n");
        sb.append("─".repeat(34)).append("\n");
        for (CartItem ci : cart.getItems()) {
            sb.append(String.format("%-12s  x%d    %,d원\n",
                ci.getMenuItem().getName(), ci.getQuantity(), ci.getSubtotal()));
        }
        sb.append("─".repeat(34)).append("\n");
        sb.append(String.format("합계:  %,d원", cart.getTotalPrice()));

        JOptionPane.showMessageDialog(this, sb.toString(), "주문 완료 - 영수증", JOptionPane.INFORMATION_MESSAGE);
        cart.clear();
        refreshCart();
    }

    private void refreshCart() {
        cartModel.clear();
        for (CartItem ci : cart.getItems()) {
            cartModel.addElement(String.format("  %-12s  x%d    %,d원",
                ci.getMenuItem().getName(), ci.getQuantity(), ci.getSubtotal()));
        }
        totalLabel.setText(String.format("합계:  %,d원", cart.getTotalPrice()));
    }

    // ── 진입점 ───────────────────────────────────────
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new KioskGUI().setVisible(true));
    }
}
