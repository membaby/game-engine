package Games
import javax.swing._
import java.awt.{Color, Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}
import scala.util.Random
import scala.util.matching.Regex
import App._
import GameSolver.solve

object Sudoku {
  type SudokuGrid = Array[Array[Int]]

  val SudokuController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null) actualState = get_init_state()
    else{
      val deletePattern : Regex = "del [0-8][a-j]".r
      val addPattern: Regex = "[0-8][a-j] [1-9]".r
      if (deletePattern.matches(input)) {
        var board = state(3).asInstanceOf[Array[Array[Int]]]
        val origNums = state(4).asInstanceOf[Array[Array[Boolean]]]
        val (row, col) = (input.charAt(4) - '0', input.charAt(5) - 'a')
        if (!origNums(row)(col) && board(row)(col) != 0) {
          board(row)(col) = 0
          actualState(6) = ""
        }
        else {
          //Invalid square to delete
          actualState(6) = "Invalid square to delete"
        }
      }
      else if (addPattern.matches(input)) {
        val (row, col) = (input.charAt(0) - '0', input.charAt(1) - 'a')
        var board = actualState(3).asInstanceOf[Array[Array[Int]]]
        if (board(row)(col) != 0) {
          //Invalid square to add to
          actualState(6) = "Invalid square to add to"
        }
        else {
          val num = input.charAt(3) - '0'
          //Check column
          var validNum = true
          for (x <- 0 to 8) {
            if (board(x)(col) == num) validNum = false
          }
          //Check row
          for (y <- 0 to 8) {
            if (board(row)(y) == num) validNum = false
          }
          //Check 3x3 grid
          val (startRow, startCol) = (row / 3 * 3, col / 3 * 3)
          for (y <- startRow to startRow + 2) {
            for (x <- startCol to startCol + 2) {
              if (board(y)(x) == num) validNum = false
            }
          }
          if (validNum) {
            board(row)(col) = num
            state(2) = state(2).asInstanceOf[Int] + 1
            actualState(6) = ""
          }
          else {
            //Invalid number to add
            actualState(6) = "number to add"
          }
        }
      }
      else if (input == "solve"){
        val board = actualState(3).asInstanceOf[Array[Array[Int]]]
        var solver_query = "[" + board.map(innerList => innerList.mkString(", ")).mkString("[", "], [", "]") + "]"
        solver_query = solver_query.replace("0", "_")
        val solution = solve("sudoku", solver_query)
        for (i <- 0 until 9) {
          for (j <- 0 until 9) {
            board(i)(j) = solution(i)(j).asInstanceOf[Int]
          }
        }
      }
      else {
        //Invalid input
        actualState(6) = "Invalid input"
      }
    }
    actualState
  }
  val SudokuDrawer = (CurrentState: Array[Any]) => {
    var gameState = CurrentState
    var game_finished = true;
    if (App.board.getComponentCount == 0) {
      App.board.setLayout(new GridLayout(gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int]))
      var buttons = Array.ofDim[JButton](gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int])
      for (i <- 0 until gameState(0).asInstanceOf[Int]) {
        for (j <- 0 until gameState(1).asInstanceOf[Int]) {
          buttons(i)(j) = new JButton(i.toString + (97 + j).toChar)
          if (gameState(3).asInstanceOf[Array[Array[Int]]](i)(j) == 0) {
            game_finished = false;
            buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 15))
            buttons(i)(j).setForeground(Color.GRAY);
          } else {
            buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 30))
            buttons(i)(j).setText(gameState(3).asInstanceOf[Array[Array[Int]]](i)(j).toString)
          }
          if (i % 3 == 0 && i != 0 && (j % 3 == 2 && j != 0)  && j != 8) buttons(i)(j).setBorder(BorderFactory.createMatteBorder(5, 1, 1, 5, Color.BLACK))
          else if (i % 3 == 0 && i != 0) buttons(i)(j).setBorder(BorderFactory.createMatteBorder(5, 1, 1, 1, Color.BLACK))
          else if (j % 3 == 2 && j != 0 && !(i % 3 == 0 && i != 0) && j != 8) buttons(i)(j).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, Color.BLACK))
          else buttons(i)(j).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK))
          App.board.add(buttons(i)(j))
        }
      }
    } else {
      gameState = CurrentState
      val buttons = App.board.getComponents
      for (i <- 0 until 9) {
        for (j <- 0 until 9) {
          val text = gameState(3).asInstanceOf[Array[Array[Int]]](i)(j)
          val button = buttons(i* gameState(1).asInstanceOf[Int] + j)
          if (text.toString != "0") {
            button.asInstanceOf[JButton].setText(text.toString)
            button.setFont(new java.awt.Font("Arial", 1, 30))
            button.setForeground(Color.BLACK);
          } else {
            button.asInstanceOf[JButton].setText(i.toString + (97 + j).toChar)
            button.setFont(new java.awt.Font("Arial", 1, 15))
            button.setForeground(Color.GRAY);
            game_finished = false;
          }
        }
      }

    }

    if (game_finished) gameState(6) = "Puzzle Solved!"
    App.errorLabel.setText(gameState(6).asInstanceOf[String])
    App.board.revalidate()
    App.board.repaint()
  }

  def get_init_state(): Array[Any] = {
    val rows = 9
    val cols = 9
    val turn = 0
    val board = SudokuSolver.generateSolvedPuzzle()
    println("Originally Generated Board:")
    for (i <- 0 to 8){
      for (j <- 0 to 8){
        printf("%5d", board(i)(j))
      }
      println()
    }
    println("================================================")
    val history = List(board)
    var originalNums = Array.fill(9)(Array.fill(9)(true))
    var state = new Array[Any](7)
    //Hiding some numbers
    val numOfHidden: Int = 10
    var hidden = 0
    while (hidden < numOfHidden){
      var (row, col) = (Random.nextInt(9), Random.nextInt(9))
      if (originalNums(row)(col)){
        originalNums(row)(col) = false
        board(row)(col) = 0
        hidden += 1
      }
    }

    state(0) = rows
    state(1) = cols
    state(2) = turn
    state(3) = board
    state(4) = originalNums
    state(5) = history
    state(6) = ""
    state
  }

}

