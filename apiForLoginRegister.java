import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class apiForLoginRegister {
	DataInputStream inputFromServer;
	DataOutputStream outputToServer;
	Connection con;

	// Constructor
	public apiForLoginRegister(DataInputStream inputFromServer, DataOutputStream outputToServer) {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/covid_database", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.inputFromServer = inputFromServer;
		this.outputToServer = outputToServer;

	}

	// Password Hashing
	public String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes(StandardCharsets.UTF_8));
		byte[] digest = md.digest();

		String hex = String.format("%064x", new BigInteger(1, digest));
		return hex;
	}

	// Old Method That I have no strength in deleting :(

//	public void sendCreds(String name, String password, int i) {
//
//		try {
//			outputToServer.writeInt(i);
//			outputToServer.writeUTF(name);
//			outputToServer.writeUTF(password);
//			String passwordForServer = hashPassword(password);
//			System.out.println(i + " sent to server");
//			System.out.println(name + " sent to server");
//			System.out.println(passwordForServer + " sent to server");
//			String received = inputFromServer.readUTF();
//			System.out.println(received + " from the server");
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//	}

	// Query for Registering Users using input values from GUI
	public int RegisterQuery(String username, String password, String fullname, String email, String photopath,
			String Vaccination, String vaccinationUpload) {
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT username FROM profiles WHERE username='" + username + "'");

			if (!rs.isBeforeFirst()) {
				System.out.println("Registering User...");
				Statement stmt2 = con.createStatement();
				stmt.execute(
						"INSERT INTO profiles (username,password,full_name, email, photo, vacc_status, vacc_cert) VALUES ('"
								+ username + "','" + password + "', '" + fullname + "','" + email + "','" + photopath
								+ "','" + Vaccination + "','" + vaccinationUpload + "')");
				return 1;
			} else {
				System.out.println("Username is Already taken");
				return 0;
			}
		} catch (Exception x) {
			System.out.println(x);

		}
		return -1;
	}

	// Query for Logging Users Into the Main Interface using their user name and
	// password
	public int LoginQuery(String name, String password) {
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT username,password FROM profiles WHERE username='" + name
					+ "' AND password='" + password + "'");

			if (!rs.isBeforeFirst()) {
				System.out.println("User Doesn't Exist");
				return 0;
			} else {

				System.out.println("Login Successful");
				return 1;
			}

		} catch (Exception x) {
			System.out.println(x);

		}
		return -1;
	}

	// Query to update the PCR result of a user based on their selective choice
	public void updatePcrResult(String username, String result) {

		try {
			Statement stmt = con.createStatement();
			stmt.execute("UPDATE profiles SET pcr_test='" + result + "' WHERE username='" + username + "'");

		} catch (Exception x) {
			System.out.println(x);
			System.out.println("Error for pcr result");

		}

	}

	// Query that Updates status condition of a user based on their PCR result
	public void updateStatusCondition(String name) {
		try {
			Statement stmt = con.createStatement();
			stmt.execute(
					"UPDATE profiles SET status_condition = CASE WHEN pcr_test = 'positive' THEN 'contagious' ELSE 'safe' END WHERE username='"
							+ name + "'");
		} catch (Exception x) {
			System.out.println(x);
			System.out.println("Error for status condidion");

		}

	}

	// Query to grab the number of contagious users
	public String getStats() {
		int numOfCases = 0;

		try {
			Statement stmt = con.createStatement();
			ResultSet cases = stmt
					.executeQuery("SELECT COUNT(*) as CASES FROM profiles WHERE status_condition = 'contagious'");
			while (cases.next()) {
				numOfCases = cases.getInt("CASES");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Number of people that are contagious: " + numOfCases;

	}

	// Query to add trusted users based on the current user name and the input value
	// from GUI
	public void addTrustedUsers(String username, String trustedusername) {

		try {
			Statement stmt = con.createStatement();
			stmt.execute("INSERT into trusting(user_name,trusted_username) VALUES('" + username + "','"
					+ trustedusername + "')");

		} catch (Exception x) {
			System.out.println(x);

		}

	}

	// Query to get trusting users [NOT USED]
	public void getTrustingUsers(String name) {

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"select username from profiles where username in (select user_id from trusting where trusted_user_id = (select id from users where username LIKE '"
							+ name + "'))");

			while (rs.next()) {
				System.out.println("Trusting Usernames: " + rs.getString("username"));

			}

		} catch (Exception x) {
			System.out.println(x);

		}
	}

	// Query to view the status condition of a user who trusted you, just like the
	// name of the method implies ;)
	public void seeTheStatusConditionOfWhoTrustedYou(String username, String textname) {
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT status_condition FROM profiles WHERE username IN (SELECT username FROM trusting WHERE username='"
							+ textname + "' AND trusted_username='" + username + "'" + ")");

			while (rs.next()) {
				System.out.println(textname + " is: " + rs.getString("status_condition"));

			}
		} catch (Exception x) {
			System.out.println(x);

		}
	}

	// Query to set the quarantine days of a user starting from the current date
	public void setQuarantineDays(String username, LocalDate date) {

		try {
			Statement stmt = con.createStatement();

			stmt.execute("UPDATE profiles SET quarantine_days='" + date.now() + "' WHERE username='" + username + "'");

		} catch (Exception x) {
			System.out.println(x);

		}

	}

	// Query to fetch the quarantine days of a user
	public String getQuarantineDays(String username) {
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT quarantine_days FROM profiles WHERE username='" + username + "'");

			while (rs.next()) {
				return rs.getString("quarantine_days");
			}

		} catch (Exception x) {
			System.out.println(x);

		}

		return "error in getting date";
	}

	// Query to fetch the status condition of a user
	public String getStatusCondition(String username) {
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT status_condition FROM profiles WHERE username='" + username + "'");

			while (rs.next()) {
				return rs.getString("status_condition");
			}

		} catch (Exception x) {
			System.out.println(x);

		}

		return "error in getting date";
	}

	// Query to set the location of a user based on the selective choice from the
	// GUI
	public void addLocation(String username, String location) {

		try {
			Statement stmt = con.createStatement();
			stmt.execute("UPDATE profiles SET location='" + location + "' WHERE username='" + username + "'");

		} catch (Exception x) {
			System.out.println(x);

		}
	}

	// Query to return a list of users who are present in a specified location in a
	// ArrayList form
	public ArrayList<String> getUsersBasedOnLocation(String location) {
		ArrayList<String> usernames = new ArrayList<>();
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT username FROM profiles WHERE location='" + location + "'");// AND NOT pcr_test='positive'

			while (rs.next()) {
				usernames.add(rs.getString("username"));
			}
			return usernames;

		} catch (Exception x) {
			System.out.println(x);

		}
		return null;
	}

	// Query to set the status condition of users in a specified location to 'at
	// risk'
	public void setStatusConditionToRisk(String location) {
		try {
			Statement stmt = con.createStatement();
			stmt.execute("UPDATE profiles SET status_condition='at risk' WHERE NOT(pcr_test='positive') AND location='"
					+ location + "'");
		} catch (Exception x) {
			System.out.println(x);
			System.out.println("Error for status condidion");

		}

	}

	// NOT USED [I THINK] [ACTUALLY MOST LIKELY]
	public String getUserIdByHealthStatus(String status) {
		String x = "";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM profiles where status_condition LIKE '" + status + "'");
			while (rs.next()) {

				x += rs.getInt("id") + " ";
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return x;

	}

}
