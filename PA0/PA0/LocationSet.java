//
// LocationSet
//
// This class provides a set of location names for which membership
// can be efficiently checked in constant time. This is done
// using an approach that involves a hash table. Each location
// name is assumed to describe a distinct location.
//
// David Noelle -- Fri Sep 11 20:42:11 PDT 2020
//

import java.util.*;

public class LocationSet {
	Set<String> nameSet = null;
	
	// Default constructor ...
	public LocationSet () {
		this.nameSet = new HashSet<String>();
	}
	
	// add -- Add the given location name to the set. Return
	// true if the set did not already contain the location.
	public boolean add(String name) {
		return (nameSet.add(name));
	}
	
	// add -- Add the given Location object name to the set.
	// Return true if the set did not already contain the
	// location name.
	public boolean add(Location loc) {
		return (nameSet.add(loc.name));
	}
	
	// remove -- Remove the given location name from the set.
	// Return true if the set contained the name.
	public boolean remove(String name) {
		return (nameSet.remove(name));
	}
	
	// remove -- Remove the given Location object name from
	// the set. Return true if the set contained that
	// location name.
	public boolean remove(Location loc) {
		return (nameSet.remove(loc.name));
	}
	
	// contains -- Return true if the given name is currently
	// in the set.
	public boolean contains(String name) {
		return (nameSet.contains(name));
	}
	
	// contains -- Return true if the given Location currently
	// has its name contained in the set.
	public boolean contains(Location loc) {
		return (nameSet.contains(loc.name));
	}
	
	// clear -- Empty the set of names.
	public void clear() {
		nameSet.clear();
	}
	
}