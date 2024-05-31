/* initial beliefs */
+home(at(vacuum, home)).
+dad(at(dad, home)).

/* plans */
// handle dad leaving.
+!dad_left : at(dad, home) <-
	-at(dad, home);		// belief changes
	+scout_dirty.		// start scouting plan

// scout dirty spots and begin sucking.
+!scout_dirty <-
	+dirty_spots([]);						// new belief
	fill_dirty_spots; 						// no clue how we do this
	!suck_next.							// start sucking plan


// suck dirt, live life.
+!suck_next <-		// i = spot to clean, n = rest of dirty spots
	step;                  // move to dirty spot
	// -at(vacuum, home);
	suck;
	!suck_next.				// recursion

// returning home and finishing up.
+!return_vacuum_home : not at(vacuum, home) <-
	step;
	// +at(vacuum, home);
	notify_mop;
	chill.		// might be a plan, left it as primitive

// return home if there is no dirt to suck.
+!suck_next : dirty_spots([]) <- !return_vacuum_home.
