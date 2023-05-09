package Games
import App._
import java.awt._
import javax.swing._


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
    if (actualState == null) {
      actualState = get_init_state()
    }
    if (input.length == 2 && input.charAt(0) <= '2' && input.charAt(0) >='0' && input.charAt(1) <= 'c' && input.charAt(1) >= 'a') {
      var row = input.charAt(0) - '0'
      var col = input.charAt(1) - 'a'
      var board = actualState(3).asInstanceOf[Array[Array[Char]]]
      if (board(row)(col) == ' ') {
        var turn = actualState(2).asInstanceOf[Int]
        var res = '0'
        if (turn%2 == 0) res = '1'
        else res = '2'
        board(row)(col) = res
      }
    }
    actualState
  }

  val TicTacToeDrawer = (state: Array[Any]) => {
    println("TicTacToeDrawer")
    var gameState = state
    if (gameState == null) {
      gameState = get_init_state()
      App.board.setLayout(new GridLayout(gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int]))
      var buttons = Array.ofDim[JButton](gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int])
      for (i <- 0 until gameState(0).asInstanceOf[Int]) {
        for (j <- 0 until gameState(1).asInstanceOf[Int]) {
          buttons(i)(j) = new JButton(gameState(3).asInstanceOf[Array[Array[Char]]](i)(j).toString)
          buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 100))
          App.board.add(buttons(i)(j))
        }
      }
    } else {
      val buttons = App.board.getComponents
      for (i <- 0 until gameState(0).asInstanceOf[Int]) {
        for (j <- 0 until gameState(1).asInstanceOf[Int]) {
          println("i=" + i + " j=" + j + " < " + gameState(3).asInstanceOf[Array[Array[Char]]](i)(j) + " >")
        }
      }
    }



    App.board.revalidate()
    App.board.repaint()

  }

  private def get_init_state(): Array[Any] = {
    var state = new Array[Any](5)
    state(0) = 3
    state(1) = 3
    state(2) = 0
    state(3) = Array.ofDim[Char](3, 3).map(_.map(_ => ' '))
    state(4) = Array(state(3))
    return state
  }
}