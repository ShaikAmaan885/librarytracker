import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class LiibraryGUI {
    private Library library;
    private JFrame frame;
    private JTextArea outputArea;

    public LiibraryGUI() {
        library = new Library();
        initializePremiumGUI();
    }

    private void initializePremiumGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Button.arc", 20);
            UIManager.put("Component.arc", 20);
            UIManager.put("TextComponent.arc", 15);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configure main frame
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 750);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(248, 249, 250));

        // Create main panel with shadow effect
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 25, 25);
                g2d.dispose();
            }
        };
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 249, 250));

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Library Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(52, 58, 64));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        // Create card panel for form
        JPanel formCard = createCardPanel();
        formCard.setLayout(new BorderLayout());

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create styled components
        Font labelFont = new Font("Segoe UI Semibold", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Create form fields
        JTextField titleField = createModernTextField(fieldFont);
        JTextField authorField = createModernTextField(fieldFont);
        JTextField isbnField = createModernTextField(fieldFont);
        JTextField borrowerNameField = createModernTextField(fieldFont);
        JTextField borrowerIdField = createModernTextField(fieldFont);
        JTextField searchField = createModernTextField(fieldFont);

        // Add form rows
        addModernFormRow(formPanel, "Title:", titleField, labelFont);
        addModernFormRow(formPanel, "Author:", authorField, labelFont);
        addModernFormRow(formPanel, "ISBN:", isbnField, labelFont);
        addModernFormRow(formPanel, "Borrower Name:", borrowerNameField, labelFont);
        addModernFormRow(formPanel, "Borrower ID:", borrowerIdField, labelFont);
        addModernFormRow(formPanel, "Search:", searchField, labelFont);

        // Create buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create and add buttons
        JButton addButton = createModernButton("Add Book", new Color(40, 167, 69));
        JButton displayButton = createModernButton("Display All", new Color(0, 123, 255));
        JButton borrowButton = createModernButton("Borrow Book", new Color(255, 193, 7));
        JButton returnButton = createModernButton("Return Book", new Color(220, 53, 69));
        JButton borrowedButton = createModernButton("Borrowed Books", new Color(111, 66, 193));
        JButton searchButton = createModernButton("Search Books", new Color(23, 162, 184));

        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(borrowedButton);
        buttonPanel.add(searchButton);

        // Add components to form card
        formCard.add(formPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        // Create output card
        JPanel outputCard = createCardPanel();
        outputCard.setLayout(new BorderLayout());

        // Create output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JLabel outputTitle = new JLabel("  Library Status");
        outputTitle.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        outputTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        outputTitle.setForeground(new Color(73, 80, 87));

        outputCard.add(outputTitle, BorderLayout.NORTH);
        outputCard.add(scrollPane, BorderLayout.CENTER);

        // Add panels to main panel
        headerPanel.add(formCard, BorderLayout.NORTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(outputCard, BorderLayout.CENTER);

        // Add main panel to frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Configure button actions
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();

            if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
                library.addBook(new Book(title, author, isbn));
                showSuccessMessage("Book added successfully!");
                outputArea.setText(library.displayBooks());
                clearFields(titleField, authorField, isbnField);
            } else {
                showErrorMessage("Please fill all fields (title, author, ISBN)");
            }
        });

        displayButton.addActionListener(e -> {
            outputArea.setText(library.displayBooks());
        });

        borrowButton.addActionListener(e -> {
            String isbn = isbnField.getText();
            String borrowerName = borrowerNameField.getText();
            String borrowerId = borrowerIdField.getText();

            if (!isbn.isEmpty() && !borrowerName.isEmpty() && !borrowerId.isEmpty()) {
                Borrower borrower = new Borrower(borrowerName, borrowerId);
                if (library.borrowBook(isbn, borrower)) {
                    showSuccessMessage("Book borrowed successfully!");
                    outputArea.setText(library.displayBooks());
                    clearFields(borrowerNameField, borrowerIdField);
                } else {
                    showErrorMessage("Book not available or not found!");
                }
            } else {
                showErrorMessage("Please enter ISBN, borrower name and ID");
            }
        });

        returnButton.addActionListener(e -> {
            String isbn = isbnField.getText();

            if (!isbn.isEmpty()) {
                if (library.returnBook(isbn)) {
                    showSuccessMessage("Book returned successfully!");
                    outputArea.setText(library.displayBooks());
                } else {
                    showErrorMessage("Book wasn't borrowed or not found!");
                }
            } else {
                showErrorMessage("Please enter ISBN");
            }
        });

        borrowedButton.addActionListener(e -> {
            outputArea.setText(library.getBorrowedBooks());
        });

        searchButton.addActionListener(e -> {
            String query = searchField.getText();

            if (!query.isEmpty()) {
                outputArea.setText(library.searchBook(query));
                searchField.setText("");
            } else {
                showErrorMessage("Please enter search term");
            }
        });

        // Center the frame on screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createCardPanel() {
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder());
        return card;
    }

    private JTextField createModernTextField(Font font) {
        JTextField field = new JTextField() {
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(getBackground());
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }
        };
        field.setFont(font);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        field.setBackground(new Color(248, 249, 250));
        return field;
    }

    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return button;
    }

    private void addModernFormRow(JPanel panel, String labelText, JTextField field, Font labelFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(new Color(73, 80, 87));
        panel.add(label);
        panel.add(field);
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private void showSuccessMessage(String message) {
        outputArea.setForeground(new Color(40, 167, 69));
        outputArea.setText("✓ " + message);
    }

    private void showErrorMessage(String message) {
        outputArea.setForeground(new Color(220, 53, 69));
        outputArea.setText("✗ " + message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LiibraryGUI();
        });
    }
}