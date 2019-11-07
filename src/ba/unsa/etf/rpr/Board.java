package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.List;

public class Board {
    ChessPiece [][]fields = new ChessPiece[8][8];

    private int[] positionToMatrixPosition(String position){ //pretvara iz oblika stringa u oblik citljiv za pristu elementima table kroz matricu
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
}
