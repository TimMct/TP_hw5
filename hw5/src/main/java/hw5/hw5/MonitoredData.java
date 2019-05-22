package hw5.hw5;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitoredData {

	private Date startTime;
	private Date endTime;
	private String activity;
	
	
	public MonitoredData(Date sT, Date eT, String a) {
		startTime = sT;
		endTime = eT;
		activity = a;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public String getActivity() {
		return activity;
	}
	
	
	public Long computeTime() {
		return (getEndTime().getTime() - getStartTime().getTime()) / 1000;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "Start>>"+sdf.format(startTime)+"    end>>"+sdf.format(endTime)+"    activity>>"+activity;
	}
}
