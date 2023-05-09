package Games
import javax.swing._
import java.awt.{Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}
import java.awt.Color
import scala.util.matching.Regex

// Define a class that extends the GameState trait
//class QueensGameState extends GameState {
//  // Override the abstract fields as desired
//  override var rows = 8
//  override var cols = 8
//  override var turn = 0
//  override var board = Array.ofDim[Char](rows, cols)
//  override var history: List[Array[Array[Char]]] = List(board)
//}

object Queens {

  def setup(panel: JPanel): Unit = {
//    var state = new QueensGameState()
//    QueensDrawer(state)
//    panel.setLayout(new GridLayout(state.rows, state.cols))
//    var buttons = Array.ofDim[JButton](state.rows, state.cols)
//    for (i <- 0 until state.rows) {
//      for (j <- 0 until state.cols) {
//        buttons(i)(j) = new JButton(state.board(i)(j).toString)
//        buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 20))
//        buttons(i)(j).setActionCommand(i.toString + j.toString)
//        if ((j+i+1) % 2 == 0) buttons(i)(j).setBackground(Color.DARK_GRAY)
//        panel.add(buttons(i)(j))
//      }
//    }
  }

  val QueensController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null){
      actualState = get_init_state()
    }
    val addPattern: Regex = "[0-7][a-h]".r
    val deletePattern: Regex = "delete [a-h]".r
    if (deletePattern.matches(input))
    actualState
  }

  val QueensDrawer = (state: Array[Any]) => {

  }

  private def get_init_state(): Array[Any] ={
    var state = Array[Any](5)
    var (row, col, turn) = (8, 8, 0)
    var board: Array[Array[Boolean]] = Array.fill(8)(Array.fill(8)(false))
    var history = List(board)
    state(0) = row
    state(1) = col
    state(2) = turn
    state(3) = board
    state(4) = history
    return state
  }

}