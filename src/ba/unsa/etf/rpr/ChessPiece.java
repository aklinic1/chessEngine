package ba.unsa.etf.rpr;

public abstract class ChessPiece {
    String position;
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
    public abstract void move(String position);
}
