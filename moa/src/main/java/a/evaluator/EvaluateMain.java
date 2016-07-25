package a.evaluator;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import a.tools.Directory;
import cutpointdetection.ADWIN;
import moa.classifiers.AbstractClassifier;
import moa.classifiers.a.HoeffdingTreeADWIN;
import moa.classifiers.a.VolatilityAdaptiveClassifer;
import moa.classifiers.trees.HoeffdingAdaptiveTree;

public class EvaluateMain
{

	final static int VOL_ADAPTIVE_CLASSIFIER = 0;
	final static int HOEFFDING_ADWIN = 1;
	final static int HAT = 2;

	public static void main(String[] args) throws Exception
	{
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		
		ArrayList<Callable<Integer>> list = new ArrayList<Callable<Integer>>();
		
		// List the tasks here
		
//		list.addAll(buildTasksList("", "100mblock_5,50,5,50,5,50,5,50", HAT, 1));
//		list.addAll(buildTasksList("", "100mblock_5,50,5,50,5,50,5,50", HOEFFDING_ADWIN, 1));
//		list.addAll(buildTasksList("", "100mblock_5,50,5,50,5,50,5,50", VOL_ADAPTIVE_CLASSIFIER, 1));
//		
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", HAT, 1));
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", HOEFFDING_ADWIN, 1));
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", VOL_ADAPTIVE_CLASSIFIER, 1));
//		
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", HAT, 1));
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", HOEFFDING_ADWIN, 1));
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", VOL_ADAPTIVE_CLASSIFIER, 1));
//		
//		list.addAll(buildTasksList("", "100mblock_200,10,200,200,200,10,200,200,200,200,10", HAT, 1));
//		list.addAll(buildTasksList("", "100mblock_200,10,200,200,200,10,200,200,200,200,10", HOEFFDING_ADWIN, 1));
//		list.addAll(buildTasksList("", "100mblock_200,10,200,200,200,10,200,200,200,200,10", HOEFFDING_ADWIN, 1));
		
//		list.addAll(buildTasksList("", "100mblock_5,50,5,50,5,50,5,50", HAT, 1));
//		list.addAll(buildTasksList("", "100mblock_5,50,5,50,5,50,5,50", HOEFFDING_ADWIN, 1));
//		list.addAll(buildTasksList("", "100mblock_5,50,5,50,5,50,5,50", VOL_ADAPTIVE_CLASSIFIER, 1));
//		
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", HAT, 1));
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", HOEFFDING_ADWIN, 1));
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", VOL_ADAPTIVE_CLASSIFIER, 1));
//		
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", HAT, 1));
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", HOEFFDING_ADWIN, 1));
//		list.addAll(buildTasksList("", "100mblock_10,200,10,10,10,200,10,10,10,200,10,10,10,200", VOL_ADAPTIVE_CLASSIFIER, 1));
//		
//		list.addAll(buildTasksList("", "100mblock_200,10,200,200,200,10,200,200,200,200,10", HAT, 1));
//		list.addAll(buildTasksList("", "100mblock_200,10,200,200,200,10,200,200,200,200,10", HOEFFDING_ADWIN, 1));
//		list.addAll(buildTasksList("", "100mblock_200,10,200,200,200,10,200,200,200,200,10", VOL_ADAPTIVE_CLASSIFIER, 1));
		
		
		list.addAll(buildTasksList("", "100mblock_5noise_5,50,5,50,5,50,5,50", VOL_ADAPTIVE_CLASSIFIER, 1));
		list.addAll(buildTasksList("", "100mblock_5noise_5,50,5,50,5,50,5,50", HOEFFDING_ADWIN, 1));
		list.addAll(buildTasksList("", "100mblock_5noise_5,50,5,50,5,50,5,50", HAT, 1));
		
		
//		list.get(0).call();
		for(Callable<Integer> task : list)
		{
			executorService.submit(task);
		}

		executorService.shutdown();
		try
		{
			executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		};
		
		System.out.println("EvaluateMain End!");
		
		
		//TODO analyse statistics
	}

	private static ArrayList<Callable<Integer>> buildTasksList(String resultPathPrefix, String streamPrefix, int classifierOption, int numSamples)
	{
		ArrayList<Callable<Integer>> list = new ArrayList<Callable<Integer>>(numSamples);
		
		for(int i=0;i<numSamples;i++)
		{
			try
			{
				list.add(buildTask(resultPathPrefix, streamPrefix+"_"+i+".arff", classifierOption));
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return list;
	}

	/**
	 * 
	 * @param streamName: give a streamName, it will generate a evaluation task for this stream.
	 * @return
	 * @throws Exception 
	 */
	private static Callable<Integer> buildTask(String resultPathPrefix, String streamName, int classifierOption) throws Exception
	{

		File resultFolder = null;
		AbstractClassifier classifier = null;

		String pathname = Directory.root+"/Results/"+ resultPathPrefix + streamName;

		if(classifierOption==HOEFFDING_ADWIN)
		{
			resultFolder = new File(pathname+"/HOEFFDING_ADWIN");
			classifier = new HoeffdingTreeADWIN(new ADWIN());
			//			classifier = new HoeffdingTreeADWIN(new SlidingWindowMonitor(1000, 0.15, 5000));
			classifier.getOptions().resetToDefaults();
		}
		else if (classifierOption==HAT) 
		{
			resultFolder = new File(pathname+"/HAT");
			classifier = new HoeffdingAdaptiveTree();
			classifier.getOptions().resetToDefaults();
		}
		else if(classifierOption==VOL_ADAPTIVE_CLASSIFIER)
		{
			resultFolder = new File(pathname+"/VOL_ADAPTIVE_CLASSIFIER");
			VolatilityAdaptiveClassifer temp = new VolatilityAdaptiveClassifer(new ADWIN(), 5000);
			temp.dumpFileDirOption.setValue(resultFolder.getPath());

			classifier = temp;
		}
		else 
		{
			throw new Exception("Wrong classifier option");
		}

		resultFolder.mkdirs();


		classifier.resetLearning();

		return new EvaluateTask(classifier, streamName, resultFolder.getAbsolutePath());

	}



}
