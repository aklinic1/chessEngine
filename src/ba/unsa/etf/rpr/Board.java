package ba.unsa.etf.rpr;

public class Board {
    private ChessPiece [][]fields = new ChessPiece[8][8];
    private King blackKing, whiteKing;                  //posto su kraljevi najbitnije figure od cijeg ostojanja igra i zavisi
                                                                //dodati cemo clanove koji ce u svakom trenutku znati njihovu poziciju;

    private boolean checkPosition(String position){  //provjerava da li je pozicija validna tj. unutar sahoveske table, da li je duzina stringa veca od 2
        position = position.toLowerCase();
        if(position.length() > 2) return false;

        if(position.charAt(0) < 'a' || position.charAt(0) > 'h') {
            return false;
        }
        else if(position.charAt(1) < '1' || position.charAt(1) > '8') {
            return false;
        }
        return true;
    }

    private int[] getMatrixPosition(String position){   //pretvara iz oblika stringa u oblik citljiv za pristup elementima table kroz matricu
        int i;
        position = position.toLowerCase();
        for(i = 0; i < 8; i++){
            if(position.charAt(0) - 65 == i || position.charAt(0) - 97 == i)  break;
        }
        int []m = new int[2];
        m[0] = i;
        m[1] = Integer.valueOf(position.substring(1,2));
        return m;
    }

    public Board() {
        int i;
        for(i = 0; i < 8; i++){
            fields[i][1] = new Pawn((char)('a' + i) + String.valueOf(2), ChessPiece.Color.WHITE);
            fields[i][6] = new Pawn((char)('a' + i) + String.valueOf(7), ChessPiece.Color.BLACK);
        }
        fields[0][0] = new Rook("a1", ChessPiece.Color.WHITE);
        fields[7][0] = new Rook("a8", ChessPiece.Color.WHITE);

        fields[0][7] = new Rook("h1", ChessPiece.Color.BLACK);
        fields[7][7] = new Rook("h8", ChessPiece.Color.BLACK);

        fields[1][0] = new Knight("b1", ChessPiece.Color.WHITE);
        fields[6][0] = new Knight("g1", ChessPiece.Color.WHITE);

        fields[1][7] = new Knight("b7", ChessPiece.Color.BLACK);
        fields[6][7] = new Knight("g7", ChessPiece.Color.BLACK);

        fields[2][0] = new Bishop("c1", ChessPiece.Color.BLACK);
        fields[5][0] = new Bishop("f1", ChessPiece.Color.BLACK);

        fields[2][7] = new Bishop("c8", ChessPiece.Color.WHITE);
        fields[5][7] = new Bishop("f8", ChessPiece.Color.WHITE);

        fields[3][0] = new Queen("d1", ChessPiece.Color.WHITE);
        fields[4][0] = new King("e1", ChessPiece.Color.WHITE);
        whiteKing = (King)fields[4][0];

        fields[3][7] = new Queen("d8", ChessPiece.Color.BLACK);
        fields[4][7] = new King("e8", ChessPiece.Color.BLACK);
        blackKing = (King)fields[4][7];
    }

    public void move(Class type, ChessPiece.Color color, String position) throws Exception {
        if(!checkPosition(position)) throw new IllegalChessMoveException("Ilegalan potez");
        position.toLowerCase();
        int i,j;
        for(i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                if (type.isInstance(fields[j][i]) && color == fields[j][i].getColor()){
                    if(fields[j][i].getPosition() == position) throw new IllegalChessMoveException("Ilegalan potez");
                        move(fields[j][i].getPosition(), position);
                }
            }
        }
    }

    public void move(String oldPosition, String newPosition) throws Exception {

        oldPosition = oldPosition.toLowerCase();
        newPosition = newPosition.toLowerCase();

        int[] pozicija = getMatrixPosition(oldPosition);
        int i = pozicija[0], j = pozicija[1];
        int[] pozicija1 = getMatrixPosition(newPosition);
        int k = pozicija1[0], l = pozicija1[1];

        if (checkPosition(newPosition) || fields[i][j] == null)  //provjerava da li je uopste unesena validna pozcicija te da li na poziciji ima figura
            throw new IllegalArgumentException("Nema figure na poziciji " + oldPosition);

        if(fields[i][j] instanceof Pawn){
            boolean a = false;
            if((i == k && l == j + 1 && fields[i][j].getColor() == ChessPiece.Color.WHITE) ||
                    (i == k && l == j - 1 && fields[i][j].getColor() == ChessPiece.Color.BLACK)){
                if(fields[k][l] == null) a = true;
            }
            else if((i == k && l == j + 2 && fields[i][j].getColor() == ChessPiece.Color.WHITE && j == 6) ||
                    (i == k && l == j - 2 && fields[i][j].getColor() == ChessPiece.Color.BLACK && j == 1)){
                if(fields[k][l] == null) a = true;
            }
            else if(fields[k][l] != null && fields[k][l].getColor() != fields[i][j].getColor())
                a = true;
            if(a == true){
                fields[i][j].move(newPosition);
                fields[k][l] = fields[i][j];
                fields[i][j] = null;
            }
            else throw new IllegalChessMoveException("ilegalan potez");
        }
        else if(fields[i][j] instanceof Knight){
            if(fields[k][l] == null || fields[k][l].getColor() != fields[i][j].getColor()){
                fields[i][j].move(newPosition);
                fields[k][l] = fields[i][j];
                fields[i][j] = null;
            }
            else throw new IllegalChessMoveException("Ilegalan potez");
        }
        else if (checkPathToNewPosition(oldPosition, newPosition)) {
            fields[i][j].move(newPosition);
            fields[k][l] = fields[i][j];
            fields[i][j] = null;
        }
    }

    public boolean isCheck(ChessPiece.Color color){
        int i,j;
        String kingPosition = new String();
        if(color == ChessPiece.Color.BLACK)
            kingPosition = blackKing.getPosition();
        else if(color == ChessPiece.Color.WHITE)
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
        }                                                                                 //te ako postoji i ako je iste boje potez je neregularan

        int br1,br2;
        if(nP2 > oP2) br2 = 1;
        else br2 = -1;                  //br1 i br2 nam govore u kojem se smjeru na tabli krecemo
        if(nP1 > oP1) br1 = 1;
        else br1 = -1;

        int br = 0;

        if(Math.abs(nP1-oP1) == Math.abs(nP2-oP2)){ //dijagonalno kretanje
            while(br < Math.abs(oP1 - nP1) - 1){
                if(fields[oP1 + br1][oP2 + br2] != null) return false;
                br++;
            }
        }
        else if(nP1 == oP1){         //vertikalno
                while(br < Math.abs(oP2 - nP2) - 1){
                    if(fields[oP1][oP2 + br2] != null) return false;
                    br++;
                }
            }
        else if(nP2 == oP2) {                  //horizontalno
            while (br < Math.abs(oP1 - nP1) - 1) {
                if (fields[oP1 + br1][oP2] != null) return false;
                br++;
            }
        }
        return true;
    }

    public String getBlackKingPosition() {
        return blackKing.getPosition();
    }
}
