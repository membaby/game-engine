package Games
import javax.swing._
import java.awt.{Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}
import java.awt.Color
import scala.util.matching.Regex
import App._

object Queens {

  val QueensController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null){
      actualState = get_init_state()
    }
    else{
      val addPattern: Regex = "[0-7][a-h]".r
      val deletePattern: Regex = "delete [a-h]".r
      if (deletePattern.matches(input)) {
        var board = state(3).asInstanceOf[Array[Array[Boolean]]]
        val col = input.charAt(7) - 'a'
        var queenAt = -1
        for (row <- 0 to 7) {
          if (board(row)(col) == true) queenAt = row
        }
        if (queenAt == -1) {
          //Invalid column
        }
        else {
          board(queenAt)(col) = false
        }
      }
      else if (addPattern.matches(input)) {
        var board = actualState(3).asInstanceOf[Array[Array[Boolean]]]
        val (row, col) = (input.charAt(0) - '0', input.charAt(1) - 'a')
        var validQueen = true
        //Check column
        for (y <- 0 to 7) {
          if (board(y)(col) == true) validQueen = false
        }
        //Check row
        for (x <- 0 to 7) {
          if (board(row)(x) == true) validQueen = false
        }
        //Check diagonals
        var (diagRow, diagCol) = (row - Math.min(row, col), col - Math.min(row, col))
        while (diagRow < 8 && diagCol < 8) {
          if (board(diagRow)(diagCol) == true) validQueen = false
          diagCol += 1
          diagRow += 1
        }
        if (validQueen) {
          board(diagRow)(diagCol) = true
          actualState(2) = actualState(2).asInstanceOf[Int] + 1
        }
        else {
          //Invalid place for queen
        }
      }
      else {
        //Invalid input
      }
    }
    actualState
  }

  val QueensDrawer = (state: Array[Any]) => {
      var gameState = state
      if (gameState == null) {
        gameState = get_init_state()
        App.board.setLayout(new GridLayout(gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int]))
        var buttons = Array.ofDim[JButton](gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int])
        for (i <- 0 until gameState(0).asInstanceOf[Int]) {
          for (j <- 0 until gameState(1).asInstanceOf[Int]) {
            buttons(i)(j) = new JButton(gameState(3).asInstanceOf[Array[Array[Boolean]]](i)(j).toString)
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

  private def get_init_state(): Array[Any] ={
    var state = new Array[Any](5)
    var (row, col, turn) = (8, 8, 0)
    var board: Array[Array[Boolean]] = Array.fill(8)(Array.fill(8)(false))
    var history = List(board)
    state(0) = row
    state(1) = col
    state(2) = turn
    state(3) = board
    state(4) = history
    return state
  }

}