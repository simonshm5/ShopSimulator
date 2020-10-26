import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BasicMethods {

	//Login - Manager, Employee
	public void promptUser() throws IOException {
		Scanner scan = new Scanner(System.in);

		String decision = "";
		String username = "";
		String password = "";

		String line = "";

		boolean loginSuccessful = false;
		boolean usernameCorrect = false;
		boolean passwordCorrect = false;

		String[] storedUsername = new String[30];
		String[] storedPassword = new String[30];
		String[] storedPosition = new String[30];
		String[] arr = new String[4];

		File loginData = new File("src/userlogin.txt");
		BufferedReader reader = new BufferedReader(new FileReader(loginData));

		int i = 0;
		int j = 0;
		int k = 0;
		while ((line = reader.readLine()) != null) {
			arr = line.split(", ");
			storedUsername[i] = arr[0];
			storedPassword[j] = arr[1];
			storedPosition[k] = arr[2];
			i++;
			j++;
			k++;

		}

		System.out.println("Hello, are you a manager, employee, or customer?");

		decision = scan.next();

		if (decision.equalsIgnoreCase("manager")) {
			do {
				System.out.println("Please enter username");
				username = scan.next();

				for (int x = 0; x < storedUsername.length; x++) {
					if (storedUsername[x] != null && storedUsername[x].equalsIgnoreCase(username)) {
						usernameCorrect = true;
					}

				}

				System.out.println("Please enter password");
				password = scan.next();

				for (int y = 0; y < storedPassword.length; y++) {
					if (storedPassword[y] != null && storedPassword[y].equalsIgnoreCase(password)) {
						passwordCorrect = true;
					}

				}

				// because no previous exising manager logins, input "A" as username and pass to
				// get in initially
				if (usernameCorrect && passwordCorrect || username.equals("A") && password.equals("A")) {
					loginSuccessful = true;
					System.out.println("\nWelcome, Manager\n");
					Manager m = new Manager();
					m.displayManagerMenu();
				} else {
					System.out.println("\nSorry, we don't recognize those credentials\n");
				}
			} while (!loginSuccessful);

		}

		if (decision.equalsIgnoreCase("employee")) {

			do {
				System.out.println("Please enter username");
				username = scan.next();

				for (int x = 0; x < storedUsername.length; x++) {
					if (storedUsername[x] != null && storedUsername[x].equalsIgnoreCase(username)) {
						usernameCorrect = true;
					}

				}

				System.out.println("Please enter password");
				password = scan.next();

				for (int y = 0; y < storedPassword.length; y++) {
					if (storedPassword[y] != null && storedPassword[y].equalsIgnoreCase(password)) {
						passwordCorrect = true;
					}

				}

				// because no previous exising employee logins, input "E" as username and pass
				// to get in initially
				if ((usernameCorrect && passwordCorrect)
						|| (username.equalsIgnoreCase("E") && password.equalsIgnoreCase("E"))) {
					loginSuccessful = true;
					System.out.println("\nWelcome, Employee\n");
					Employee e = new Employee();
					e.displayEmployeeMenu();
				} else {
					System.out.println("\nSorry, we don't recognize those credentials\n");
				}
			} while (!loginSuccessful);

		}

		if (decision.equalsIgnoreCase("customer")) {
			System.out.println("\nEnjoy Shopping!\n");
			Customer c = new Customer();
			c.displayCustomerMenu();
		}
	}

}
