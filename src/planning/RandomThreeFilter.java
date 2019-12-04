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

public class RandomThreeFilter implements Filter
{
	private static RandomThreeFilter rf = null;
	protected HelpfulFilter hf;

	private RandomThreeFilter()
	{
		hf = HelpfulFilter.getInstance();
	}

	public static RandomThreeFilter getInstance()
	{
		if (rf == null) rf = new RandomThreeFilter(); // Singleton design pattern - return one central instance
		return rf;
	}

	public Set getActions(State S) 
	{
		Set helpfulFiltered = hf.getActions(S);
		Set subset = new HashSet();
		Set helpfulActions = hf.getActions(S);
		int size = helpfulFiltered.size();
		int j = 0;
		int i = 0;
		while (j != 3)
		{
			int item = new Random().nextInt(size);
			for(Object obj : helpfulFiltered)
			{
				if (i == item)
					subset.add((Action)obj);
				i++;
			}
			j++;
		}
		return subset;
	}

} 