package Games
import javax.swing._
import java.awt.{Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}
import scala.util.Random
import scala.util.matching.Regex


object Sudoku {
  type SudokuGrid = Array[Array[Int]]

  def setup(panel: JPanel): Unit = {
//    var state = new SudokuGameState()
//    state.board = generateSudokuGrid().map(_.map(_.toString.charAt(0)))
//    SudokuDrawer(state)
//    panel.setLayout(new GridLayout(state.rows, state.cols))
//    var buttons = Array.ofDim[JButton](state.rows, state.cols)
//    for (i <- 0 until state.rows) {
//      for (j <- 0 until state.cols) {
//        buttons(i)(j) = new JButton(state.board(i)(j).toString)
//        buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 20))
//        buttons(i)(j).setActionCommand(i.toString + j.toString)
//        panel.add(buttons(i)(j))
//      }
//    }
  }

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
      var board = state(3).asInstanceOf[Array[Array[Int]]]
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

  }

  private def generateSudokuGrid(): Array[Array[Int]] = {
    val n = 9
    val m = math.sqrt(n).toInt
    val random = new Random()
    val grid = Array.fill(n)(Array.fill(n)(0))

    // Define a function to get the possible values for a cell
    def possibleValues(i: Int, j: Int): Seq[Int] = {
      val row = grid(i)
      val col = grid.map(_(j))
      val box = for {
        x <- 0 until m
        y <- 0 until m
      } yield grid((i / m) * m + x)((j / m) * m + y)
      (1 to n).diff(row ++ col ++ box)
    }

    // Define a function to recursively fill the grid
    def fillGrid(i: Int, j: Int): Option[SudokuGrid] = {
      if (i == n) {
        Some(grid)
      } else if (j == n) {
        fillGrid(i + 1, 0)
      } else if (grid(i)(j) != 0) {
        fillGrid(i, j + 1)
      } else {
        val values = possibleValues(i, j)
        if (values.isEmpty) {
          None
        } else {
          val shuffledValues = random.shuffle(values)
          shuffledValues.view.flatMap { value =>
            grid(i)(j) = value
            fillGrid(i, j + 1)
          }.headOption
        }
      }
    }

    // Fill the grid starting from the top-left cell
    fillGrid(0, 0).getOrElse(generateSudokuGrid())

    grid
  }

  private def get_init_state(): Array[Any] = {
    var rows = 9
    var cols = 9
    var turn = 0
    var board = generateSudokuGrid()
    var history = List(board)
    var originalNums = Array.fill(9)(Array.fill(9)(true))
    var state: Array[Any] = Array[Any](6)
    //Hiding some numbers
    val numOfHidden: Int = 30
    var hidden = 0
    while (hidden < numOfHidden){
      var (row, col) = (Random.nextInt(9), Random.nextInt(9))
      if (originalNums(row)(col) == true){
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
    return state
  }

}