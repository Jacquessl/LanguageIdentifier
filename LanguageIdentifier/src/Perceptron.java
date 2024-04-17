import java.util.*;

public class Perceptron {
    private List<Double> wagi;
    private Map<String, List<Map<Character, Integer>>> dataToTeach;
    private double prog;
    private final double stalaUczenia = Math.random() *0.1;
    private Map<Character, Integer> chars;
    private String language;
    private int totalChars;
    private int counterRekursji;
    private String nextLanguage;
    public Perceptron(Map<String, List<Map<Character, Integer>>> dataToTeach2, String language, double wagiVal){
        wagi = new ArrayList<>();
        counterRekursji = 0;
        prog = 10;
        chars = new LinkedHashMap<>();
        dataToTeach = new HashMap<>();
        this.language = language;
        for(Map.Entry<String, List<Map<Character, Integer>>> entry : dataToTeach2.entrySet()){
            List<Map<Character, Integer>> originalList = entry.getValue();
            List<Map<Character, Integer>> copiedList = new ArrayList<>(originalList);
            dataToTeach.put(entry.getKey(), copiedList);
        }
        for(int i = 0; i<123-97; i++){
            wagi.add(2.0);
        }
    }
    public String getLanguage(){
        return language;
    }
    public void setNextLanguage(String nextLanguage){
        this.nextLanguage = nextLanguage;
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
//        return 1/(1+Math.pow(Math.E, (sum - prog )*-1));
//        return sum -1 >= prog || sum +1 <= prog ? 1 : 0;
        return sum;
    }
    public void teachOwnLanguage() {
        String result;
        counterRekursji++;
        boolean czyJeszczeRaz = false;
        for (Map.Entry<String, List<Map<Character, Integer>>> entry : dataToTeach.entrySet()) {
            if (entry.getKey().equals(language)) {
                for (Map<Character, Integer> lista : entry.getValue()) {
                    double sum = 0;
                    int index = 0;
                    totalChars = 0;
                    for (Map.Entry<Character, Integer> entr1 : lista.entrySet()) {
                        totalChars += entr1.getValue();
                    }
                    for (Map.Entry<Character, Integer> entry1 : lista.entrySet()) {
                        sum += ((double) entry1.getValue() / totalChars) * wagi.get(index);
                    }
                    if (sum > prog) {
                        result = language;
                    } else {
                        result = nextLanguage;
                    }
                    if (!result.equals(language)) {
                        czyJeszczeRaz = true;
                        int plusCzyMinus;

                        plusCzyMinus = 1;

                        List<Double> noweWagi = new ArrayList<>();
                        index = 0;
                        for (Map.Entry<Character, Integer> entry1 : lista.entrySet()) {
                            double newWaga = wagi.get(index) + (plusCzyMinus * ((float) entry1.getValue() / totalChars) * stalaUczenia);

                            noweWagi.add(newWaga);
                            //}
                            index++;
                        }
                        wagi = noweWagi;
                        prog += (plusCzyMinus * (-1) * stalaUczenia);
                    }
                }



            }

        }
        if (czyJeszczeRaz) {
            try {
                teachOwnLanguage();
            } catch (StackOverflowError e) {
                System.out.println("COS");
            }
        }
    }

    public void teach(){
        if(counterRekursji==0) {
            teachOwnLanguage();
        }
        String result;
        counterRekursji++;
        boolean czyJeszczeRaz = false;
        List<Map<Character, Integer>> toRemove = new ArrayList<>();
        for(Map.Entry<String, List<Map<Character, Integer>>> entry : dataToTeach.entrySet()) {
            if(!entry.getKey().equals(language)) {
                for (Map<Character, Integer> lista : entry.getValue()) {
                    double sum = 0;
                    int index = 0;
                    totalChars = 0;
                    for (Map.Entry<Character, Integer> entr1 : lista.entrySet()) {
                        totalChars += entr1.getValue();
                    }
                    for (Map.Entry<Character, Integer> entry1 : lista.entrySet()) {
                        sum += ((double) entry1.getValue() / totalChars) * wagi.get(index);
                    }
                    if (sum > prog) {
                        result = language;
                    } else {
                        result = nextLanguage;
                    }
                    if (result.equals(language)) {
                        czyJeszczeRaz = true;
                        int plusCzyMinus;

                        plusCzyMinus = -1;

                        List<Double> noweWagi = new ArrayList<>();
                        index = 0;
                        for (Map.Entry<Character, Integer> entry1 : lista.entrySet()) {
                            double newWaga = wagi.get(index) + (plusCzyMinus * ((float) entry1.getValue() / totalChars) * stalaUczenia);

                            noweWagi.add(newWaga);
                            index++;
                        }
                        wagi = noweWagi;
                        prog += (plusCzyMinus * (-1) * stalaUczenia);
//                        if (prog < 0) {
//                            prog = 0;
//                        }
                        //                    }else if (prog > 1){
                        //                        prog = 1;
                        //                    }
                        toRemove.clear();
                    }
                    else {
                        if(Math.random()<0.1) {
                            toRemove.add(lista);
                        }
                    }
                }


            }
        }
        for(Map.Entry<String, List<Map<Character, Integer>>> entry : dataToTeach.entrySet()){
            for(Map<Character, Integer> remove : toRemove){
                entry.getValue().remove(remove);
            }
        }
        try {
           if(czyJeszczeRaz) {
               if(Math.random()<0.1){
                   teachOwnLanguage();
               }
                teach();
            }
        }catch (StackOverflowError e){
            System.out.println(":cos");
            teachOwnLanguage();
        }
    }
}
