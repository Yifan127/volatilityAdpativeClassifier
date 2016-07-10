package a.tools;

import moa.streams.ArffFileStream;

public class Directory
{
//	public static String root = "C:\\Users\\rjia477\\789\\"; 
	public static String root = "/Users/rl/789/"; 
	public static String streamsPath = root+"/Streams/";
	
	public static ArffFileStream getStreamFromFileByName(String streamName)
	{
		String path = Directory.streamsPath + streamName + '/' + streamName;
		ArffFileStream stream = new ArffFileStream();
		stream.arffFileOption.setValue(path);
		stream.prepareForUse();
		
		return stream;
	}
}
