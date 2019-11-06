package ba.unsa.etf.rpr;

public class King extends ChessPiece {
    public King(String position, Color color) {
        super(position, color);
    }

    @Override
    public boolean checkMove(String position){
        int [] niz = setComparablePositionValues(this.position, position);
        int pocetna1 = niz[0], pocetna2 = niz[1], krajnja1 = niz[2], krajnja2 = niz[3];

        if(krajnja1 == pocetna1 - 1 || krajnja1 == pocetna1 + 1 || krajnja1 == pocetna1) {
            if (krajnja2 == pocetna2 || krajnja2 == pocetna2 + 1 || krajnja2 == pocetna2 - 1)
                return true;
        }
        return false;
    }

}
