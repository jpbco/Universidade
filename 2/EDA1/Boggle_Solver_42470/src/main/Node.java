package main;

import auxiliar.MyArrayList;

public class Node {

	private final MyArrayList<Position> neighbours;
	private final char letter;
	private final Position position;

	public Node(char letter, Position position, MyArrayList<Position> neighbours) {
		this.letter = letter;
		this.position = position;
		this.neighbours = neighbours;
	}

	public char getLetter() {
		return letter;
	}

	public Position getPosition() {
		return position;
	}

	public MyArrayList<Position> getNeighbours() {
		return neighbours;
	}

}