import scala.util.Random

object SudokuSolver {
  // Define a 9x9 grid as a two-dimensional array of integers
  type Grid = Array[Array[Int]]

  // Function to print a grid
  def printGrid(grid: Grid): Unit = {
    for (row <- grid) {
      println(row.mkString(" "))
    }
  }

  // Function to check if a value can be placed in a given position
  def isValid(grid: Grid, row: Int, col: Int, value: Int): Boolean = {
    // Check row
    for (i <- 0 until 9) {
      if (grid(row)(i) == value) return false
    }

    // Check column
    for (i <- 0 until 9) {
      if (grid(i)(col) == value) return false
    }

    // Check box
    val boxRow = row / 3 * 3
    val boxCol = col / 3 * 3
    for (i <- boxRow until boxRow + 3) {
      for (j <- boxCol until boxCol + 3) {
        if (grid(i)(j) == value) return false
      }
    }

    true
  }

  // Function to solve a Sudoku puzzle using backtracking
  def solve(grid: Grid): Option[Grid] = {
    for (row <- 0 until 9; col <- 0 until 9) {
      if (grid(row)(col) == 0) {
        for (value <- Random.shuffle(1 to 9)) {
          if (isValid(grid, row, col, value)) {
            grid(row)(col) = value
            solve(grid) match {
              case Some(solvedGrid) => return Some(solvedGrid)
              case None => grid(row)(col) = 0
            }
          }
        }
        return None
      }
    }
    Some(grid)
  }

  // Function to generate a solved Sudoku puzzle
  def generateSolvedPuzzle(): Grid = {
    val emptyGrid = Array.fill(9, 9)(0)
    solve(emptyGrid).get
  }
}