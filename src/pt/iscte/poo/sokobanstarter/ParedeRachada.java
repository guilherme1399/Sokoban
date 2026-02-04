package pt.iscte.poo.sokobanstarter;


import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement implements PowerRemovable{

	public ParedeRachada(Point2D position){
		super(position, "ParedeRachada");
	}

	@Override
	public int getLayer() {
		return 1;
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
	public void interact(boolean b) {
		if (b){
			destroy();
		}
	}
}
