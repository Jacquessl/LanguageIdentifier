import java.util.*;

public class Perceptron {
    private List<Double> wagi = new ArrayList<>();
    private List<String> dataToTeach = new ArrayList<>();
    private double prog = 1;
    private final double stalaUczenia = 0.5;
    private Map<Character, Integer> chars = new LinkedHashMap<>();
    public Perceptron(List<String> dataToTeach2){
        for(int i = 97; i<123; i++){
            chars.put((char)i, 0);
        }
        for(String str : dataToTeach2){
            for(char c : str.toLowerCase().toCharArray()){
                if(chars.containsKey(c))
                    chars.put(c, chars.get(c)+1);
            }
            dataToTeach.add(str);
        }
        for(int i = 0; i<4; i++){
            wagi.add(1.0);
        }
    }
    public String analyze(String data){
        float sum = 0;
        int index = 0;
        for(Map.Entry<Character, Integer> entry : chars.entrySet()){
            System.out.println(entry.getKey() + " : "+ entry.getValue());
            try {
                sum += entry.getValue()*wagi.get(index);
                index++;
            }catch (Exception e){

            }
        }
        if (sum > prog){
            return "Iris-versicolor";
        }
        return "Iris-setosa";
    }
    public void teach(){
        String result;
        boolean czyJeszczeRazTeach = false;
        List<String> strToRemove = new ArrayList<>();
        for(String strArr : dataToTeach){
            float sum = 0;
            int index = 0;

                try {
                    sum += wagi.get(index);

                }catch (Exception e){

                }

            if(sum>prog){
                result = "Iris-versicolor";
            }else{
                result = "Iris-setosa";
            }
            if (!result.equals(strArr.trim())){
                czyJeszczeRazTeach = true;
                int plusCzyMinus;
                if(sum>prog){
                    plusCzyMinus = -1;
                }
                else{
                    plusCzyMinus = 1;
                }
                List<Double> noweWagi = new ArrayList<>();
                index = 0;
                for(double i : wagi){
                    noweWagi.add(i+(plusCzyMinus*Double.parseDouble(strArr)*stalaUczenia));
                    index++;
                }
                wagi = noweWagi;
                prog += (plusCzyMinus*(-1)*stalaUczenia);
            }else{
                strToRemove.add(strArr);
            }
        }
        for(String strArr : strToRemove){
            dataToTeach.remove(strArr);
        }
        if(czyJeszczeRazTeach){
            teach();
        }
    }
}
