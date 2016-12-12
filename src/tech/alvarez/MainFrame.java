package tech.alvarez;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created on 12/11/16.
 *
 * @author Daniel Alvarez
 */
public class MainFrame extends JFrame implements ActionListener {

    private ProcessSimulator P;
    private Chart grafico;
    private JButton adicionarButton;
    private JButton algoritmo1Button;
    private JButton algoritmo2Button;
    private JButton algoritmo3Button;
    private JButton algoritmo4Button;
    private JLabel tiempoInicioLabel;
    private JLabel tiempoCpuLabel;
    private JTextField tiempoInicioTextField;
    private JTextField tiempoCpuTextField;
    private JPanel aPanel;
    private JPanel bPanel;
    private JPanel cPanel;
    private JPanel dPanel;

    public MainFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        P = new ProcessSimulator();
        initComponents();
        configurarVentana();
    }

    public void initComponents() {
        setLayout(null);
        aPanel = new JPanel();
        bPanel = new JPanel();
        cPanel = new JPanel();
        dPanel = new JPanel();
        aPanel.setBorder(BorderFactory.createTitledBorder("Proceso"));
        aPanel.setBounds(5, 0, 100, 150);
        tiempoInicioLabel = new JLabel("Tiempo de Inicio");
        tiempoInicioLabel.setFont(new Font(tiempoInicioLabel.getName(), Font.PLAIN, 10));
        tiempoCpuLabel = new JLabel("Tiempo de CPU");
        tiempoCpuLabel.setFont(new Font(tiempoCpuLabel.getName(), Font.PLAIN, 10));
        tiempoInicioTextField = new JTextField(7);
        tiempoCpuTextField = new JTextField(7);
        adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(this);
        aPanel.add(tiempoInicioLabel);
        aPanel.add(tiempoInicioTextField);
        aPanel.add(tiempoCpuLabel);
        aPanel.add(tiempoCpuTextField);
        aPanel.add(adicionarButton);
        bPanel.setBorder(BorderFactory.createTitledBorder("Gráfica"));
        bPanel.setBounds(5, 150, 560, 290);
        grafico = new Chart();
        bPanel.add(grafico);
        cPanel.setBorder(BorderFactory.createTitledBorder("Tabla"));
        cPanel.setBounds(105, 0, 360, 150);
        JTable tabla = new JTable(new String[0][], new String[]{"Proceso", "t ini", "t cpu", "t fin", "T", "E", "I"});
        tabla.setPreferredScrollableViewportSize(new Dimension(340, 85));
        JScrollPane scrollPane = new JScrollPane(tabla);
        cPanel.add(scrollPane);
        dPanel.setBorder(BorderFactory.createTitledBorder("Algoritmos"));
        dPanel.setBounds(465, 0, 100, 150);
        algoritmo1Button = new JButton("F.I.F.O.");
        algoritmo2Button = new JButton("R.R. (Q=1)");
        algoritmo4Button = new JButton("H.R.N.");
        algoritmo1Button.addActionListener(this);
        algoritmo2Button.addActionListener(this);
        algoritmo4Button.addActionListener(this);
        dPanel.add(algoritmo1Button);
        dPanel.add(algoritmo2Button);
        dPanel.add(algoritmo4Button);
        add(aPanel);
        add(bPanel);
        add(cPanel);
        add(dPanel);
    }

    public void configurarVentana() {
        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - 575) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 470) / 2, 575, 470);
        setDefaultCloseOperation(3);
        setTitle("Simulador Planificación de Procesos 1.0.1");
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        JTable tabla;
        JScrollPane scrollPane;
        if (adicionarButton == ae.getSource()) {
            try {
                P.adicionarProceso(Integer.parseInt(tiempoInicioTextField.getText()), Integer.parseInt(tiempoCpuTextField.getText()));
            } catch (Exception var4) {
            }

            tiempoInicioTextField.setText("");
            tiempoCpuTextField.setText("");
            cPanel.setVisible(false);
            remove(cPanel);
            cPanel = new JPanel();
            cPanel.setBorder(BorderFactory.createTitledBorder("Tabla"));
            cPanel.setBounds(105, 0, 360, 150);
            tabla = new JTable(P.getDatosI(), new String[]{"Proceso", "t ini", "t cpu", "t fin", "T", "E", "I"});
            tabla.setPreferredScrollableViewportSize(new Dimension(340, 85));
            scrollPane = new JScrollPane(tabla);
            cPanel.add(scrollPane);
            add(cPanel);
        }

        if (algoritmo1Button == ae.getSource() && P.getNP() > 0) {
            P.fifo();
            cPanel.setVisible(false);
            remove(cPanel);
            cPanel = new JPanel();
            cPanel.setBorder(BorderFactory.createTitledBorder("Tabla"));
            cPanel.setBounds(105, 0, 360, 150);
            tabla = new JTable(P.getDatos(), new String[]{"Proceso", "t ini", "t cpu", "t fin", "T", "E", "I"});
            tabla.setPreferredScrollableViewportSize(new Dimension(340, 85));
            scrollPane = new JScrollPane(tabla);
            cPanel.add(scrollPane);
            add(cPanel);
            grafico.setVisible(false);
            grafico.setX(P.maximoY() - P.getIni()[0] + 1);
            grafico.setY(P.getNP());
            grafico.setD(P.getDatos2());
            grafico.repaint();
            grafico.setVisible(true);
        }

        if (algoritmo4Button == ae.getSource() && P.getNP() > 0) {
            P.hrn();
            cPanel.setVisible(false);
            remove(cPanel);
            cPanel = new JPanel();
            cPanel.setBorder(BorderFactory.createTitledBorder("Tabla"));
            cPanel.setBounds(105, 0, 360, 150);
            tabla = new JTable(P.getDatos(), new String[]{"Proceso", "t ini", "t cpu", "t fin", "T", "E", "I"});
            tabla.setPreferredScrollableViewportSize(new Dimension(340, 85));
            scrollPane = new JScrollPane(tabla);
            cPanel.add(scrollPane);
            add(cPanel);
            grafico.setVisible(false);
            grafico.setX(P.maximoY() - P.getIni()[0] + 1);
            grafico.setY(P.getNP());
            grafico.setD(P.getDatos2());
            grafico.repaint();
            grafico.setVisible(true);
        }

        if (algoritmo2Button == ae.getSource()) {
            P.rr();
            cPanel.setVisible(false);
            remove(cPanel);
            cPanel = new JPanel();
            cPanel.setBorder(BorderFactory.createTitledBorder("Tabla"));
            cPanel.setBounds(105, 0, 360, 150);
            tabla = new JTable(P.getDatos(), new String[]{"Proceso", "t ini", "t cpu", "t fin", "T", "E", "I"});
            tabla.setPreferredScrollableViewportSize(new Dimension(340, 85));
            scrollPane = new JScrollPane(tabla);
            cPanel.add(scrollPane);
            add(cPanel);
            grafico.setVisible(false);
            grafico.setX(P.maximoY() - P.getIni()[0] + 1);
            grafico.setY(P.getNP());
            grafico.setD(P.getDatos2());
            grafico.repaint();
            grafico.setVisible(true);
        }

    }

}
