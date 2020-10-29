package zone.arctic.quencher;

public class SHA1Patch {
    
    public byte[] old1;
    public byte[] new1;
    
    public SHA1Patch(byte[] old1, byte[] new1) {
        this.old1 = old1;
        this.new1 = new1;
    }
    
}
