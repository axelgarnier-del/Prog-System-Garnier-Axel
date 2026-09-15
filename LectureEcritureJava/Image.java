import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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

    /**
     * Lit une image au format texte PPM (P3).
     */
    static public Image read_txt(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            List<String> tokens = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                int hash = line.indexOf('#');
                if (hash >= 0) line = line.substring(0, hash);
                for (String tok : line.trim().split("\\s+")) {
                    if (!tok.isEmpty()) tokens.add(tok);
                }
            }

            Iterator<String> it = tokens.iterator();
            String magic = it.next();
            if (!magic.equals("P3")) {
                throw new IOException("Format non supporte : " + magic);
            }
            int w = Integer.parseInt(it.next());
            int h = Integer.parseInt(it.next());
            int maxval = Integer.parseInt(it.next());

            Image img = new Image(w, h);
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int r = Integer.parseInt(it.next());
                    int g = Integer.parseInt(it.next());
                    int b = Integer.parseInt(it.next());
                    img.setPixel(x, y, r, g, b);
                }
            }
            return img;
        }
    }

    /**
     * Sauvegarde l'image au format binaire PPM (P6).
     */
    public void write_bin(String filename) throws IOException {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            String header = "P6\n" + width + " " + height + "\n255\n";
            out.write(header.getBytes("US-ASCII"));

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    out.write(pixels[y][x][0]);
                    out.write(pixels[y][x][1]);
                    out.write(pixels[y][x][2]);
                }
            }
            System.out.println("Image PPM binaire enregistree : " + filename);
        }
    }

    /**
     * Lit une image au format binaire PPM (P6).
     */
    static public Image read_bin(String filename) throws IOException {
        try (FileInputStream in = new FileInputStream(filename)) {
            StringBuilder header = new StringBuilder();
            int nbLignes = 0;
            while (nbLignes < 3) {
                int c = in.read();
                if (c == '\n') nbLignes++;
                header.append((char) c);
            }
            String[] tokens = header.toString().split("\\s+");
            int w = Integer.parseInt(tokens[1]);
            int h = Integer.parseInt(tokens[2]);

            Image img = new Image(w, h);
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int r = in.read();
                    int g = in.read();
                    int b = in.read();
                    img.setPixel(x, y, r, g, b);
                }
            }
            return img;
        }
    }
}
