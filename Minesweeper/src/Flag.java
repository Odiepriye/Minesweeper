public class Flag{
    private byte flags;
    public Flag(byte flags){
        this.flags = flags;
    }
    public byte getFlags() {
        return flags;
    }
    public void drop(){
        flags--;
    }
    public void lift(){
        flags++;
    }
}