package Games
import App._
import java.awt._
import javax.swing._

object TicTacToe {

  val TicTacToeController = (input: String, state: Array[Any]) => Array[Any] {
    var actualState: Array[Any] = state
    if (actualState == null) {
      actualState = get_init_state()
    } else {
      actualState = actualState(0).asInstanceOf[Array[Any]]
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
        actualState(2) = actualState(2).asInstanceOf[Int] + 1
      }
    }
    actualState
  }

  val TicTacToeDrawer = (state: Array[Any]) => {
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
      gameState = state(0).asInstanceOf[Array[Any]]
      val buttons = App.board.getComponents
      for (i <- 0 until 3) {
        for (j <- 0 until 3) {
          val text = gameState(3).asInstanceOf[Array[Array[Char]]](i)(j).asInstanceOf[Int]
          if (text == 32) buttons(i*3 + j).asInstanceOf[JButton].setText(" ")
          else if (text == 49) buttons(i*3 + j).asInstanceOf[JButton].setText("X")
          else if (text == 50) buttons(i*3 + j).asInstanceOf[JButton].setText("O")
          println("i="+i+" j="+j+" text="+text)
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