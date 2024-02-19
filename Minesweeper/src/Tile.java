public class Tile {
    private boolean isBomb;
    private boolean isFlipped;
    private boolean isFlagged;
    private byte number;

    public boolean isFlagged() {
        return isFlagged;
    }
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
    public boolean isBomb() {
        return isBomb;
    }
    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }
    public boolean isFlipped() {
        return isFlipped;
    }
    public void flip() {
        isFlipped = true;
    }
    public byte getNumber() {
        return number;
    }

    public void setNumber(byte number) {
        this.number = number;
    }
}
