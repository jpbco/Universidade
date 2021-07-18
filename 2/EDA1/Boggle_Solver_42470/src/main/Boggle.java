package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import auxiliar.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Boggle {

	private Board board;
	private DoubleHashTable tabela;
	private JFrame frame;
	private JPanel panel;
	private JTextField textField;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JButton button;

	public void createUI() {

		frame = new JFrame();
		panel = new JPanel();
		textField = new JTextField(20);
		textArea = new JTextArea();
		button = new JButton("Solve");

		textArea.setColumns(30);
		textArea.setLineWrap(true);
		textArea.setRows(20);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setText("Input a sequence of lower case characters.\nFor example : seldoumoometinky");

		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panel.add(textField);
		panel.add(scrollPane);
		panel.add(button);

		frame.setTitle("Boggle");
		frame.setSize(400, 500);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(panel);

	}

	public Boggle() {

		createUI();

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String boardchars = textField.getText();
				int sqrt = (int) Math.sqrt(boardchars.length());
				if (boardchars.length() < 3) {
					textArea.setText(null);
					textArea.setLineWrap(true);
					textArea.append("Please create a bigger Boggle board!" + "\n"
							+ "The number of characters must be a perfect square number.\n E.g = seldoumoometinky");

				} else if (sqrt * sqrt == boardchars.length()) {
					textArea.setText(null);
					textArea.setLineWrap(true);
					String results = new Boggle(boardchars).solve();
					if (!results.strip().equals("")) {
						textArea.append(results);
					} else {
						textArea.append("No results found.");
					}

				} else {

					textArea.setText(null);
					textArea.setLineWrap(true);
					textArea.append(
							"The number of characters must be a perfect square number.\n E.g = seldoumoometinky");

				}

			}
		});

	}

	public Boggle(String input) {
		importDictionary();
		createBoard(input);

	}

	private void createBoard(String input) {
		MyArrayList<Node> nodes = new MyArrayList<Node>(input.length());

		int dimemsion = (int) Math.sqrt(input.length());

		for (int i = 0, eachLetter = input.length(); i < eachLetter; i++) {
			nodes.add(createNode(input.charAt(i), new Position(i / dimemsion, i % dimemsion), dimemsion));
		}

		board = new Board(nodes);
	}

	private Node createNode(char letter, Position pos, int dim) {
		return new Node(letter, pos, getNeighbours(pos, dim));
	}

	private MyArrayList<Position> getNeighbours(Position pos, int dim) {

		int[] range = { -1, 0, 1 };

		MyArrayList<Position> neighs = new MyArrayList<Position>();

		for (int row : range) {
			for (int col : range) {
				Position neighbour = new Position(pos.getRow() + row, pos.getCol() + col);

				if (!pos.equals(neighbour) && isvalidPosition(neighbour, dim)) {
					neighs.add(neighbour);
				}
			}
		}
		return neighs;
	}

	private boolean isvalidPosition(Position pos, int dim) {

		return (pos.getRow() >= 0 && pos.getRow() < dim) && (pos.getCol() >= 0 && pos.getCol() < dim);
	}

	public void importDictionary() {
		String word;
		tabela = new DoubleHashTable(100003);
		try {
			URL url = getClass().getResource("all_words.txt");
			File f = new File(url.getPath());
			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

			while ((word = bufferedReader.readLine()) != null) {

				tabela.insert(word);
			}

			bufferedReader.close();
		}

		catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	private Node positionToNode(Position pos, Board board) {
		for (Node node : board.getNodes()) {
			if (pos.equals(node.getPosition()))
				return node;
		}

		return null;
	}

	public static <E> MyArrayList<E> removeDuplicates(MyArrayList<E> list) {

		MyArrayList<E> newList = new MyArrayList<E>();

		for (E element : list) {

			if (!newList.contains(element)) {

				newList.add(element);
			}
		}

		return newList;
	}

	public String solve() {

		MyArrayList<String> foundInTable = new MyArrayList<String>();

		for (Node Node : board.getNodes()) {

			MyArrayList<Position> visited = new MyArrayList<Position>();
			visited.add(Node.getPosition());
			backtracking("" + Node.getLetter(), Node, board, visited, foundInTable);
		}
		MyArrayList<String> unico = removeDuplicates(foundInTable);

		String storing = " ";
		int count = 1;
		for (String foundWord : unico) {

			storing += count + ": " + foundWord + " ";
			count++;
		}

		return storing;

	}

	private void backtracking(String cWord, Node cNode, Board board, MyArrayList<Position> visited,
			MyArrayList<String> foundInTable) {

		if (tabela.find(cWord) != null) {
			foundInTable.add(cWord);
		}

		MyArrayList<Node> neighbours = new MyArrayList<Node>();

		for (Position pos : cNode.getNeighbours()) {
			neighbours.add(positionToNode(pos, board));
		}

		for (Node neighbour : neighbours) {
			if (visited.contains(neighbour.getPosition()))
				continue;

			visited.add(neighbour.getPosition());
			backtracking(cWord + neighbour.getLetter(), neighbour, board, visited, foundInTable);
			visited.remove(neighbour.getPosition());

		}
	}

	public static void main(String[] args) {
		new Boggle();

	}
}
