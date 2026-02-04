package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends MoveableElement implements Targeted, Fall{
	
	private String currentImageName;
	private boolean isOnTarget;
	
	private static final String DEFAULT_IMAGE = "Caixote";
	private static final String TARGET_IMAGE = "CaixoteAlvo";
	
	public Caixote(Point2D position){
		super(position,DEFAULT_IMAGE);
		currentImageName = DEFAULT_IMAGE;
		isOnTarget = false;
	}
	
	@Override
	public String getName() {
		return currentImageName;
	}
	
	
	
	 public void updateImage(Point2D position) {
		 Targets t = (Targets) Metodos.getTargetsAt(position);
		 isOnTarget = (t != null && t.getPosition().equals(position));
		 if (isOnTarget) {
			 currentImageName = TARGET_IMAGE;
		} else {
			currentImageName = DEFAULT_IMAGE;
		}
	        // Atualizar o nome da imagem com base na direcao
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
	        
	        Hole hole = (Hole) Metodos.getRemovablesAt(newPosition, RemovableType.HOLE);
	        if (hole != null) {
				fall();
			}
	        
	        Transition t = (Transition) Metodos.getTransitionAt(newPosition);
	        if (t != null) {
	            newPosition = t.handleTeleport(newPosition, direction, TransitionType.MOVEABLE);
	            if (!checkInteractables(newPosition)) {
	                return false;
	            }
	            if (Metodos.isTileSolid(newPosition)) {
		            return false; 
		        }

	        }
	        
		    updateImage(newPosition);
	        setPosition(newPosition);
	        return true; // Permite o movimento se a próxima posicao estiver vazia
		}

	@Override
	public int getLayer() {
		return 2;
	}
	
	
	@Override
	public RemovableType getRemovableType() {
		return RemovableType.FALL;
	}


	@Override
	public InteractableType getInteractableType() {
		return InteractableType.MOVEABLE;
	}

	@Override
	public void markAsOnTarget() {
		isOnTarget = true;
	}	
}