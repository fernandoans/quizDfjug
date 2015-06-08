import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Quiz do DFJUG
 * Janela principal do sistema
 * @autor Fernando Anselmo
 */
public class Quizz extends JFrame implements Runnable {

    /** Tempo padrao em Segundos */
    private final int TEMPO_PADRAO = 10;
    /** Controla a execucao do cronometro */
    private boolean rodar;
    /** Tratamento do arquivo */
    private TrataArquivo trArquivo;
    // Objetos de tela
    private JLabel lbTempo;
    private JTextArea txPergunta;
    private Thread th;
    private Tempo tempo;
    private JButton btParar;
    private JButton btContinuar;
    private JButton btEsconder;
    private JButton btMostrar;
    private JButton btFinalizar;
    private JTextField txNumPergunta;
    private JButton btObter;

    /**
     * Construtor da Janela
     */
    public Quizz() {
        super("Quiz DFJUG");
        this.setSize(650,600);
        trArquivo = new TrataArquivo();
        // Cronômetro
        JPanel pnCronos = new JPanel();
        tempo = new Tempo(TEMPO_PADRAO);
        lbTempo = new JLabel(tempo.transHora());
        lbTempo.setFont(new Font("Arial", Font.BOLD, 30));
        pnCronos.add(lbTempo);
        this.add(pnCronos, BorderLayout.NORTH);
        // Painel Central
        txPergunta = new JTextArea("\nQUIZ do DFJUG.\n\nVocê está pronto para o Desafio?");
        txPergunta.setFont(new Font("Courier New", Font.BOLD, 15));
        txPergunta.setForeground(Color.BLACK);
        txPergunta.setBackground(Color.WHITE);
        txPergunta.setLineWrap(true);
        txPergunta.setEditable(false);
        this.add(txPergunta, BorderLayout.CENTER);
        this.add(new JLabel("   "), BorderLayout.EAST);
        this.add(new JLabel("   "), BorderLayout.WEST);
        // Barra de Botões
        JPanel pnBarButtons = new JPanel();
        btParar = new JButton("Parar");
        btParar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parar();
            }
        });
        btContinuar = new JButton("Continuar");
        btContinuar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                continuar();
            }
        });
        btEsconder = new JButton("Esconder");
        btEsconder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                esconder();
            }
        });
        btMostrar = new JButton("Mostrar");
        btMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrar();
            }
        });
        btFinalizar = new JButton("Finalizar");
        btFinalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                finalizar();
            }
        });
        txNumPergunta = new JTextField("1",2);
        btObter = new JButton("Obter");
        btObter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                obter();
            }
        });
        pnBarButtons.add(btParar);
        pnBarButtons.add(btContinuar);
        pnBarButtons.add(btFinalizar);
        pnBarButtons.add(btEsconder);
        pnBarButtons.add(btMostrar);
        pnBarButtons.add(new JLabel("      Nº Pergunta:"));
        pnBarButtons.add(txNumPergunta);
        pnBarButtons.add(btObter);
        this.add(pnBarButtons, BorderLayout.SOUTH);
        // Corrigir Botoes
        this.setBotaoEstadoInicial();
        // Mostrar Tela
        this.setVisible(true);
    }

    /**
     * Colocar os botões em estado inicial de tela
     */
    private void setBotaoEstadoInicial() {
        btParar.setEnabled(false);
        btContinuar.setEnabled(false);
        btFinalizar.setEnabled(false);
        btEsconder.setEnabled(false);
        btMostrar.setEnabled(false);
        btObter.setEnabled(true);
    }
    
    /**
     * Interromper o cronometro
     */
    private void parar() {
        rodar = false;
        // Corrigir Botoes
        btParar.setEnabled(false);
        btContinuar.setEnabled(true);
        btFinalizar.setEnabled(false);
    }

    /**
     * Continuar o cronometro
     */
    private void continuar() {
        rodar = true;
        // Corrigir Botoes
        btParar.setEnabled(true);
        btContinuar.setEnabled(false);
        btFinalizar.setEnabled(true);
    }
    
    /**
     * Finalizar o tempo
     */
    private void finalizar() {
        tempo.setIntHora(0);
        lbTempo.setText(tempo.transHora());
        this.setBotaoEstadoInicial();
    }
    
    /**
     * Esconde a pergunta em uma tela preta
     */
    private void esconder() {
        txPergunta.setBackground(Color.BLACK);
        // Corrigir Botoes
        btEsconder.setEnabled(false);
        btMostrar.setEnabled(true);
    }
    
    /**
     * Mostra novamente a pergunta
     */
    private void mostrar() {
        txPergunta.setBackground(Color.WHITE);
        // Corrigir Botoes
        btEsconder.setEnabled(true);
        btMostrar.setEnabled(false);
    }
    
    /**
     * Obter uma nova pergunta com base no numero desta
     */
    private void obter() {
        try {
            int num = Integer.parseInt(txNumPergunta.getText());
            txPergunta.setText(trArquivo.getPergunta(num));
            this.iniciar();
            this.continuar();
            this.mostrar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Verifique o número da pergunta", "Quiz DFJUG",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Iniciar o cronometro
     */
    private void iniciar() {
        tempo.setIntHora(TEMPO_PADRAO);
        th = new Thread(this);
        th.start();
    }
    
    /**
     * Metodo padrao da Thread para rodar o cronometro
     */
    public void run() {
        while (tempo.isMaiorZero()) {
           lbTempo.setText(tempo.transHora());
           if (rodar) {
               tempo.reduz();
           }
           try {
               th.sleep(1000L);
           } catch (Exception e) {
           }
        }
        lbTempo.setText(tempo.transHora());
        this.setBotaoEstadoInicial();
        this.esconder();
    }

    /**
     * Metodo de entrada principal
     */
    public static void main(String [] args) {
        new Quizz();
    }
}