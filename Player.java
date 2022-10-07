import Extras.SimpleLinkedList;
import Extras.Stack;

/**
 * Clase que modela un jugador de CamelUp
 * @author Rodrigo Ortega 318036104
 * @version 1.0 Oct 6 2022
 * @since ED 2023-1
 */

public class Player {
    
    /**
     * Nombre del jugador
     */
    private String name;


    /**
     * Dinero del jugador
     */
    private int money;
    
    /**
     * Indica si el jugador posee un modificador
     */
    private boolean hasModifier = true;

    /**
     * Modificador de tablero
     */
    public boolean modifier;

    /**
     * Lista de las cartas de apuesta que posee el jugador
     */
    public SimpleLinkedList<Camel.betCard> betCardList = new SimpleLinkedList<>();

    /**
     * Lista de las cartas de camello que posee el jugador
     */
    public SimpleLinkedList<CamelCard> camelCardList = new SimpleLinkedList<>();

    /**
     * Constructor de un jugador
     * @param name nombre del jugador
     * @param money dinero con el que inicia el jugador
     */
    public Player(String name,int money){
        this.name = name;
        this.money = money;
    }
    
    /**
     * Clase interna de Jugador que representa una carta de camello
     */
    public class CamelCard{
        /**
         * Nombre del dueno de la carta
         */
        private String owner = getName();
        
        /**
         * Nombre del camello de la carta
         */
        public String camel;
    

        /**
         * Constructor de la calse CamelCard
         * @param camel nombre del camello
         */
        public CamelCard(String camel){
            owner = getOwner();
            this.camel = camel;
        }

        /**
         * Metodo que regresa el nombre del dueno de la carta
         * @return nombre del dueno de la carta
         */
        public String getOwner(){
            return this.owner;
        }        
    }

    /**
     * Regresa la cantidad de dinero del Jugador
     * @return la cantidad de dinero del jugador
     */
    public int getMoney(){
        return this.money;
    }
    
    /**
     * Cambia la cantidad de dinero del jugador
     * @param money nueva cantidad de dinero
     */
    public void setMoney(int money){
        this.money = money;
    }

    /**
     * Regresa el nombre del jugador
     * @return el nombre del jugador
     */
    public String getName(){
        return this.name;
    }

    /**
     * Cambia el nombre del jugador
     * @param name nuevo nombre del jugador
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Regresa si el jugador tiene el modificador
     * @return true si el jugador tiene el modificador
     */
    public boolean getHasModifier(){
        return this.hasModifier;
    }

    /**
     * Modifica hasModifier
     * @param hasModifier nuevo valor de hasModifier
     */
    public void setHasModifier(boolean hasModifier){
        this.hasModifier = hasModifier;
    }
    
    /**
     * Genera cartas de camello y las coloca en una lista 
     */
    public void fillCamelCardList(){
        CamelCard camelCard1 = new CamelCard("Camel 0");
        CamelCard camelCard2 = new CamelCard("Camel 1");
        CamelCard camelCard3 = new CamelCard("Camel 2");
        CamelCard camelCard4 = new CamelCard("Camel 3");
        CamelCard camelCard5 = new CamelCard("Camel 4");
        camelCardList.add(0, camelCard1);
        camelCardList.add(0, camelCard2);
        camelCardList.add(0, camelCard3);
        camelCardList.add(0, camelCard4);
        camelCardList.add(0, camelCard5);
    }
    
    public String toString(){
        return this.name;
    }

    /**
     * Toma una carta de la pila de cartas de apuesta
     * @param camel el camello por el que se apostara
     */
    public void betWinRound(Camel camel){
        betCardList.add(0, camel.camelStack.pop());
    }

    /**
     * Coloca una carta de camello en la pila de camellos ganadores
     * @param stack pila de camellos ganadores
     */
    public void betWin(Stack<CamelCard> stack){
        stack.push(camelCardList.get(0));
        camelCardList.remove(0);
    }
    
    /**
     * Coloca una carta de camello en la pila de camellos perdedores
     * @param stack pila de camellos perdedores
     */
    public void betLose(Stack<CamelCard> stack){
        stack.push(camelCardList.get(0));
        camelCardList.remove(0);
    }
    
    /**
     * Coloca un modificador en el tablero
     * @param board el tablero
     * @param modifier true si quieres que avance, false si quieres que retroceda
     */
    public void placeMod(Tile[] board, boolean modifier){
        int i = 0;
        if(!board[i].hasModifier || !board[i - 1].hasModifier || !board[i + 1].hasModifier)
            this.hasModifier = false;   
            board[i].setModifier(modifier);
            board[i].hasModifier = true;     
    }
    
    /**
     * Gira el dado de un camello aleatorio que no se haya movido
     */
    public void rollDie(Camel[] camelArr, Tile[] board){
        int i = 0;
        camelArr[i].move(camelArr[i], board);
    }

}
