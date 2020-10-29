package zone.arctic.quencher;

public class FarcEntry {
    
    private byte[] hash;        // SHA1 Hash of file
    private long offset;         // Offset to the actual file data
    private long size;           // Size of file

    public FarcEntry(byte[] hash, long offset, long size) {
        this.hash = hash;
        this.offset = offset;
        this.size = size;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }
    
    public long getSize() {
        return size;
    }
    public long getOffset() {
        return offset;
    }
    public byte[] getHash() {
        return hash;
    }
    
}
