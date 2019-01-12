# ICPC 2018 East Central North America Regional Contest Problems

This section contains problem solutions saved from the November 2018 ICPC East Central North American Regional Contest, at the University of Cincinnati site. 

## Problem E: The Punctilious Cruciverbalist

**Overview**  
Given a crossword puzzle, produce the optimal order in which clues should be solved. Assume all clues can be solved. Only the black squares and initially filled squares are given. Clue numbers are not given.  
Clues are given ratings according to the following system:  
>   1. A clue spanning n spaces gives values n, n-1, ... , 1 to each square going left to right or top to bottom (the first square has value n, the second has value n-1, etc.).  
>   2. Each clue has a fractional rating.  
>       a. The denominator is the sum of all values from 1 to n  
>       b. For each square in the clue, if it is filled with a letter, add that squares  value from (1) to a running total. This total will become the numerator.  
        c. For example, if a clue is 4 squares long, and the first and second squares are filled in, the rating for that clue would be (4 + 3) / (1 + 2 + 3 + 4) = 7/10. 

When determining which clue to solve first, clues are compared according to the following criteria:  
>   1. Clues are first compared by ratings. Larger ratings indicate higher priority.  
>   2. If two clues have the same rating, across clues are prioritized over down clues.  
>   3. If there is still a tie (both go in the same direction), the clue with the lower  clue number is given higher priority.  

**Input**  
Input starts with a line containing two integers *r, c* (1 <= *r, c* <= 50) indicating the size of the puzzle. Following this are *r* lines contaning *c* characters describing the puzzle. Characters are either a letter, indicating a filled square, a '.' indicating an empty square, or a '#' indicating a black square. There will always be at least one empty square in the grid.  

**Output**  
Display the order clues should be solved one per line. Each clue should be displayed as the clue number followed by 'A' or 'D' indicating across or down, respectively ('1A', '5D', e.g.).
