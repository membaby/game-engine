:- use_module(library(clpfd)).

% Main predicate for solving the Sudoku puzzle
sudoku(Rows, Solution) :-
    length(Row, 9),                     % Ensure that each row has 9 elements
    maplist(same_length(Row), Rows),    % Rows is a list of lists of length 9
    append(Rows, Vs),                   % Flatten the list of rows into a single list
    Vs ins 1..9,                        % Vs is a list of integers from 1 to 9
    maplist(all_distinct, Rows),        % Rows are distinct
    transpose(Rows, Columns),           % Transpose the rows to get a list of columns
    maplist(all_distinct, Columns),     % Columns are distinct
    Rows = [As,Bs,Cs,Ds,Es,Fs,Gs,Hs,Is],
    squares(As, Bs, Cs),
    squares(Ds, Es, Fs),
    squares(Gs, Hs, Is),
    Solution = Rows,
    label(Vs).

% Helper predicate to ensure that three lists of numbers form distinct squares
squares([], [], []).                    % Squares are distinct
squares([N1,N2,N3|Ns1], [N4,N5,N6|Ns2], [N7,N8,N9|Ns3]) :-
    all_distinct([N1,N2,N3,N4,N5,N6,N7,N8,N9]),
    squares(Ns1, Ns2, Ns3).