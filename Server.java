import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Server {

	public static void main(String[] args) throws IOException {
		// Server is listening on port 5056
		ServerSocket serverSocket = new ServerSocket(5055);

		// Server keeps on receiving new Clients
		while (true) {
			Socket clientSocket = null;
			try {
				// ServerSocket waits for a Client to connect
				clientSocket = serverSocket.accept();

//
//				// Receiving input and sending output to Client
				DataInputStream inputFromClient = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(clientSocket.getOutputStream());

				// Create a new Thread object for the Client

				Thread thread = new ClientHandler(clientSocket, inputFromClient, outputToClient);
				thread.start();

			} catch (Exception e) {
				clientSocket.close();
				e.printStackTrace();
			}
		}
	}
}

//ClientHandler class
class ClientHandler extends Thread {
	final Socket clientSocket;
	final DataInputStream inputFromClient;
	final DataOutputStream outputToClient;

	InetAddress IP;
	int port;
	String userName = "";
	Random rand = new Random();
	int randomifyLocations = rand.nextInt(7);
	LocalDate myDateObj = LocalDate.now();

	// Constructor
	public ClientHandler(Socket clientSocket, DataInputStream inputFromClient, DataOutputStream outputToClient) {
		this.clientSocket = clientSocket;
		this.inputFromClient = inputFromClient;
		this.outputToClient = outputToClient;

		port = clientSocket.getPort();
		IP = clientSocket.getInetAddress();

	}

