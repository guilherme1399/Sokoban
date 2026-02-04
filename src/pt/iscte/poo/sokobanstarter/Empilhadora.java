package pt.iscte.poo.sokobanstarter;


import java.util.List;


import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement implements Fall, Rechargable{

    private String imageName;
    private int power;
    private boolean Poder;
    private int moves;
    
    public Empilhadora(Point2D initialPosition){
    	super(initialPosition, "Empilhadora");
        imageName = "Empilhadora_D";
        power = 100;
        Poder = false;
        moves = 0;
    }
    
    @Override
    public String getName() {
        return imageName;
    }

    @Override
    public int getLayer() {
        return 2;
    }
    public int getPower(){
    	return power;
    }
    public int getMovimentos(){
    	return moves;
    }
    public void move(int key) {
        Direction[] possibleDirections = Direction.values();
        Direction movement = possibleDirections[key];
        
        Point2D newPosition = getPosition().plus(movement.asVector());
        
        if (power == 0 ) {
        	Metodos.sairDoJogo();
        }
        if (newPosition.getX() >= 0 && newPosition.getX() < 10 && newPosition.getY() >= 0 && newPosition.getY() < 10) {
            if (Metodos.isTileSolid(newPosition)) {
                return;
            }
            	
        	Moveable movivel = (Moveable) Metodos.getInteractablesAt(newPosition, InteractableType.MOVEABLE);
            if (movivel != null) {
            	if(movivel.push(movement)== false){
                	return;
            	}else {
					power --;
				}
            }
            List<GameElement> interactables = Metodos.getInteractablesAt(newPosition);
            if (interactables != null) {
                for (GameElement element : interactables) {
                    if (element instanceof Interactable) {
                    	if (element instanceof Pickable) {
							Poder = true;
						}
                    	if(element instanceof Destroyable){
                    		powerOn(50);
                    	}
                    	Interactable interactable = (Interactable) element;
                        interactable.interact();
                    }
                }
                
            }
            List<GameElement> powerRemovables = Metodos.getRemovablesAt(newPosition);
            if (powerRemovables != null) {
                for (GameElement element : powerRemovables) {
                    if (element instanceof PowerRemovable) {
                    	PowerRemovable p = (PowerRemovable) element;
                    	p.interact(Poder);
                    	if (!Poder) {
                            return; // Nao se move se for uma ParedeRachada e o poder do martelo não estiver ativado
                        }
                    }
                }
                
            }
            
            
            Hole h = (Hole) Metodos.getRemovablesAt(newPosition, RemovableType.HOLE);
            if (h != null) {
                fall();
            }
            
            Transition t = (Transition) Metodos.getTransitionAt(newPosition);
            if (t != null) {
                newPosition = t.handleTeleport(newPosition, movement, TransitionType.PLAYER);
            }
            
            setPosition(newPosition);
            System.out.println(newPosition);
            updateImage(movement);
            power--;
            moves ++;			
        }
    }
    
	public void updateImage(Direction direction) {
        // Atualizar o nome da imagem com base na direcao
        switch (direction) {
            case RIGHT:
                imageName = "Empilhadora_R";
                break;
            case LEFT:
                imageName = "Empilhadora_L";
                break;
            case UP:
                imageName = "Empilhadora_U"; 
                break;
            case DOWN:
                imageName = "Empilhadora_D";
                break;
		default:
			break;
        }
    }

	@Override
	public RemovableType getRemovableType() {
		return RemovableType.FALL;
	}

	@Override
	public void fall() {
		Metodos.sairDoJogo();
	}



	@Override
	public int powerOn(int p) {
		System.out.println(p);
		power += p;
		return power;
	}

	@Override
	public void destroy(){
		fall();
	}

}