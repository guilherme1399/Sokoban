package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Metodos {
	static GameEngine gameEngine = GameEngine.getInstance();
	
	
	// Criacao da planta do armazem - so' chao 
		public static void createWarehouse(String file) {
			try {
				Scanner scanner= new Scanner(new File("levels/" + file));

	            int y = 0;
	            while (scanner.hasNextLine()) {
	                String line = scanner.nextLine();
	                processLine(line, y);
	                y++;
	            }

	            scanner.close();
	            
	            List<ImageTile> tileList = new ArrayList<ImageTile>(gameEngine.getGameList());
	            gameEngine.gui.addImages(tileList);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }		
		}
		private static void processLine(String line, int y) {
	        for (int x = 0; x < line.length(); x++) {
	            char symbol = line.charAt(x);
	            processSymbol(symbol, x, y);
	        }
	    }
		public static void processSymbol(char symbol, int x, int y) {
	        switch (symbol) {
	            case '#':
	            	gameEngine.getGameList().add(new Parede(new Point2D(x, y)));
	                break;
	            case ' ':
	            	gameEngine.getGameList().add(new Chao(new Point2D(x, y)));
	                break;
	            case 'C':
	            	gameEngine.getGameList().add(new Chao(new Point2D(x, y)));
	            	gameEngine.getGameList().add(new Caixote(new Point2D(x, y)));
	            	gameEngine. setCaixotesCount();
	            	System.out.println("caixote:"+ gameEngine.getCaixotesCount());
	                break;
	            case 'X':
	            	gameEngine.getGameList().add(new Alvo(new Point2D(x, y)));
	            	gameEngine.setAlvos();
	            	System.out.println("alvos:"+gameEngine.getAlvos());
	                break;
	            case 'E':
	            	Empilhadora bobcat = new Empilhadora(new Point2D(x, y));
	                gameEngine.getGameList().add(new Chao(new Point2D(x, y)));
	                gameEngine.getGameList().add(bobcat);
	                gameEngine.setBobcat(bobcat);
	                break;
	            case '=':
	            	gameEngine.getGameList().add(new Vazio(new Point2D(x, y)));
	                break;
	            case 'O':
	            	gameEngine.getGameList().add(new Chao(new Point2D(x, y)));
	            	gameEngine.getGameList().add(new Buraco(new Point2D(x, y)));
	                break;
	            case 'P':
	            	gameEngine.getGameList().add(new Chao(new Point2D(x, y)));
	            	gameEngine.getGameList().add(new Palete(new Point2D(x, y)));
	            	break;
	            case 'T':
	                Point2D destination;
	                Teleporte lastTeleporte = gameEngine.getLastTeleporte();
	            	if (lastTeleporte != null) {
	                    destination = lastTeleporte.getPosition();
	                } else {
	                    destination = new Point2D(x, y);
	                }
	                Teleporte teleporte = new Teleporte(new Point2D(x, y), destination);
	                gameEngine.getGameList().add(teleporte);
	                
	                gameEngine.setLastTeleporte(teleporte);
	                
	            	if (lastTeleporte != null) {
	                    lastTeleporte.setDestination(new Point2D(x, y));
	                    teleporte.setDestination(lastTeleporte.getPosition());
	                }
	                
	                // Atualiza o ultimo teletransportador para o atual
	                lastTeleporte = teleporte;
	            	break;
	            case '%':
	            	gameEngine.getGameList().add(new Chao(new Point2D(x, y)));
	            	gameEngine.getGameList().add(new ParedeRachada(new Point2D(x, y)));
	            	break;
	            case 'B':
	            	gameEngine.getGameList().add(new Chao(new Point2D(x, y)));
	            	gameEngine.getGameList().add(new Bateria(new Point2D(x, y)));
	            	break;
	            case 'M':
	            	gameEngine.getGameList().add(new Chao(new Point2D(x, y)));
	            	gameEngine.getGameList().add(new Martelo(new Point2D(x, y)));
	            	break;
	            case 'S':
	            	gameEngine.getGameList().add(new Chao(new Point2D(x, y)));
	            	gameEngine.getGameList().add(new BigStone(new Point2D(x, y)));
	            	break;
	            case 's':
	            	gameEngine.getGameList().add(new Chao(new Point2D(x, y)));
	            	gameEngine.getGameList().add(new SmallStone(new Point2D(x, y)));
	            	break;
	            case 'p':
	            	gameEngine.getGameList().add(new Chao(new Point2D(x, y)));
	            	gameEngine.getGameList().add(new Picareta(new Point2D(x, y)));
	            	break;
	        }
	    }
	
	public static boolean isTileSolid(Point2D position) {
	    for (GameElement element : gameEngine.getGameList()) {
	        if (element.getPosition().equals(position) && element instanceof SolidObject)
	            return true;
	    }
	    return false;
	}

	public static GameElement getTransitionAt(Point2D position) {
        for (GameElement element : gameEngine.getGameList()) {
            if (element.getPosition().equals(position) && element instanceof Transition) {
            	return element;
            }
        }
        return null;
    }
	public static GameElement getTargetsAt(Point2D position) {
        for (GameElement element : gameEngine.getGameList()) {
            if (element.getPosition().equals(position) && element instanceof Targets) {
            	return element;
            }
        }
        return null;
    }
	public static GameElement getTargetedAt(Point2D position) {
        for (GameElement element : gameEngine.getGameList()) {
            if (element.getPosition().equals(position) && element instanceof Targeted) {
            	return element;
            }
        }
        return null;
    }
	public static GameElement getInteractablesAt(Point2D position, InteractableType type) {
        for (GameElement element : gameEngine.getGameList()) {
            if (element.getPosition().equals(position) && isOfType(element, type)) {
            	return element;
            }
        }
        return null;
    }
    private static boolean isOfType(GameElement element, InteractableType type) {
        switch (type) {
            case MOVEABLE:
                return element instanceof Moveable;
            case DESTROYABLE:
                return element instanceof Destroyable;
            case PICKABLE:
                return element instanceof Pickable;   
            default:
                return false;
        }
    }
    
    public static GameElement getRemovablesAt(Point2D position, RemovableType type) {
        for (GameElement element : gameEngine.getGameList()) {
            if (element.getPosition().equals(position) && isType(element, type)) {
                return element;
            }
        }
        return null;
    }
    private static boolean isType(GameElement element, RemovableType type) {
        switch (type) {
            case RECHARGABLE:
                return element instanceof Rechargable;
            case DESTROYABLE:
                return element instanceof Destroyable;
            case PICKABLE:
                return element instanceof Pickable;
            case POWERREMOVABLE:
            	return element instanceof PowerRemovable;
            case FALL:
            	return element instanceof Fall;
            case HOLE:
            	return element instanceof Hole;	
            default:
                return false;
        }
    }


    public static List<GameElement> getInteractablesAt(Point2D position) {
        List<GameElement> interactables = new ArrayList<>();
        for (GameElement element : gameEngine.getGameList()) {
            if (element.getPosition().equals(position) && element instanceof Interactable) {
                interactables.add(element);
            }
        }
        if (interactables.isEmpty()) {
            return null;
        } else {
            return interactables;
        }
    }
    public static List<GameElement> getRemovablesAt(Point2D position) {
        List<GameElement> removables = new ArrayList<>();
        for (GameElement element : gameEngine.getGameList()) {
            if (element.getPosition().equals(position) && element instanceof Removable) {
            	removables.add(element);
            }
        }
        if (removables.isEmpty()) {
            return null;
        } else {
            return removables;
        }
    }
    
    public static void remove(Point2D position) {
        List<ImageTile> lst = new ArrayList<>(gameEngine.getGameList());
        for (ImageTile element : lst) {
            if (element.getPosition().equals(position) && element instanceof Removable) {
            	gameEngine.getGameList().remove(element);
                gameEngine.gui.removeImage(element);
            }
        }
        gameEngine.gui.update();
    }
    
    public static void checkTargets() {
        int alvosCobertos = 0; // Contador para alvos cobertos

        for (GameElement element : gameEngine.getGameList()) {
            if (element instanceof Targets) {
                Targets target = (Targets) element;
                if (target.isCovered(gameEngine.getGameList()))
                    alvosCobertos++; // Incrementa a contagem se o alvo estiver coberto
            }
        }

        // Verifica se todos os alvos estão cobertos
        if (alvosCobertos == gameEngine.getAlvos()) {
            salvarPontuacao();
            System.out.println("Todos os alvos estao cobertos!");
            goToNextLevel();
        }

        if (shouldEndGame()) {
            gameEngine.gui.setMessage("Perdeste! Menos caixotes que alvos.");
            sairDoJogo();
        }
    }
    
    private static boolean shouldEndGame() {
        return gameEngine.getCaixotesCount() < gameEngine.getAlvos();
    }
    
    public static void goToNextLevel() {
    	gameEngine.nullAlvos();
        gameEngine.nullCaixotesCount();
        String nextLevelFileName = "level" + gameEngine.addLevel() +   ".txt";
        File nextLevelFile = new File("levels/" + nextLevelFileName);

        if (nextLevelFile.exists()) {
        	System.out.println("nivel" + gameEngine.getLevel());
            clearLevel(); // Metodo para limpar o nivel atual
            gameEngine.getGameList().clear();
            gameEngine.gui.setMessage("Passaste para o nivel: " + gameEngine.getLevel());
            
            Metodos.createWarehouse(nextLevelFileName);
            gameEngine.gui.update();
        } else {
        	gameEngine.gui.setMessage("PARABENS");
            Metodos.sairDoJogo();
            
        }
    }

    // Metodo clearLevel() para remover imagens do nivel atual da GUI
    private static void clearLevel() {
        for (ImageTile element : gameEngine.getGameList()) {
            gameEngine.gui.removeImage(element);
        }
    }
    
    
    public static void restartLevel(){
    	gameEngine.removeLevel();
        goToNextLevel();
    }
    
    public static void sairDoJogo() {
    	gameEngine.gui.setMessage("A sair do jogo ...");
        System.exit(0); 
    }
    
    public static void salvarPontuacao() {
        Pontuacao.salvarPontuacao(gameEngine.getLevel(), gameEngine.getBobcat().getMovimentos(), gameEngine.getNomeJogador());
        List<Pontuacao> topPontuacoes = Pontuacao.top3("Pontuacoes/Pontuacoes_" + gameEngine.getLevel() + ".txt");
        Pontuacao.atualizarArquivoPontuacoes("Pontuacoes/Pontuacoes_" + gameEngine.getLevel() + ".txt", topPontuacoes);
    }
}
