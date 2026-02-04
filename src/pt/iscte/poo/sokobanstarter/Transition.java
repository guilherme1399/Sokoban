package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public interface Transition {
	//Point2D getDestination();
	Point2D handleTeleport(Point2D newPosition, Direction movement, TransitionType transitionType);

	Point2D getDestination();
}
