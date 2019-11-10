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
