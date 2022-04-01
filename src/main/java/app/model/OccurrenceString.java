package app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class OccurrenceString implements Comparable<OccurrenceString>, Serializable {
    private String label;
    private Integer occurrence;

    public OccurrenceString(String label, Integer occurrence) {
        this.label = label;
        this.occurrence = occurrence;
    }

    @Override
    public int compareTo(OccurrenceString other) {
        return other.occurrence - this.occurrence;
    }

    public static ArrayList<OccurrenceString> hashtableToOccurrenceString(Hashtable<String, Integer> hashtable) {

        ArrayList<OccurrenceString> occurrenceStrings = new ArrayList<>();

        Set<String> keys = hashtable.keySet();
        for(String key : keys) {
            occurrenceStrings.add(new OccurrenceString(key, hashtable.get(key)));
        }

        return occurrenceStrings;
    }

    public String toString() {
        String out = label + ": " + occurrence;
        return out;
    }
}
