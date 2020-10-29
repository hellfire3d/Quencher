package zone.arctic.quencher;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FarcFile {

    public List<FarcEntry> entries = new ArrayList<>();
    public File farcData;
    public long entriesOffset = 0;
    
    public FarcFile() {
        this.farcData = null;
    }
    
    public FarcFile(File fileData) {
        this.farcData = fileData;
    }
    
    public void setEntriesOffset(long offset) {
        this.entriesOffset = offset;
    }
    
    public File getFileHandle() {
        return farcData;
    }
    
    public void addEntry(FarcEntry entry) {
        entries.add(entry);
    }
    
    public void sortEntries() {
        Collections.sort(entries, new FarcEntryComparator());
    }
    
}

class FarcEntryComparator implements Comparator<FarcEntry>{
    @Override
    public int compare(FarcEntry entry1, FarcEntry entry2){
        return MiscUtils.byteArrayToHexString(entry1.getHash()).compareTo(MiscUtils.byteArrayToHexString(entry2.getHash()));
    }
}