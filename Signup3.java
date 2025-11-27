package banking.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.Random;

/**
 * Signup3 - Account details page (page 3)
 * - Inserts into signupthree(formno, account_type, card_number, pin, facility_atm, facility_internet, facility_mobile, facility_email, facility_chequebook)
 *
 * Assumes Connn class provides a java.sql.Connection field named `connection`.
 */
public class Signup3 extends JFrame implements ActionListener {

    JComboBox<String> accountTypeCombo;
    JCheckBox atmCheckbox, internetCheckbox, mobileCheckbox, emailCheckbox, chequeCheckbox;
    JButton submit, cancel;
    String formno;
    JLabel formNoLabel, cardLabel, pinLabel;
    JTextField cardField, pinField;

    public Signup3(String formno) {
        super("NEW ACCOUNT APPLICATION - Page 3");
        this.formno = formno;

        // Frame setup
        setSize(850, 800);
        setLocation(450, 20);
        setLayout(null);
        getContentPane().setBackground(new Color(252, 208, 76));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo (optional) - keep same icon path used earlier
        try {
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
            Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
            JLabel image = new JLabel(new ImageIcon(i2));
            image.setBounds(150, 5, 100, 100);
            add(image);
        } catch (Exception ex) {
            // ignore if icon not found
        }

        JLabel heading = new JLabel("Page 3: Account Details");
        heading.setFont(new Font("Raleway", Font.BOLD, 22));
        heading.setBounds(300, 30, 400, 40);
        add(heading);

        // Form No
        JLabel lForm = new JLabel("Form No :");
        lForm.setFont(new Font("Raleway", Font.BOLD, 14));
        lForm.setBounds(700, 10, 80, 30);
        add(lForm);

        formNoLabel = new JLabel(formno);
        formNoLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        formNoLabel.setBounds(780, 10, 80, 30);
        add(formNoLabel);

        // Account Type
        JLabel accTypeLabel = new JLabel("Account Type :");
        accTypeLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        accTypeLabel.setBounds(100, 120, 200, 30);
        add(accTypeLabel);

        String[] accountTypes = {"Savings Account", "Fixed Deposit Account", "Current Account", "Recurring Deposit Account"};
        accountTypeCombo = new JComboBox<>(accountTypes);
        accountTypeCombo.setFont(new Font("Raleway", Font.BOLD, 14));
        accountTypeCombo.setBounds(350, 120, 320, 30);
        add(accountTypeCombo);

        // Card number (generated)
        JLabel lCard = new JLabel("Card Number :");
        lCard.setFont(new Font("Raleway", Font.BOLD, 18));
        lCard.setBounds(100, 200, 200, 30);
        add(lCard);

        cardField = new JTextField();
        cardField.setFont(new Font("Raleway", Font.BOLD, 16));
        cardField.setBounds(350, 200, 320, 30);
        cardField.setEditable(false);
        add(cardField);

        // Pin (generated)
        JLabel lPin = new JLabel("PIN :");
        lPin.setFont(new Font("Raleway", Font.BOLD, 18));
        lPin.setBounds(100, 260, 200, 30);
        add(lPin);

        pinField = new JTextField();
        pinField.setFont(new Font("Raleway", Font.BOLD, 16));
        pinField.setBounds(350, 260, 320, 30);
        pinField.setEditable(false);
        add(pinField);

        // Facilities (checkboxes)
        JLabel facilitiesLabel = new JLabel("Services Required :");
        facilitiesLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        facilitiesLabel.setBounds(100, 330, 250, 30);
        add(facilitiesLabel);

        atmCheckbox = new JCheckBox("ATM Card");
        atmCheckbox.setFont(new Font("Raleway", Font.BOLD, 14));
        atmCheckbox.setBounds(350, 330, 150, 30);
        atmCheckbox.setBackground(new Color(252, 208, 76));
        add(atmCheckbox);

        internetCheckbox = new JCheckBox("Internet Banking");
        internetCheckbox.setFont(new Font("Raleway", Font.BOLD, 14));
        internetCheckbox.setBounds(350, 370, 200, 30);
        internetCheckbox.setBackground(new Color(252, 208, 76));
        add(internetCheckbox);

        mobileCheckbox = new JCheckBox("Mobile Banking");
        mobileCheckbox.setFont(new Font("Raleway", Font.BOLD, 14));
        mobileCheckbox.setBounds(350, 410, 200, 30);
        mobileCheckbox.setBackground(new Color(252, 208, 76));
        add(mobileCheckbox);

        emailCheckbox = new JCheckBox("Email Alerts");
        emailCheckbox.setFont(new Font("Raleway", Font.BOLD, 14));
        emailCheckbox.setBounds(350, 450, 200, 30);
        emailCheckbox.setBackground(new Color(252, 208, 76));
        add(emailCheckbox);

        chequeCheckbox = new JCheckBox("Cheque Book");
        chequeCheckbox.setFont(new Font("Raleway", Font.BOLD, 14));
        chequeCheckbox.setBounds(350, 490, 200, 30);
        chequeCheckbox.setBackground(new Color(252, 208, 76));
        add(chequeCheckbox);

        // Buttons
        submit = new JButton("Submit");
        submit.setFont(new Font("Raleway", Font.BOLD, 14));
        submit.setBounds(350, 600, 120, 30);
        submit.setBackground(Color.WHITE);
        submit.setForeground(Color.BLACK);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Raleway", Font.BOLD, 14));
        cancel.setBounds(520, 600, 120, 30);
        cancel.setBackground(Color.WHITE);
        cancel.setForeground(Color.BLACK);
        cancel.addActionListener(e -> {
            // close or go back
            setVisible(false);
            new Login(); // if you have a Login class; if not, simply dispose
        });
        add(cancel);

        // Generate card & pin initially (so user sees them)
        String generatedCard = generateCardNumber();
        String generatedPin = generatePin();
        cardField.setText(generatedCard);
        pinField.setText(generatedPin);

        setVisible(true);
    }

