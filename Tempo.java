/**
 * Quiz do DFJUG
 * Controle do Cronometro
 * @autor Fernando Anselmo
 */
public class Tempo {

    /** Para calculos do tempo */
    private int intHora;
    
    /**
     * Construtor do cronometro recebe o tempo maximo
     */
    public Tempo(int intHora) {
        this.intHora = intHora;
    }
    
    /**
     * Verificar se esta zerado
     */
    public boolean isMaiorZero() {
        return intHora > 0;
    }
    
    /**
     * Diminui um segundo no tempo
     */
    public void reduz() {
        intHora--;
    }
    
    /**
     * Enviar um novo tempo maximo (normalmente usado para zerar o cronometro)
     */
    public void setIntHora(int intHora) {
        this.intHora = intHora;
    }
    
    /**
     * Transforma os segundos em HH:MM:SS
     */
    public String transHora() {
        int resta = intHora;
        int horHora = 0;
        int minHora = 0;
        int segHora = 0;
        if (resta > 0) {
            horHora = resta / 60 / 60;
            resta -= horHora * 3600;
            if (resta > 0) {
                minHora = resta / 60;
                segHora = resta - minHora * 60;
            }
        }
        return addZero(horHora,2) + ":" + addZero(minHora,2) + ":" + addZero(segHora,2);
    }

    /**
     * Coloca zeros a esquerda
     */
    public String addZero(int num, int tam) {
        StringBuilder ret = new StringBuilder(""+num);
        while (ret.length() < tam) {
            ret.insert(0,"0");
        }
        return ret.toString();
    }

}