import java.util.Arrays;

public class Round {
    public SimpleLinkedList<Player> playerList = new SimpleLinkedList<>();
    public Camel[] camelArr = new Camel[5];
    public Tile[] board = new Tile[16];

    public void setUpRound(int num){
        for(int i = 0; i < num; i++){
            Player jugador = new Player(("Player " + i), 3);
            //jugador.setMoney(jugador.getMoney() + 1);
            jugador.fillCamelCardList();
            playerList.add(i, jugador);
        }
        for(int i = 0; i < 5; i++){
            Camel camello = new Camel("Camel " + i);
            camello.fillCamelStack();
            camelArr[i] = camello;
        }
    }

    public static void round(){}

    

    public static void main(String[] args){
        Round round = new Round();
        round.setUpRound(4);
        System.out.println(round.playerList);
        System.out.println(Arrays.toString(round.camelArr));
        System.out.println(round.camelArr[0].roll());
    }
}
