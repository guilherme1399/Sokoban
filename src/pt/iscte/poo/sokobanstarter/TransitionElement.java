package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class TransitionElement extends GameElement implements Transition{

	private Point2D destination;

	public TransitionElement(Point2D position, String nome) {
		super(position, nome);
	}
	
	@Override
	public Point2D handleTeleport(Point2D newPosition, Direction movement, TransitionType transitionType) {
		Transition t = (Transition) Metodos.getTransitionAt(newPosition);
		if (t != null ){
			switch (transitionType) {
				case PLAYER:
					newPosition = t.getDestination();
					break;
				case MOVEABLE:
					newPosition = t.getDestination().plus(movement.asVector());
					break;
		
				default:
					break;
			}
		}
	    return newPosition;
	}

	public Point2D getDestination() {
        return destination;
    }
	
	@Override
	public int getLayer() {
		return 1;
	}
	
	public void setDestination(Point2D destination) {
        this.destination = destination;
    }
}
