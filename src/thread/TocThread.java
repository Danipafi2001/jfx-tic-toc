package thread;

import javafx.application.Platform;
import model.Toc;
import ui.TicToc;

public class TocThread extends Thread {

	private TicToc ticToc;
	private Toc toc, flag;

	public TocThread(TicToc tt, Toc t, Toc f) {
		ticToc = tt;
		toc = t;
		flag = f;
	}

	@Override
	public void run() {
		toc.totalGap();
		flag.totalGap();
		while(!toc.isStop()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					ticToc.updateToc();
				}
			});
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
		toc.setPause(System.currentTimeMillis());
		flag.setPause(System.currentTimeMillis());
	}
}
