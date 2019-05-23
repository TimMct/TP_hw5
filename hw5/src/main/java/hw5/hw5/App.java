package hw5.hw5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class App {
    
	@SuppressWarnings("deprecation")
	public static void main( String[] args ){

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Stream<String> fileData = null;
		List<MonitoredData> monitoredData = new ArrayList<MonitoredData>();
		
		//1
		try{
			//m-am inspirat de aici https://www.mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
			fileData = Files.lines(Paths.get("Activities.txt"));//citirea din fisier
		}catch(IOException e) {}
		
		//despart string-ul in start time, end time si activity label
		monitoredData = fileData.map( m -> { String[] threeStrings = m.split("		");  try {
			return new MonitoredData(sdf.parse(threeStrings[0]), sdf.parse(threeStrings[1]), threeStrings[2]);
		} catch (ParseException e) {}
		return null;}).collect(toList());
		
		
		//2
		long nrOfDays = monitoredData.stream().map( m -> m.getStartTime().getDate()).distinct().count();
		System.out.println("The number of days is: "+nrOfDays);
		System.out.println();
		System.out.println();
		System.out.println("_________________________________________________________________________________________________________________");
		
		//3
		Map<String, Long> activityFreq = monitoredData.stream().collect(Collectors.groupingBy( m -> m.getActivity(),  Collectors.counting()));
		for(Map.Entry<String, Long> e : activityFreq.entrySet()) {
			System.out.println(e.getKey() + "		total frequency	" + e.getValue());
		}
		System.out.println();
		System.out.println();
		System.out.println("_________________________________________________________________________________________________________________");		
		
		//4
		List<Integer> days = monitoredData.stream().map( m -> m.getStartTime().getDate()).distinct().collect(toList());
		for(Integer i : days) {
			System.out.println("The day #"+i);
			Map<String, Long> activity = monitoredData.stream().filter( m -> m.getStartTime().getDate() == i)
					.map( m -> m.getActivity())
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

			for (Map.Entry<String, Long> e : activity.entrySet()) {
				System.out.println(e.getKey() + "	frequency	" + e.getValue());
			}
			System.out.println("_________");
			System.out.println();
			System.out.println();
			
		}
		System.out.println("_________________________________________________________________________________________________________________");
		
		
		//5
		monitoredData.stream().forEach( m -> System.out.println(m.getActivity()+"	duration	"+m.computeTime()));
		System.out.println();
		System.out.println();
		System.out.println("_________________________________________________________________________________________________________________");
		
		
		
		//6
		//toate activitatile distincte
		List<String> activities = monitoredData.stream().map( m -> m.getActivity()).distinct().collect(toList());
		for(String act: activities) {
			long totalTime = monitoredData.stream().filter( m -> m.getActivity().equals(act) ).map( m -> m.computeTime()).reduce(0L, Long::sum);
			System.out.println(act + "		total duration		" + totalTime);
		}
		System.out.println();
		System.out.println();
		System.out.println("_________________________________________________________________________________________________________________");
		
		
		
		//7
		for(String act: activities) {
			long lessThen5Min = monitoredData.stream().filter( m -> m.getActivity().equals(act) && m.computeTime() < 300).count();
			long allDurations = monitoredData.stream().filter( m -> m.getActivity().equals(act)).count();
			
			double percent = lessThen5Min / allDurations;//procentul celor care au durata mai mica de 5 min

			monitoredData.stream().filter( m -> m.getActivity().equals(act) && percent >= 0.9).map( m -> m.getActivity() ).distinct().forEach(System.out::println);
			//doar cele care au cel putin 90% dintre activitati cu durata mai mica de 5 min
		}
    }
}
