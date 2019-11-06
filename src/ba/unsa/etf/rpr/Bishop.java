package ba.unsa.etf.rpr;

public class Bishop extends ChessPiece {
    public Bishop(String position, Color color) {
        super(position, color);
    }

    @Override
    public boolean checkMove(String position){
        int [] niz = setComparablePositionValues(this.position, position);
        int pocetna1 = niz[0], pocetna2 = niz[1], krajnja1 = niz[2], krajnja2 = niz[3];
        if(Math.abs(pocetna1 - krajnja1) == Math.abs(pocetna2 - krajnja2)) return true;
        return false;
    }
}
