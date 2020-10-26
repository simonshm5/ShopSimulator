import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Customer {

	private String[][] cart = new String[30][3];

	private Employee e = new Employee();

	Scanner scan = new Scanner(System.in);

	
	public void loadCart() throws IOException {
		String name = "";
		String line;

		System.out.println("Enter the name you used to save your cart:");
		name = scan.next();

		BufferedReader reader = new BufferedReader(new FileReader("src/customerCarts/" + name + "cart.txt"));

		System.out.println("\nYour cart\n===============================\nName   |   quantity   |   price");

		while ((line = reader.readLine()) != null) {
			String trimmedLine = line.trim();
			System.out.println(trimmedLine);
		}

	}
	
	public void saveCart(File customerCart, String decision, String name, String[][] arr) throws IOException {

		if (decision.equalsIgnoreCase("yes")) {
			System.out.println("\nSaved to cart!"
					+ "\nPlease do not forget the name you used to save your cart at the beginning of this purchase."
					+ "\nYour order name is under " + "\"" + name + "\".");
		} else if (decision.equalsIgnoreCase("no")) {
			System.out.println("\nYour order has been cancelled and not saved.");
			customerCart.delete();
		} else {
			System.out.println(
					"\nSorry, an error has occured. Your order has not been saved. Please restart application and try again.");
			customerCart.delete();
		}
	}
	
	public void shopForItems() throws IOException {

		int numItems = 0;
		String name = "";

		System.out.println("\nWhat is your name? This will be used to save your cart.");
		name = scan.next();

		File customerCart = new File("src/customerCarts/" + name + "cart.txt");

		BufferedWriter writer = new BufferedWriter(new FileWriter(customerCart));

		customerCart.createNewFile();

		e.displayInventory();

		System.out.println("\nHow many different items would you like?"
				+ "\nEx: If you would like to purchase bananas and apples, that is 2 different items.");
		numItems = scan.nextInt();
		for (int i = 0; i < numItems; i++) {

			System.out.println("\nEnter item data.\n");

			for (int j = 0; j < cart[i].length; j++) {
				if (j == 0) {
					System.out.println("Item name:");
				} else if (j == 1) {
					System.out.println("\nQuantity:");
				} else if (j == 2) {
					System.out.println("\nPrice:");
				}
				cart[i][j] = scan.next();

			}

			double individualTotal = (Integer.parseInt(cart[i][1]) * Double.parseDouble(cart[i][2]));

			writer.write(cart[i][0] + ", " + cart[i][1] + ", " + cart[i][2] + "\n");

		}
		System.out.println("\nWould you like to save to cart?");
		String decision = scan.next();

		saveCart(customerCart, decision, name, cart);
		writer.flush();
		writer.close();
	}
	
	public void displayCustomerMenu() throws IOException {
		String decision = "";
		System.out.println("1. Load cart"); //Customer - 1. Load Data

		System.out.println("2. Checkout"); //Customer - 3. Checkout

		System.out.println("3. Create account"); //Customer - 4. Create account

		System.out.println("4. Shop for items and manage cart"); //Customer - 5. Shop && Customer - 2. Save Data
		decision = scan.next();

		switch (decision) {

		case "1":
			loadCart();
			break;
		case "2":
			e.checkOut(); 
			break;
		case "3":
			e.addCustomer();
			break;
		case "4":
			shopForItems();
			break;
		}
	}

}
