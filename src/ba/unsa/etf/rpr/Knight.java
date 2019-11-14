package ba.unsa.etf.rpr;

public class Knight extends ChessPiece{
    public Knight(String position, Color color) {
        super(position, color);
    }

    @Override
    protected boolean checkMove(String position){
        if (!checkPosition(position)) return false;
        int [] niz = setComparablePositionValues(this.position, position);
        int pocetna1 = niz[0], pocetna2 = niz[1], krajnja1 = niz[2], krajnja2 = niz[3];
        if((Math.abs(pocetna1 - krajnja1) == 2 && Math.abs(pocetna2 - krajnja2) == 1)
                || (Math.abs(pocetna2 - krajnja2) == 2 && Math.abs(pocetna1 - krajnja1) == 1))
            return true;
        return false;
    }
}
