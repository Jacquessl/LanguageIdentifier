import javax.swing.*;
import java.util.List;
import java.util.Locale;

class Main {

    public static void main(String[] args)
    {
        Locale.setDefault(new Locale("pl", "PL"));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFrame();
            }
        });
    }
}
