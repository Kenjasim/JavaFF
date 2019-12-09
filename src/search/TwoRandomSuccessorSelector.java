//
//  TwoRandomSuccessorSelector.java
//  JavaFF
//
//  Created by Andrew Coles on Thu Jan 31 2008.
//

package javaff.search;

import javaff.planning.State;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.math.BigDecimal;
import java.util.Random;

public class TwoRandomSuccessorSelector implements SuccessorSelector
{

	private static TwoRandomSuccessorSelector ss = null;

	public static TwoRandomSuccessorSelector getInstance()
	{
		if (ss == null)
			ss = new TwoRandomSuccessorSelector(); // Singleton, as in NullFilter
		return ss;
	}

	public State choose(Set toChooseFrom)
	{

		if (toChooseFrom.isEmpty())
			return null;

		//Randomly Select Two States
		HashSet subset = new HashSet();
		int size = toChooseFrom.size();
		int j = 0;
		int i = 0;
		while (j != 2)
		{
			int item = new Random().nextInt(size);
			for(Object obj : toChooseFrom)
			{
				if (i == item)
					subset.add((State)obj);
				i++;
			}
			j++;
		}

		//Compare the States
		double h_value = 1000000000;
		State bestState = null;
		for (Object obj: toChooseFrom)
		{
			State s = (State) obj;
			double state_h_value = s.getHValue().doubleValue();
			if(state_h_value < h_value)
			{
				bestState = s;
			}

		}
		if (bestState != null)
		{
			return bestState;
		}
		return null;
	}
};