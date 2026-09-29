import java.io.*;

public class MemoryManager {

    public static final int BLOCK_SIZE = 512;
    public static final int TOTAL_MEMORY = 1024 * 1024;
    public static final int NUM_BLOCKS =
            TOTAL_MEMORY / BLOCK_SIZE;

    public static final int SUPERBLOCK_OFFSET = 0;
    public static final int BITMAP_OFFSET = BLOCK_SIZE;
    public static final int INODE_TABLE_OFFSET =
            2 * BLOCK_SIZE;
    public static final int DATA_OFFSET =
            129 * BLOCK_SIZE;

    public static final int INODE_SIZE = 128;

    public static final int INODE_TABLE_SIZE =
            DATA_OFFSET - INODE_TABLE_OFFSET;

    public static final int MAX_INODES =
            INODE_TABLE_SIZE / INODE_SIZE;

    private byte[] memory;

    public MemoryManager() {
        this.memory = new byte[TOTAL_MEMORY];
        initializeFilesystem();
    }

    private void initializeFilesystem() {
        writeSuperblock();

        // TODO:
        // Réserver les blocs système 0 à 128.
        for (int b = 0; b <= 128; b++) {
            int off = BITMAP_OFFSET + b / 8;
            memory[off] = (byte) (memory[off] | (1 << (b % 8)));
        }
    }

    private void writeSuperblock() {
        // TODO:
        // Utiliser Utils pour écrire les métadonnées.

        Utils.writeString(
                memory,
                SUPERBLOCK_OFFSET,
                "MYFS1.0",
                16);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 16,
                BLOCK_SIZE);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 20,
                TOTAL_MEMORY);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 24,
                NUM_BLOCKS);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 28,
                MAX_INODES);
    }

    public byte[] getFilesystemMemory() {
        return memory;
    }
	public boolean setBlockUsed(int blockNumber, boolean used) {
    if (blockNumber < 0 ||
        blockNumber >= NUM_BLOCKS) {
        return false;
    }

    int byteIndex = blockNumber / 8;
    int bitPosition = blockNumber % 8;
    int offset = BITMAP_OFFSET + byteIndex;

    if (used) {
        // Positionner le bit à 1.
		disk[offset] |= masque;
    } else {
        // Positionner le bit à 0.
		disk[offset] &= ~masque;
    }

    return true;
	}

	public int isBlockUsed(int blockNumber) {

		if (blockNumber < 0 ||
			blockNumber >= NUM_BLOCKS) {
			return -1;
		}

		// TODO:
		// Calculer byteIndex.
		int byteIndex = blockNumber / 8;
		// Calculer bitPosition.
		int bitposition = blockNumber % 8;
		// Lire le bit.
		int offset = BITMAP_OFFSET + byteIndex; 
		int octet = disk[offset] & 0xFF;
		return (octet >> bitPosition) & 1;
	}

	public int allocateBlock() {

		// TODO:
		// Parcourir les blocs de données :
		// 129 .. NUM_BLOCKS - 1.
		// Retourner le premier bloc libre.
		// Le marquer immédiatement comme utilisé.
		for (int i = 129; i < NUM_BLOCKS; i++){
			if ()
		}isBlockUsed(i) = 0) {
			setBlockUsed(i, true);
			return i ;
		}

		return -1;
	}
}
