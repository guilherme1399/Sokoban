package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement implements Destroyable{
	
	public Bateria(Point2D position){
		super(position, "Bateria");
	}

	@Override
	public int getLayer() {
		return 2;
	}
	

	@Override
	public RemovableType getRemovableType() {
		return RemovableType.RECHARGABLE;
	}


	@Override
	public void destroy() {
		Metodos.remove(position);
	}

	@Override
	public InteractableType getInteractableType() {
		return InteractableType.DESTROYABLE;
	}

	@Override
	public void interact() {
		destroy();
	}
}
