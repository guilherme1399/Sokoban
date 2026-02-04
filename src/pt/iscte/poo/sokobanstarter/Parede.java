package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Parede extends GameElement implements SolidObject{

	
	public Parede(Point2D position) {
		super(position, "Parede");
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
	@Override
    public boolean isSolid() {
        return true; // Indica que a parede é um elemento sólido
    }
}
