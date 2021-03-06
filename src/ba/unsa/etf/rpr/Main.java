package ba.unsa.etf.rpr;

import java.util.Scanner;

import static ba.unsa.etf.rpr.ChessPiece.Color.BLACK;
import static ba.unsa.etf.rpr.ChessPiece.Color.WHITE;

public class Main {
     public static void main(String[] args) {

         Board chessGame = new Board();
         System.out.println("Igra pocinje : \n");
         int br=0;
    	while(true) {
            ChessPiece.Color chessPieceColor = null;
            if (br % 2 == 0) {
                chessPieceColor = WHITE;
                System.out.println("White move: ");
            } else if (br % 2 != 0) {
                chessPieceColor = BLACK;
                System.out.println("Black move: ");
            }
            Scanner scanner = new Scanner(System.in);
            String pozicija = scanner.nextLine();

            if (pozicija == "X") {
                String s = new String();
                if(chessPieceColor == WHITE) s = "bijela";
                else if (chessPieceColor == BLACK) s = "crna";
                System.out.println("Igrac sa bojom " + s + " je izgubio partiju!");
                break;
            }

            Class type;

            if (pozicija.charAt(0) == 'K') {
                type = King.class;
            } else if (pozicija.charAt(0) == 'Q') {
                type = Queen.class;
            } else if (pozicija.charAt(0) == 'R') {
                type = Rook.class;
            } else if (pozicija.charAt(0) == 'B') {
                type = Bishop.class;
            } else if (pozicija.charAt(0) == 'N') {
                type = Knight.class;
            } else if (pozicija.charAt(0) == 'P') {
                type = Pawn.class;
            } else {
                System.out.println("unesite opet");
                continue;      //throw IllegalArgumentException("Figura na slovo " + pozicija.charAt(0) + " ne postoji");
            }
            pozicija = pozicija.substring(1,3);
            try {
                chessGame.move(type, chessPieceColor, pozicija);
            }
            catch (Exception e){
                System.out.println("Nedozvoljen potez, igrajte ponovo: ");
                br--;
            }
            if (chessPieceColor == WHITE) {
                if (chessGame.isCheck(BLACK)) System.out.println("CHECK!!");
            } else {
                if (chessGame.isCheck(WHITE)) System.out.println("CHECK!!");
            }
            br++;
        }


    }
}
