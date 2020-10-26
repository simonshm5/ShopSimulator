import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Manager extends Employee {
	protected String[][] empLogin = new String[30][3];
	protected String[][] empData = new String[30][3]; // ID and username are generated
	protected String[][] itemData = new String[5][3];
	protected String[][] itemDataRemoval = new String[5][3];

	Scanner scan = new Scanner(System.in);
	Random rand = new Random();

	
	public void addItems() throws IOException {
		int numItems = 0;

		PrintWriter outfileData = new PrintWriter(new BufferedWriter(
				new FileWriter("/Users/simonshamoon/eclipse-workspace/Final Project/src/inventory.txt", true)));

		System.out.println("\nHow many items would you like to add?");
		numItems = scan.nextInt();

		for (int i = 0; i < numItems; i++) {

			System.out.println("\nEnter item #" + numItems + " data (NOTE: Item names CANNOT include spaces)\nCorrect formatting example for multi-word products: [icedCoffee, 20, 3.99]\n");

			for (int j = 0; j < itemData[i].length; j++) {
				if (j == 0) {
					System.out.println("Name:");
				}
				if (j == 1) {
					System.out.println("Quantity:");
				}
				if (j == 2) {
					System.out.println("Price:");
				}
				itemData[i][j] = scan.next();

			}

			outfileData.append(itemData[i][0] + ", " + itemData[i][1] + ", " + itemData[i][2] + "\n");

		}
		outfileData.flush();
		outfileData.close();
	}

	
	public void modifyItems() throws IOException {

		File file = new File("src/inventory.txt");

		String[] oldItemLine = new String[31];

		System.out.println();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int count = 0;
		String line = "";
		while ((line = reader.readLine()) != null) {
			oldItemLine[count] = line + System.lineSeparator();
			System.out.println(line);
			count++;
		}
		reader.close();

		System.out.println("\nEnter the first token of the line that contains the item"
				+ "\nEx: for \"pineapple, 50, 50\", you would input \"pineapple\" to modify item. ");
		String replacee = scan.next();
		scan.nextLine();
		System.out.println("Enter the updated information in format (Name, Quantity, Price)"
				+ "\nEx: if you want to enter 30 pineapples at a price of 5.50, enter \"pineapples, 30, 5.50\"");
		String replacer = scan.nextLine() + "\n";

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < count; i++) {
			if (oldItemLine[i].startsWith(replacee)) {
				writer.write(replacer);
			} else {
				writer.write(oldItemLine[i]);
			}
		}
		writer.flush();
		writer.close();
	}

	
	public void removeItems() throws IOException {

		String line;

		File inventory = new File("src/inventory.txt");
		File temp = new File("src/deleteditems.txt");

		BufferedReader reader = new BufferedReader(new FileReader("src/inventory.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("src/deleteditems.txt"));

		displayInventory();

		temp.createNewFile();

		System.out.println("\nWhat item would you like to remove?");
		String removedLine = scan.next();

		while ((line = reader.readLine()) != null) {
			String trimmedLine = line.trim();
			if (trimmedLine.startsWith(removedLine)) {
				trimmedLine = "[THE ITEM " + removedLine + " IS CURRENTLY UNAVAILABLE]";
			}
			writer.write(trimmedLine + System.getProperty("line.separator"));
		}
		System.out.println("\nItem has successfully been marked as [UNAVAILABLE]");
		reader.close();
		writer.close();
		inventory.delete();
		temp.renameTo(inventory);

	}

	
	public void addEmployees() throws IOException {
		int numEmployees = 0;

		PrintWriter outfileData = new PrintWriter(new BufferedWriter(
				new FileWriter("/Users/simonshamoon/eclipse-workspace/Final Project/src/employeedata.txt", true)));
		PrintWriter outfileLogin = new PrintWriter(new BufferedWriter(
				new FileWriter("/Users/simonshamoon/eclipse-workspace/Final Project/src/userlogin.txt", true)));

		System.out.println("How many employees would you like to add?");
		numEmployees = scan.nextInt();

		for (int i = 0; i < numEmployees; i++) {

			System.out.println("Enter customer data");

			for (int j = 0; j < empData[i].length; j++) {
				if (j == 0) {
					System.out.println("First name");
				} else if (j == 1) {
					System.out.println("Last name");
				} else if (j == 2) {
					System.out.println("Position");
				}
				empData[i][j] = scan.next();

			}

			outfileData.append("000" + rand.nextInt(10000) + ", " + empData[i][0] + ", " + empData[i][1] + ", "
					+ empData[i][2] + ", " + (empData[i][0].substring(0, 1) + empData[i][1]).toLowerCase() + "\n");

			outfileLogin.append(empData[i][0].charAt(0) + empData[i][1].toLowerCase() + ", " + rand.nextInt(10000)
					+ "ASU" + ", " + empData[i][2] + "\n");
		}

		outfileData.flush();
		outfileData.close();

		outfileLogin.flush();
		outfileLogin.close();
	}

	
	public void removeEmployees() throws IOException {
		String line;

		File employeedata = new File("src/employeedata.txt");
		File temp = new File("src/deletedemployees.txt");

		BufferedReader reader = new BufferedReader(new FileReader("src/employeedata.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("src/deletedemployees.txt"));

		displayEmployees();

		temp.createNewFile();

		System.out.println("Which employee would you like to remove?");
		String removedLine = scan.next();

		while ((line = reader.readLine()) != null) {
			String trimmedLine = line.trim();
			if (trimmedLine.contains(removedLine)) {
				trimmedLine = "[DELETED], [DELETED], [DELETED], [DELETED], [DELETED]";
			}
			writer.write(trimmedLine + System.getProperty("line.separator"));
		}
		reader.close();
		writer.close();
		employeedata.delete();
		temp.renameTo(employeedata);
	}

	
	public void displayEmployees() throws FileNotFoundException {

		Scanner infile = new Scanner(
				new FileReader("/Users/simonshamoon/eclipse-workspace/Final Project/src/employeedata.txt"));
		String line = "";
		System.out.println();
		while (infile.hasNextLine()) {
			line = infile.nextLine();
			System.out.print(line + "\n");

		}
		System.out.println();
		infile.close();
	}

	
	public void modifyEmployees() throws IOException {

		File file = new File("src/employeedata.txt");

		String[] oldEmpLine = new String[31];

		System.out.println();

		BufferedReader reader = new BufferedReader(new FileReader(file));
		int count = 0;
		String line = "";
		while ((line = reader.readLine()) != null) {
			oldEmpLine[count] = line + System.lineSeparator();
			System.out.println(line);
			count++;
		}
		reader.close();

		System.out.println("\nEnter the ID of the employee you would like to modify."
				+ "\nEx: for \"000123, John, Doe, Manager, jdoe\", input \"000123\" to modify employee.  ");
		String replacee = scan.next();
		scan.nextLine();
		System.out.println(
				"\nEnter the updated information in the format (ID, First name, Last name, Position, Username)");
		String replacer = scan.nextLine() + "\n";

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < count; i++) {
			if (oldEmpLine[i].startsWith(replacee)) {
				writer.write(replacer);
			} else {
				writer.write(oldEmpLine[i]);
			}
		}
		writer.flush();
		writer.close();

	}

	
	public void displayManagerMenu() throws IOException {
		String decision = "";
		System.out.println("1. Enter items into inventory"); //Manager - 1. Enter items into storage/inventory.

		System.out.println("2. Modify items in inventory"); //Manager - 2. Modify items in storage/inventory.

		System.out.println("3. Delete items in inventory"); //Manager - 3. Delete items in storage/inventory.

		System.out.println("4. Add employees"); //Manager - 4. Add employees

		System.out.println("5. Remove employees"); //Manager - 5. Remove employees

		System.out.println("6. Display employees"); //Manager - 6. Display all Employees.

		System.out.println("7. Modify employees"); //Manager - 7. Modify Employees

		System.out.println("8. Checkout customer"); //Manager - 8. Checkout Customer

		System.out.println("9. Check inventory"); //Manager - 9. Check Inventory

		System.out.println("10. Check orders"); //Manager - 10. Review current online orders && Manager - 11. Review a customer’s order.

		System.out.println("11. Remove order"); //Manager - 12. Remove a customer’s order.

		System.out.println("12. Create customer account"); //Manager - 13. Create customer account
		decision = scan.next();

		switch (decision) {

		case "1":
			addItems();
			break;

		case "2":
			modifyItems();
			break;

		case "3":
			removeItems();
			break;

		case "4":
			addEmployees();
			break;

		case "5":
			removeEmployees();
			break;

		case "6":
			displayEmployees();
			break;

		case "7":
			modifyEmployees();
			break;

		case "8":
			checkOut();
			break;

		case "9":
			displayInventory();
			break;

		case "10":
			checkOrders();
			break;

		case "11":
			removeOrders();
			break;

		case "12":
			addCustomer();
			break;
		}

	}

}
