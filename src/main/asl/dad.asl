/* initial beliefs */
+home(dad, true).
+tanktop(false).
+beer(false).

/* plans */
!start.
+!start : true <-
    !grabtanktop.

// when dad has his tanktop on, he goes to grab a beer.
+!grabtanktop : tanktop(true) <-
    !grabbeer.

// dad puts on his tanktop.
+!putiton : tanktop(false) <-
    -tanktop(false);
    +tanktop(true).

// dad walks until he gets to his beer.
+!grabbeer : beer(false) <-
    step;
    !grabbeer.

// when dad has had his beer, he starts leaving the house.
+!grabbeer : beer(true) <-
    !getout.

// dad walks until he gets to his tanktop.
+!grabtanktop: tanktop(false) <-
    step;
    !grabtanktop.
+!grabtanktop: true <-
    step;
    !grabtanktop.

// dad drinks the beer.
+!drinkup : beer(false) <-
    -beer(false);
    +beer(true);
    notify_freezer.

// dad walks until he gets out of the house (inside the house)
+!getout : home(true) <-
    step;
    !getout.

// dad yells at the vacuum and chills (outside the house)
+!getout : home(false) <-
    dad_left;
    chill.

// dad leaves the house.
+!leaving <-
    -home(true);
    +home(false).



