package nix.baseeducation.seawar.model;

public class Cell {
	
	private boolean isVisited;
	private boolean isEmpty;
	private boolean isVisible;
	
	public Cell()
	{
		this.isEmpty = true;
		this.isVisited = false;
		this.isVisible = false;
	}
	
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	
	
}
