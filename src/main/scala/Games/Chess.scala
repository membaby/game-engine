package Games
import Model._

import javax.swing._
import java.awt.{Color, Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}

// Define a class that extends the GameState trait
class ChessGameState extends GameState {
  // Override the abstract fields as desired
  override var rows = 8
  override var cols = 8
  override var turn = 0
  override var board = Array.ofDim[Char](8, 8)
  override var history: List[Array[Array[Char]]] = List(board)
}

object Chess {

  def setup(panel: JPanel): Unit = {
//    var state = new ChessGameState()
//    ChessDrawer(state)
//    panel.setLayout(new GridLayout(state.rows, state.cols))
//    var buttons = Array.ofDim[JButton](state.rows, state.cols)
//    for (i <- 0 until state.rows) {
//      for (j <- 0 until state.cols) {
//        buttons(i)(j) = new JButton(state.board(i)(j).toString)
//        buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 20))
//        buttons(i)(j).setActionCommand(i.toString + j.toString)
//        if ((j+i+1) % 2 == 0) buttons(i)(j).setBackground(Color.GREEN)
//        panel.add(buttons(i)(j))
//      }
//    }
  }

  val ChessController = (input: String, CurrentState: Array[Any]) => {
    new Array[Any](0)
  }

  val ChessDrawer = (CurrentState: Array[Any]) => {

  }


}