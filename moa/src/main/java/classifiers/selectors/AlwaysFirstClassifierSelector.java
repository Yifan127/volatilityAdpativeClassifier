package classifiers.selectors;

import moa.classifiers.a.VAC.other.ClassifierSelector;

public class AlwaysFirstClassifierSelector implements ClassifierSelector
{

	@Override
	public int makeDecision(double avgInterval)
	{
		return 1;
	}

}
