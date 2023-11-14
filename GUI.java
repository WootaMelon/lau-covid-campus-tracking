import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;

public class GUI {
	private JTextField LoginUsername;
	private JPasswordField LoginPassword;
	private JTextField RegisterUsername;
	private JTextField RegisterEmail;
	private JTextField RegisterName;
	private JPasswordField RegisterPassword;
	String Photopath;
	String Vaccinationpath;
	int daysLeftForQuarantine;
	String status_condition = "";

	public GUI(DataInputStream inputFromServer, DataOutputStream outputToServer) {

		JFrame LoginFrame = new JFrame();
		JFrame RegisterFrame = new JFrame();
		JFrame HomeFrame = new JFrame();

		// *****************LOGIN FRAME*********************//

		LoginFrame.setSize(new Dimension(1920 / 2, 1080 / 2));
		LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoginFrame.setTitle("GUI");
		LoginFrame.setVisible(true);
		LoginFrame.getContentPane().setLayout(null);
		JPanel Loginpanel = new JPanel();
		Loginpanel.setBackground(new Color(230, 230, 230));
		Loginpanel.setBounds(63, 39, 833, 422);
		LoginFrame.getContentPane().add(Loginpanel);
		Loginpanel.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		Loginpanel.setLayout(null);
		Loginpanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblUsername.setBounds(554, 61, 107, 42);
		Loginpanel.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblPassword.setBounds(554, 151, 107, 35);
		Loginpanel.add(lblPassword);

		LoginUsername = new JTextField();
		LoginUsername.setForeground(Color.BLACK);
		LoginUsername.setBounds(534, 116, 144, 22);
		Loginpanel.add(LoginUsername);
		LoginUsername.setColumns(10);

		LoginPassword = new JPasswordField();
		LoginPassword.setBounds(534, 199, 144, 22);
		Loginpanel.add(LoginPassword);

		JLabel lblIncorrectUsernameOr = new JLabel("Incorrect Username or Password");
		lblIncorrectUsernameOr.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblIncorrectUsernameOr.setBounds(483, 26, 254, 22);
		Loginpanel.add(lblIncorrectUsernameOr);
		lblIncorrectUsernameOr.setVisible(false);
		lblIncorrectUsernameOr.setForeground(new Color(235, 64, 52));

		JButton btnRegisterHere = new JButton("Register Here");
		btnRegisterHere.setBounds(534, 325, 144, 25);
		Loginpanel.add(btnRegisterHere);
		btnRegisterHere.setBorder(BorderFactory.createLineBorder(Color.black));
		btnRegisterHere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				LoginFrame.dispose();
				RegisterFrame.setSize(new Dimension(1920 / 2, 1080 / 2));
				RegisterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				RegisterFrame.setTitle("GUI");
				RegisterFrame.setVisible(true);

			}
		});

		JLabel lblDontHaveAn = new JLabel("Don't have an account?");
		lblDontHaveAn.setHorizontalAlignment(SwingConstants.CENTER);
		lblDontHaveAn.setBackground(Color.WHITE);
		lblDontHaveAn.setBounds(534, 290, 144, 22);
		Loginpanel.add(lblDontHaveAn);
		lblDontHaveAn.setBorder(BorderFactory.createLineBorder(Color.black));
		lblDontHaveAn.setHorizontalTextPosition(0);

		JLabel lblHello = new JLabel("Welcome to our COVID-19");
		lblHello.setBounds(47, 72, 334, 31);
		Loginpanel.add(lblHello);
		lblHello.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lblCovidComputerNetworks = new JLabel("Computer Networks Project");
		lblCovidComputerNetworks.setBounds(50, 117, 324, 31);
		Loginpanel.add(lblCovidComputerNetworks);
		lblCovidComputerNetworks.setFont(new Font("Tahoma", Font.BOLD, 23));

		Panel circle1 = new Panel();
		circle1.setBackground(Color.GRAY);
		circle1.setBounds(62, 202, 43, 42);
		Loginpanel.add(circle1);

		Panel circle2 = new Panel();
		circle2.setBackground(Color.GRAY);
		circle2.setBounds(111, 181, 28, 25);
		Loginpanel.add(circle2);

		Panel circle3 = new Panel();
		circle3.setBackground(Color.GRAY);
		circle3.setBounds(397, 26, 28, 25);
		Loginpanel.add(circle3);

		// *****************REGISTER FRAME*********************//

		RegisterFrame.getContentPane().setLayout(null);

		JPanel Registerpanel = new JPanel();
		Registerpanel.setBounds(229, 47, 510, 366);
		Registerpanel.setBackground(new Color(230, 230, 230));
		RegisterFrame.getContentPane().add(Registerpanel);
		Registerpanel.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		Registerpanel.setLayout(null);
		Registerpanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel RegisterlblUsername = new JLabel("Username:");
		RegisterlblUsername.setFont(new Font("Tahoma", Font.PLAIN, 21));
		RegisterlblUsername.setBounds(12, 114, 107, 42);
		Registerpanel.add(RegisterlblUsername);

		RegisterUsername = new JTextField();
		RegisterUsername.setBounds(122, 127, 116, 22);
		Registerpanel.add(RegisterUsername);
		RegisterUsername.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblEmail.setBounds(299, 114, 61, 42);
		Registerpanel.add(lblEmail);

		RegisterEmail = new JTextField();
		RegisterEmail.setColumns(10);
		RegisterEmail.setBounds(372, 127, 116, 22);
		Registerpanel.add(RegisterEmail);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblName.setBounds(12, 59, 107, 42);
		Registerpanel.add(lblName);

		RegisterName = new JTextField();
		RegisterName.setColumns(10);
		RegisterName.setBounds(122, 72, 116, 22);
		Registerpanel.add(RegisterName);

		JLabel lblPhoto = new JLabel("Photo:");
		lblPhoto.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblPhoto.setBounds(299, 59, 66, 42);
		Registerpanel.add(lblPhoto);

		JLabel lblRegister = new JLabel("Register");
		lblRegister.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblRegister.setBounds(193, 0, 151, 64);
		Registerpanel.add(lblRegister);

		RegisterPassword = new JPasswordField();
		RegisterPassword.setBounds(244, 189, 116, 22);
		Registerpanel.add(RegisterPassword);

		JLabel RegisterlblPassword = new JLabel("Password:");
		RegisterlblPassword.setFont(new Font("Tahoma", Font.PLAIN, 21));
		RegisterlblPassword.setBounds(134, 176, 107, 42);
		Registerpanel.add(RegisterlblPassword);

		JRadioButton rdbtnVaccinated = new JRadioButton("Vaccinated");
		rdbtnVaccinated.setBounds(141, 227, 127, 25);
		rdbtnVaccinated.setActionCommand("Vaccinated");
		Registerpanel.add(rdbtnVaccinated);

		JRadioButton rdbtnNotVaccinated = new JRadioButton("Not Vaccinated");
		rdbtnNotVaccinated.setBounds(141, 257, 127, 25);
		rdbtnNotVaccinated.setActionCommand("Not Vaccinated");
		Registerpanel.add(rdbtnNotVaccinated);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNotVaccinated);
		group.add(rdbtnVaccinated);

		JLabel lblVaccination = new JLabel("Vaccination:");
		lblVaccination.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblVaccination.setBounds(12, 220, 121, 42);
		Registerpanel.add(lblVaccination);

		JLabel lblVaccinationCertificate = new JLabel("Vaccination Certificate");
		lblVaccinationCertificate.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblVaccinationCertificate.setBounds(284, 215, 211, 51);
		Registerpanel.add(lblVaccinationCertificate);

		JLabel lblPasswordIncorrect = new JLabel("");
		lblPasswordIncorrect.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPasswordIncorrect.setBounds(100, 300, 300, 25);
		Registerpanel.add(lblPasswordIncorrect);
		lblPasswordIncorrect.setVisible(false);
		lblPasswordIncorrect.setForeground(new Color(235, 64, 52));
		lblPasswordIncorrect.setHorizontalAlignment(0);

		JButton btnUpload = new JButton("Upload");
		btnUpload.setBounds(372, 71, 97, 25);
		Registerpanel.add(btnUpload);
		btnUpload.setBorder(BorderFactory.createLineBorder(Color.black));
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "png");
				file.addChoosableFileFilter(filter);
				int result = file.showSaveDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					Photopath = selectedFile.getAbsolutePath();

				}
			}
		});

		JButton btnUpload_1 = new JButton("Upload");
		btnUpload_1.setBounds(344, 257, 97, 25);
		Registerpanel.add(btnUpload_1);
		btnUpload_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "png", "pdf");
				file.addChoosableFileFilter(filter);
				int result = file.showSaveDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					Vaccinationpath = selectedFile.getAbsolutePath();

				}
			}
		});

		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(193, 328, 97, 25);
		Registerpanel.add(btnRegister);
		btnRegister.setBorder(BorderFactory.createLineBorder(Color.black));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (RegisterUsername.getText().equals("") || RegisterEmail.getText().equals("")
						|| RegisterName.getText().equals("") || Photopath.equals("")
						|| String.valueOf(RegisterPassword.getPassword()).equals("") || group.getSelection().equals("")
						|| Vaccinationpath.equals("")) {
					lblPasswordIncorrect.setText("Some fields are missing.");
					lblPasswordIncorrect.setVisible(true);

					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {

							lblPasswordIncorrect.setVisible(false);
						}
					}, 2 * 1000);
				} else {

					if (String.valueOf(RegisterPassword.getPassword()).length() < 8) {
						lblPasswordIncorrect.setText("Password must be at least 8 characters.");
						lblPasswordIncorrect.setVisible(true);

						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {

								lblPasswordIncorrect.setVisible(false);
							}
						}, 2 * 1000);

						return;
					}

					try {
						outputToServer.writeInt(1);

						outputToServer.writeUTF(RegisterName.getText());
						outputToServer.writeUTF(RegisterUsername.getText());
						outputToServer.writeUTF(RegisterEmail.getText());
						outputToServer.writeUTF(Photopath);
						outputToServer.writeUTF(String.valueOf(RegisterPassword.getPassword()));
						outputToServer.writeUTF(group.getSelection().getActionCommand());
						outputToServer.writeUTF(Vaccinationpath);

						System.out.println(RegisterUsername.getText() + " " + RegisterEmail.getText() + " "
								+ RegisterName.getText() + " " + Photopath + " "
								+ String.valueOf(RegisterPassword.getPassword()) + " "
								+ group.getSelection().getActionCommand() + " " + Vaccinationpath);

					} catch (IOException e) {
						e.printStackTrace();
					}

					RegisterFrame.dispose();
					HomeFrame.setSize(new Dimension(1920 / 2, 1080 / 2));
					HomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					HomeFrame.setTitle("GUI");
					HomeFrame.setVisible(true);

				}
			}
		});

		// *****************HOME FRAME*********************//

		HomeFrame.getContentPane().setLayout(null);

		JPanel Homepanel = new JPanel();
		Homepanel.setBounds(25, 33, 381, 440);
		Homepanel.setBackground(new Color(230, 230, 230));
		HomeFrame.getContentPane().add(Homepanel);
		Homepanel.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		Homepanel.setLayout(null);
		Homepanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel lblProfileDetails = new JLabel("Profile Details");
		lblProfileDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfileDetails.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblProfileDetails.setBounds(62, 13, 240, 68);
		Homepanel.add(lblProfileDetails);

		JLabel lblstatusCondition = new JLabel("Safe");
		lblstatusCondition.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblstatusCondition.setBounds(41, 94, 127, 51);
		Homepanel.add(lblstatusCondition);

		JLabel pcrLabel = new JLabel("Not found.");
		pcrLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pcrLabel.setBounds(121, 346, 102, 51);
		Homepanel.add(pcrLabel);

		String[] resultarr = { "", "positive", "negative" };
		JComboBox comboBox = new JComboBox(resultarr);
		comboBox.setBounds(22, 394, 117, 23);
		Homepanel.add(comboBox);
		comboBox.setSelectedIndex(0);

		JPanel Homepanel_1 = new JPanel();
		Homepanel_1.setLayout(null);
		Homepanel_1.setBorder(BorderFactory.createLineBorder(Color.black));
		Homepanel_1.setBackground(new Color(230, 230, 230));
		Homepanel_1.setAlignmentX(0.5f);
		Homepanel_1.setBounds(450, 33, 466, 440);
		HomeFrame.getContentPane().add(Homepanel_1);

		JLabel lblQuar = new JLabel("Days in Quarintine:");
		lblQuar.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblQuar.setBounds(55, 396, 186, 31);
		lblQuar.setBorder(BorderFactory.createLineBorder(Color.black));
		Homepanel_1.add(lblQuar);

		lblQuar.setVisible(false);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(253, 396, 146, 31);
		Homepanel_1.add(progressBar);
		progressBar.setMaximum(14);

		progressBar.setForeground((new Color(235, 64, 52)));
		if (status_condition.equals("contagious")) {
			progressBar.setVisible(true);
		} else if (status_condition.equals("safe")) {
			progressBar.setVisible(false);

		}

		JLabel lblPcrResult = new JLabel("PCR result:");
		lblPcrResult.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPcrResult.setBounds(12, 346, 152, 51);
		Homepanel.add(lblPcrResult);

		JButton btnGetInstructions = new JButton("Get Instructions");
		btnGetInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String instructions = "";
				try {
					outputToServer.writeInt(7);

					instructions = inputFromServer.readUTF();
					System.out.print(instructions);
				} catch (IOException e) {
					e.printStackTrace();
				}

				JOptionPane.showMessageDialog(HomeFrame, instructions);

			}
		});
		btnGetInstructions.setBounds(146, 110, 136, 25);
		Homepanel.add(btnGetInstructions);
		btnGetInstructions.setVisible(false);

		JPanel Homecircle3_1 = new JPanel();
		Homecircle3_1.setBackground(new Color(51, 204, 51));
		Homecircle3_1.setBounds(10, 108, 25, 25);
		Homepanel.add(Homecircle3_1);

		JButton pcrbtn = new JButton("Add PCR");
		pcrbtn.setBounds(165, 391, 57, 27);
		Homepanel.add(pcrbtn);
		pcrbtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pcrbtn.setBorder(BorderFactory.createLineBorder(Color.black));
		pcrbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int pcrconfirm = JOptionPane.showConfirmDialog(HomeFrame, "You changed your PCR result. Are you sure?");

				System.out.println(pcrconfirm);

				if (pcrconfirm == 0) {
					try {
						outputToServer.writeInt(6);
						outputToServer.writeUTF(String.valueOf(comboBox.getSelectedItem()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pcrLabel.setText(String.valueOf(comboBox.getSelectedItem()));
					if (String.valueOf(comboBox.getSelectedItem()).equalsIgnoreCase("positive")) {
						lblQuar.setVisible(true);
						progressBar.setVisible(true);
						btnGetInstructions.setVisible(true);
						lblstatusCondition.setText("Contagious");
						Homecircle3_1.setBackground(new Color(235, 64, 52));

						// outputToClient
					} else if (String.valueOf(comboBox.getSelectedItem()).equalsIgnoreCase("negative")) {
						lblQuar.setVisible(false);
						progressBar.setVisible(false);
						btnGetInstructions.setVisible(false);
						lblstatusCondition.setText("Safe");
						Homecircle3_1.setBackground(new Color(51, 204, 51));
					}
				}
			}
		});

		String[] campuses = { "", "Irwin Hall", "Sage Hall", "AKSOB", "Nicol Hall", "Safadi Fine Arts", "Shanon",
				"Omre grey" };
		JComboBox comboBox_1 = new JComboBox(campuses);
		comboBox_1.setSelectedIndex(0);
		comboBox_1.setBounds(12, 208, 117, 23);
		Homepanel.add(comboBox_1);
		comboBox_1.setSelectedIndex(0);

		JButton btnUpdateLocation = new JButton("Update Location");
		btnUpdateLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox_1.getSelectedIndex() != 0) {
					int locconfirm = JOptionPane.showConfirmDialog(HomeFrame,
							"You changed your current location. Are you sure? ");

					if (locconfirm == 0) {
						try {
							outputToServer.writeInt(5);
							outputToServer.writeUTF(String.valueOf(comboBox_1.getSelectedItem()));
						
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						JOptionPane.showMessageDialog(HomeFrame, "Location Updated.");
					}
				}

			}
		});
		btnUpdateLocation.setBounds(146, 207, 136, 25);
		Homepanel.add(btnUpdateLocation);

		JButton checkStatsbtn = new JButton("Check Statistics");
		checkStatsbtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkStatsbtn.setBounds(138, 13, 202, 72);
		Homepanel_1.add(checkStatsbtn);
		checkStatsbtn.setBorder(BorderFactory.createLineBorder(Color.black));
		checkStatsbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					outputToServer.writeInt(2);
					String stats = inputFromServer.readUTF();

					JOptionPane.showMessageDialog(HomeFrame, stats);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// send input from text field and set a label
			}
		});

		JButton viewProfilebtn = new JButton("View User Status");
		viewProfilebtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		viewProfilebtn.setBounds(138, 98, 202, 72);
		Homepanel_1.add(viewProfilebtn);
		viewProfilebtn.setBorder(BorderFactory.createLineBorder(Color.black));
		viewProfilebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String user = JOptionPane.showInputDialog(HomeFrame,
						"Input name of user. User must have you as Trusted User.");
				if (user != null && !user.equals("")) {
					try {
						outputToServer.writeInt(3);
						outputToServer.writeUTF(user);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

//					boolean trust = true;
//					String userstat = "Infected";

//					JOptionPane.showMessageDialog(HomeFrame, user + " is " + userstat);
				}

				// send input from text field and set a label
			}
		});

		JButton trustUserbtn = new JButton("Trust User");
		trustUserbtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		trustUserbtn.setBounds(138, 183, 202, 72);
		Homepanel_1.add(trustUserbtn);
		trustUserbtn.setBorder(BorderFactory.createLineBorder(Color.black));
		trustUserbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String user = JOptionPane.showInputDialog(HomeFrame,
						"Input name of user to add them to Trusted Users.");
				if (user != null && !user.equals("")) {

					try {
						outputToServer.writeInt(4);
						outputToServer.writeUTF(user);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(HomeFrame, user + " was added to Trusted Users.");
				}

			}
		});

		JPanel Homecircle1 = new JPanel();
		Homecircle1.setBackground(new Color(210, 210, 210));
		Homecircle1.setBounds(33, 183, 55, 50);
		Homepanel_1.add(Homecircle1);

		JPanel Homecircle2 = new JPanel();
		Homecircle2.setBackground(new Color(210, 210, 210));
		Homecircle2.setBounds(79, 132, 33, 33);
		Homepanel_1.add(Homecircle2);

		JPanel Homecircle3 = new JPanel();
		Homecircle3.setBackground(new Color(210, 210, 210));
		Homecircle3.setBounds(344, 333, 55, 50);
		Homepanel_1.add(Homecircle3);

		JButton btnLogIn = new JButton("Log in");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (LoginUsername.getText().equals("") || String.valueOf(LoginPassword.getPassword()).equals("")) {

					lblIncorrectUsernameOr.setVisible(true);
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							lblIncorrectUsernameOr.setVisible(false);
						}
					}, 2 * 1000);

				} else {
					int AuthConfirm = -1;

					System.out.println("aaa" + LoginUsername.getText());
					try {
						outputToServer.writeInt(0);

						outputToServer.writeUTF(LoginUsername.getText());

						outputToServer.writeUTF(String.valueOf(LoginPassword.getPassword()));

						AuthConfirm = inputFromServer.readInt();

					} catch (IOException e) {
						e.printStackTrace();
					}

					System.out.println(LoginUsername.getText() + " " + String.valueOf(LoginPassword.getPassword()));

					if (AuthConfirm == 1) {
						try {
							status_condition = inputFromServer.readUTF();
							daysLeftForQuarantine = inputFromServer.readInt();

							System.out.print("DAYS FROM GUI: " + daysLeftForQuarantine);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						LoginFrame.dispose();
						HomeFrame.setSize(new Dimension(1920 / 2, 1080 / 2));
						HomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						HomeFrame.setTitle("GUI");
						HomeFrame.setVisible(true);
						if (status_condition.equals("contagious")) {
							lblstatusCondition.setText("Contagious");
							btnGetInstructions.setVisible(true);
							progressBar.setVisible(true);
							lblQuar.setVisible(true);
							progressBar.setValue(daysLeftForQuarantine);
							Homecircle3_1.setBackground(new Color(235, 64, 52));
						} else if (status_condition.equals("safe")) {
							lblstatusCondition.setText("Safe");
							btnGetInstructions.setVisible(false);
							progressBar.setVisible(false);
							Homecircle3_1.setBackground(new Color(51, 204, 51));
						} else if (status_condition.equals("at risk")) {
							lblstatusCondition.setText("At Risk!");
							btnGetInstructions.setVisible(true);
							progressBar.setVisible(true);
							lblQuar.setVisible(true);
							Homecircle3_1.setBackground(new Color(235, 64, 52));
						}
					}

					else {

						lblIncorrectUsernameOr.setVisible(true);
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								lblIncorrectUsernameOr.setVisible(false);
							}
						}, 2 * 1000);

					}

				}

			}
		});

		btnLogIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogIn.setBounds(554, 249, 97, 25);
		Loginpanel.add(btnLogIn);
		btnLogIn.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public static void main(String[] args) {
		try {

			// Getting local IP Address (127.0.0.1)
			InetAddress ip = InetAddress.getByName("localhost");

			// Establish the connection with Server on port 5056
			Socket socket = new Socket(ip, 5055);
			// This will trigger the accept() function of the Server

			// Receiving input and sending output to Server
			DataInputStream inputFromServer = new DataInputStream(socket.getInputStream());
			DataOutputStream outputToServer = new DataOutputStream(socket.getOutputStream());

			new GUI(inputFromServer, outputToServer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
