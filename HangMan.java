package impiccato;

import java.util.Random;

public class HangMan {
    private String parola;
    private int[] mask;
    private int tentativiRimasti;

    // Elenco di parole da cui scegliere casualmente
    private static final String[] PAROLE = {
        "supersballo", "cane", "gatto", "elefante", "tigre", "leone", "rinoceronte", "ippopotamo",
        "serpente", "aquila", "falco", "pappagallo", "colomba", "pinguino", "orso", "lupo", "volpe",
        "canguro", "koala", "scimmia", "zebra", "giraffa", "coccodrillo", "squalo", "delfino", "balena",
        "formica", "ape", "vespa", "ragno"
    };

    // Costruttore per inizializzare il gioco con una parola casuale
    public HangMan(int tentativi) {
        Random random = new Random();
        this.parola = PAROLE[random.nextInt(PAROLE.length)].toLowerCase(); // Sceglie una parola casuale
        this.mask = new int[parola.length()];
        this.tentativiRimasti = tentativi;
    }

    // Metodo per verificare se una lettera è presente nella parola
    public boolean indovinaLettera(char lettera) {
        boolean trovata = false;
        for (int i = 0; i < parola.length(); i++) {
            if (parola.charAt(i) == lettera && mask[i] == 0) {
                mask[i] = 1;
                trovata = true;
            }
        }
        if (!trovata) {
            tentativiRimasti--;
        }
        return trovata;
    }

    // Metodo per stampare lo stato corrente del gioco
    public String getStatoCorrente() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parola.length(); i++) {
            if (mask[i] == 1) {
                sb.append(parola.charAt(i));
            } else {
                sb.append("_");
            }
        }
        return sb.toString();
    }

    // Metodo per verificare se il giocatore ha vinto
    public boolean haiVinto() {
        for (int i = 0; i < mask.length; i++) {
            if (mask[i] == 0) {
                return false;
            }
        }
        return true;
    }

    // Metodo per verificare se il giocatore ha perso
    public boolean haiPerso() {
        return tentativiRimasti <= 0;
    }

    // Metodo per ottenere il numero di tentativi rimasti
    public int getTentativiRimasti() {
        return tentativiRimasti;
    }

    // Metodo per ottenere la parola corrente
    public String getParola() {
        return parola;
    }
}