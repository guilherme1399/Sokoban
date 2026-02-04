package pt.iscte.poo.sokobanstarter;


import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement implements Hole{
	
	
	public Buraco(Point2D position){
		super(position, "Buraco");
	}
	
	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public RemovableType getRemovableType() {
		return RemovableType.HOLE;
	}

	@Override
	public void destroy() {
		Metodos.remove(position);
	}


	@Override
	public void drop() {
		destroy();
	}

}
