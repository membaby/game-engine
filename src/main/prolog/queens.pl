:- use_module(library(clpfd)).

% Main predicate for solving the Eight Queens problem
eight_queens(Qs) :-
    length(Qs, 8),          % Ensure that the list Qs has a length of 8
    Qs ins 1..8,            % Specify the domain of Qs to be integers from 1 to 8
    no_attack(Qs).          % Check for no attacking queens

% Predicate to check that no two queens attack each other
no_attack([]).
no_attack([Q|Qs]) :-
    no_attack(Qs, Q, 1),    % Check that the current queen Q does not attack any of the remaining queens Qs
    no_attack(Qs).          % Recursively check for no attacking queens in the remaining list Qs

no_attack([], _, _).
no_attack([Q|Qs], Q0, D0) :-
    Q0 #\= Q,               % Check that the vertical positions of Q and Q0 are not the same
    abs(Q0 - Q) #\= D0,     % Check that the horizontal and diagonal distances between Q and Q0 are not the same
    D1 #= D0 + 1,           % Increment the diagonal distance counter D0 by 1
    no_attack(Qs, Q0, D1).  % Recursively check for no attacking queens in the remaining list Qs