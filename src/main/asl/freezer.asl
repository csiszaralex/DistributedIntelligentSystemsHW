/* initial beliefs */
+enough_beer(true).

/* plans */
+!notify_freezer : enough_beer(true) <-
    remove_beer.

+!notify_freezer : enough_beer(false) <-
    remove_beer;
    -enough_beer(false);
    +enough_beer(true);
    refill.

+!need_beer <-
    -enough_beer(true);
    +enough_beer(false).
