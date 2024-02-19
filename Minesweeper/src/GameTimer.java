public class GameTimer {
    private long seconds;
    private boolean running;

    public GameTimer(long length){
        seconds = 0;
        running = true;
        Thread thread = new Thread(() ->{
            while(seconds < length && running){
                try {
                    Thread.sleep(1000);
                    seconds++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
    public long getTime(){
        return seconds;
    }
    public void stop(){
        this.running = false;
    }
}