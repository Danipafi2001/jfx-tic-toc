package thread;

import javafx.application.Platform;
import model.Tic;
import ui.TicToc;

public class TicThread extends Thread {

	private TicToc ticToc;
	private Tic tic;

	public TicThread(TicToc tt, Tic t) {
		ticToc = tt;
		tic = t;
	}

	@Override
	public void run() {
		while(!tic.isStop()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					ticToc.updateTic(tic.now());
				}
			});
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
	}
}
