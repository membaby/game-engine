package Games
import javax.swing._
import java.awt.{Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}
import scala.util.matching.Regex
import App._


object Connect4 {

  val ConnectController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null) actualState = get_init_state()
    val inputPattern: Regex = "[a-g]".r
    if (inputPattern.matches(input)){
      val col = input.charAt(0)-'a'
      val board = state(3).asInstanceOf[Array[Array[Char]]]
      if (board(0)(col) == ' '){
        var nextRow = 5
        while (board(nextRow)(col) != ' ') nextRow -= 1
        var res = '0'
        if (state(2).asInstanceOf[Int]%2 == 0) res = '1'
        else res = '2'
        board(nextRow)(col) = res
        state(2) = state(2).asInstanceOf[Int] + 1
      }
      else{
        //Full column
      }
    }
    else{
      //Invalid Input
    }
    actualState
  }

  val ConnectDrawer = (state: Array[Any]) => {
    var gameState = state
    if (gameState == null) {
      gameState = get_init_state()
      App.board.setLayout(new GridLayout(gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int]))
      var buttons = Array.ofDim[JButton](gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int])
      for (i <- 0 until gameState(0).asInstanceOf[Int]) {
        for (j <- 0 until gameState(1).asInstanceOf[Int]) {
          buttons(i)(j) = new JButton(gameState(3).asInstanceOf[Array[Array[Char]]](i)(j).toString)
          buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 40))
          App.board.add(buttons(i)(j))
        }
      }
    } else {
      gameState = state
      val buttons = App.board.getComponents
      for (i <- 0 until 3) {
        for (j <- 0 until 3) {
          val text = gameState(3).asInstanceOf[Array[Array[Char]]](i)(j).asInstanceOf[Int]
          if (text == 32) buttons(i * 3 + j).asInstanceOf[JButton].setText(" ")
          else if (text == 49) buttons(i * 3 + j).asInstanceOf[JButton].setText("X")
          else if (text == 50) buttons(i * 3 + j).asInstanceOf[JButton].setText("O")
          println("i=" + i + " j=" + j + " text=" + text)
        }
      }

    }

    App.board.revalidate()
    App.board.repaint()
  }


  private def get_init_state(): Array[Any] = {
    var state = new Array[Any](5)
    val (row, col, turn) = (6,7,0)
    var board = Array.fill(6)(Array.fill(7)(' '))
    var history = List(board)
    state(0) = row
    state(1) = col
    state(2) = turn
    state(3) = board
    state(4) = history
    return state
  }


}