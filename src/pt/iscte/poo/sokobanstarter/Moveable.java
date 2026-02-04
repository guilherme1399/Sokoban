package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;

public interface Moveable extends Interactable{
	public boolean push(Direction direction);
}
