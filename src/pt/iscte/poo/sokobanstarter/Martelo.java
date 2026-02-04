package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement implements Pickable {
	
    private boolean PoderMartelo = false;
	public Martelo(Point2D position){
		super(position, "Martelo");
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
    public boolean powerOn(boolean p) {
        destroy();
        PoderMartelo = true; // Define o PoderMartelo como true no Martelo
        return PoderMartelo;
    }
	
	@Override
	public RemovableType getRemovableType() {
		return RemovableType.PICKABLE;
	}

	@Override
	public InteractableType getInteractableType() {
		return InteractableType.PICKABLE;
	}

	@Override
	public void interact() {
		PoderMartelo = powerOn(true);
	}

	@Override
	public void destroy() {
		Metodos.remove(position);		
	}
}
