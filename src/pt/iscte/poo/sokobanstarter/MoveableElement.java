package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class MoveableElement extends GameElement implements Moveable, Destroyable, Fall {

    public MoveableElement(Point2D position, String name) {
        super(position, name);
    }
    
    public boolean checkInteractables( Point2D position) {
		List<GameElement> elements = Metodos.getInteractablesAt(position);
		return elements == null || elements.isEmpty();
	}
    
    public Point2D getNewPosition(Direction d){
    	return getPosition().plus(d.asVector());

    }
    @Override
    public void destroy() {
        Metodos.remove(getPosition());
    }
    
    @Override
    public void interact() {
        destroy();
    }
    
    @Override
	public void fall() {
		Metodos.remove(position);
		GameEngine.getInstance().setCaixotes();
	}
}