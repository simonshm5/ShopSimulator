import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Employee {

	protected String[][] customerData = new String[30][6];

	Random rand = new Random();
	Scanner scan = new Scanner(System.in);

	public void checkOut() throws IOException {
		String cartname = "";
		String line = "";

		String linePrice = "";
		String[] prices = new String[4];
		String[] array = new String[10];
		String[] amounts = new String[4];

		int i = 0;
		int j = 0;

		System.out.println("Enter the name of the order");
		cartname = scan.next();

		File customerCart = new File("src/customerCarts/" + cartname + "cart.txt");
		BufferedReader orderReader = new BufferedReader(new FileReader(customerCart));
		BufferedReader totalReader = new BufferedReader(new FileReader(customerCart));

		// fix this later so it looks like the assignment
		if (customerCart.getName().contains(cartname)) {
			System.out.println("\nThe cart contains:\n-----------------------");
			while ((line = orderReader.readLine()) != null) {
				String trimmedLine = line.trim();
				System.out.println(trimmedLine);

			}
		}

		while ((linePrice = totalReader.readLine()) != null) {
			array = linePrice.split(", ");
			prices[i] = array[2];
			amounts[j] = array[1];
			i++;
			j++;

		}
		// get total price
		double totalPrice = 0;
		int k;

		for (k = 0; k < array.length; k++) {
			if (prices[k] != null) {
				totalPrice += (Double.parseDouble(amounts[k]) * Double.parseDouble(prices[k]));
			}
		}

		double totalPriceWithTax = .04 * totalPrice + totalPrice;

		System.out.println("-----------------------\nTotal:\t\t$" + String.format("%.2f", totalPrice));
		System.out.println("Discounts:\t$0.00");
		System.out.print("Tax:\t\t4%");
		System.out.println("\n-----------------------\nTotal  Price:\t$" + String.format("%.2f", totalPriceWithTax));

		System.out.println("\nCheckout Customer? Press \"1\" to checkout, or \"2\" to cancel."
				+ "\n(NOTE: Cancelling checkout does NOT delete the customer's order from our databases.");
		String checkOutDecision = scan.next();

		if (checkOutDecision.equalsIgnoreCase("1")) {
			System.out.println("Customer has successfully been checked out.");

		} else if (checkOutDecision.equalsIgnoreCase("2")) {
			System.out.println("Customer's checkout has been cancelled.");
		} else {
			System.out.println("Sorry, we don't recognize that. Please restart.");
		}

		orderReader.close();
		totalReader.close();
		scan.close();
	}

	public void displayInventory() throws FileNotFoundException {

		Scanner infile = new Scanner(
				new FileReader("/Users/simonshamoon/eclipse-workspace/Final Project/src/inventory.txt"));
		String line = "";
		System.out.println();
		while (infile.hasNextLine()) {
			line = infile.nextLine();
			System.out.print(line + "\n");
		}

		infile.close();

	}

	public void checkOrders() throws IOException {
		File customerCartsFolder = new File("/Users/simonshamoon/eclipse-workspace/Final Project/src/customerCarts");
		String[] individualCarts = customerCartsFolder.list();
		String filename = "";
		System.out.println();

		System.out.println("Pending customer carts (If empty, no orders are currently pending): ");
		if (individualCarts == null) {
			System.out.println("There are no current orders.");
		} else {
			for (int i = 0; i < individualCarts.length; i++) {

				filename = individualCarts[i];

				System.out.println(filename);
			}
		}

		reviewCustomerOrder();
	}
	
	public void reviewCustomerOrder() throws IOException {

		String fileNameRead = "";
		String line = "";

		System.out.println("\nWhat order would you like to review?");
		fileNameRead = scan.next();

		BufferedReader reader = new BufferedReader(new FileReader("src/customerCarts/" + fileNameRead + "cart.txt"));

		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
	}

	public void removeOrders() throws IOException {

		String removedOrder = "";

		checkOrders();

		System.out.println("\nWhich cart do you want to remove?");
		removedOrder = scan.next();

		File customerOrder = new File("src/customerCarts/" + removedOrder + "cart.txt");

		customerOrder.delete();

		System.out.println("\n" + removedOrder + "'s cart has successfully been deleted.");

	}

	public void addCustomer() throws IOException {

		int numCustomers = 0;

		System.out.print("How many customers?\n");
		numCustomers = scan.nextInt();
		scan.nextLine();

		BufferedWriter writer = new BufferedWriter(
				new FileWriter("/Users/simonshamoon/eclipse-workspace/Final Project/src/customerdata.txt", true));

		BufferedWriter loginWriter = new BufferedWriter(
				new FileWriter("/Users/simonshamoon/eclipse-workspace/Final Project/src/userlogin.txt", true));

		for (int i = 0; i < numCustomers; i++) {

			for (int j = 0; j < customerData[i].length; j++) {
				if (j == 0) {
					System.out.println("First name");
				}
				if (j == 1) {
					System.out.println("Last name");
				}
				if (j == 2) {
					System.out.println("Address");
				}
				if (j == 3) {
					System.out.println("City");
				}
				if (j == 4) {
					System.out.println("State");
				}
				if (j == 5) {
					System.out.println("Zip");
				}
				customerData[i][j] = scan.nextLine();

			}
			writer.write(customerData[i][0] + ", " + customerData[i][1] + ", " + customerData[i][2] + ", "
					+ customerData[i][3] + ", " + customerData[i][4] + ", " + customerData[i][5] + "\n");

			loginWriter.write(customerData[i][0].charAt(0) + customerData[i][1].toLowerCase() + ", "
					+ rand.nextInt(10001) + "ASU" + ", Customer" + "\n");

		}

		writer.flush();
		writer.close();
		loginWriter.flush();
		loginWriter.close();

	}

	public void displayEmployeeMenu() throws IOException {
		Scanner scan = new Scanner(System.in);
		String decision = "";
		System.out.println("1. Checkout customer"); //Employee - 1. Checkout Customer

		System.out.println("2. Check inventory"); //Employee - 2. Check Inventory

		System.out.println("3. Check orders"); //Employee - 3. Review current online orders. && Employee - 4. Review a customer’s order.

		System.out.println("4. Remove order"); //Employee - 5. Delete a customer’s order.

		System.out.println("5. Create customer account"); //Employee - 6. Create customer account
		decision = scan.next();

		switch (decision) {

		case "1":
			checkOut(); 
			break;
		case "2":
			displayInventory();
			break;
		case "3":
			checkOrders();
			break;
		case "4":
			removeOrders();
			break;
		case "5":
			addCustomer();
			break;
		}

		scan.close();

	}

}
