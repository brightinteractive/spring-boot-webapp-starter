package com.bright.appstarter.util;

import static com.google.common.collect.Sets.*;

import java.util.Set;

import com.google.common.collect.Sets.SetView;

public class Sets
{
	/**
	 * Return an object that is in set1 but not in set2.
	 */
	public static <E> E firstDifference(Set<E> set1, Set<E> set2)
	{
		final SetView<E> newObjects = difference(set1, set2);
		return newObjects.iterator().next();
	}
}
