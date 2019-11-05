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
        if(!checkPosition(position)) throw  new IllegalChessMoveException("Nedozvoljen potez");
        this.position = position;
    }
    public abstract boolean checkPosition(String position); //svaka figura ima svoje dozvoljene kretnje
}
