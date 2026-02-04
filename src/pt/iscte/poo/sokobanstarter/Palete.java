package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Palete extends MoveableElement {
	
	public Palete(Point2D position){
		super(position, "Palete");
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
	@Override
	public boolean push(Direction direction) {
	    Point2D newPosition = getPosition().plus(direction.asVector());

	    if (Metodos.isTileSolid(newPosition)) {
            return false; 
        }

        if (!checkInteractables(newPosition)) {
            return false;
        }
        if (Metodos.getRemovablesAt(newPosition, RemovableType.POWERREMOVABLE) != null){
        	return false;
        }

    
        Hole h = (Hole) Metodos.getRemovablesAt(newPosition, RemovableType.HOLE);
        if (h != null) {
        	h.destroy();
            destroy();
            return true;
        }
        Transition t = (Transition) Metodos.getTransitionAt(newPosition);
        if (t != null) {
            t.handleTeleport(newPosition, direction, TransitionType.MOVEABLE);
            if (!checkInteractables(newPosition)) {
                return false;
            }
            if (Metodos.isTileSolid(newPosition)) {
                return false; 
            }
        }
	    
	        
        setPosition(newPosition);
        return true; 
        
	    
	}

	@Override
	public RemovableType getRemovableType() {
		return RemovableType.DESTROYABLE;
	}

	@Override
	public InteractableType getInteractableType() {
		return InteractableType.MOVEABLE;
	}
	
}