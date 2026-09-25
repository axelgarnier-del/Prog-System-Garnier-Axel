public class Utils {

    public static int writeInt(byte[] memory, int offset, int value) {
        // TODO: Écrire les 4 octets de 'value' dans 'memory'
        // à partir de 'offset', en big-endian.
		memory[offset] = (byte) (value >>> 24);
        memory[offset + 1] = (byte) (value >>> 16);
        memory[offset + 2] = (byte) (value >>> 8);
        memory[offset + 3] = (byte) value;
        return 4;
    }

    public static int readInt(byte[] memory, int offset) {
        // TODO: Reconstituer le int sur 4 octets.
		return ((memory[offset] & 0xFF) << 24)
         | ((memory[offset + 1] & 0xFF) << 16)
         | ((memory[offset + 2] & 0xFF) << 8)
         |  (memory[offset + 3] & 0xFF);
		 
    }

    public static int writeShort(byte[] memory, int offset, short value) {
        // TODO: Écrire les 2 octets de 'value'.
		memory[offset] = (byte) (value >>> 8);
        memory[offset + 1] = (byte) value;
        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        // TODO: Lire le short sur 2 octets.
        return (short) (((memory[offset] & 0xFF) << 8) | (memory[offset + 1] & 0xFF));
    }

    public static int writeLong(byte[] memory, int offset, long value) {
        // TODO: Écrire les 8 octets du long en big-endian.
        for (int i = 0; i < 8; i++) {
            memory[offset + i] = (byte) (value >>> (56 - 8 * i));
        }
        return 8;
    }

    public static long readLong(byte[] memory, int offset) {
        // TODO: Reconstituer le long.
        long res = 0;
        for (int i = 0; i < 8; i++) {
            res = (res << 8) | (memory[offset + i] & 0xFF);
        }
        return res;
    }

    public static int writeString(byte[] memory, int offset, String str, int maxLength) {
        // TODO:
        // 1. Convertir la chaîne en octets.
        // 2. Copier les octets sans dépasser maxLength.
        // 3. Nettoyer le reste de la zone avec des zéros.
        byte[] b = str.getBytes();
        int n = b.length;
        if (n > maxLength) n = maxLength;
        for (int i = 0; i < n; i++) {
            memory[offset + i] = b[i];
        }
        for (int i = n; i < maxLength; i++) {
            memory[offset + i] = 0;
        }
        return maxLength;
    }

    public static String readString(byte[] memory, int offset, int maxLength) {
        // TODO:
        // Lire jusqu'au premier octet nul
        // ou jusqu'à maxLength.
        int len = 0;
        while (len < maxLength && memory[offset + len] != 0) {
            len++;
        }
        return new String(memory, offset, len);
    }
}