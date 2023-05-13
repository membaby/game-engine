package Games
import App._
import java.awt._
import javax.swing._

object TicTacToe {

  val TicTacToeController = (input: String, state: Array[Any]) => Array[Any] {
    var actualState = state
    if (actualState == null) {
      actualState = get_init_state()
    }
    else {
      actualState = actualState(0).asInstanceOf[Array[Any]]
      val inputPattern = "[0-2][a-c]".r
      if (inputPattern.matches(input)) {
        var row = input.charAt(0) - '0'
        var col = input.charAt(1) - 'a'
        var board = actualState(3).asInstanceOf[Array[Array[Char]]]
        if (board(row)(col) == ' ') {
          var turn = actualState(2).asInstanceOf[Int]
          var res = '0'
          if (turn % 2 == 0) res = '1'
          else res = '2'
          board(row)(col) = res
          actualState(2) = actualState(2).asInstanceOf[Int] + 1
        }
      } else if ("del [0-2][a-c]".r.matches(input)){
        var row = input.charAt(4) - '0'
        var col = input.charAt(5) - 'a'
        var board = actualState(3).asInstanceOf[Array[Array[Char]]]
        board(row)(col) = ' '
        actualState(2) = actualState(2).asInstanceOf[Int] + 1
      }
      else {
        println("INVALID INPUT")
      }
    }
    actualState
  }

  val TicTacToeDrawer = (state: Array[Any]) => {
    var gameState = state.asInstanceOf[Array[Any]]
    gameState = gameState(0).asInstanceOf[Array[Any]]
    if (App.board.getComponentCount == 0) {
      App.board.setLayout(new GridLayout(gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int]))
      var buttons = Array.ofDim[JButton](gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int])
      for (i <- 0 until gameState(0).asInstanceOf[Int]) {
        for (j <- 0 until gameState(1).asInstanceOf[Int]) {
          buttons(i)(j) = new JButton(gameState(3).asInstanceOf[Array[Array[Char]]](i)(j).toString)
          buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 30))
          buttons(i)(j).setText(i.toString + (97+j).toChar)
          buttons(i)(j).setForeground(Color.GRAY);
          App.board.add(buttons(i)(j))
        }
      }
    } else {
      gameState = state(0).asInstanceOf[Array[Any]]
      val buttons = App.board.getComponents
      for (i <- 0 until 3) {
        for (j <- 0 until 3) {
          val button = buttons(i* gameState(1).asInstanceOf[Int] + j)
          val text = gameState(3).asInstanceOf[Array[Array[Char]]](i)(j).asInstanceOf[Int]
          if (text == 49){
            button.asInstanceOf[JButton].setText("X")
            button.setForeground(Color.BLACK);
            button.setFont(new java.awt.Font("Arial", 1, 100))
          } else if (text == 50){
            button.asInstanceOf[JButton].setText("O")
            button.setForeground(Color.RED);
            button.setFont(new java.awt.Font("Arial", 1, 100))
          } else {
            if (buttons(i * gameState(1).asInstanceOf[Int] + j).asInstanceOf[JButton].getText.length != 2){
              buttons(i * gameState(1).asInstanceOf[Int] + j).asInstanceOf[JButton].setText("")
            }
          }
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