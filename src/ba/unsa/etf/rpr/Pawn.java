package ba.unsa.etf.rpr;

public class Pawn extends ChessPiece {
    public Pawn(String position, Color color) {
        super(position, color);
    }
    @Override
    protected boolean checkMove(String position){
        if (!checkPosition(position)) return false;
        int [] niz = setComparablePositionValues(this.position, position);
        int pocetna1 = niz[0], pocetna2 = niz[1], krajnja1 = niz[2], krajnja2 = niz[3];
        if(pocetna1 == krajnja1 && (pocetna2 == krajnja2 - 1 || pocetna2 == krajnja2 + 1 || pocetna2 == krajnja2 - 2 || pocetna2 == krajnja2 + 2)) return true;
        if(Math.abs(pocetna1 - krajnja1) == 1 && Math.abs(pocetna2 - krajnja2) == 1) return true; //moze se kretati jedno polje dijagonalno pod uslovom
        return false;                                                                          //da je na toj poziciji figura druge boje sto cemo provjeriti
    }                                                                                               //u klasi board
}
