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

package javaff.planning;

import javaff.data.Action;
import javaff.planning.HelpfulFilter;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class RandomKFilter implements Filter
{
	private static RandomKFilter rf = null;
	protected HelpfulFilter hf;
	static int k = 3;

	private RandomKFilter()
	{
		hf = HelpfulFilter.getInstance();
	}

	public static RandomKFilter getInstance(int j)
	{
		if (rf == null) rf = new RandomKFilter(); // Singleton design pattern - return one central instance
		k = j;
		return rf;
	}

	public static Set randomK(Set st) {
		List<Action> copy = new ArrayList<Action>(st);
		Collections.shuffle(copy);
		try{
			copy = copy.subList(0, k);
		}catch(Exception e)
		{
			copy = copy;
		}
		Set<Action> new_set = new HashSet<Action>(copy);
		return new_set;
	}

	public Set getActions(State S) 
	{
		Set helpfulActions = hf.getActions(S);
		Set subset = randomK(helpfulActions);
		return subset;
	}

} 