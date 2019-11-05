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

    public String getPosition() {
        return position;
    }
    public Color getColor(){
        return color;
    }
    public void move(String position) throws IllegalChessMoveException {
        if(!checkMove(position)) throw  new IllegalChessMoveException("Nedozvoljen potez");
        this.position = position;
    }
    public abstract boolean checkMove(String position); //svaka figura ima svoje dozvoljene kretnje

    public boolean checkPosition(String position){
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


}
