package pt.iscte.poo.sokobanstarter;


import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{
	protected Point2D position;
	protected String nome;
	
	public GameElement(Point2D position, String nome) {
		this.position = position;
		this.nome = nome;
	}
	
	public String getName(){
		return nome;
	}

	public abstract int getLayer();
	

    public Point2D getPosition() {
        return position;
    }
	
	public Point2D setPosition(Point2D position) {
        return this.position = position;
    }
}
