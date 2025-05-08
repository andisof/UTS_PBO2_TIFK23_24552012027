import java.util.*;

// ===== Abstract Menu Base Class =====
abstract class ItemMenu {
    private int id;
    private String nama;
    private String jenis;
    private double harga;

    public ItemMenu(int id, String nama, String jenis, double harga) {
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getJenis() {
        return jenis;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public abstract double calculateTotal(int jumlah);
}

// ===== Subclasses =====
class Makanan extends ItemMenu {
    public Makanan(int id, String nama, double harga) {
        super(id, nama, "makanan", harga);
    }

    @Override
    public double calculateTotal(int jumlah) {
        return getHarga() * jumlah;
    }
}

class Minuman extends ItemMenu {
    public Minuman(int id, String nama, double harga) {
        super(id, nama, "minuman", harga);
    }

    @Override
    public double calculateTotal(int jumlah) {
        return getHarga() * jumlah;
    }
}

// ===== DetailOrder =====
class DetailOrder {
    private ItemMenu menu;
    private int jumlah;

    public DetailOrder(ItemMenu menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }

    public void printDetail() {
        double total = menu.calculateTotal(jumlah);
        System.out.println(menu.getNama() + " x " + jumlah + " = Rp" + total);
    }

    public double getTotal() {
        return menu.calculateTotal(jumlah);
    }
}

// ===== Main CLI Program =====
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<ItemMenu> menus = new ArrayList<>();
    private static List<DetailOrder> orderDetails = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Menu Management System ===");

        // Input menu items
        inputMenus();

        // Input orders
        inputOrders();

        // Show total
        printOrders();
    }

    private static void inputMenus() {
        System.out.print("Masukan Banyak Menu : ");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("Menu #" + (i + 1));
            System.out.print("Jenis Menu (Makanan/Minuman): ");
            String jenis = scanner.nextLine().toLowerCase();

            System.out.print("Nama: ");
            String nama = scanner.nextLine();

            System.out.print("Harga: ");
            double harga = Double.parseDouble(scanner.nextLine());

            ItemMenu menu;
            if (jenis.equals("makanan")) {
                menu = new Makanan(i + 1, nama, harga);
            } else {
                menu = new Minuman(i + 1, nama, harga);
            }

            menus.add(menu);
        }
    }

    private static void inputOrders() {
        System.out.println("\n=== Halaman Order ===");
        while (true) {
            System.out.println("\n Menu Yang Tersedia:");
            for (ItemMenu m : menus) {
                System.out.println(m.getId() + ". " + m.getNama() + " (" + m.getJenis() + ") - Rp" + m.getHarga());
            }

            System.out.print("Input Id Menu  (Tekan 0 jika selesai): ");
            int id = Integer.parseInt(scanner.nextLine());
            if (id == 0) break;

            ItemMenu selected = null;
            for (ItemMenu m : menus) {
                if (m.getId() == id) {
                    selected = m;
                    break;
                }
            }

            if (selected == null) {
                System.out.println("Menu tidak tersedia.");
                continue;
            }

            System.out.print("Jumlah yang di pesan : ");
            int qty = Integer.parseInt(scanner.nextLine());

            orderDetails.add(new DetailOrder(selected, qty));
        }
    }

    private static void printOrders() {
        System.out.println("\n=== Detail order  ===");
        double grandTotal = 0;
        for (DetailOrder d : orderDetails) {
            d.printDetail();
            grandTotal += d.getTotal();
        }



        System.out.println("Grand Total: Rp" + grandTotal);
    }
}
