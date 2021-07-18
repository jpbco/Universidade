package main;

import auxiliar.MyArrayList;

public class Board {
	//Attributes
	private final MyArrayList<Node> nodes;

	// Constructor
	public Board(MyArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	// Getters
	public MyArrayList<Node> getNodes() {
		return nodes;
	}
}