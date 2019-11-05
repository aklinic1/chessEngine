package ba.unsa.etf.rpr;

public class IllegalChessMoveException extends Exception {
    public IllegalChessMoveException(String poruka){
        super(poruka);
    }
}
