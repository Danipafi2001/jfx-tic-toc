package model;

public class Toc {
	
	private long initial, pause, gap;
	private boolean stop;
	
	public Toc() {
		initial = System.currentTimeMillis();
		pause = 0;
		gap = 0;
		stop = true;
	}
	
	public String totalTime() {
		long current = System.currentTimeMillis();
		long total = current - initial - gap;
		long seconds = total / 1000;
		long minutes = seconds >= 60 ? seconds / 60 : 0;
		seconds = seconds >= 60 ? seconds - (seconds / 60) * 60 : seconds;
		long millis = total / 10;
		millis = millis >= 100 ? millis - ((millis / 100) * 100) : millis;
		return minutes + "," + String.format("%02d", seconds) + "," + String.format("%02d", millis);
	}
	
	public void totalGap() {
		long current = System.currentTimeMillis();
		gap = pause != 0 ? gap + current - pause : 0;
	}
	
	public void setPause(long p) {
		pause = p;
	}
	
	public boolean isStop() {
		return stop;
	}
	
	public void setStop(boolean s) {
		stop = s;
	}
	
	public void reset() {
		initial = System.currentTimeMillis();
		pause = 0;
		gap = 0;
	}
}
