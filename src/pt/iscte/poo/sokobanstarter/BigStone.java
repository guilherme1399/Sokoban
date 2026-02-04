package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class BigStone extends MoveableElement{

	public BigStone(Point2D position) {
		super(position, "BigStone");
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
	public InteractableType getInteractableType() {
		return InteractableType.MOVEABLE;
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
        }
        
	    	        
        setPosition(newPosition);
        return true;
	}
}
