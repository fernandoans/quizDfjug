import java.io.*;
import javax.swing.JOptionPane;

/**
 * Quiz do DFJUG
 * Controle do arquivo de perguntas
 * @autor Fernando Anselmo
 */
public class TrataArquivo {

    /** Quantidade de questões no arquivo */
    private final int TOTAL_PERGUNTA = 10;

    /**
     * Obter uma determinada pergunta
     */
    public String getPergunta(int num) {
        if (num > TOTAL_PERGUNTA) {
           JOptionPane.showMessageDialog(null, "Erro: Pergunta não foi encontrada", "Quiz DFJUG",
                JOptionPane.ERROR_MESSAGE);
            return "Pergunta não foi encontrada";
        }
        // ***
        String pergunta = "";
        try {
            BufferedReader arqEntrada = new BufferedReader(new FileReader("pergunta.dfj"));
            int qual = 1;
            String linha = "";
            while ((linha = arqEntrada.readLine()) != null) {
                if (qual++ == num) {
                    pergunta = linha;
                    break;
                }
            }
        } catch (IOException e) {
           JOptionPane.showMessageDialog(null, "Erro Leitura: " + e.getMessage(), "Quiz DFJUG",
                JOptionPane.ERROR_MESSAGE);
        }
        return pergunta.replace("<br>","\n");
    }
}