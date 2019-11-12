package ba.unsa.etf.rpr;

public abstract class ChessPiece {
    protected String position;

    private boolean checkPosition(String position){  //provjerava da li je pozicija validna tj. unutar sahoveske table, da li je duzina stringa veca od 2
        position.toLowerCase();
        if(position.length() > 2) return false;

        if(position.charAt(0) < 'a' || position.charAt(0) > 'h') {
            return false;
        }
        else if(position.charAt(1) < '1' || position.charAt(1) > '8') {
            return false;
        }
        return true;
    }

    public enum Color{
        WHITE("Bijela"),
        BLACK("Crna");
        private final String colorVal;

        Color(String colorVal){
            this.colorVal = colorVal;
        }
        public String getColorVal(){
            return colorVal;
        }
    }
    public static Color color;

    public ChessPiece(String position, Color color) throws IllegalArgumentException{
        if(!checkPosition(position)) throw new IllegalArgumentException("Nedozvoljena pozicija!");
        this.position = position;
        this.color = color;
    }

    public String getPosition() {
        return position;
    }
    public Color getColor(){
        return color;
    }
    public void move(String position) throws IllegalChessMoveException { //prvo provejravamo da li je potez unutar table pa onda da li je validan za neku figuru
        if(!this.checkMove(position)) throw  new IllegalChessMoveException("Nedozvoljen potez");

        this.position = position;
    }
    protected abstract boolean checkMove(String position);  //svaka figura ima svoje dozvoljene kretnje

    protected int[] setComparablePositionValues(String position, String move){
        int [] n = new int[4];
        n[0] = this.position.charAt(0); n[1] = Integer.valueOf(this.position.charAt(1));
        n[2] = position.charAt(0); n[3] = Integer.valueOf(position.charAt(1));
        return n;
    }

}
