package impiccato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PlayGUI {
    private HangMan game;
    private JFrame frame;
    private JTextArea wordTextArea;
    private JTextArea attemptsTextArea;
    private JTextField inputField; // Campo di input per le lettere
    private JButton newGameButton;
    private JButton checkAttemptsButton;
    private JButton exitButton;

    public PlayGUI() {
        // Inizializzazione del gioco con una parola casuale
        game = new HangMan(15);

        // Creazione della finestra principale
        frame = new JFrame("Gioco dell'Impiccato");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().setLayout(null); // Absolute Layout

        // Etichetta e area di testo per la parola corrente
        JLabel wordLabel = new JLabel("Parola:");
        wordLabel.setBounds(50, 50, 100, 30);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        wordTextArea = new JTextArea(game.getStatoCorrente());
        wordTextArea.setBounds(150, 50, 200, 30);
        wordTextArea.setEditable(false);
        wordTextArea.setFont(new Font("Arial", Font.PLAIN, 16));

        // Etichetta e area di testo per i tentativi rimasti
        JLabel attemptsLabel = new JLabel("Tentativi:");
        attemptsLabel.setBounds(50, 100, 100, 30);
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        attemptsTextArea = new JTextArea(String.valueOf(game.getTentativiRimasti()));
        attemptsTextArea.setBounds(150, 100, 200, 30);
        attemptsTextArea.setEditable(false);
        attemptsTextArea.setFont(new Font("Arial", Font.PLAIN, 16));

        // Campo di input per le lettere
        JLabel inputLabel = new JLabel("Inserisci lettera:");
        inputLabel.setBounds(50, 150, 128, 30);
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputField = new JTextField();
        inputField.setBounds(188, 152, 50, 30);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().toLowerCase();
                if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                    handleLetterGuess(input.charAt(0));
                    inputField.setText(""); // Pulisce il campo di input
                } else {
                    JOptionPane.showMessageDialog(frame, "Inserisci una sola lettera valida!", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        checkAttemptsButton = new JButton("Verifica Tentativi");
        checkAttemptsButton.setBounds(50, 250, 150, 30);
        checkAttemptsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Tentativi rimasti: " + game.getTentativiRimasti(), "Tentativi", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Pulsante "Nuova Partita"
        newGameButton = new JButton("Nuova Partita");
        newGameButton.setBounds(50, 200, 150, 30);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

   
        // Pulsante "Esci"
        exitButton = new JButton("Esci");
        exitButton.setBounds(250, 250, 150, 30);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Aggiunta dei componenti alla finestra
        frame.getContentPane().add(wordLabel);
        frame.getContentPane().add(wordTextArea);
        frame.getContentPane().add(attemptsLabel);
        frame.getContentPane().add(attemptsTextArea);
        frame.getContentPane().add(inputLabel);
        frame.getContentPane().add(inputField);
        frame.getContentPane().add(newGameButton);
        frame.getContentPane().add(checkAttemptsButton);
        frame.getContentPane().add(exitButton);

        // Rendi visibile la finestra
        frame.setVisible(true);
    }

    // Metodo per resettare il gioco
    private void resetGame() {
        game = new HangMan(15);
        updateUI();
    }

    // Metodo per aggiornare l'interfaccia utente
    private void updateUI() {
        wordTextArea.setText(game.getStatoCorrente());
        attemptsTextArea.setText(String.valueOf(game.getTentativiRimasti()));
    }

    // Metodo per gestire la scelta di una lettera
 // Metodo per gestire la scelta di una lettera
    private void handleLetterGuess(char letter) {
        if (!game.haiVinto() && !game.haiPerso()) {
            boolean trovata = game.indovinaLettera(letter);
            updateUI();

            // Controlla se il giocatore ha vinto o perso
            if (game.haiVinto()) {
                JOptionPane.showMessageDialog(frame, "Complimenti! Hai indovinato la parola: " + game.getStatoCorrente(), "Hai Vinto!", JOptionPane.INFORMATION_MESSAGE);
                resetGame();
            } else if (game.haiPerso()) {
                JOptionPane.showMessageDialog(frame, "Hai perso! La parola era: " + game.getParola(), "Game Over", JOptionPane.ERROR_MESSAGE);
                resetGame();
            }
        }
    }

    // Metodo per fornire un indizio (una lettera casuale non indovinata)
    private void giveHint() {
        String statoCorrente = game.getStatoCorrente();
        String parola = game.getParola();
        Random random = new Random();

        // Trova tutte le lettere non ancora indovinate
        StringBuilder lettereNonIndovinate = new StringBuilder();
        for (int i = 0; i < parola.length(); i++) {
            if (statoCorrente.charAt(i) == '_') {
                lettereNonIndovinate.append(parola.charAt(i));
            }
        }

        if (lettereNonIndovinate.length() > 0) {
            // Scegli una lettera casuale tra quelle non indovinate
            char hint = lettereNonIndovinate.charAt(random.nextInt(lettereNonIndovinate.length()));
            JOptionPane.showMessageDialog(frame, "Indizio: Una delle lettere è '" + hint + "'", "Indizio", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Non ci sono più lettere da suggerire!", "Indizio", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlayGUI());
    }
}