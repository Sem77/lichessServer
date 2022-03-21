package app.model;

import java.util.ArrayList;
import java.io.Serializable;

public class Strokes implements Serializable {
    private String strokesString;
    private ArrayList<String> whiteStrokes;
    private ArrayList<String> blackStrokes;

    public Strokes(String strokesString) {
        this.strokesString = strokesString;
        whiteStrokes = new ArrayList<>();
        blackStrokes = new ArrayList<>();

        extractStrokes(whiteStrokes, blackStrokes);
    }

    private void extractStrokes(ArrayList<String> whiteStrokes, ArrayList<String> blackStrokes) {
        // regular expression to tokenize the strokes
        String regExStrokeSep = "\\d+\\.|1\\.|([01(1\\\\2)]-[01(1\\\\2)])| "; // \d+\.|1\.|([01(1\\2)]-[01(1\\2)])|
        String[] s = this.strokesString.split(regExStrokeSep); // tableau contenant tous les coups joués (blanc et noir combinés)

        for (int i = 0; i < s.length-1; i += 2) {
            whiteStrokes.add(s[i]);
            blackStrokes.add(s[i+1]);
        }

        // si la taille du tableau est impair alors c'est le noir qui a gagné
        // il faut ajouter le dernier coup restant dans le tableau du noir
        if((s.length % 2) != 0) {
            blackStrokes.add(s[s.length-1]);
        }
    }


    public int getStrokesNumber() {
        return Math.max(whiteStrokes.size(), blackStrokes.size());
    }


    public String getFirstStroke() {
        String stroke = "";
        if(!whiteStrokes.isEmpty() && !blackStrokes.isEmpty())
            stroke = "<" + whiteStrokes.get(0) + "," + blackStrokes.get(0) + ">";
        return stroke;
    }


    public String toString() {
        String strokes = "";

        strokes += "white: ";
        for(String stroke : whiteStrokes) {
            strokes += stroke + " ";
        }
        strokes += "\nblack: ";
        for(String stroke : blackStrokes) {
            strokes += stroke + " ";
        }

        return strokes;
    }
}
