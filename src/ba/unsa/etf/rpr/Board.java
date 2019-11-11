package ba.unsa.etf.rpr;

import java.util.ArrayList;

import java.util.List;

public class Board {
    private ChessPiece [][]fields = new ChessPiece[8][8];
    private King blackKing, whiteKing;                  //posto su kraljevi najbitnije figure od cijeg ostojanja igra i zavisi
                                                                //dodati cemo clanove koji ce u svakom trenutku znati njihovu poziciju;
    private boolean checkPosition(String position){  //provjerava da li je pozicija validna tj. unutar sahoveske table, da li je duzina stringa veca od 2
        if(position.length() > 2) return false;
        else if(position.charAt(0) < 'a' || position.charAt(0) > 'h') {
            return false;
        }
        else if(position.charAt(0) < 'A' || position.charAt(0) >'H') {
            return false;
        }
        else if(position.charAt(1) < '1' || position.charAt(1) > '8') {
            return false;
        }
        return true;
    }

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
        whiteKing = (King)fields[4][0];
        fields[3][7] = new Queen("d8", ChessPiece.Color.Black);
        fields[4][7] = new King("e8", ChessPiece.Color.Black);
        blackKing = (King)fields[4][7];
    }

    public void move(Class type, ChessPiece.Color color, String position) throws IllegalChessMoveException {
        if(!checkPosition(position)) throw new IllegalChessMoveException("Ilegalan potez");
        int i,j;
        for(i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                if (type.isInstance(fields[i][j]) && color == fields[i][j].getColor()){
                    if(fields[i][j].getPosition() == position) throw new IllegalChessMoveException("Ilegalan potez");
                    if(checkPathToNewPosition(fields[i][j].getPosition(), position))
                        fields[i][j].move(position);
                }
            }
        }
    }

    public void move(String oldPosition, String newPosition) throws Exception {

        int[] pozicija = getMatrixPosition(oldPosition);
        int i = pozicija[0], j = pozicija[1];
        if (checkPosition(oldPosition) || fields[i][j] == null)  //provjerava da li je uopste unesena validna pozcicija te da li na poziciji ima figura
            throw new IllegalArgumentException("Nema figure na poziciji " + oldPosition);
        if (checkPosition(newPosition) && checkPathToNewPosition(oldPosition, newPosition)) {
            fields[i][j].move(newPosition);
            int [] a = getMatrixPosition(newPosition);
            fields[a[0]][a[1]] = fields[i][j];
            fields[i][j] = null;
        }
    }

    public boolean isCheck(ChessPiece.Color color){
        int i,j;
        String kingPosition = new String();
        if(color == ChessPiece.Color.Black)
            kingPosition = blackKing.getPosition();
        else if(color == ChessPiece.Color.White)
            kingPosition = whiteKing.getPosition();
        for(i = 0; i < 8; i++){
            for(j = 0; j < 8; j++){
                if(fields[i][j] != null && fields[i][j].getColor() != color){
                    if(checkPathToNewPosition(fields[i][j].getPosition(), kingPosition) && fields[i][j].checkMove(kingPosition))
                        return true;
                }
            }
        }
        return false;
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