    // Helper to generate a 16-digit card number (string)
    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        // Use a simple pattern: 16 digits
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    // Helper to generate a 4-digit PIN
    private String generatePin() {
        Random random = new Random();
        int pin = random.nextInt(10000); // 0 - 9999
        return String.format("%04d", pin);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Submit button pressed
        if (ae.getSource() == submit) {
            String accountType = (String) accountTypeCombo.getSelectedItem();
            String cardNumber = cardField.getText().trim();
            String pin = pinField.getText().trim();

            String atm = atmCheckbox.isSelected() ? "Yes" : "No";
            String internet = internetCheckbox.isSelected() ? "Yes" : "No";
            String mobile = mobileCheckbox.isSelected() ? "Yes" : "No";
            String email = emailCheckbox.isSelected() ? "Yes" : "No";
            String chequebook = chequeCheckbox.isSelected() ? "Yes" : "No";

            // Basic validation
            if (formno == null || formno.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Form number missing. Can't create account.");
                return;
            }
            if (accountType == null || accountType.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select an account type.");
                return;
            }

            // Insert into DB using PreparedStatement - list columns explicitly
            String sql = "INSERT INTO signupthree (formno, account_type, card_number, pin, facility_atm, facility_internet, facility_mobile, facility_email, facility_chequebook) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                Connn c = new Connn(); // your DB connection helper
                Connection conn = c.connection; // assumes Connn exposes a 'connection' field
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, formno);
                pst.setString(2, accountType);
                pst.setString(3, cardNumber);
                pst.setString(4, pin);
                pst.setString(5, atm);
                pst.setString(6, internet);
                pst.setString(7, mobile);
                pst.setString(8, email);
                pst.setString(9, chequebook);

                int rows = pst.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Account created successfully.\nCard Number: " + cardNumber + "\nPIN: " + pin);
                    // After successful creation you can proceed to next screen (e.g., deposit) or close
                    setVisible(false);
                    // optionally open a next window, e.g., new Deposit(pin) if exists
                } else {
                    JOptionPane.showMessageDialog(null, "Account creation failed. Try again.");
                }
                pst.close();
                // conn.close(); // manage connection lifecycle in Connn class
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
            }
        }
    }

    // quick main for testing - pass a test form number
    public static void main(String[] args) {
        new Signup3("TESTFORM123");
    }
}
