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
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class TwoRandomSuccessorSelector implements SuccessorSelector
{

	private static TwoRandomSuccessorSelector ss = null;

	public static TwoRandomSuccessorSelector getInstance()
	{
		if (ss == null)
			ss = new TwoRandomSuccessorSelector(); // Singleton, as in NullFilter
		return ss;
	}

	public static Set randomTwo(Set st) {
		List<State> copy = new ArrayList<State>(st);
		Collections.shuffle(copy);
		try{
			copy = copy.subList(0, 2);
		}catch(Exception e)
		{
			copy = copy;
		}
		Set<State> new_set = new HashSet<State>(copy);
		return new_set;
	}

	public State choose(Set toChooseFrom)
	{

		if (toChooseFrom.isEmpty())
			return null;

		//Randomly Select Two States
		Set subset = randomTwo(toChooseFrom);
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