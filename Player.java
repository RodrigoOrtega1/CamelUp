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
     * Lista de las cartas de apuesta que posee el jugador
     */
    public SimpleLinkedList<Camel.betCard> betCardList = new SimpleLinkedList<>();

    /**
     * Lista de las cartas de camello que posee el jugador
     */
    public SimpleLinkedList<CamelCard> camelCardList = new SimpleLinkedList<>();

    public SimpleLinkedList<Modifier> modifierL = new SimpleLinkedList<>();

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

        public String toString(){
            return this.camel;
        }
    }

    public class Modifier{
        private boolean value;
        private Player owner;

        public Modifier(Player owner){
            this.owner = owner;
        }

        public Player getOwner(){
            return owner;
        }
        
        public boolean getValue(){
            return value;
        }

        public void setValue(boolean value){
            this.value = value;
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
     * Genera cartas de camello y las coloca en una lista 
     */
    public void fillCamelCardList(){
        CamelCard camelCard1 = new CamelCard("Red");
        CamelCard camelCard2 = new CamelCard("Green");
        CamelCard camelCard3 = new CamelCard("Yellow");
        CamelCard camelCard4 = new CamelCard("Purple");
        CamelCard camelCard5 = new CamelCard("Blue");
        camelCardList.add(0, camelCard1);
        camelCardList.add(0, camelCard2);
        camelCardList.add(0, camelCard3);
        camelCardList.add(0, camelCard4);
        camelCardList.add(0, camelCard5);
    }

    public void fillModifier(){
        Modifier modifier = new Modifier(this);
        modifierL.add(0, modifier);
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
     * Coloca una carta de camello en la pila de camellos ganadores o perdedores
     * @param stack pila de camellos ganadores o perdedores
     */
    public void bet(Stack<CamelCard> stack, int i){
        stack.push(camelCardList.get(i));
        camelCardList.remove(i);
    }
    
    /**
     * Coloca un modificador en el tablero
     * @param board el tablero
     * @param modifier true si quieres que avance, false si quieres que retroceda
     * @param i casilla en el tablero
     */
    public void placeMod(Tile[] board, boolean modifier, int i){
        boolean canPlace = false;
        while(canPlace == false){
            if(!board[i].hasModifier() && !board[i - 1].hasModifier() && !board[i + 1].hasModifier() && board[i].camelStack.isEmpty()){
                modifierL.get(0).setValue(modifier);
                board[i].modifier.add(0, modifierL.get(0));
                modifierL.clear();
                canPlace = true;
            } else {
                System.out.println("Casilla invalida, intenta de nuevo");
                break;
            }
        }
    }
    
    /**
     * Gira el dado de un camello aleatorio que no se haya movido
     */
    public void rollDie(SimpleLinkedList<Camel> camelList, Tile[] board, int i, Stack<String> history){
        camelList.get(i).move(camelList.get(i), board, history);
        setMoney(this.money + 1);
    }

    public void grade(){
        for(int i = 0; i < betCardList.size(); i++){
            System.out.println("El camello esta en: " + betCardList.get(i).camelPlace);
            if(betCardList.get(i).camelPlace == 1){
                setMoney(getMoney() + betCardList.get(i).value);
            } else if (betCardList.get(i).camelPlace == 2){
                setMoney(getMoney() + 1);
            } else {
                setMoney(getMoney() - 1);
            }
        }
    }

}
