package pt.iscte.poo.sokobanstarter;

import java.util.List;


import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement implements Targets {
		
	public Alvo(Point2D position) {
		super(position, "Alvo");
	}
	
	@Override
	public Point2D getPosition(){
		return position;
	}
	@Override
	public int getLayer() {
		return 1;
	}

	@Override
    public boolean isCovered(List<GameElement> gameList) {
        for (GameElement element : gameList) {
            if (element instanceof Targeted && element.getPosition().equals(getPosition())) {
                return true;
            }
        }
        return false;
    }

}
