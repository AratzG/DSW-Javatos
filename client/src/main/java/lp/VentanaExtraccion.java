package lp;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaExtraccion {
    private JFrame frame = new JFrame("Ventana de Extraccion de Datos");
    private JPanel panelPrincipal;
    private JButton botonExtraccion;

    public VentanaExtraccion(Controller controller) {

        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        botonExtraccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Extraccion realizada");
                controller.extraerDatos();

            }
        });
    }

}
