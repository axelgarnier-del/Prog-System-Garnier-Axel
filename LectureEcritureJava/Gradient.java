import java.io.IOException;

public class Gradient {
    public static void main(String[] args) {
        int largeur = 200;
        int hauteur = 100;

        Image img = new Image(largeur, hauteur);

        // Degrade horizontal du noir vers le bleu :
        // pour chaque colonne x, la composante bleue vaut x * 255 / (largeur - 1).
        // R et G restent a 0, seul B varie de 0 (a gauche) a 255 (a droite).
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                int bleu = x * 255 / (largeur - 1);
                img.setPixel(x, y, 0, 0, bleu);
            }
        }

        try {
            img.save_txt("gradient.ppm");
        } catch (IOException e) {
            System.err.println("Erreur d'ecriture : " + e.getMessage());
        }
    }
}
