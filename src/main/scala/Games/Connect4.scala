package Games
import javax.swing._
import java.awt.{Color, Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}
import scala.util.matching.Regex
import App._

import scala.util.control.Breaks._


object Connect4 {

  val ConnectController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null) actualState = get_init_state()
    else{
      val inputPattern: Regex = "[a-g]".r
      if (inputPattern.matches(input)) {
        val col = input.charAt(0) - 'a'
        val board = state(3).asInstanceOf[Array[Array[Char]]]
        if (board(0)(col) == ' ') {
          var nextRow = 5
          while (board(nextRow)(col) != ' ') nextRow -= 1
          var res = '0'
          if (state(2).asInstanceOf[Int] % 2 == 0) res = '1'
          else res = '2'
          board(nextRow)(col) = res
          state(2) = state(2).asInstanceOf[Int] + 1
          actualState(5) = ""
        }
        else {
          //Full column
          actualState(5) = "Column is full"
        }
      }
      else if ("del [a-g]".r.matches(input)) {
        var col = input.charAt(4) - 'a'
        var board = actualState(3).asInstanceOf[Array[Array[Char]]]
        breakable {
          for (i <- 0 until 6) {
            if (board(i)(col) != ' ') {
              board(i)(col) = ' '
              break
            }
          }
        }
        actualState(2) = actualState(2).asInstanceOf[Int] + 1
        actualState(5) = ""
        }
      else{
        //Invalid input
        actualState(5) = "Invalid input"
      }
    }
    actualState
  }

  val ConnectDrawer = (state: Array[Any]) => {
    var gameState = state.asInstanceOf[Array[Any]]
    if (App.board.getComponentCount == 0) {
      gameState = get_init_state()
      App.board.setLayout(new GridLayout(gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int]))
      var buttons = Array.ofDim[JButton](gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int])
      for (i <- 0 until gameState(0).asInstanceOf[Int]) {
        for (j <- 0 until gameState(1).asInstanceOf[Int]) {
          buttons(i)(j) = new JButton(gameState(3).asInstanceOf[Array[Array[Char]]](i)(j).toString)
          buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 20))
          buttons(i)(j).setText((97 + j).toChar.toString)
          buttons(i)(j).setForeground(Color.GRAY);
          App.board.add(buttons(i)(j))
        }
      }
    } else {
      gameState = state
      val buttons = App.board.getComponents
      for (i <- 0 until gameState(0).asInstanceOf[Int]) {
        for (j <- 0 until gameState(1).asInstanceOf[Int]) {
          val button = buttons(i * gameState(1).asInstanceOf[Int] + j)
          val text = gameState(3).asInstanceOf[Array[Array[Char]]](i)(j).asInstanceOf[Int]
          if (text == 49){
            button.asInstanceOf[JButton].setIcon(new ImageIcon("src/main/static/red-circle.png"))
            button.asInstanceOf[JButton].setText("")
          }
          else if (text == 50){
            button.asInstanceOf[JButton].setIcon(new ImageIcon("src/main/static/blue-circle.png"))
            button.asInstanceOf[JButton].setText("")
          } else {
            if (button.asInstanceOf[JButton].getText.length != 1) {
              button.asInstanceOf[JButton].setText("")
              button.asInstanceOf[JButton].setIcon(null)
            }
          }
        }
      }
    }
    App.errorLabel.setText(gameState(5).asInstanceOf[String])
    App.board.revalidate()
    App.board.repaint()
  }


  private def get_init_state(): Array[Any] = {
    var state = new Array[Any](6)
    val (row, col, turn) = (6,7,0)
    var board = Array.fill(6)(Array.fill(7)(' '))
    var history = List(board)
    state(0) = row
    state(1) = col
    state(2) = turn
    state(3) = board
    state(4) = history
    state(5) = ""
    return state
  }


}