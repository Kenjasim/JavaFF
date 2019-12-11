/************************************************************************
 * Strathclyde Planning Group,
 * Department of Computer and Information Sciences,
 * University of Strathclyde, Glasgow, UK
 * http://planning.cis.strath.ac.uk/
 *
 * Copyright 2007, Keith Halsey
 * Copyright 2008, Andrew Coles and Amanda Smith
 *
 * (Questions/bug reports now to be sent to Andrew Coles)
 *
 * This file is part of JavaFF.
 *
 * JavaFF is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * JavaFF is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaFF.  If not, see <http://www.gnu.org/licenses/>.
 *
* Edited by Daniel Crouch, Nov 2019
 ************************************************************************/

package javaff.search;

import javaff.planning.State;
import javaff.planning.Filter;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Comparator;
import java.math.BigDecimal;
import java.lang.Math;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.HashSet;


public class LRTAStarSearch extends Search
{

	protected Hashtable closed;
	protected LinkedList open;
	protected Filter filter = null;
	protected SuccessorSelector selector = null;
	protected int maxDepth = 10000;

	public LRTAStarSearch(State s)
	{
		this(s, new HValueComparator());
	}


	public LRTAStarSearch(State s, Comparator c)
	{
		super(s);
		setComparator(c);

		closed = new Hashtable();
		open = new LinkedList();
	}


	public void setFilter(Filter f)
	{
		filter = f;
	}

	public void setSelector(SuccessorSelector s)
	{
		selector = s;
	}

	public State removeNext()
	{
		return (State) ((LinkedList) open).removeLast();
	}

	public void setMaxDepth(int d)
	{
		maxDepth = d;
	}


	public boolean needToVisit(State s) {
		Integer Shash = new Integer(s.hashCode()); // compute hash for state
		State D = (State) closed.get(Shash); // see if its on the closed list
			if (closed.containsKey(Shash) && D.equals(s))
			{
				// javaff.JavaFF.infoOutput.println("Found a closed state");
				return false;  // if it is return false
			}
			else if (closed.containsKey(Shash) && !D.equals(s))
			{
				return false;
			}

		return true; // and return true
	}

	public void addToClosed(State s) {
		Integer Shash = new Integer(s.hashCode()); // compute hash for state
		closed.put(Shash, s); // put it on closed list
	}


	public State search()
	{
		// check if initial state is a goal, if not set to first state
		if (start.goalReached())
		{
			return start;
		}
		open.add(start); // add it to the open list

		// initialise current heuristic value
		LinkedList hValues = new LinkedList();
		hValues.add(start.getHValue().doubleValue());

		// keep track of search depth
		int depth = 0;

		// whilst still states to consider and depth not exceeded
		while (!open.isEmpty()  && depth < maxDepth) // whilst still states to consider
		{
			// Consider open states
			State s = (State) open.getLast(); // get the next one
			double currentHValue = (double) hValues.getLast();

			addToClosed(s); // add to closed list
			Set successors = s.getNextStates(filter.getActions(s)); // and find its neighbourhood

			// initialise record of best successor based on F function (cost + heuristic)
			double bestFValue = 10000;
			State bestFSucc = null;

			// iterate through successors to find best FValue
			Iterator succItr = successors.iterator();
			while (succItr.hasNext())
			{
				State succ = (State) succItr.next(); // next successor
				if (needToVisit(succ))
				{
					// calculate best F Value
					double FValue = (succ.getGValue().doubleValue()
													 - s.getGValue().doubleValue())
													 + succ.getHValue().doubleValue();
					if (FValue < bestFValue)
					{
						bestFValue = FValue;
						bestFSucc = succ;
					}
				}
			}

			// set current current heuristic value
			if (s.goalReached())
			{
				return s;
			}
			else
			{
				if (bestFSucc != null && depth < maxDepth -1)
				{
					open.add(bestFSucc);
					hValues.add(Math.min(currentHValue, bestFValue));
				}else
				{
					addToClosed((State) open.removeLast());
					hValues.removeLast();
					depth = depth -1;
				}
			}

			// increase depth
			depth ++;
			if (depth == maxDepth)
			{
				javaff.JavaFF.infoOutput.println("Max depth exceeded " + depth + "/" + maxDepth + ", restarting...");
			}

		}

		return null;
	}
}