	@Override
	public void run() {
		String taskType = "";
		System.out.println("Port number:" + port + " IP address: " + IP.toString());
		while (true) {
			try {
				System.out.println("A new client is connected : " + clientSocket);

				apiForLoginRegister loginRegister = new apiForLoginRegister(inputFromClient, outputToClient);

				String password;
				String hashedpass;
				int type = inputFromClient.readInt();

				switch (type) {
				case (0):
					// Take input
					// Login query (input)
					userName = inputFromClient.readUTF();
					password = inputFromClient.readUTF();

					hashedpass = loginRegister.hashPassword(password);
					System.out.println(userName + " recieved from client");
					System.out.println(hashedpass + " recieved from client");

					// Attempt to Login User With Hashed Password
					int loginauth = loginRegister.LoginQuery(userName, hashedpass);
					outputToClient.writeInt(loginauth);

					// If Query Returns value 1, authenticate the login attempt
					if (loginauth == 1) {

						// send the stored status condition of a user
						outputToClient.writeUTF(loginRegister.getStatusCondition(userName));

						// Checking the amount of quarantine days left,if any
						try {
							String databaseDate = loginRegister.getQuarantineDays(userName);
							if (databaseDate != null) {
								LocalDate StringDateFromDatabase = LocalDate.parse(databaseDate);

								int DaysLeftToClient = myDateObj.getDayOfYear() - StringDateFromDatabase.getDayOfYear();
								outputToClient.writeInt(DaysLeftToClient);
								System.out.println("Number of Days Left In Quarantine: "
										+ (myDateObj.getDayOfYear() - StringDateFromDatabase.getDayOfYear()));
							} else {
								// send value 0 to client indicating that they have not received a quarantine
								// date before
								outputToClient.writeInt(0);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					break;

				case (1):
					// Register Query based on filled fields from GUI
					String fullname = inputFromClient.readUTF();
					userName = inputFromClient.readUTF();
					String email = inputFromClient.readUTF();
					String photopath = inputFromClient.readUTF();
					password = inputFromClient.readUTF();
					String vaccinationStatus = inputFromClient.readUTF();
					String vaccinationPath = inputFromClient.readUTF();

					hashedpass = loginRegister.hashPassword(password);

					// Attempt to Register User
					loginRegister.RegisterQuery(userName, hashedpass, fullname, email, photopath, vaccinationStatus,
							vaccinationPath);

					break;

				case (2):
					// Get Statistics from Database and Display on GUI through client request
					String stats = loginRegister.getStats();
					outputToClient.writeUTF(stats);
					break;
				case (3):
					// Display Status Condition of a user who have the logged in user on their
					// trusted list
					String textname = inputFromClient.readUTF();
					loginRegister.seeTheStatusConditionOfWhoTrustedYou(userName, textname);
					break;

				case (4):
					// Add a trusted user to the list
					String trustingName = inputFromClient.readUTF();
					loginRegister.addTrustedUsers(userName, trustingName);
					break;

				case (5):
					// This section determines the status condition of individuals. If a user is
					// 'contagious' and is present in an location, it will notify all users in that
					// location who are 'safe' and set their status condition to 'at risk'

					// read location from client
					String userInputLocation = inputFromClient.readUTF();

					// add location to logged in user to database
					loginRegister.addLocation(userName, userInputLocation);

					System.out.println("User Added the following location to the db: " + userInputLocation);

					// get the users who are present in the same location
					ArrayList<String> usersInLocation = loginRegister.getUsersBasedOnLocation(userInputLocation);
					System.out.println("Users in the same location as " + userName + " Are: " + usersInLocation);

					// if the user is contagious, set the status condition of all other present
					// users to at risk. Does not change the status condition of those who are
					// already contagious
					if (loginRegister.getStatusCondition(userName).equals("contagious")) {
						loginRegister.setStatusConditionToRisk(userInputLocation);
						System.out.println("Updating Status Conditions of users in proximity....");
						System.out.println("Showing status condition of users in proximity: ");
						// looping over the users present and printing their status condition
						for (int i = 0; i < usersInLocation.size(); i++) {
							System.out.println("User Name " + usersInLocation.get(i) + " Has status condition of: "
									+ loginRegister.getStatusCondition(usersInLocation.get(i)));

						}
					} else {
						// if the user is not contagious, it shows the status condition of everyone in
						// the proximity
						System.out.println("Showing status condition of users in proximity: ");
						for (int i = 0; i < usersInLocation.size(); i++) {
							System.out.println("User Name " + usersInLocation.get(i) + " Has status condition of: "
									+ loginRegister.getStatusCondition(usersInLocation.get(i)));
							//checking if users in the location are at risk or contagious to notify safe users
							if (loginRegister.getStatusCondition(usersInLocation.get(i)).equals("at risk")
									|| loginRegister.getStatusCondition(usersInLocation.get(i)).equals("contagious")) {
								loginRegister.setStatusConditionToRisk(userInputLocation);

							}
						}
						// maybe make a if statement so that if it finds a contagious user, changes all
						// users in location to 'at risk'
						// [NOT SURE IF THIS IS ALREADY WORKING WILL BE TESTING LATER]
					}

					break;

				case (6):
					// Updates the PCR result of a user based on their selective choice :D
					String result = inputFromClient.readUTF();
					System.out.println(result);
					System.out.println("username: " + userName);
					loginRegister.updatePcrResult(userName, result);
					loginRegister.updateStatusCondition(userName);
					if (result.equalsIgnoreCase("positive")) {
						loginRegister.setQuarantineDays(userName, myDateObj);
					}
					break;

				case (7):
					// Displays Instructions to the User if they are 'at risk' or 'contagious'
					String instructions = "During quarantine:\r\n"
							+ "Stay home. Do not go to work, school, or public areas.\r\n"
							+ "Do not allow visitors and limit the number of people in your home. \r\n"
							+ "Separate yourself from others in your home\r\n" + "PCR Test Instructions:\r\n"
							+ "Wash your hands with soap or use a hand sanitizer.\r\n"
							+ "Lay out all the items in the test kit on the clean surface.\r\n"
							+ "Blow your nose and wash your hands again.\r\n"
							+ "Open your mouth wide and rub the swab over your tonsils (or where they would have been). Avoid the end of the swab touching your teeth, tongue and gums.\r\n"
							+ "Put the same swab inside your nose (about 2.5cm up or until you feel some resistance).\r\n"
							+ "Put the swab facing down into the tube and screw the lid tight.\r\n"
							+ "Put the tube in the bag provided.";
					outputToClient.writeUTF(instructions);
					break;
				}
				if (taskType.equals("Exit")) {
					System.out.println(
							"-----------------------------------------------------------------------------------");
					System.out.println("Client " + this.clientSocket + " sends exit...");
					System.out.println("Closing this connection.");
					this.clientSocket.close();
					System.out.println("Connection closed");
					break;
				}

			} catch (IOException |

					NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		try

		{
			// Closing resources
			this.inputFromClient.close();
			this.outputToClient.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}