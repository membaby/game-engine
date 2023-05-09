package Games
import Model._
import javax.swing._
import java.awt.{GridLayout, Component}
import javax.swing.{JPanel, JFrame, JLabel}
import scala.util.Random

// Define a class that extends the GameState trait
class SudokuGameState extends GameState {
  // Override the abstract fields as desired
  override var rows = 9
  override var cols = 9
  override var turn = 0
  override var board = Array.ofDim[Char](rows, cols)
  override var history: List[Array[Array[Char]]] = List(board)
}

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
    new Array[Any](0)
  }
  val SudokuDrawer = (CurrentState: Array[Any]) => {

  }

  private def generateSudokuGrid(): SudokuGrid = {
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


}