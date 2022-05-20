package model;

import java.time.LocalDateTime;

public class Tic {
	
	private long plus;
	public boolean stop;
	
	public Tic () {
		plus = 0;
		stop = false;
	}
	
	public LocalDateTime now() {
		return LocalDateTime.now().plusHours(plus);
	}
	
	public void setPlus(Long p) {
		plus = p;
	}
	
	public void stop() {
		stop = true;
	}
	
	public boolean isStop() {
		return stop;
	}
}
