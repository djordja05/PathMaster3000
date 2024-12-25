import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class PathMaster3000 {

    public PathMaster3000() {
    }

    String activeFieldColor;
    String usedFieldColor;
    String unusedFieldColor;
    int gridSize;

    public void begin() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("PathMaster3000");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);

        JPanel menu = new JPanel();
        menu.setVisible(true);

        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(20, 1));
        mainMenu.setVisible(true);
        menu.add(mainMenu);
        JPanel optionsMenu = new JPanel();
        optionsMenu.setLayout(new GridLayout(20, 1));
        optionsMenu.setVisible(false);
        menu.add(optionsMenu);
        JPanel colorPicker = new JPanel();
        colorPicker.setLayout(new GridLayout(20, 1));
        colorPicker.setVisible(false);
        menu.add(colorPicker);
        JPanel gridSizeMenu = new JPanel();
        gridSizeMenu.setLayout(new GridLayout(20, 1));
        gridSizeMenu.setVisible(false);
        menu.add(gridSizeMenu);

        ArrayList<String> configContent = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
            configContent = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                configContent.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception exc) {
        }
        for (int i = 0; i < configContent.size(); i++) {
            if (configContent.get(i).split(" ")[0].equals("activeFieldColor")) {
                this.activeFieldColor = configContent.get(i).split(" ")[2];
            } else if (configContent.get(i).split(" ")[0].equals("usedFieldColor")) {
                this.usedFieldColor = configContent.get(i).split(" ")[2];
            } else if (configContent.get(i).split(" ")[0].equals("unusedFieldColor")) {
                this.unusedFieldColor = configContent.get(i).split(" ")[2];
            } else if (configContent.get(i).split(" ")[0].equals("gridSize")) {
                this.gridSize = Integer.parseInt(configContent.get(i).split(" ")[2]);
            }
        }
        JPanel game = new JPanel();
        game.setLayout(new GridLayout(gridSize, gridSize));
        game.setVisible(false);
        menu.add(game);
        frame.add(menu);

        //---------------MENU1--------------------
        JLabel headerText = new JLabel();
        headerText.setText("PathMaster3000");
        headerText.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        mainMenu.add(headerText);

        JButton playButton = new JButton("Play");
        playButton.setSize(100, 100);
        playButton.addActionListener(e -> {
            mainMenu.setVisible(false);
            game.setVisible(true);
            gameStart(game);
        });
        mainMenu.add(playButton);

        JButton loadButton = new JButton("Load");
        loadButton.setSize(100, 100);
        mainMenu.add(loadButton);

        JButton optionsButton = new JButton("Options");
        optionsButton.setSize(100, 100);
        optionsButton.addActionListener(e -> {
            mainMenu.setVisible(false);
            optionsMenu.setVisible(true);
        });
        mainMenu.add(optionsButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setSize(100, 100);
        exitButton.addActionListener(e -> {
            frame.dispose();
        });
        mainMenu.add(exitButton);
        //-------------------MENU1 END----------------

        //----------------OPTIONS MENU-------------------
        JButton colorPickerButton = new JButton("Appearance");
        colorPickerButton.setSize(100, 100);
        colorPickerButton.addActionListener(e -> {
            optionsMenu.setVisible(false);
            colorPicker.setVisible(true);
        });
        optionsMenu.add(colorPickerButton);

        JButton gridSizeButton = new JButton("Grid Size");
        gridSizeButton.setSize(100, 100);
        gridSizeButton.addActionListener(e -> {
            optionsMenu.setVisible(false);
            gridSizeMenu.setVisible(true);
        });
        optionsMenu.add(gridSizeButton);

        JButton optionsMenuBackButton = new JButton("Back");
        optionsMenuBackButton.setSize(100, 100);
        optionsMenuBackButton.addActionListener(e -> {
            optionsMenu.setVisible(false);
            mainMenu.setVisible(true);
        });
        optionsMenu.add(optionsMenuBackButton);

        //-----------OPTIONS MENU END----------------

        //-----------COLOR PICKER MENU----------------
        JLabel activeFieldLabel = new JLabel("Select active field color");
        activeFieldLabel.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        colorPicker.add(activeFieldLabel);

        String[] colors = new String[]{"Red", "Green", "Blue", "Yellow", "Orange", "Magenta", "Cyan", "White"};
        JComboBox<String> activeFieldColorBox = new JComboBox<>(colors);
        activeFieldColorBox.setVisible(true);
        colorPicker.add(activeFieldColorBox);

        JButton setActiveFieldButton = new JButton("Set");
        setActiveFieldButton.setSize(100, 100);
        setActiveFieldButton.addActionListener(e -> {
            setActiveFieldColor((String)activeFieldColorBox.getSelectedItem());
        });
        colorPicker.add(setActiveFieldButton);

        JLabel usedFieldLabel = new JLabel("Select used field color");
        usedFieldLabel.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        colorPicker.add(usedFieldLabel);

        JComboBox<String> usedFieldColorBox = new JComboBox<>(colors);
        usedFieldColorBox.setVisible(true);
        colorPicker.add(usedFieldColorBox);

        JButton setUsedFieldButton = new JButton("Set");
        setUsedFieldButton.setSize(100, 100);
        setUsedFieldButton.addActionListener(e -> {
            setUsedFieldColor((String)usedFieldColorBox.getSelectedItem());
        });
        colorPicker.add(setUsedFieldButton);

        JLabel unusedFieldLabel = new JLabel("Select unused field color");
        unusedFieldLabel.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        colorPicker.add(unusedFieldLabel);

        JComboBox<String> unusedFieldColorBox = new JComboBox<>(colors);
        unusedFieldColorBox.setVisible(true);
        colorPicker.add(unusedFieldColorBox);

        JButton unusedFieldButton = new JButton("Set");
        unusedFieldButton.setSize(100, 100);
        unusedFieldButton.addActionListener(e -> {
            setunusedFieldColor((String)unusedFieldColorBox.getSelectedItem());
        });
        colorPicker.add(unusedFieldButton);

        JButton colorPickerBackButton = new JButton("Back");
        colorPickerBackButton.setSize(100, 100);
        colorPickerBackButton.addActionListener(e -> {
            colorPicker.setVisible(false);
            optionsMenu.setVisible(true);
        });
        colorPicker.add(colorPickerBackButton);
        //-----------COLORPICKER MENU END-------------

        //------------GRID SIZE MENU-------------
        String[] sizes = new String[]{"1 x 1", "2 x 2", "3 x 3", "4 x 4", "5 x 5", "6 x 6", "7 x 7", "8 x 8", "9 x 9", "10 x 10", "11 x 11", "12 x 12"};
        JComboBox<String> sizesBox = new JComboBox<>(sizes);
        sizesBox.setVisible(true);
        gridSizeMenu.add(sizesBox);
        JButton setGridSizeButton = new JButton("Set");
        setGridSizeButton.setSize(100, 100);
        setGridSizeButton.addActionListener(e -> {
            setGridSize(Integer.valueOf(sizesBox.getSelectedItem().toString().charAt(0) - '0'));
        });
        gridSizeMenu.add(setGridSizeButton);

        JButton gridSizeBackButton = new JButton("Back");
        gridSizeBackButton.setSize(100, 100);
        gridSizeBackButton.addActionListener(e -> {
            gridSizeMenu.setVisible(false);
            optionsMenu.setVisible(true);
        });
        gridSizeMenu.add(gridSizeBackButton);


        //----------GRID SIZE MENU END------------

        menu.setVisible(true);
        mainMenu.setVisible(true);
        frame.setVisible(true);
    }

    private void setActiveFieldColor(String activeFieldColor) {
        this.activeFieldColor = activeFieldColor;
    }

    private void setUsedFieldColor(String usedFieldColor) {
        this.usedFieldColor = usedFieldColor;
    }

    private void setunusedFieldColor(String unusedFieldColor) {
        this.unusedFieldColor = unusedFieldColor;
    }

    private void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    private Color getColor(String txt) {
        switch (txt) {
            case "Red": return Color.RED;
            case "Green": return Color.GREEN;
            case "Blue": return Color.BLUE;
            case "Yellow": return Color.YELLOW;
            case "Orange": return Color.ORANGE;
            case "Magenta": return Color.MAGENTA;
            case "Cyan": return Color.CYAN;
            case "White": return Color.WHITE;
            default: return Color.DARK_GRAY;
        }
    }

    private void gameStart(JPanel game) {
        int count = 0;
        game.setLayout(new GridLayout(gridSize, gridSize));
        Random random = new Random();
        ArrayList<JButton> fields = new ArrayList<>();
        for (int i = 0; i < gridSize * gridSize; i++) {
                JButton button = new JButton(String.valueOf((int)(Math.random() * 10)));
                button.setVisible(true);
                button.setBackground(getColor(this.unusedFieldColor));
                button.setForeground(getColor("White"));
                fields.add(button);
                count++;
        }
        fields.set(0, new JButton("Start"));
        fields.set(fields.size() - 1, new JButton("End"));
        for (int i = 0; i < gridSize * gridSize; i++) {
                game.add(fields.get(i));
        }
    }
}
