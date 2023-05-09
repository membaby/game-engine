package Games
import Model._
import javax.swing._
import java.awt.{GridLayout, Component}
import javax.swing.{JPanel, JFrame, JLabel}

// Define a class that extends the GameState trait
class ConnectGameState extends GameState {
  // Override the abstract fields as desired
  override var rows = 6
  override var cols = 7
  override var turn = 0
  override var board = Array.ofDim[Char](rows, cols)
  override var history: List[Array[Array[Char]]] = List(board)
}

object Connect {

  def setup(panel: JPanel): Unit = {
    var state = new ConnectGameState()
    ConnectDrawer(state)
    panel.setLayout(new GridLayout(state.rows, state.cols))
    var buttons = Array.ofDim[JButton](state.rows, state.cols)
    for (i <- 0 until state.rows) {
      for (j <- 0 until state.cols) {
        buttons(i)(j) = new JButton(state.board(i)(j).toString)
        buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 20))
        buttons(i)(j).setActionCommand(i.toString + j.toString)
        panel.add(buttons(i)(j))
      }
    }
  }

  private def ConnectDrawer(CurrentState: ConnectGameState): Unit = {

  }


}