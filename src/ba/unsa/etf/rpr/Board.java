package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.List;

public class Board {
    ChessPiece [][]fields = new ChessPiece[8][8];

    private int[] getMatrixPosition(String position){   //pretvara iz oblika stringa u oblik citljiv za pristup elementima table kroz matricu
        int i;
        for(i = 0; i < 8; i++){
            if(position.charAt(0) - 65 == i || position.charAt(0) - 97 == i)  break;
        }
        int []m = new int[2];
        m[0] = i;
        m[1] = Integer.valueOf(position.charAt(1));
        return m;
    }

    public Board() {
        int i;
        for(i = 0; i < 8; i++){
            fields[i][1] = new Pawn((char)('a' + i) + String.valueOf(1), ChessPiece.Color.White);
            fields[i][6] = new Pawn((char)('a' + i) + String.valueOf(6), ChessPiece.Color.Black);
        }
        fields[0][0] = new Rook("a1", ChessPiece.Color.White);
        fields[7][0] = new Rook("a8", ChessPiece.Color.White);
        fields[0][7] = new Rook("h1", ChessPiece.Color.Black);
        fields[7][7] = new Rook("h8", ChessPiece.Color.Black);
        fields[1][0] = new Knight("b1", ChessPiece.Color.Black);
        fields[6][0] = new Knight("g1", ChessPiece.Color.Black);
        fields[1][7] = new Knight("b7", ChessPiece.Color.White);
        fields[6][7] = new Knight("g7", ChessPiece.Color.White);
        fields[2][0] = new Bishop("c1", ChessPiece.Color.Black);
        fields[5][0] = new Bishop("f1", ChessPiece.Color.Black);
        fields[2][7] = new Bishop("c8", ChessPiece.Color.White);
        fields[5][7] = new Bishop("f8", ChessPiece.Color.White);
        fields[3][0] = new Queen("d1", ChessPiece.Color.White);
        fields[4][0] = new King("e1", ChessPiece.Color.White);
        fields[3][7] = new Queen("d8", ChessPiece.Color.Black);
        fields[4][7] = new King("e8", ChessPiece.Color.Black);
    }
    public void move(Class type, ChessPiece.Color color, String position) throws IllegalChessMoveException {
        int i,j;
        for(i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                if (fields[i][j] instanceof type && color == fields[i][j].getColor()){

                    fields[i][j].move(position);

                }
            }
        }
    }
    private boolean checkPathToNewPosition(String oldPosition, String newPosition){     //provjerava da li ima figura na putu do pozicije
        int [] oP = getMatrixPosition(oldPosition);
        int [] nP = getMatrixPosition(newPosition);
        int oP1 = oP[0], oP2 = oP[1], nP1 = nP[0], nP2 = nP[1];

        if(fields[nP1][nP2] != null){
            if(fields[nP1][nP2].getColor() == fields[oP1][oP2].getColor()) return false;   //odmah smo provjerili da li je postoji figura na novoj poziciji
        }                                                                                   //te ako postoji i ako je iste boje potez je neregularan

        int i,j;

        int br1,br2;
        if(oP2 > oP1) br1 = 1;
        else br1 = -1;                  //br1 i br2 nam govore u kojem se smjeru na tabli krecemo
        if(nP2 > nP1) br2 = 1;
        else br2 = -1;

        int br = 0;

        if(Math.abs(nP1-oP1) == Math.abs(nP2-oP2)){ //dijagonalno kretanje
            while(br < Math.abs(oP1 - nP1) - 1){
                if(fields[oP1 + br1][oP2 + br2] != null) return false;
                br++;
            }
        }
        else {              //provjerava horizonatalno i vertikalno
            if(nP1 == oP1){         //vertikalno
                while(br < Math.abs(oP2 - nP2) - 1){
                    if(fields[oP1][oP2 + br2] != null) return false;
                    br++;
                }
            }
            else {                  //horizontalno
                while(br < Math.abs(oP1 - nP1) - 1){
                    if(fields[oP1 + br1][oP2] != null) return false;
                    br++;
                }
            }
        }
        return false;
    }

}
