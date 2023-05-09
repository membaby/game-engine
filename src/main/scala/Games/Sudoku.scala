package Games
import javax.swing._
import java.awt.{Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}
import scala.util.Random
import scala.util.matching.Regex
import App._


object Sudoku {
  type SudokuGrid = Array[Array[Int]]

  val SudokuController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null) actualState = get_init_state()
    val deletePattern : Regex = "delete [0-8][a-j]".r
    val addPattern : Regex = "[0-8][a-j] [1-9]".r
    if (deletePattern.matches(input)){
      var board = state(3).asInstanceOf[Array[Array[Int]]]
      val origNums = state(4).asInstanceOf[Array[Array[Boolean]]]
      val (row, col) = (input.charAt(7) - '0', input.charAt(8) - 'a')
      if (!origNums(row)(col) && board(row)(col) != 0){
        board(row)(col) = 0
      }
      else{
        //Invalid square to delete
      }
    }
    else if (addPattern.matches(input)) {
      val (row, col) = (input.charAt(0) - '0', input.charAt(1) - 'a')
      var board = actualState(3).asInstanceOf[Array[Array[Int]]]
      if (board(row)(col) != 0) {
        //Invalid square to add to
      }
      else {
        val num = input.charAt(3) - '0'
        //Check column
        var validNum = true
        for (x <- 0 to 8){
          if (board(x)(col) == num) validNum = false
        }
        //Check row
        for (y <- 0 to 8) {
          if (board(row)(y) == num) validNum = false
        }
        //Check 3x3 grid
        val (startRow, startCol) = (row/3 * 3, col/3 * 3)
        for (y <- startRow to startRow+2){
          for (x <- startCol to startCol+2){
            if (board(y)(x) == num) validNum = false
          }
        }
        if (validNum){
          board(row)(col) = num
          state(2) = state(2).asInstanceOf[Int] + 1
        }
        else {
          //Invalid number to add
        }
      }
    }
    else {
      //Invalid input
    }
    actualState
  }
  val SudokuDrawer = (CurrentState: Array[Any]) => {
    var gameState = CurrentState
    if (gameState == null) {
      gameState = get_init_state()
      App.board.setLayout(new GridLayout(gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int]))
      var buttons = Array.ofDim[JButton](gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int])
      for (i <- 0 until gameState(0).asInstanceOf[Int]) {
        for (j <- 0 until gameState(1).asInstanceOf[Int]) {
          if (gameState(3).asInstanceOf[Array[Array[Int]]](i)(j) == 0) buttons(i)(j) = new JButton
          else buttons(i)(j) = new JButton(gameState(3).asInstanceOf[Array[Array[Int]]](i)(j).toString)
          println(gameState(3).asInstanceOf[Array[Array[Int]]](i)(j).toString)
          buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 40))
          App.board.add(buttons(i)(j))
        }
      }
    } else {
      gameState = CurrentState
      val buttons = App.board.getComponents
      for (i <- 0 until 9) {
        for (j <- 0 until 9) {
          val text = gameState(3).asInstanceOf[Array[Array[Int]]](i)(j)
          if (text.toString == "0") buttons(i * 3 + j).asInstanceOf[JButton].setText("")
          else buttons(i * 3 + j).asInstanceOf[JButton].setText(text.toString)
          println("i=" + i + " j=" + j + " text=" + text)
        }
      }

    }

    App.board.revalidate()
    App.board.repaint()
  }

  private def get_init_state(): Array[Any] = {
    val rows = 9
    val cols = 9
    val turn = 0
    val board = SudokuGenerator.generateSudoku()
    val history = List(board)
    var originalNums = Array.fill(9)(Array.fill(9)(true))
    var state = new Array[Any](6)
    //Hiding some numbers
    val numOfHidden: Int = 30
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
    state
  }

}

object SudokuGenerator {
  // Define the size of the Sudoku grid
  val n = 9
  val m = math.sqrt(n).toInt

  // Define a random number generator
  val random = new Random()

  // Define a function to get the possible values for a cell
  def possibleValues(grid: Array[Array[Int]], i: Int, j: Int): Seq[Int] = {
    val row = grid(i)
    val col = grid.map(_(j))
    val box = for {
      x <- 0 until m
      y <- 0 until m
    } yield grid((i / m) * m + x)((j / m) * m + y)
    (1 to n).diff(row ++ col ++ box)
  }

  // Define a function to recursively fill the grid
  def fillGrid(grid: Array[Array[Int]], i: Int, j: Int): Boolean = {
    if (i == n) {
      true
    } else if (j == n) {
      fillGrid(grid, i + 1, 0)
    } else if (grid(i)(j) != 0) {
      fillGrid(grid, i, j + 1)
    } else {
      val values = possibleValues(grid, i, j)
      if (values.isEmpty) {
        false
      } else {
        val shuffledValues = random.shuffle(values)
        shuffledValues.exists { value =>
          grid(i)(j) = value
          fillGrid(grid, i, j + 1)
        }
      }
    }
  }

  // Define a function to generate a Sudoku puzzle
  def generateSudoku(): Array[Array[Int]] = {
    val grid = Array.fill(n)(Array.fill(n)(0))
    fillGrid(grid, 0, 0)
    grid
  }
}
