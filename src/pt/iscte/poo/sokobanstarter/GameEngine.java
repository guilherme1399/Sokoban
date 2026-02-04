package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	
	
	private List<GameElement> gameList;
	private Empilhadora bobcat;	        // Referencia para a empilhadora
	private int caixotesCount = 0;
	private int alvos = 0;
	private int level = 0;
	private Teleporte lastTeleporte = null; // Armazenar a referência para o último teleporte criado
	private Portal lastPortal = null;
	private String nomeJogador;
	
	
	public List<GameElement> getGameList(){
		return gameList;
	}
	public int getCaixotesCount(){
		return caixotesCount;
	}
	public int setCaixotesCount(){
		return ++caixotesCount;
	}
	public int nullCaixotesCount() {
		return caixotesCount = 0;
	}
	public int getAlvos(){
		return alvos;
	}
	public int setAlvos(){
		return ++alvos;
	}
	public int nullAlvos() {
		return alvos = 0;
	}
	public Empilhadora getBobcat() {
	    return bobcat;
	}
	public void setBobcat(Empilhadora empilhadora) {
	    this.bobcat = empilhadora;
	}
	public Teleporte getLastTeleporte() {
	    return lastTeleporte;
	}
	public Portal getLastPortal() {
	    return lastPortal;
	}
	public void setLastTeleporte(Teleporte lastTeleporte) {
	    this.lastTeleporte = lastTeleporte;
	}
	public int getLevel(){
		return level;
	}
	public String getNomeJogador(){
		return nomeJogador;
	}
    public int setCaixotes(){
    	return --caixotesCount;
    }
    public int addLevel(){
    	return ++level;
    }
    public int removeLevel(){
    	return --level;
    }
    
    
    
    
	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
		gameList = new ArrayList<>();
	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	// Inicio
	public void start() {
		// Setup inicial da janela que faz a interface com o utilizador
		// algumas coisas poderiam ser feitas no main, mas estes passos tem sempre que ser feitos!
		
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI
		
	     // Inicializa a lista de arquivos dos níveis
		nomeJogador = gui.askUser("Digite o nome do Jogador: ");
		try{	
		while(nomeJogador.equals("")){
			gui.setMessage("Nome Invalido");
			nomeJogador = gui.askUser("Digite o nome do Jogador: ");
		}
		}catch(NullPointerException e ){
			System.exit(0);
		}
		
		// Criar o cenario de jogo
		Metodos.createWarehouse("level" + level + ".txt");      // criar o armazem
		gui.update();
		
		// Escrever uma mensagem na StatusBar
		 gui.setStatusMessage("Sokoban jogado por " + getNomeJogador() + " Energia:" + getBobcat().getPower() 
		    		+ " Pontuacao:" + getBobcat().getMovimentos() + " Nivel:" + getLevel());	}

	@Override
	public void update(Observed source) {
		int key = gui.keyPressed();    // obtem o codigo da tecla pressionada
	    Empilhadora bobcat = getBobcat();
	    
	    if (key == KeyEvent.VK_RIGHT) {
	        key = 2;
	        bobcat.move(key);
	        bobcat.updateImage(Direction.RIGHT);
	    } else if (key == KeyEvent.VK_LEFT) {
	        key = 0;
	        bobcat.move(key);
	        bobcat.updateImage(Direction.LEFT);
	    } else if (key == KeyEvent.VK_UP) {
	        key = 1;
	        bobcat.move(key);
	        bobcat.updateImage(Direction.UP);
	    } else if (key == KeyEvent.VK_DOWN) {
	        key = 3;
	        bobcat.move(key);
	        bobcat.updateImage(Direction.DOWN);
	    } else if (key == KeyEvent.VK_SPACE){
	    	Metodos.restartLevel();
	    }
	    
	    gui.update();                  // redesenha a lista de ImageTiles na GUI,
	    Metodos.checkTargets();	
	    // tendo em conta as novas posicoes dos objetos
	    gui.setStatusMessage("Sokoban jogado por " + getNomeJogador() + " Energia:" + getBobcat().getPower() 
	    		+ " Pontuacao:" + getBobcat().getMovimentos() + " Nivel:" + getLevel());
	}
}