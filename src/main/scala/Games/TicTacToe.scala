package Games
import Model._
import javax.swing._
import java.awt.{GridLayout, Component}
import javax.swing.{JPanel, JFrame, JLabel}


// Define a class that extends the GameState trait
//class TicTacToeGameState extends GameState {
//  // Override the abstract fields as desired
//  override var rows = 3
//  override var cols = 3
//  override var board = Array.ofDim[Char](rows, cols)
//  override var turn = 0
//  override var history: List[Array[Array[Char]]] = List(board)
//}

object TicTacToe {

  def setup(panel: JPanel): Unit = {
//    var state = new TicTacToeGameState()
//    TicTacToeDrawer(state)
//    panel.setLayout(new GridLayout(state.rows, state.cols))
//    var buttons = Array.ofDim[JButton](state.rows, state.cols)
//    for (i <- 0 until state.rows) {
//      for (j <- 0 until state.cols) {
//        buttons(i)(j) = new JButton(state.board(i)(j).toString)
//        buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 100))
//        buttons(i)(j).setActionCommand(i.toString + j.toString)
//        panel.add(buttons(i)(j))
//      }
//    }
  }

  val TicTacToeController = (input: String, state: Array[Any]) => {
    var actualState: Array[Any] = state
    if (input.length == 2 && input.charAt(0) <= '2' && input.charAt(0) >='0' && input.charAt(1) <= 'c' && input.charAt(1) >= 'a') {
      var row = input.charAt(0) - '0'
      var col = input.charAt(1) - 'a'
      if (actualState == null){
        actualState = get_init_state()
      }
      var board = actualState(3).asInstanceOf[Array[Array[Char]]]
      if (board(row)(col) == 0) {
        var turn = actualState(2).asInstanceOf[Int]
        board(row)(col) = (turn % 2).asInstanceOf[Char]
      }
    }
    actualState
  }

  val TicTacToeDrawer = (state: Array[Any]) => {

  }

  private def get_init_state(): Array[Any] = {
    var state = new Array[Any](5)
    state(0) = 3
    state(1) = 3
    state(2) = 0
    state(3) = Array.ofDim[Char](3, 3)
    state(4) = List(state(3).asInstanceOf[Array[Array[Char]]])
    return state
  }
}