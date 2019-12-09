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
import java.util.Random;

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
			fitness.put((double) 1/curr.getHValue().doubleValue(), curr); 
			sum = sum + (double) 1/curr.getHValue().doubleValue();
		}
		double random = javaff.JavaFF.generator.nextDouble() * sum;
		Iterator skipThrough = fitness.keySet().iterator();
		double sumInner = 0;
		while (skipThrough.hasNext())
		{ 
			double fitnessVal = (double) skipThrough.next();
			if (random >= sumInner && random <= (sumInner + fitnessVal))
			{
				State state = (State) fitness.get(fitnessVal);
				return state;
			}
			sumInner = fitnessVal + sumInner;
		}
		return null; // return tmstate from set

	};

};