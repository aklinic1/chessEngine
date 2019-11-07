package ba.unsa.etf.rpr;

public class Pawn extends ChessPiece {
    public Pawn(String position, Color color) {
        super(position, color);
    }
    @Override
    public boolean checkMove(String position){
        int [] niz = setComparablePositionValues(this.position, position);
        int pocetna1 = niz[0], pocetna2 = niz[1], krajnja1 = niz[2], krajnja2 = niz[3];
        if(pocetna1 == krajnja1 && pocetna2 == krajnja2 - 1) return true;
        return false;
    }
}