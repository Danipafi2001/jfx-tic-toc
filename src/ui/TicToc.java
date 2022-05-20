package ui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Tic;
import model.Toc;
import thread.TicThread;
import thread.TocThread;

public class TicToc {

	@FXML
	private VBox mainPane;
	@FXML
	private GridPane flags;
	@FXML
	private ImageView tocImage;
	@FXML
	private Label ticTime, ticDate, ticCity, ticTimeZone, ticZone, tocSecs, tocMillis, tocNumber, tocFlag, tocTotal;
	
	private Tic tic, zone;
	private Toc toc, flag;
	private ArrayList<Label> list;
	private ArrayList<String> timeZones;
	
	public void initialize() {
		timeZones = new ArrayList<>();
		timeZones.add("Los Angeles,-2h");
		timeZones.add("Managua,-1h");
		timeZones.add("Buenos Aires,+2h");
		timeZones.add("London,+6h");
		timeZones.add("Amsterdam,+7h");
		timeZones.add("Moscu,+8h");
		timeZones.add("Islambad,+10h");
		timeZones.add("Shangai,+13h");
		timeZones.add("Tokio,+14h");
		timeZones.add("Sidney,+15h");
		timeZones.add("Rio de Janeiro,+2h");
	}

	@FXML
	public void changeToTic(ActionEvent event) throws IOException {
		tocReset();
		loaderPane("fxml/tic.fxml");
		playTic();
	}
	
	public void playTic() {
		tic = new Tic();
		zone = new Tic();
		zone.setPlus(-2l);
		TicThread thread = new TicThread(this, tic);
		thread.setDaemon(true);
		thread.start();
	}

	@FXML
	public void changeToToc(ActionEvent event) throws IOException {
		tic.stop();
		loaderPane("fxml/toc.fxml");
	}
	
	@FXML
	public void tocStatus(ActionEvent event) {
		if(toc == null) {
			toc = new Toc();
			flag = new Toc();
			list = new ArrayList<Label>();
		}
		if(toc.isStop()) {
			toc.setStop(false);
			flag.setStop(false);
			TocThread tocThread = new TocThread(this, toc, flag);
			tocThread.setDaemon(true);
			tocThread.start();
			tocImage.setImage(new Image(getClass().getResourceAsStream("png/pause.png")));
		} else {
			toc.setStop(true);
			flag.setStop(true);
			tocImage.setImage(new Image(getClass().getResourceAsStream("png/play.png")));
		}
	}

	@FXML
	public void tocReset(ActionEvent event) throws IOException {
		tocReset();
		loaderPane("fxml/toc.fxml");
	}
	
	public void tocReset() {
		if(toc != null) {
			toc.setStop(true);
			flag.setStop(true);
			toc = null;
			flag = null;
		}
	}

	@FXML
    public void tocSave(ActionEvent event) {
		if(toc != null && !toc.isStop()) {
			flag.reset();
			int n = Integer.parseInt(tocNumber.getText().substring(1));
			tocNumber.setText("#" + (n+1));
			Label number = new Label("#" + n);
			number.setStyle("-fx-text-fill: orange; -fx-font-size: 14px; -fx-font-family: Menlo");
			Label flag = new Label(tocFlag.getText());
			flag.setStyle("-fx-text-fill: white; -fx-padding: 0 15 0 15; -fx-font-size: 14px; -fx-font-family: Menlo");
			Label total = new Label(tocTotal.getText());
			total.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Menlo");
			while(list.size() > 24) {
				flags.getChildren().remove(list.get(list.size() - 1));
				list.remove(list.size() - 1);
			}
			for(int i = 1, k = 0; i < 9 && k < list.size(); i++) {
				GridPane.setRowIndex(list.get(k), i + 1);
				GridPane.setRowIndex(list.get(k + 1), i + 1);
				GridPane.setRowIndex(list.get(k + 2), i + 1);
				k = k + 3;
			}
			flags.add(number, 0, 1);
			flags.add(flag, 1, 1);
			flags.add(total, 2, 1);
			list.add(0, number);
			list.add(0, flag);
			list.add(0, total);
		}
    }
	
	public void updateToc() {
		String time = toc.totalTime();
		String[] times = time.split(",");
		if(times[0].equals("0"))
			tocSecs.setText(times[1]);
		else
			tocSecs.setText(times[0] + ":" + times[1]);
		tocMillis.setText(times[2]);
		tocTotal.setText(times[0] + " " + times[1] + "," + times[2]);
		time = flag.totalTime();
		times = time.split(",");
		tocFlag.setText(times[0] + " " + times[1] + "," + times[2]);
	}
	
	@FXML
    public void ticNext(ActionEvent event) {
		int i = timeZones.indexOf(ticCity.getText() + "," + ticTimeZone.getText());
    	if(i == timeZones.size() - 1)
    		i = 0;
    	else
    		i++;
    	String[] timeZone = timeZones.get(i).split(",");
    	ticCity.setText(timeZone[0]);
    	ticTimeZone.setText(timeZone[1]);
    	zone.setPlus(Long.parseLong(timeZone[1].substring(0, timeZone[1].length() - 1)));
    }

    @FXML
    public void ticPrev(ActionEvent event) {
    	int i = timeZones.indexOf(ticCity.getText() + "," + ticTimeZone.getText());
    	if(i == 0)
    		i = timeZones.size() - 1;
    	else
    		i--;
    	String[] timeZone = timeZones.get(i).split(",");
    	ticCity.setText(timeZone[0]);
    	ticTimeZone.setText(timeZone[1]);
    	zone.setPlus(Long.parseLong(timeZone[1].substring(0, timeZone[1].length() - 1)));
    }

	public void updateTic(LocalDateTime now) {
		String hour = String.format("%02d", now.getHour());
        String minute = String.format("%02d", now.getMinute());
        String second = String.format("%02d", now.getSecond());
        ticTime.setText(hour+":"+minute+":"+second);
        String dayOfWeek = String.valueOf(now.getDayOfWeek()).substring(0, 3);
        dayOfWeek = dayOfWeek.charAt(0)+dayOfWeek.toLowerCase().substring(1);
        String month = now.getMonth().name().substring(0, 3);
        month = month.charAt(0)+month.toLowerCase().substring(1);
        String dayOfMonth = String.valueOf(now.getDayOfMonth());
        ticDate.setText(dayOfWeek+"., "+month+" "+dayOfMonth);
        now = zone.now();
        hour = String.format("%02d", now.getHour());
        minute = String.format("%02d", now.getMinute());
        second = String.format("%02d", now.getSecond());
        ticZone.setText(hour+":"+minute+":"+second);
	}

	public void loaderPane(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
		fxmlLoader.setController(this);
		Parent root = fxmlLoader.load();
		mainPane.getChildren().clear();
		mainPane.getChildren().add(root);
	}
}
