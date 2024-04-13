import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MyFrame
        extends JFrame
        implements ActionListener {

    private Container c;
    private JLabel title;
    private JLabel name;
    private JFormattedTextField tekst;
    private JButton sub;
    private JButton reset;
    private JButton zbiorTestowy;
    private JTextPane tout;
    private Map<String, List<String>> data;
    private boolean workerDone = true;
    private List<String> testData;
    private int testIndex;
    private int accurateTest;
    private int possibleTest;
    private boolean wypisywacDokladnosc = false;
    private List<Perceptron> pc = new ArrayList<>();
    public MyFrame()
    {
        ReadData rd = new ReadData("C:\\Users\\litwi\\Desktop\\NAIMPP\\LanguageIdentifier\\LanguageIdentifier\\Languages");
        data = rd.readData();

        for(Map.Entry<String, List<String>> entry : data.entrySet()) {
            pc.add(new Perceptron(data, entry.getKey()));
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

        name = new JLabel("Długość Listka (cm)");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(180, 30);
        name.setLocation(20, 100);
        c.add(name);

        tekst = new JFormattedTextField();
        tekst.setFont(new Font("Arial", Font.PLAIN, 15));
        tekst.setSize(190, 300);
        tekst.setLocation(220, 100);
        c.add(tekst);


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
            try {
                double max = 0;
                for(Map.Entry<Double, String> entry : results.entrySet()){
                    if(entry.getKey()>max){
                        max = entry.getKey();
                    }
                }


                dataToPrint += results.get(max);
//                tout.setContentType("text/html");
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
//                    tout.setText("<html><body><div style='font-family: Arial, Helvetica, sans-serif; font-size: 15pt; text-align: center;'>" + dataToPrint +
//                            "<img src=\'file:img\\" + result.toLowerCase().trim() + ".jpg\'/></div></body></html>");
//                }
                tout.setText(dataToPrint);
                tout.setEditable(false);
            }catch (NumberFormatException ex){
                tout.setText("Wpisz wartości");
                tout.setEditable(false);

            }
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
}
