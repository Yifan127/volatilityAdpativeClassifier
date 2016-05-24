package a.evaluator;

import a.tools.Directory;
import moa.classifiers.a.HoeffdingTreeADWIN;
import moa.streams.ArffFileStream;

public class EvaluateMain
{

	public static void main(String[] args)
	{
		LinEvaluator e = new LinEvaluator();
		e.stream = getStreamFromFile("smalldrift.arff");
		e.classifier = getHT();
		e.evaluate();

	}
	
	
	public static ArffFileStream getStreamFromFile(String streamName)
	{
		String path = Directory.root + "Streams/" + streamName + '/' + streamName;
		ArffFileStream stream = new ArffFileStream();
		stream.arffFileOption.setValue(path);
		stream.prepareForUse();
		return stream;
	}
	
	public static HoeffdingTreeADWIN getHT()
	{
		HoeffdingTreeADWIN ht = new HoeffdingTreeADWIN();
		ht.getOptions().resetToDefaults();
		ht.maxByteSizeOption.setValue(33000);
		
		return ht;
	}
}
