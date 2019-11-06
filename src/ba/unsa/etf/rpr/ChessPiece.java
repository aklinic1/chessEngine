package ba.unsa.etf.rpr;

public abstract class ChessPiece {
    protected String position;

    public enum Color{
        White("W"),
        Black("B");
        private final String colorVal;

        Color(String colorVal){
            this.colorVal = colorVal;
        }
        public String getColorVal(){
            return colorVal;
        }
    }
    public static Color color;

    public ChessPiece(String position, Color color) {
        this.position = position;
        this.color = color;
    }

    public String getPosition() {
        return position;
    }
    public Color getColor(){
        return color;
    }
    public void move(String position) throws IllegalChessMoveException {
        if(!checkPosition(position) || !checkMove(position)) throw  new IllegalChessMoveException("Nedozvoljen potez");

        this.position = position;
    }
    public abstract boolean checkMove(String position); //svaka figura ima svoje dozvoljene kretnje

    public boolean checkPosition(String position){  //provjerava da li je pozicija validna tj. unutar sahoveske table
        if(position == this.position) return false;
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
    public int[] setComparablePositionValues(String position, String move){
        int [] n = new int[4];
        n[0] = this.position.charAt(0); n[1] = Integer.valueOf(this.position.charAt(1));
        n[2] = position.charAt(0); n[3] = Integer.valueOf(position.charAt(1));
        return n;
    }

}
