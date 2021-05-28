package lp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.Controller;
public class VentanaExtraccion extends JFrame {
    private JButton button1;
    private JPanel panel1;

    public VentanaExtraccion(Controller controller)  {
        panel1 = new JPanel();
        this.add(panel1);
        button1 = new JButton("Descargar Datos");
        panel1.add(button1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.extraerDatos();
            }
        });
    }
}
