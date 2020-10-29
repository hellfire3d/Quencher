package zone.arctic.quencher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MapFile {
    
    private int mapType = 0;
    
    public List<MapEntry> entries = new ArrayList<>();
    
    public void addEntry(MapEntry entry) {
        entries.add(entry);
    }
    public int getMapEntryCount() {
        return entries.size();
    }
    
    public int getMapType() {
        return mapType;
    }
    
    public void setMapType(int mapType) {
        this.mapType = mapType;
    }
    
    public MapEntry containsGUID(long guid) {
        for (MapEntry entry : entries) 
            if (entry.getGUID()==guid) return entry;
        
        return null;
    }

    public MapEntry containsHash(byte[] hash) {
        for (MapEntry entry : entries) 
            if (entry.getHash()==hash) return entry;
        return null;
    }
    
    public void addIndexedEntry(MapEntry entry) {
        
        for (MapEntry entryScan : entries) {
            if (entryScan.getGUID()>entry.getGUID()) {
                System.out.println(entryScan.getGUID() + " > " + entry.getGUID());
                entries.add(entries.indexOf(entryScan), entry);
                System.out.println(entryScan.getPath());
                break;
            }
        }
    }
  
    public void sortEntries() {
        Collections.sort(entries, new EntryComparator());
    }
    
}

class EntryComparator implements Comparator<MapEntry>{
    @Override
    public int compare(MapEntry entry1, MapEntry entry2){
        if( entry1.getGUID()> entry2.getGUID() ){
            return 1;
        } else if( entry1.getGUID() < entry2.getGUID() ){
            return -1;
        } else {
            return 0;
        }
    }
}