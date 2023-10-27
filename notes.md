### Cards 


-> No. of cards: 52
-> Categories: 4
-> Cards in category: 13
-> No. of cards to each player: 3
-> Max no. of players: 17
-> Min no of players: 2

-> Combinations to win [highest]
    -> all same 
    -> continuous with color
    -> continuous ...
    -> all cards with same color
    -> two cards same 
    -> hand with highest card



[A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K]



-> Group of hands 

if
    check if any hands have all cards same?
        if only hand have all cards same then declare that player winner.
        if many hands have all cards same then check the order[hierarchy] and declare the winner.
else if
    check if any hands have [continuous numbers && all cards same color]
        if only hand [continuous numbers && all cards same color] declare the winner.
        if many hands have [continuous numbers && all cards same color] then check the continuous hands having highest number and declare the winner.
        if many hands have [continuous numbers && all cards same color] with highest number same then declare draw.
else if
    check if any hands have [continuous numbers]
        if only hand have [continuous numbers] declare the winner.
        if many hands have [continuous numbers] then check the continuous hands having highest number and declare the winner.
        if many hands have [continuous numbers] with highest number same then declare draw.
else if
    check if any hands have [all cards same color]
        if only hand have [all cards same color] declare the winner.
        if many hands have [all cards same color] then check the same color hands having highest number and declare the winner.
        if many hands have [all cards same color] with highest number same then check second highest number and declare the winner to hand having highest second number.
        if many hands have [all cards same color] with highest number && second highest number same then check third highest number and declare the winner to hand having highest third number.
else if 
    check if any hands have [two cards same]
        if only hand have [two cards same] then declare that player winner.
        if many hands have [two cards same] then compare rank of [Same Card] and declare highest the winner.
        if many hands have [two cards same] && rank of same card is also same, then compare rank of third card and declare highest the winner.
        if many hands have [two cards same] && rank of [same card] is also same, third card of each hand is also same and declare draw.
else
    check the highest rank of one of the card of the hand 
    hand with the highest rank wins 
    if many hands have highest rank same then declare winner on the basis of second highest card
    if many hands have 2 cards have same then declare thw winner on the basis of third highest card
    if many hands have all 3 cards same then declare the draw..



Functions
    -> checking if all cards are same
    -> checking if 2 cards same
    -> checking if all cards same color
    -> checking run
    -> checking run
    -> checking run