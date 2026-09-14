import java.io.FileWriter;
import java.io.IOException;

public class Image {
    private int width;
    private int height;
    // pixels[y][x][0=R, 1=G, 2=B]
    private int[][][] pixels;

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    /**
     * Constructeur : initialise une image vide (tous les pixels a 0,0,0 = noir).
     */
    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[height][width][3];
    }

    /**
     * Definit la couleur d'un pixel a la position (x, y).
     */
    public void setPixel(int x, int y, int r, int g, int b) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixels[y][x][0] = r;
            pixels[y][x][1] = g;
            pixels[y][x][2] = b;
        }
    }

    /**
     * Sauvegarde l'image au format texte PPM (P3).
     */
    public void save_txt(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("P3\n");
            writer.write(width + " " + height + "\n");
            writer.write("255\n");

            for (int y = 0; y < height; y++) {
                StringBuilder ligne = new StringBuilder();
                for (int x = 0; x < width; x++) {
                    ligne.append(pixels[y][x][0]).append(' ')
                         .append(pixels[y][x][1]).append(' ')
                         .append(pixels[y][x][2]);
                    if (x < width - 1) ligne.append(' ');
                }
                ligne.append('\n');
                writer.write(ligne.toString());
            }

            System.out.println("Image PPM enregistree : " + filename);
        }
    }
}
