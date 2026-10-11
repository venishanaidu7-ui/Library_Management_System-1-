package library.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UITheme {

    // =====================================================
    // COLOUR SCHEME
    // =====================================================

    // Main dusty rose colour
    public static final Color PRIMARY =
            new Color(183, 110, 121);

    // Dark rose-brown
    public static final Color PRIMARY_DARK =
            new Color(92, 58, 58);

    // Rose-gold accent
    public static final Color ACCENT =
            new Color(199, 145, 147);

    // Soft blush background
    public static final Color BACKGROUND =
            new Color(255, 245, 245);

    // Warm white for cards and panels
    public static final Color CARD =
            new Color(255, 250, 250);

    // Main text
    public static final Color TEXT =
            new Color(92, 58, 58);

    // Secondary text
    public static final Color SECONDARY_TEXT =
            new Color(138, 106, 106);

    // Success colour
    public static final Color SUCCESS =
            new Color(95, 158, 114);

    // Error / delete colour
    public static final Color DANGER =
            new Color(201, 76, 92);

    // Warning / gold colour
    public static final Color WARNING =
            new Color(218, 165, 80);

    // Light rose border
    public static final Color BORDER =
            new Color(232, 202, 202);


    // =====================================================
    // FONTS
    // =====================================================

    public static final Font TITLE_FONT =
            new Font(
                    "Arial",
                    Font.BOLD,
                    26
            );

    public static final Font HEADING_FONT =
            new Font(
                    "Arial",
                    Font.BOLD,
                    18
            );

    public static final Font LABEL_FONT =
            new Font(
                    "Arial",
                    Font.BOLD,
                    13
            );

    public static final Font NORMAL_FONT =
            new Font(
                    "Arial",
                    Font.PLAIN,
                    13
            );

    public static final Font BUTTON_FONT =
            new Font(
                    "Arial",
                    Font.BOLD,
                    13
            );


    // =====================================================
    // APPLY MAIN FRAME BACKGROUND
    // =====================================================

    public static void applyFrame(
            JFrame frame) {

        frame.getContentPane()
                .setBackground(
                        BACKGROUND
                );
    }


    // =====================================================
    // CREATE TITLE
    // =====================================================

    public static JLabel createTitle(
            String text) {

        JLabel title =
                new JLabel(
                        text,
                        SwingConstants.CENTER
                );

        title.setFont(
                TITLE_FONT
        );

        title.setForeground(
                PRIMARY_DARK
        );

        title.setBorder(
                new EmptyBorder(
                        20,
                        10,
                        15,
                        10
                )
        );

        return title;
    }


    // =====================================================
    // CREATE NORMAL LABEL
    // =====================================================

    public static JLabel createLabel(
            String text) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                LABEL_FONT
        );

        label.setForeground(
                TEXT
        );

        return label;
    }


    // =====================================================
    // CREATE TEXT FIELD
    // =====================================================

    public static JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                NORMAL_FONT
        );

        field.setForeground(
                TEXT
        );

        field.setBackground(
                CARD
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                5,
                                8,
                                5,
                                8
                        )
                )
        );

        return field;
    }


    // =====================================================
    // CREATE PASSWORD FIELD
    // =====================================================

    public static JPasswordField createPasswordField() {

        JPasswordField field =
                new JPasswordField();

        field.setFont(
                NORMAL_FONT
        );

        field.setForeground(
                TEXT
        );

        field.setBackground(
                CARD
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                5,
                                8,
                                5,
                                8
                        )
                )
        );

        return field;
    }


    // =====================================================
    // PRIMARY BUTTON
    // =====================================================

    public static JButton createPrimaryButton(
            String text) {

        JButton button =
                new JButton(
                        text
                );

        button.setFont(
                BUTTON_FONT
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                PRIMARY
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }


    // =====================================================
    // SUCCESS BUTTON
    // =====================================================

    public static JButton createSuccessButton(
            String text) {

        JButton button =
                createPrimaryButton(
                        text
                );

        button.setBackground(
                SUCCESS
        );

        return button;
    }


    // =====================================================
    // DANGER BUTTON
    // =====================================================

    public static JButton createDangerButton(
            String text) {

        JButton button =
                createPrimaryButton(
                        text
                );

        button.setBackground(
                DANGER
        );

        return button;
    }


    // =====================================================
    // SECONDARY BUTTON
    // =====================================================

    public static JButton createSecondaryButton(
            String text) {

        JButton button =
                new JButton(
                        text
                );

        button.setFont(
                BUTTON_FONT
        );

        button.setForeground(
                PRIMARY_DARK
        );

        button.setBackground(
                CARD
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }


    // =====================================================
    // CREATE PANEL
    // =====================================================

    public static JPanel createPanel() {

        JPanel panel =
                new JPanel();

        panel.setBackground(
                CARD
        );

        return panel;
    }


    // =====================================================
    // STYLE TABLE
    // =====================================================

    public static void styleTable(
            JTable table) {

        table.setFont(
                NORMAL_FONT
        );

        table.setForeground(
                TEXT
        );

        table.setBackground(
                CARD
        );

        table.setRowHeight(
                28
        );

        table.setSelectionBackground(
                new Color(
                        248,
                        220,
                        223
                )
        );

        table.setSelectionForeground(
                TEXT
        );

        table.setGridColor(
                BORDER
        );

        table.getTableHeader()
                .setFont(
                        BUTTON_FONT
                );

        table.getTableHeader()
                .setBackground(
                        PRIMARY
                );

        table.getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        table.getTableHeader()
                .setReorderingAllowed(
                        false
                );
    }
}