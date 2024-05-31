/* initial beliefs */
+home(at(mop, home)).

/* plans */
+!notify_mop <-
	+scout_vacuumed.

+!scout_vacuumed <-
	+vacuumed_spots([]);
	fill_vacuumed_spots;
	!clean.

+!clean_next : vacuumed_spots([]) <- !return_mop_home.

+!clean_next <-
	step;
	clean;
	!clean_next.

+!return_mop_home : at(mop, home)<-
	step;
	chill.