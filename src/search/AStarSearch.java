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
import javaff.search.AStarState;
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

public class AStarSearch extends Search
{
	protected Hashtable closed;
	protected PriorityQueue<AStarState> open;
    protected Filter filter = null;
	protected SuccessorSelector selector = null;
	protected int randValue;
	protected int maxDepth;
	protected int restartbound;
	protected int depthBound;
	
	public AStarSearch(State s)
	{
		this(s, new HValueComparator());
	}

	public AStarSearch(State s, Comparator c)
	{
		super(s);
		setComparator(c);
		
		closed = new Hashtable();
		open = new PriorityQueue<AStarState>(20, new Comparator<AStarState>(){
                 public int compare(AStarState i, AStarState j){
                     double i_score = i.getFValue();
                     double j_score = j.getFValue();
                    if(i_score > j_score){
                        return 1;
                    }

                    else if (i_score < j_score){
                        return -1;
                    }

                    else{
                        return 0;
                    }
                 }

                }
            );;
	}

	public void setFilter(Filter f)
	{
		filter = f;
	}

	public void setSelector (SuccessorSelector s)
	{
		selector = s;
	}

    public boolean closedContains(State s)
    {
        Integer Shash = new Integer(s.hashCode());
        AStarState a = (AStarState) closed.get(Shash);
        if(a == null)
        {
            return false;
        }
        State S = a.getState();

        if (closed.containsKey(Shash) && S.equals(s))
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

    public void needToVisit(State s, double f_value)
    {
        AStarState a = new AStarState(s, f_value);

        open.add(a);

    }

    public void explored(State s, double f_value)
    {
        Integer Shash = new Integer(s.hashCode());
        AStarState a = new AStarState(s, f_value);

        closed.put(Shash, a);

    }

    public boolean openContains(State s, double f_value)
    {
        Iterator op = open.iterator();
        while (op.hasNext()) {
            AStarState a = (AStarState) op.next();
            State state = a.getState();
            double original_f_value = a.getFValue();
            if (state.hashCode() == s.hashCode())
            {
                if(original_f_value > f_value){
                    System.out.println("Bigger");
                    a.setState(s);
                    a.setFValue(f_value);
                    return true;
                }else
                {
                    System.out.println("Not");
                    return true;
                }
            }
        }
        return false;
    }
	
    public State search()
    {
        if (start.goalReached()) { // wishful thinking
			return start;
		}

        double start_f_value = start.getGValue().doubleValue() + start.getHValue().doubleValue();
        explored(start, start_f_value);
		
		needToVisit(start, start_f_value);

        while (!open.isEmpty() && open.size() < 20000) // whilst still states to consider
		{
            System.out.println("Open List SIze:" + open.size());
            System.out.println("Closed List SIze:" + closed.size());
            AStarState astrcurrent = open.poll();//get the state with the best a* value
            State current = astrcurrent.getState();
            if (current.goalReached()) { // check if the current is the goal state
                System.out.println("Found Goal");
                return current;
		    }
            double g_value = current.getGValue().doubleValue();
            double h_value = current.getHValue().doubleValue();
            double f_value = g_value + h_value;

            Set successors = current.getNextStates(filter.getActions(current)); // find the adjacent nodes
            explored(current, f_value);
			Iterator succItr = successors.iterator();

			while (succItr.hasNext()) {
                State child = (State) succItr.next(); // next successor
                double temp_g_value = child.getGValue().doubleValue();
                double temp_h_value = child.getHValue().doubleValue();
                double temp_f_value = temp_g_value + temp_h_value;
                if(closedContains(child))
                {
                    continue;
                }else if(openContains(child, temp_f_value))
                {
                    continue;
                }else{
                    AStarState astrchild = new AStarState(child, temp_f_value);
                    open.add(astrchild);
                }
            }

        }
        System.out.println("Failed: " + open.size());
        return null;
    }
    }

