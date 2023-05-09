package Games
import Model._
import javax.swing._
import java.awt.{GridLayout, Component}
import javax.swing.{JPanel, JFrame, JLabel}


// Define a class that extends the GameState trait
class TicTacToeGameState extends GameState {
  // Override the abstract fields as desired
  override var rows = 3
  override var cols = 3
  override var board = Array.ofDim[Char](rows, cols)
  override var turn = 0
  override var history: List[Array[Array[Char]]] = List(board)
}

object TicTacToe {

  def setup(panel: JPanel): Unit = {
    var state = new TicTacToeGameState()
    TicTacToeDrawer(state)
    panel.setLayout(new GridLayout(state.rows, state.cols))
    var buttons = Array.ofDim[JButton](state.rows, state.cols)
    for (i <- 0 until state.rows) {
      for (j <- 0 until state.cols) {
        buttons(i)(j) = new JButton(state.board(i)(j).toString)
        buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 100))
        buttons(i)(j).setActionCommand(i.toString + j.toString)
        panel.add(buttons(i)(j))
      }
    }
  }

  def TicTacToeController(input: String, state: GameState): GameState = {
    return new TicTacToeGameState
  }

  def TicTacToeDrawer(state: GameState): Unit = {

  }


}