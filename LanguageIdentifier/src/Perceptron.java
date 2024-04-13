import java.util.*;

public class Perceptron {
    private List<Double> wagi;
    private Map<String, List<String>> dataToTeach;
    private double prog = 1;
    private final double stalaUczenia = 0.5;
    private Map<Character, Integer> chars;
    private String language;
    private int totalChars;
    public Perceptron(Map<String, List<String>> dataToTeach2, String language){
        wagi = new ArrayList<>();
        chars = new LinkedHashMap<>();
        dataToTeach = new HashMap<>();
        this.language = language;
        for(Map.Entry<String, List<String>> entry : dataToTeach2.entrySet()){
            List<String> originalList = entry.getValue();
            List<String> copiedList = new ArrayList<>(originalList);
            dataToTeach.put(entry.getKey(), copiedList);
        }
        for(int i = 97; i<123; i++){
            chars.put((char)i, 0);
        }

        for(int i = 0; i<123-97; i++){
            wagi.add(1.0);
        }
        teach();
    }
    public String getLanguage(){
        return language;
    }
    public double analyze(String data){
        float sum = 0;
        int index = 0;
        totalChars = 0;
        for(int i = 97; i<123; i++){
            chars.put((char)i, 0);
        }
        for(char c : data.toLowerCase().toCharArray()) {
            if (chars.containsKey(c)) {
                chars.put(c, chars.get(c) + 1);
                totalChars++;
            }
        }
        for(Map.Entry<Character, Integer> entry : chars.entrySet()){
            sum += ((float)entry.getValue()/totalChars)*wagi.get(index);
            index++;
        }
        return (sum-prog);
    }
    private void teach(){
        String result;
        boolean czyJeszczeRazTeach = false;
        List<String> strToRemove = new ArrayList<>();
        for(Map.Entry<String, List<String>> entry : dataToTeach.entrySet()){
            for(String str : entry.getValue()) {
                for(char c : str.toLowerCase().toCharArray()) {
                    if (chars.containsKey(c)) {
                        chars.put(c, chars.get(c) + 1);
                        totalChars++;
                    }
                }
                float sum = 0;
                int index = 0;
                for(Map.Entry<Character, Integer> entry1 : chars.entrySet()) {
                    sum += ((float)entry1.getValue()/totalChars)*wagi.get(index);
                }
                if (sum > prog) {
                    result = language;
                } else {
                    result = "no " + language;
                }
                if ((!result.equals(language) && entry.getKey().equals(language)) || (result.equals(language) && !entry.getKey().equals(language))) {
                    czyJeszczeRazTeach = true;
                    int plusCzyMinus;
                    if (sum > prog) {
                        plusCzyMinus = -1;
                    } else {
                        plusCzyMinus = 1;
                    }
                    List<Double> noweWagi = new ArrayList<>();
                    index = 0;
                    for (Map.Entry<Character, Integer> entry1 : chars.entrySet()) {
                        noweWagi.add(wagi.get(index) + (plusCzyMinus * ((float)entry1.getValue()/totalChars) * stalaUczenia));
                        index++;
                    }
                    wagi = noweWagi;
                    prog += (plusCzyMinus * (-1) * stalaUczenia);
                } else {
                    strToRemove.add(str);
                }
            }
            for(int i = 97; i<123; i++){
                chars.put((char)i, 0);
            }
            totalChars = 0;
        }
        for(String strArr : strToRemove){
            for(Map.Entry<String, List<String>> entry : dataToTeach.entrySet()) {
                entry.getValue().remove(strArr);
            }
        }
        if(czyJeszczeRazTeach){
            teach();
        }
    }
}
