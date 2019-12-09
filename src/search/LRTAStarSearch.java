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
 ************************************************************************/

package javaff.search;

import javaff.planning.State;
import javaff.planning.Filter;
import javaff.search.SuccessorSelector;
import javaff.search.LRTAStarState;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Comparator;
import java.math.BigDecimal;
import java.util.Collections;

import java.util.Hashtable;
import java.util.Iterator;

public class LRTAStarSearch extends Search
{
	protected Hashtable closed;
	protected LinkedList open;
    protected Filter filter = null;
	protected SuccessorSelector selector = null;
	protected int randValue;
	protected int maxDepth;
	protected int restartbound;
	protected int depthBound;
	
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

	public void setSelector (SuccessorSelector s)
	{
		selector = s;
	}

    public LRTAStarState removeNext()
	{
			
		return (LRTAStarState) ((LinkedList) open).removeFirst();
	}
    public boolean needToVisit(State s) {
		Integer Shash = new Integer(s.hashCode()); // compute hash for state
		State D = (State) closed.get(Shash); // see if its on the closed list
		
		if (closed.containsKey(Shash) && D.equals(s)) return false;  // if it is return false
		
		closed.put(Shash, s); // otherwise put it on
		return true; // and return true
	}
	
    public State search()
    {
        if (start.goalReached()) { // wishful thinking
			return start;
		}
        LRTAStarState lrtastrstart = new LRTAStarState(start, start.getHValue().doubleValue());
		open.add(lrtastrstart);

        while (!open.isEmpty()) // whilst still states to consider
		{
            System.out.println("Open List Size: " + open.size());
            LRTAStarState lrtastrcurrent = removeNext();//get the next state on the open list
            State current = lrtastrcurrent.getState();
            if (needToVisit(current))
            {
                System.out.println("Plan Cost: " + current.getGValue().doubleValue());
                System.out.println("-------------------------------------------------");

                if (current.goalReached()) { // check if the current is the goal state
                    System.out.println("Found Goal");
                    return current;
                }
                double g_value = current.getGValue().doubleValue();
                double h_value = lrtastrcurrent.getHValue();
                double f_value = g_value + h_value;

                Set successors = current.getNextStates(filter.getActions(current)); // find the adjacent nodes

                Iterator succItr = successors.iterator();
                State bestState = null;

                while (succItr.hasNext()) {
                    State child = (State) succItr.next(); // next successor
                    double temp_g_value = child.getGValue().doubleValue();
                    double temp_h_value = child.getHValue().doubleValue();
                    double temp_f_value = temp_g_value + temp_h_value;
                    if(bestState == null)
                    {
                        bestState = child;
                        LRTAStarState bestLRTA = new LRTAStarState(bestState, temp_h_value);
                        open = new LinkedList();
                        open.add(bestLRTA);
                    }else
                    {
                        double best_h = bestState.getHValue().doubleValue();
                        double best_g = bestState.getGValue().doubleValue();
                        double best_f = best_g + best_h;
                        if(best_f > temp_f_value)
                        {
                            bestState = child;
                            LRTAStarState bestLRTA = new LRTAStarState(bestState, temp_h_value);
                            open = new LinkedList();
                            open.add(bestLRTA);
                        }
                    }
                }
                double best_h = bestState.getHValue().doubleValue();
                double best_g = bestState.getGValue().doubleValue();
                double best_f = best_g + best_h;
                if (h_value < best_f)
                {
                    lrtastrcurrent.setHValue(best_f);
                }

            }else
            {
                return null;
            }
        }
        return null;
    }
    }

