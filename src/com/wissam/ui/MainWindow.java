package com.wissam.ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.wissam.controller.MainController;
import com.wissam.model.Filters;
import com.wissam.model.ResourceType;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	private MainController controller;
	private JTextField txtReqUri;
	private JTextField txtCrUri;
	private JTextField txtResUri;
	private JTextField txtCreatedFrom;
	private JTextField txtCreatedTo;
	private JTextField txtLastModifiedFrom;
	private JTextField txtLastModifiedTo;
	private JTextField txtKeywords;
	private final ArrayList<JCheckBox> changeRequestStateList = new ArrayList<>();
	private JCheckBox chckbxClosed;
	private JCheckBox chckbxInProgress;
	private JCheckBox chckbxFixed;
	private JCheckBox chckbxApproved;
	private JCheckBox chckbxReviewed;
	private JCheckBox chckbxVerified;
	private JTabbedPane tabbedPane;
	private JComboBox comboBoxResType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setResizable(false);
		setTitle("OSLC Social Network Builder");
		controller = new MainController();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 461, 701);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(6, 6, 62, 16);
		contentPane.add(lblNewLabel);

		txtUsername = new JTextField();
		txtUsername.setBounds(80, 4, 355, 21);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(6, 34, 61, 16);
		contentPane.add(lblNewLabel_1);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(80, 29, 355, 21);
		contentPane.add(txtPassword);

		JButton btnCreateSn = new JButton("Create SN");
		btnCreateSn.setBounds(343, 627, 92, 30);
		btnCreateSn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get username & password
				String username = txtUsername.getText();
				String password = new String(txtPassword.getPassword());

				// Get paths
				String path = null;

				// Prepare filters
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				Date createdFrom = null;
				try {
					createdFrom = df.parse(txtCreatedFrom.getText());
				} catch (Exception exp) {
					// Ignored
				}
				Date createdTo = null;
				try {
					createdTo = df.parse(txtCreatedTo.getText());
				} catch (Exception exp) {
					// Ignored
				}
				Date lastModifiedFrom = null;
				try {
					lastModifiedFrom = df.parse(txtLastModifiedFrom.getText());
				} catch (Exception exp) {
					// Ignored
				}
				Date lastModifiedTo = null;
				try {
					lastModifiedTo = df.parse(txtLastModifiedTo.getText());
				} catch (Exception exp) {
					// Ignored
				}

				List<String> keywords;
				if (txtKeywords.getText().isEmpty()) {
					keywords = new ArrayList<>();
				} else {
					keywords = Arrays.asList(txtKeywords.getText().split("\\s*,\\s*"));
				}

				Filters filters = new Filters.FiltersBuilder().createdFrom(createdFrom).createdTo(createdTo)
						.lastModifiedFrom(lastModifiedFrom).lastModifiedTo(lastModifiedTo).keywords(keywords)
						.crClosed(chckbxClosed.isSelected()).crInprogress(chckbxInProgress.isSelected())
						.crFixed(chckbxFixed.isSelected()).crApproved(chckbxApproved.isSelected())
						.crReviewed(chckbxReviewed.isSelected()).crVerified(chckbxVerified.isSelected()).build();

				// Create the network
				// Check which tab is selected
				if (tabbedPane.getSelectedIndex() == 0) {
					// TODO implement this
					JOptionPane.showMessageDialog(null, "This mode is not implemented yet. Please select a resource!");
					tabbedPane.setSelectedIndex(1);
					return;
				} else {
					path = txtResUri.getText();
					
					if (username == null || username.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill in the username!");
						return;
					}

					if (password == null || password.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill in the password!");
						return;
					}

					if (path == null || path.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill in the path!");
						return;
					}

					switch (comboBoxResType.getSelectedIndex()) {
					case 0:
						// TODO implement this
						JOptionPane.showMessageDialog(null,
								"This mode is not implemented yet for this resource type. Please select another resource type!");
						return;
					case 1:
						controller.createSnFromChangeRequest(username, password, path, filters);
						break;
					case 2:
						controller.createSnFromTestCase(username, password, path, filters);
						break;
					default:
						break;
					}
					return;
				}
			}
		});

		contentPane.add(btnCreateSn);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 61, 438, 144);
		contentPane.add(tabbedPane);

		JPanel tabProject = new JPanel();
		tabbedPane.addTab("Project", null, tabProject, null);
		tabProject.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Requirements Uri");
		lblNewLabel_2.setBounds(6, 43, 126, 16);
		tabProject.add(lblNewLabel_2);

		txtReqUri = new JTextField();
		txtReqUri.setBounds(144, 38, 279, 26);
		tabProject.add(txtReqUri);
		txtReqUri.setColumns(10);

		JLabel lblChangerequestsUri = new JLabel("ChangeRequests Uri");
		lblChangerequestsUri.setBounds(6, 71, 126, 16);
		tabProject.add(lblChangerequestsUri);

		txtCrUri = new JTextField();
		txtCrUri.setBounds(144, 66, 279, 26);
		tabProject.add(txtCrUri);
		txtCrUri.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Please input the requirements and change requests query URI.");
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setBounds(6, 11, 405, 16);
		tabProject.add(lblNewLabel_3);

		JPanel tabResource = new JPanel();
		tabbedPane.addTab("Resource", null, tabResource, null);
		tabResource.setLayout(null);

		comboBoxResType = new JComboBox();
		comboBoxResType.setModel(new DefaultComboBoxModel(ResourceType.values()));
		comboBoxResType.setBounds(112, 40, 311, 21);
		tabResource.add(comboBoxResType);

		JLabel lblResourceType = new JLabel("Resource Type");
		lblResourceType.setBounds(6, 42, 94, 16);
		tabResource.add(lblResourceType);

		JLabel lblResourceUri = new JLabel("Resource Uri");
		lblResourceUri.setBounds(6, 70, 79, 16);
		tabResource.add(lblResourceUri);

		txtResUri = new JTextField();
		txtResUri.setBounds(112, 68, 311, 21);
		tabResource.add(txtResUri);
		txtResUri.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel(
				"<html>Please select the type of the resource and input the URI for the resource.</html>\n");
		lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(6, 11, 417, 16);
		tabResource.add(lblNewLabel_4);

		JPanel filterPanel = new JPanel();
		filterPanel.setBounds(6, 199, 438, 417);
		contentPane.add(filterPanel);
		filterPanel.setLayout(null);

		JLabel lblFilters = new JLabel("Filters");
		lblFilters.setBounds(10, 11, 65, 14);
		filterPanel.add(lblFilters);

		JLabel lblNewLabel_5 = new JLabel("Created Date:");
		lblNewLabel_5.setBounds(10, 36, 110, 14);
		filterPanel.add(lblNewLabel_5);

		JLabel lblFromddmmyyyy = new JLabel("From (dd-MM-yyyy)");
		lblFromddmmyyyy.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblFromddmmyyyy.setBounds(10, 61, 110, 14);
		filterPanel.add(lblFromddmmyyyy);

		txtCreatedFrom = new JTextField();
		txtCreatedFrom.setBounds(130, 58, 298, 20);
		filterPanel.add(txtCreatedFrom);
		txtCreatedFrom.setColumns(10);

		JLabel lblToddmmyyyy = new JLabel("To (dd-MM-yyyy)");
		lblToddmmyyyy.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblToddmmyyyy.setBounds(10, 86, 110, 14);
		filterPanel.add(lblToddmmyyyy);

		txtCreatedTo = new JTextField();
		txtCreatedTo.setBounds(130, 83, 298, 20);
		filterPanel.add(txtCreatedTo);
		txtCreatedTo.setColumns(10);

		JLabel lblLastModifiedDate = new JLabel("Last Modified Date:");
		lblLastModifiedDate.setBounds(10, 111, 132, 14);
		filterPanel.add(lblLastModifiedDate);

		JLabel label = new JLabel("From (dd-MM-yyyy)");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		label.setBounds(10, 136, 110, 14);
		filterPanel.add(label);

		JLabel label_1 = new JLabel("To (dd-MM-yyyy)");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		label_1.setBounds(10, 161, 110, 14);
		filterPanel.add(label_1);

		txtLastModifiedFrom = new JTextField();
		txtLastModifiedFrom.setColumns(10);
		txtLastModifiedFrom.setBounds(130, 133, 298, 20);
		filterPanel.add(txtLastModifiedFrom);

		txtLastModifiedTo = new JTextField();
		txtLastModifiedTo.setColumns(10);
		txtLastModifiedTo.setBounds(130, 158, 298, 20);
		filterPanel.add(txtLastModifiedTo);

		JLabel lblKeywordscommaSeparated = new JLabel("Keywords (comma separated):");
		lblKeywordscommaSeparated.setBounds(10, 186, 194, 14);
		filterPanel.add(lblKeywordscommaSeparated);

		txtKeywords = new JTextField();
		txtKeywords.setBounds(10, 211, 418, 20);
		filterPanel.add(txtKeywords);
		txtKeywords.setColumns(10);

		JLabel lblChangeRequestState = new JLabel("Change Request State:");
		lblChangeRequestState.setBounds(10, 238, 179, 14);
		filterPanel.add(lblChangeRequestState);

		chckbxClosed = new JCheckBox("Closed");
		chckbxClosed.setBounds(10, 259, 165, 23);
		filterPanel.add(chckbxClosed);
		changeRequestStateList.add(chckbxClosed);

		chckbxInProgress = new JCheckBox("In Progress");
		chckbxInProgress.setBounds(10, 285, 132, 23);
		filterPanel.add(chckbxInProgress);
		changeRequestStateList.add(chckbxInProgress);

		chckbxFixed = new JCheckBox("Fixed");
		chckbxFixed.setBounds(10, 311, 132, 23);
		filterPanel.add(chckbxFixed);
		changeRequestStateList.add(chckbxFixed);

		chckbxApproved = new JCheckBox("Approved");
		chckbxApproved.setBounds(10, 337, 132, 23);
		filterPanel.add(chckbxApproved);
		changeRequestStateList.add(chckbxApproved);

		chckbxReviewed = new JCheckBox("Reviewed");
		chckbxReviewed.setBounds(10, 363, 132, 23);
		filterPanel.add(chckbxReviewed);
		changeRequestStateList.add(chckbxReviewed);

		chckbxVerified = new JCheckBox("Verified");
		chckbxVerified.setBounds(10, 389, 132, 23);
		filterPanel.add(chckbxVerified);
		changeRequestStateList.add(chckbxVerified);
	}
}
