package ba.unsa.etf.rpr;

public abstract class ChessPiece {
    protected String position;

    protected boolean checkPosition(String position){  //provjerava da li je pozicija validna tj. unutar sahoveske table, da li je duzina stringa veca od 2
        position = position.toLowerCase();
        if(position.length() == 0 || position.length() > 2) return false;

        if(position.charAt(0) < 'a' || position.charAt(0) > 'h') {
            return false;
        }
        else if(position.charAt(1) < '1' || position.charAt(1) > '8') {
            return false;
        }
        return true;
    }

    public enum Color{
        WHITE,
        BLACK;
    }
    public static Color color;

    public ChessPiece(String position, Color color) throws IllegalArgumentException{
        if(!checkPosition(position)) throw new IllegalArgumentException("Nedozvoljena pozicija!");
        position = position.toLowerCase();
        this.position = position;
        this.color = color;
    }

    public String getPosition() {
        return position;
    }
    public Color getColor(){
        return this.color;
    }

    public void move(String position) throws IllegalChessMoveException, IllegalArgumentException { //prvo provejravamo da li je potez unutar table pa onda da li je validan za neku figuru
        position = position.toLowerCase();
        if(!checkPosition(position)) throw new IllegalArgumentException("neispravan potez");
        if(!checkMove(position)) throw  new IllegalChessMoveException("Nedozvoljen potez");
        this.position = position;
    }
    protected abstract boolean checkMove(String position);  //svaka figura ima svoje dozvoljene kretnje

    protected int[] setComparablePositionValues(String position, String move){
        position = position.toLowerCase();
        move = move.toLowerCase();
        int [] n = new int[4];
        n[0] = position.charAt(0); n[1] = Integer.valueOf(position.charAt(1));
        n[2] = move.charAt(0); n[3] = Integer.valueOf(move.charAt(1));
        return n;
    }

}
