import javax.swing.*;
import java.util.List;

class Main {

    public static void main(String[] args)
    {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFrame();
            }
        });
    }
}
