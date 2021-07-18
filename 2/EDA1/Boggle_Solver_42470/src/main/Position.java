package main;

class Position {

	private final int row, col;

	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		else if (obj == null) {
			return false;
		}

		else if (obj instanceof Position) {
			Position pos = (Position) obj;

			if (pos.getRow() == this.row && pos.getCol() == this.col) {
				return true;
			}

		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + row + "," + col + ")";
	}
}
