package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Picareta extends GameElement implements Pickable {
	
	private boolean PoderPicareta = false;
	public Picareta(Point2D position) {
		super(position, "Picareta");
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public RemovableType getRemovableType() {
		return RemovableType.DESTROYABLE;
	}

	@Override
	public void destroy() {
		Metodos.remove(position);		
	}

	@Override
	public InteractableType getInteractableType() {
		return InteractableType.PICKABLE;
	}

	@Override
	public void interact() {
		PoderPicareta = powerOn(true);
		
	}

	@Override
	public boolean powerOn(boolean p) {
        destroy();
        PoderPicareta = true; // Define o PoderMartelo como true no Martelo
        return PoderPicareta;
	}

}
