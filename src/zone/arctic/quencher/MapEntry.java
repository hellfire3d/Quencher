package zone.arctic.quencher;

public class MapEntry {
    
    private String path;        // Path relative to gamedata directory
    private int timestamp;      // UNIX timestamp
    private int size;           // Size of file
    private byte[] hash;        // SHA1 Hash of file
    private long guid;           // Global Unique ID of File

    public MapEntry(String path, int timestamp, int size, byte[] hash, long guid) {
        this.path = path;
        this.timestamp = timestamp;
        this.size = size;
        this.hash = hash;
        this.guid = guid;
    }
    
    public String getPath() {
        return path;
    }
    public int getTimestamp() {
        return timestamp;
    }
    public int getSize() {
        return size;
    }
    public byte[] getHash() {
        return hash;
    }
    public long getGUID() {
        return guid;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public void setHash(byte[] hash) {
        this.hash = hash;
    }
    public void setGUID(long guid) {
        this.guid = guid;
    }
  
    
}
