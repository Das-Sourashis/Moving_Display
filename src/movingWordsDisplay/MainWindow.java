/**
 * 
 */
package movingWordsDisplay;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JTextField;
import java.awt.GridBagConstraints;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;

/**
 * @author Sourashis Das
 */

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField newInputTextField;
	private JTextArea newInputTextArea;
	private JPanel matrix_panel;
	private JLabel rowNumLabel;
	private JTextField rowTextField;
	private JLabel columnNumLabel;
	private JTextField columnTextField;
	private JButton createButton;
	private JPanel createPanel;
	private JLabel symbolLabel;
	private JButton saveButton;
	private JLabel newCodeLabel;
	private JPanel buttonPatternPanel;
	private JPanel buttonContainerJPanel;
	private JButton removeAllButton;

	char[][] ledTable;
	int getRow, getColumn;
    private int MIN_ROW_COLUMN = 5;
    private int MAX_ROW_COLUMN = 10;

	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
    	new MainWindow().setVisible(true);
    }

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel input_panel = new JPanel();
		input_panel.setFont(new Font("Arial", Font.BOLD, 14));
		tabbedPane.addTab("NEW INPUT", null, input_panel, null);
		GridBagLayout gbl_input_panel = new GridBagLayout();
		gbl_input_panel.columnWidths = new int[] { 0, 0 };
		gbl_input_panel.rowHeights = new int[] { 40, 45, 40, 508, 0 };
		gbl_input_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_input_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		input_panel.setLayout(gbl_input_panel);

		JLabel newInputTextFieldLabel = new JLabel("Enter Your Text You Want To Display : ");
		newInputTextFieldLabel.setHorizontalTextPosition(SwingConstants.LEADING);
		newInputTextFieldLabel.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_newInputTextFieldLabel = new GridBagConstraints();
		gbc_newInputTextFieldLabel.insets = new Insets(0, 0, 5, 0);
		gbc_newInputTextFieldLabel.gridx = 0;
		gbc_newInputTextFieldLabel.gridy = 0;
		input_panel.add(newInputTextFieldLabel, gbc_newInputTextFieldLabel);

		newInputTextField = new JTextField();
		newInputTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		newInputTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_newInputTextField = new GridBagConstraints();
		gbc_newInputTextField.insets = new Insets(0, 0, 5, 0);
		gbc_newInputTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_newInputTextField.gridx = 0;
		gbc_newInputTextField.gridy = 1;
		input_panel.add(newInputTextField, gbc_newInputTextField);
		newInputTextField.setColumns(10);

		JScrollPane newInputScrollPane = new JScrollPane();
		GridBagConstraints gbc_newInputScrollPane = new GridBagConstraints();
		gbc_newInputScrollPane.fill = GridBagConstraints.BOTH;
		gbc_newInputScrollPane.gridx = 0;
		gbc_newInputScrollPane.gridy = 3;
		input_panel.add(newInputScrollPane, gbc_newInputScrollPane);

		JButton inputGenerateButton = new JButton("Generate RAM Code");
		InputStringToCode inputStringToCode = new InputStringToCode();
		inputGenerateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String msgString = inputStringToCode.inputToCode(newInputTextField.getText());
				newInputTextArea.setText("");
				updateTextArea(newInputTextArea, msgString, false);
			}
		});
		inputGenerateButton.setRequestFocusEnabled(false);
		GridBagConstraints gbc_inputGenerateButton = new GridBagConstraints();
		gbc_inputGenerateButton.insets = new Insets(0, 0, 5, 0);
		gbc_inputGenerateButton.gridx = 0;
		gbc_inputGenerateButton.gridy = 2;
		input_panel.add(inputGenerateButton, gbc_inputGenerateButton);

		newInputTextArea = new JTextArea();
		newInputTextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		newInputTextArea.setBorder(new LineBorder(SystemColor.controlShadow, 2));
		newInputTextArea.setBackground(new Color(234, 255, 255));
		newInputScrollPane.setViewportView(newInputTextArea);

		JPanel input_code_panel = new JPanel();
		tabbedPane.addTab("PRESENT INPUT CODE", null, input_code_panel, null);
		input_code_panel.setLayout(new BorderLayout(0, 0));

		JScrollPane inputCodeScrollPane = new JScrollPane();
		input_code_panel.add(inputCodeScrollPane, BorderLayout.CENTER);

		JTextArea inputCodeTextArea = new JTextArea();
		inputCodeTextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		inputCodeTextArea.setBorder(new LineBorder(SystemColor.controlShadow, 2));
		inputCodeTextArea.setBackground(new Color(234, 255, 255));
		inputCodeTextArea.setEditable(false);
		inputCodeScrollPane.setViewportView(inputCodeTextArea);

		JPanel symbol_code_panel = new JPanel();
		tabbedPane.addTab("PRESENT SYMBOL CODE", null, symbol_code_panel, null);
		symbol_code_panel.setLayout(new BorderLayout(0, 0));

		JScrollPane symbolCodeScrollPane = new JScrollPane();
		symbol_code_panel.add(symbolCodeScrollPane, BorderLayout.CENTER);

		JTextArea symbolCodeTextArea = new JTextArea();
		symbolCodeTextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		symbolCodeTextArea.setBorder(new LineBorder(SystemColor.controlShadow, 2));
		symbolCodeTextArea.setBackground(new Color(234, 255, 255));
		symbolCodeTextArea.setEditable(false);
		symbolCodeScrollPane.setViewportView(symbolCodeTextArea);

		matrix_panel = new JPanel();
		tabbedPane.addTab("NEW SYMBOL CODE", null, matrix_panel, null);
		GridBagLayout gbl_matrix_panel = new GridBagLayout();
		gbl_matrix_panel.columnWidths = new int[] { 235, 235, 235, 235, 235, 0 };
		gbl_matrix_panel.rowHeights = new int[] { 50, 0, 40, 0 };
		gbl_matrix_panel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_matrix_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		matrix_panel.setLayout(gbl_matrix_panel);

		rowNumLabel = new JLabel("Number of Rows : ");
		GridBagConstraints gbc_rowNumLabel = new GridBagConstraints();
		gbc_rowNumLabel.insets = new Insets(0, 0, 5, 5);
		gbc_rowNumLabel.anchor = GridBagConstraints.EAST;
		gbc_rowNumLabel.gridx = 0;
		gbc_rowNumLabel.gridy = 0;
		matrix_panel.add(rowNumLabel, gbc_rowNumLabel);

		rowTextField = new JTextField();
		GridBagConstraints gbc_rowNumberFormattedTextField = new GridBagConstraints();
		gbc_rowNumberFormattedTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_rowNumberFormattedTextField.insets = new Insets(0, 0, 5, 5);
		gbc_rowNumberFormattedTextField.gridx = 1;
		gbc_rowNumberFormattedTextField.gridy = 0;
		matrix_panel.add(rowTextField, gbc_rowNumberFormattedTextField);

		columnNumLabel = new JLabel("Number of Columns : ");
		GridBagConstraints gbc_columnNumLabel = new GridBagConstraints();
		gbc_columnNumLabel.insets = new Insets(0, 0, 5, 5);
		gbc_columnNumLabel.anchor = GridBagConstraints.EAST;
		gbc_columnNumLabel.gridx = 2;
		gbc_columnNumLabel.gridy = 0;
		matrix_panel.add(columnNumLabel, gbc_columnNumLabel);

		columnTextField = new JTextField();
		GridBagConstraints gbc_columnNumberFormattedTextField = new GridBagConstraints();
		gbc_columnNumberFormattedTextField.insets = new Insets(0, 0, 5, 5);
		gbc_columnNumberFormattedTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_columnNumberFormattedTextField.gridx = 3;
		gbc_columnNumberFormattedTextField.gridy = 0;
		matrix_panel.add(columnTextField, gbc_columnNumberFormattedTextField);

		createButton = new JButton("Create");
		createButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (createButton.isEnabled()) {
					getRow = 0;
					getColumn = 0;
					try {
						getRow = Integer.parseInt(rowTextField.getText());
						getColumn = Integer.parseInt(columnTextField.getText());

						if (getRow < MIN_ROW_COLUMN || getRow > MAX_ROW_COLUMN || getColumn < MIN_ROW_COLUMN || getColumn > MAX_ROW_COLUMN) {
							JOptionPane.showMessageDialog(null,
									"You can have at least 5x5 matrix and atmost 10x10 matrix", null,
									JOptionPane.ERROR_MESSAGE);
						} else {
							ledTable = new char[getRow][getColumn]; /* table containing status of led display */
							/* Initialize ledTable */
							for (int i = 0; i < getRow; i++) {
								for (int j = 0; j < getColumn; j++) {
									ledTable[i][j] = '0';
								}
							}

							// disable upper portion
							rowTextField.setText("");
							columnTextField.setText("");
							createButton.setEnabled(false);

							// enable middle and lower portion
							for (Component component : createPanel.getComponents()) {
								component.setEnabled(true);
							}
							removeAllButton.setEnabled(true);

							buttonContainerJPanel.setPreferredSize(new Dimension(getColumn * 20, getRow * 20));
							buttonContainerJPanel.setLayout(new GridLayout(getColumn, getRow, 0, 0));

							JButton[][] button = new JButton[getRow][getColumn];
							for (int i = 0; i < getRow; i++) {
								for (int j = 0; j < getColumn; j++) {
									button[i][j] = new JButton("");
									button[i][j].setSize(20, 20);
									button[i][j].setName("b_" + i + "_" + j);
									button[i][j].setBackground(new Color(255, 255, 255));
									buttonContainerJPanel.add(button[i][j]);

									button[i][j].addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											JButton o = (JButton) e.getSource();
											String xString = o.getName();
											String[] separatedStrings = xString.split("_");
											int a = Integer.parseInt(separatedStrings[1]);
											int b = Integer.parseInt(separatedStrings[2]);

											if (ledTable[a][b] == '0') {
												ledTable[a][b] = '1';
												o.setBackground(new Color(255, 0, 0)); // make red
											} else {
												ledTable[a][b] = '0';
												o.setBackground(new Color(255, 255, 255)); // make white
											}
										}
									});
								}
							}
							buttonContainerJPanel.revalidate();
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Wrong Input. Input is Not intiger.", null,
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		createButton.setRequestFocusEnabled(false);
		GridBagConstraints gbc_createButton = new GridBagConstraints();
		gbc_createButton.insets = new Insets(0, 0, 5, 0);
		gbc_createButton.gridx = 4;
		gbc_createButton.gridy = 0;
		matrix_panel.add(createButton, gbc_createButton);

		createPanel = new JPanel();
		createPanel.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_createPanel = new GridBagConstraints();
		gbc_createPanel.insets = new Insets(0, 0, 5, 0);
		gbc_createPanel.gridwidth = 5;
		gbc_createPanel.fill = GridBagConstraints.BOTH;
		gbc_createPanel.gridx = 0;
		gbc_createPanel.gridy = 1;
		matrix_panel.add(createPanel, gbc_createPanel);
		GridBagLayout gbl_createPanel = new GridBagLayout();
		gbl_createPanel.columnWidths = new int[] { 707, 164, 0, 0 };
		gbl_createPanel.rowHeights = new int[] { 40, 30, 40, 0, 0 };
		gbl_createPanel.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_createPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		createPanel.setLayout(gbl_createPanel);

		symbolLabel = new JLabel("Symbol Corrosponding To Code : ");
		GridBagConstraints gbc_symbolLabel = new GridBagConstraints();
		gbc_symbolLabel.insets = new Insets(0, 0, 5, 5);
		gbc_symbolLabel.anchor = GridBagConstraints.EAST;
		gbc_symbolLabel.gridx = 1;
		gbc_symbolLabel.gridy = 0;
		createPanel.add(symbolLabel, gbc_symbolLabel);

		JTextField symbolTextField = new JTextField();
		symbolTextField.setHorizontalAlignment(SwingConstants.CENTER);
		symbolTextField.setFont(new Font("Arial", Font.BOLD, 12));
		GridBagConstraints gbc_symbolTextField = new GridBagConstraints();
		gbc_symbolTextField.insets = new Insets(0, 0, 5, 0);
		gbc_symbolTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_symbolTextField.gridx = 2;
		gbc_symbolTextField.gridy = 0;
		createPanel.add(symbolTextField, gbc_symbolTextField);
		symbolTextField.setColumns(10);

		saveButton = new JButton("SAVE");
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (saveButton.isEnabled()) {
					saveSymbol();
				}
			}

			private void saveSymbol() {
			    if (symbolTextField.getText().length() != 1) {
			        JOptionPane.showMessageDialog(null, "Give a symbol corresponding to this code.", null, JOptionPane.ERROR_MESSAGE);
			        return;
			    }

			    String finalCode = generateFinalCode();

			    newCodeLabel.setText(symbolTextField.getText() + " : " + finalCode);
			    String symbolText = symbolTextField.getText();

			    JRadioButton[] options = {
			        new JRadioButton("Create New Symbol Table"),
			        new JRadioButton("Append to Existing Symbol Table", true)
			    };

			    ButtonGroup group = new ButtonGroup();
			    for (JRadioButton button : options) {
			        group.add(button);
			    }

			    int choice = JOptionPane.showOptionDialog(null, options, "Choose an Option", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, options[1]);
			    Path filePath = Paths.get("asset/symbol_code.txt");
			    if (choice == JOptionPane.OK_OPTION) {
			    	try {
				        if (options[0].isSelected()) {
				            Files.write(filePath, (symbolText + " " + finalCode).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
				            JOptionPane.showMessageDialog(null, "Symbol table created successfully!");
				        } else if(options[1].isSelected()){
				            handleAppendOption(filePath, symbolText, finalCode);
				        }
				    } catch (IOException e) {
				        e.printStackTrace();
				        JOptionPane.showMessageDialog(null, "Failed to save symbol table", "Error", JOptionPane.ERROR_MESSAGE);
				    }
				}
			    
			    symbolTextField.setText("");
			}

			private String generateFinalCode() {
			    StringBuilder finalCode = new StringBuilder();

			    for (int i = 0; i < getColumn; i++) {
			        StringBuilder binCodeOfColumn = new StringBuilder(Character.toString(ledTable[0][i]));
			        for (int j = 1; j < getRow; j++) {
			            binCodeOfColumn.append(ledTable[j][i]);
			        }
			        finalCode.append(" ").append(Integer.toString(Integer.parseInt(binCodeOfColumn.toString(), 2), 16));
			    }

			    return finalCode.toString();
			}

			private void handleAppendOption(Path filePath, String symbolText, String finalCode) throws IOException {
			    if (!Files.exists(filePath)) {
			        JOptionPane.showMessageDialog(null, "Symbol table file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
			        return;
			    }

			    List<String> lines = Files.readAllLines(filePath);
			    boolean symbolExists = false;

			    for (String line : lines) {
			        if (line.startsWith(symbolText + " ")) {
			            symbolExists = true;
			            break; // No need to continue checking once a matching line is found
			        }
			    }

			    if (symbolExists) {
			        int replaceChoice = JOptionPane.showConfirmDialog(null, "Do you want to replace the existing content?", "Confirm Replace", JOptionPane.YES_NO_OPTION);
			        if (replaceChoice == JOptionPane.YES_OPTION) {

			            List<String> updatedLines = new ArrayList<>();

			            for (String line : lines) {
			                if (!line.startsWith(symbolText + " ")) {
			                    updatedLines.add(line);
			                }
			            }


			            Files.write(filePath, updatedLines, StandardOpenOption.TRUNCATE_EXISTING);
			            Files.write(filePath, (symbolText + finalCode ).getBytes(), StandardOpenOption.APPEND);
			            JOptionPane.showMessageDialog(null, "Symbol table updated successfully!");
			        } else {
			            JOptionPane.showMessageDialog(null, "No changes made to the symbol table.");
			        }
			    } else {
			        Files.write(filePath, ("\n"+symbolText + finalCode).getBytes(), StandardOpenOption.APPEND);
			        JOptionPane.showMessageDialog(null, "Symbol table content appended successfully!");
			    }
			}

		});
		saveButton.setRequestFocusEnabled(false);
		saveButton.setAlignmentX(10.0f);
		GridBagConstraints gbc_saveButton = new GridBagConstraints();
		gbc_saveButton.gridwidth = 2;
		gbc_saveButton.insets = new Insets(0, 0, 5, 0);
		gbc_saveButton.gridx = 1;
		gbc_saveButton.gridy = 1;
		createPanel.add(saveButton, gbc_saveButton);

		newCodeLabel = new JLabel("Generated Code : ");
		newCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		newCodeLabel.setBorder(new LineBorder(UIManager.getColor("ToggleButton.light")));
		GridBagConstraints gbc_newCodeLabel = new GridBagConstraints();
		gbc_newCodeLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_newCodeLabel.gridwidth = 2;
		gbc_newCodeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_newCodeLabel.gridx = 1;
		gbc_newCodeLabel.gridy = 2;
		createPanel.add(newCodeLabel, gbc_newCodeLabel);

		buttonPatternPanel = new JPanel();
		buttonPatternPanel.setBorder(new MatteBorder(0, 0, 0, 2, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_buttonPatternPanel = new GridBagConstraints();
		gbc_buttonPatternPanel.gridheight = 4;
		gbc_buttonPatternPanel.insets = new Insets(0, 0, 0, 5);
		gbc_buttonPatternPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPatternPanel.gridx = 0;
		gbc_buttonPatternPanel.gridy = 0;
		createPanel.add(buttonPatternPanel, gbc_buttonPatternPanel);

		buttonContainerJPanel = new JPanel();
		buttonContainerJPanel.setBackground(SystemColor.textHighlight);
		// Use GridBagLayout to center the JPanel
		buttonPatternPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc_buttonContainerJPanel = new GridBagConstraints();
		gbc_buttonContainerJPanel.gridx = 0;
		gbc_buttonContainerJPanel.gridy = 0;
		gbc_buttonContainerJPanel.insets = new Insets(10, 10, 10, 10); // Add some padding
		gbc_buttonContainerJPanel.anchor = GridBagConstraints.CENTER; // Center the component
		buttonPatternPanel.add(buttonContainerJPanel, gbc_buttonContainerJPanel);

		removeAllButton = new JButton("Remove All");
		removeAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (removeAllButton.isEnabled()) {
					removeButtons(buttonContainerJPanel);
					createButton.setEnabled(true);
					newCodeLabel.setText("Generated Code : ");
					lowerAndMideleDisable(createPanel, removeAllButton);
				}
			}
		});
		removeAllButton.setRequestFocusEnabled(false);
		GridBagConstraints gbc_removeAllButton = new GridBagConstraints();
		gbc_removeAllButton.insets = new Insets(0, 0, 0, 5);
		gbc_removeAllButton.gridx = 2;
		gbc_removeAllButton.gridy = 2;
		matrix_panel.add(removeAllButton, gbc_removeAllButton);

		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				if (selectedIndex == 1) {
					inputCodeTextArea.setText("");
					updateTextArea(inputCodeTextArea, "asset/input_text.txt", true);
					updateTextArea(inputCodeTextArea, "asset/RAM_code.txt", true);
				} else if (selectedIndex == 2) {
					symbolCodeTextArea.setText("");
					updateTextArea(symbolCodeTextArea, "asset/symbol_code.txt", true);
				}
			}
		});

		lowerAndMideleDisable(createPanel, removeAllButton);
	}

	private void lowerAndMideleDisable(JPanel panel, JButton button) {
		for (java.awt.Component component : panel.getComponents()) {
			component.setEnabled(false);
		}
		button.setEnabled(false);
	};

	private void removeButtons(Container container) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			if (component instanceof JButton) {
				container.remove(component);
			}
		}
		container.revalidate();
		container.repaint();
	}

	private void updateTextArea(JTextArea textArea, String input, boolean isFilePath) {
		try {
			String content;
			if (isFilePath) {
				content = new String(Files.readAllBytes(Paths.get(input)));
			} else {
				content = input;
			}
			textArea.append(content);
			textArea.append("\n------------------------------------------------------------------------------ \n");
		} catch (IOException e) {
			textArea.setText("Failed to read file: " + input);
			e.printStackTrace();
		}
	}

}
