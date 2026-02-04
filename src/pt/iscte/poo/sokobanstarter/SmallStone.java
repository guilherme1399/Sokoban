package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class SmallStone extends GameElement implements PowerRemovable {

	public SmallStone(Point2D position) {
		super(position, "SmallStone");
	}

	@Override
	public RemovableType getRemovableType() {
		return RemovableType.POWERREMOVABLE;
	}

	@Override
	public void destroy() {
		Metodos.remove(position);
	}


	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public void interact(boolean b) {
		if(b){
			destroy();
		}
	}

}
