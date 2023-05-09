package Games
import Model._
import javax.swing._
import java.awt.{GridLayout, Component}
import javax.swing.{JPanel, JFrame, JLabel}
import java.awt.Color

// Define a class that extends the GameState trait
class QueensGameState extends GameState {
  // Override the abstract fields as desired
  override var rows = 8
  override var cols = 8
  override var turn = 0
  override var board = Array.ofDim[Char](rows, cols)
  override var history: List[Array[Array[Char]]] = List(board)
}

object Queens {

  def setup(panel: JPanel): Unit = {
    var state = new QueensGameState()
    QueensDrawer(state)
    panel.setLayout(new GridLayout(state.rows, state.cols))
    var buttons = Array.ofDim[JButton](state.rows, state.cols)
    for (i <- 0 until state.rows) {
      for (j <- 0 until state.cols) {
        buttons(i)(j) = new JButton(state.board(i)(j).toString)
        buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 20))
        buttons(i)(j).setActionCommand(i.toString + j.toString)
        if ((j+i+1) % 2 == 0) buttons(i)(j).setBackground(Color.DARK_GRAY)
        panel.add(buttons(i)(j))
      }
    }
  }

  private def QueensDrawer(CurrentState: QueensGameState): Unit = {

  }


}