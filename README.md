# Ultimate X Game Engine
Game Engine built with Scala

![Home Screen](Images/home.png?raw=true "Home Screen")

- Phase 1 Report: [Link to Google Docs](https://docs.google.com/document/d/1qz47yCn05e7oeptXKtYCcPcDrVB4AiB7p_L46e-QugQ/)
- Phase 2 Report: [Link to Google Docs](https://docs.google.com/document/d/1MuG2YE3CpQSWyCuoXCpydsBD99gOocffA1zGazdmDqM/)

The project involves investigating and implementing three main programming language paradigms:
- Object-oriented programming (Javascript)
- Functional programming (Scala)
- Logical programming (Prolog)

The first part of the project focuses on implementing a generic game engine for drawing various board games, including Tic-Tac-Toe, Connect-4, Checkers, Chess, Sudoku, and 8-Queens, using both object-oriented (Javascript) and functional (Scala) programming paradigms.

The second part of the project involves implementing solvers for the Sudoku and 8-Queens games using the logical programming paradigm (Prolog).

The project aims to showcase the differences between these programming paradigms and provide an understanding of when to use each one. The deliverables include source code for both implementations, an executable jar for each implementation, and a report detailing the application of at least two main features of each paradigm, the main differences between the two implementations, and the pros and cons of using each paradigm in this project.

# Prolog Solver
![Solved Sudoku](Images/solved_sudoku.png?raw=true "Solved Sudoku")
The solvers for both games need to be integrated with the drawer engine developed in the previous project phase. The game screens of Sudoku and 8-Queens should have a "solve" button, which is initially active only at the beginning of the game. If the user makes any valid move, the "solve" button becomes inactive.

The Prolog solver must define the rules for each game and generate a valid solution, starting from the initial state for Sudoku. The solution obtained from the solver should be passed back to the engine in a simple format, allowing the engine to draw and display it to the user.

Additionally, it should be handled if certain starting states for either game have no valid solutions. In such cases, the solver should detect the invalidity and inform the engine, which should display an appropriate error message to the user.