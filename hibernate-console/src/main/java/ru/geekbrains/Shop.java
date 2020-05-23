package ru.geekbrains;

import lombok.SneakyThrows;
import ru.geekbrains.entities.Customer;
import ru.geekbrains.entities.OrderProduct;
import ru.geekbrains.entities.Product;
import ru.geekbrains.repo.ShopRepo;
import ru.geekbrains.repo.ShopRepoImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.*;
import java.util.concurrent.Callable;

public class Shop {
    public static void main(String[] args) {
        EntityManagerFactory factory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        EntityManager em = factory.createEntityManager();
        ShopRepo repo = new ShopRepoImpl(em);
        Shop shop = new Shop(repo);
        shop.run();
    }

    private ShopRepo repo;
    private Map<Integer, Callable<Boolean>> menu;
    private Scanner in;

    public Shop(ShopRepo repo) {
        this.repo = repo;
        this.in = new Scanner(System.in);
        this.menu = new HashMap<>();
        setupMenu();
    }

    public void run() {
        while (true) {
            printSeparator();
            int choice = dialogChoice();
            printSeparator();
            if (!resolveChoice(choice)) break;
        }
    }

    private int dialogChoice() {
        while (true) {
            System.out.println("1. Get product titles by customer name");
            System.out.println("2. Get product titles by customer name with order prices");
            System.out.println("3. Get customer names by product title");
            System.out.println("4. Get customer names by product title with order prices");
            System.out.println("5. Remove customer");
            System.out.println("6. Remove product");
            System.out.println("7. Exit");
            System.out.println("Choose:");
            try {
                int i = Integer.parseInt(in.nextLine());
                if (i >= 1 && i <= menu.size()) return i;
                else throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Wrong number!");
            }
        }
    }

    private void setupMenu() {
        menu.put(1, () -> {
            System.out.println("Enter customer name:");
            String name = in.nextLine();
            Set<Product> products = repo.getProductsByCustomerName(name);
            printSeparator();
            if (products == null) System.out.println("No customer '" + name + "'");
            else if (products.size() == 0) System.out.println("No products!");
            else {
                System.out.println("Products of customer '" + name + "':");
                products.stream()
                        .map(Product::getTitle)
                        .forEach(System.out::println);
            }
            return true;
        });
        menu.put(2, () -> {
            System.out.println("Enter customer name:");
            String name = in.nextLine();
            List<OrderProduct> orders = repo.getOrderProductsByCustomerName(name);
            printSeparator();
            if (orders == null) System.out.println("No customer '" + name + "'");
            else if (orders.size() == 0) System.out.println("No orders!");
            else {
                System.out.println("Orders of customer '" + name + "':");
                orders.forEach(orderProducts -> System.out.println(orderProducts.getProduct().getTitle() + "\t" +
                        String.format("%.2f", orderProducts.getPrice())));
            }
            return true;
        });
        menu.put(3, () -> {
            System.out.println("Enter product title:");
            String title = in.nextLine();
            Set<Customer> customers = repo.getCustomersByProductTitle(title);
            printSeparator();
            if (customers == null) System.out.println("No product '" + title + "'");
            else if (customers.size() == 0) System.out.println("No customers!");
            else {
                System.out.println("Customers of product '" + title + "':");
                customers.stream()
                        .map(Customer::getName)
                        .forEach(System.out::println);
            }
            return true;
        });
        menu.put(4, () -> {
            System.out.println("Enter product title:");
            String title = in.nextLine();
            List<OrderProduct> orders = repo.getOrderProductsByProductTitle(title);
            printSeparator();
            if (orders == null) System.out.println("No product '" + title + "'");
            else if (orders.size() == 0) System.out.println("No orders!");
            else {
                System.out.println("Orders of product '" + title + "':");
                orders.forEach(orderProducts -> System.out.println(orderProducts.getCustomer().getName() + "\t" +
                        String.format("%.2f", orderProducts.getPrice())));
            }
            return true;
        });
        menu.put(5, () -> {
            System.out.println("Enter customer name to delete:");
            String name = in.nextLine();
            repo.deleteCustomerByName(name);
            return true;
        });
        menu.put(6, () -> {
            System.out.println("Enter product title to delete:");
            String title = in.nextLine();
            repo.deleteProductByTitle(title);
            return true;
        });
        menu.put(7, () -> {
            System.out.println("Good bye!");
            return false;
        });
    }

    @SneakyThrows
    private boolean resolveChoice(int i) {
        return menu.get(i).call();
    }

    private void printSeparator() {
        System.out.println("----------------");
    }
}
