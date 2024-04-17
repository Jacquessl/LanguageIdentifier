import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

class MyFrame
        extends JFrame
        implements ActionListener {

    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextArea tekst;
    private JScrollPane tekstScroll;
    private JButton sub;
    private JButton reset;
    private JButton zbiorTestowy;
    private JTextPane tout;
    private Map<String, List<Map<Character, Integer>>> data;

    private List<Perceptron> pc = new ArrayList<>();
    public MyFrame()
    {
        ReadData rd = new ReadData("C:\\Users\\litwi\\Desktop\\NAIMPP\\LanguageIdentifier\\LanguageIdentifier\\Languages");
        data = rd.readData();
        double wagiVal = Math.random();
        for(Map.Entry<String, List<Map<Character, Integer>>> entry : data.entrySet()) {
            pc.add(new Perceptron(data, entry.getKey(), wagiVal));
        }
        int index = 0;
        for(Perceptron p : pc){
            try {
                p.setNextLanguage(pc.get(index - 1).getLanguage());
                p.teach();
                index++;
            }catch (IndexOutOfBoundsException e){
                p.setNextLanguage(pc.get(pc.size()-1).getLanguage());
                p.teach();
                index++;
            }
        }
        setTitle("Rozpoznawanie Języka");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Rozpoznawanie Języka");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(400, 30);
        title.setLocation(250, 30);
        c.add(title);

        name = new JLabel("Tekst");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(80, 30);
        name.setLocation(20, 100);
        c.add(name);
        tekst = new JTextArea();
        tekst.setFont(new Font("Arial", Font.PLAIN, 15));
        tekst.setLineWrap(true);
        tekst.setWrapStyleWord(true);
        tekst.setMargin(new Insets(10, 10, 10, 10));


        tekstScroll = new JScrollPane(tekst);
        tekstScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tekstScroll.setSize(290, 300);
        tekstScroll.setLocation(120, 100);
        c.add(tekstScroll);
        sub = new JButton("Analizuj");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 400);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 400);
        reset.addActionListener(this);
        c.add(reset);

        zbiorTestowy = new JButton("Uruchom Testowy Zbiór");
        zbiorTestowy.setFont(new Font("Arial", Font.PLAIN, 15));
        zbiorTestowy.setSize(220, 20);
        zbiorTestowy.setLocation(150, 450);
        zbiorTestowy.addActionListener(this);
        c.add(zbiorTestowy);

        tout = new JTextPane();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setSize(300, 400);
        tout.setLocation(500, 100);
        tout.setEditable(false);
        c.add(tout);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == sub) {
            String dataToPrint = "";
            String dataToAnalyze = tekst.getText();
            Map<Double, String> results = new HashMap<>();
            for(Perceptron percc : pc){
                results.put(percc.analyze(dataToAnalyze), percc.getLanguage());
            }
           // try {
            double max = -10000;
            for(Map.Entry<Double, String> entry : results.entrySet()){
                if(entry.getKey()>max){
                    max = entry.getKey();
                }
            }


            dataToPrint += results.get(max);

            String countryCode = getCode(dataToPrint);

//                if (wypisywacDokladnosc) {
//                    try {
//                        int dokladnoscDoWypisania = (accurateTest * 100) / possibleTest;
//                        tout.setText("<html><body><div style='font-family: Arial, Helvetica, sans-serif; font-size: 15pt; text-align: center;'>" + dataToPrint +
//                                "<img src=\'file:img/" + result.toLowerCase().trim() + ".jpg\'/><br>Dokladność: " + dokladnoscDoWypisania + "%</div></body></html>");
//                    }catch (IndexOutOfBoundsException ex){
//                        tout.setText("<html><body><div style='font-family: Arial, Helvetica, sans-serif; font-size: 15pt; text-align: center;'>" + dataToPrint +
//                                "<img src=\'file:img\\" + result.toLowerCase().trim() + ".jpg\'/></div></body></html>");
//                    }
//                } else {

            tout.setContentType("text/html");
                    tout.setText("<html><head><meta charset=\"UTF-8\"></head><body style='font-family: Arial, Helvetica, sans-serif; font-size: 15pt; text-align: center;'><h1>" + dataToPrint.substring(0,1).toUpperCase() + "" + dataToPrint.substring(1) + "</h1><div>"+
                            "<img src=\"https://flagsapi.com/" + countryCode + "/shiny/64.png\"/></div></body></html>");
//                }
            //tout.setText();
            tout.setEditable(false);
           // }catch (NumberFormatException ex){
                //tout.setText("Wpisz wartości");
                //tout.setEditable(false);

           // }
        }
        else if (e.getSource() == reset) {

            String def = "";
            tekst.setText(def);
            tout.setText(def);
        }
        else if(e.getSource() == zbiorTestowy) {
//            try {
//                ReadData rd = new ReadData("Languages");
//                testData = rd.readData();
//                accurateTest = 0;
//                possibleTest = 1;
//                JFormattedTextField textFields = tekst;
//                SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
//                    @Override
//                    protected Void doInBackground() throws Exception {
//                        for (String str : testData) {
//                            reset.doClick();
//                            textFields.setText(str.replace(".", ","));
//                            Thread.sleep(100);
//                            wypisywacDokladnosc = true;
//                            sub.doClick();
//                            testIndex++;
//                            possibleTest++;
//                            Thread.sleep(2000);
//
//                            wypisywacDokladnosc=false;
//                        }
//                        testIndex=0;
//                        return null;
//                    }
//
//                    @Override
//                    protected void done() {
//                        workerDone = true;
//                    }
//                };
////                worker.execute();
//            } catch (NullPointerException exc) {
//                tout.setText("Wpisz stałą uczenia");
//            }
        }
    }
    private String getCode(String dataToPrint){
        String countryCode = "";
        for(Locale avaliable : Locale.getAvailableLocales()){
            if(avaliable.getDisplayLanguage().toLowerCase().equals(dataToPrint)) {
                if (avaliable.getDisplayCountry(Locale.ENGLISH).equals("United Kingdom") || !avaliable.getDisplayLanguage(Locale.ENGLISH).equals("English")) {
                    if(!avaliable.getCountry().equals(""))
                        return avaliable.getCountry();
                }
            }
        }
        return countryCode;
    }
}
