//
//  BestSuccessorSelector.java
//  JavaFF
//
//  Created by Andrew Coles on Thu Jan 31 2008.
//

package javaff.search;

import javaff.planning.State;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.math.BigDecimal;

public class RouletteSelector implements SuccessorSelector
{

	private static RouletteSelector ss = null;

	public static RouletteSelector getInstance()
	{
		if (ss == null)
			ss = new RouletteSelector(); // Singleton, as in NullFilter
		return ss;
	}

	public State choose(Set toChooseFrom)
	{

		if (toChooseFrom.isEmpty())
			return null;

		HashMap fitness = new HashMap();// states
		Iterator itr = toChooseFrom.iterator();
		double sum = 0;

		while (itr.hasNext())
		{
			State curr = (State) itr.next();
			double i = 1/(curr.getHValue().doubleValue());
			fitness.put(curr, i); 
			sum = sum + i;
		}
		double random = javaff.JavaFF.generator.nextDouble() * sum;
		Iterator skipThrough = fitness.keySet().iterator();
		double sumInner = 0;
		State bestState = null;
		while (skipThrough.hasNext())
		{ 
			State state = (State) skipThrough.next();
			double fitnessVal = (double) fitness.get(state);
			if (random >= sumInner && random <= (sumInner + fitnessVal))
			{
				bestState = state;
			}
			sumInner = fitnessVal + sumInner;
		}
		return bestState; // return tmstate from set

	};

};