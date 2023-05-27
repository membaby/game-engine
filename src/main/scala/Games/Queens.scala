package Games
import javax.swing._
import java.awt.{Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}
import java.awt.Color
import scala.util.matching.Regex
import App._
import GameSolver.solve
import org.jpl7.Compound

object Queens {

  val QueensController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null){
      actualState = get_init_state()
    }
    else{
      val addPattern: Regex = "[0-7][a-h]".r
      val deletePattern: Regex = "del [a-h]".r
      if (deletePattern.matches(input)) {
        val col = input.charAt(4) - 'a'
        delete_queen_at(col, actualState)
      }
      else if (addPattern.matches(input)) {
        val (row, col) = (input.charAt(0) - '0', input.charAt(1) - 'a')
        add_queen_at(row, col, actualState)
      }
      else if (input == "solve") {
        var board = actualState(3).asInstanceOf[Array[Array[Boolean]]]
        var solver_query = ""
        for (row <- 0 to 7) {
          var queen = 0
          for (col <- 0 to 7) {
            if (board(row)(col)) queen = col + 1
          }
          if (row < 7) solver_query += queen.toString + ","
          else solver_query += queen.toString
        }
        solver_query = "[" + solver_query + "]"
        solver_query = solver_query.replace("0", "_")
        val solution = solve("8queens", solver_query)
        if (solution == null) {
          actualState(5) = "No solution"
        }
        else {
          for (row <- 0 to 7) {
            for (col <- 0 to 7) {
              board(row)(col) = solution(row)(col).asInstanceOf[Boolean]
            }
          }
        }
      }
      else {
        //Invalid input
        actualState(5) = "Invalid Input"
      }
    }
    actualState
  }

  private def delete_queen_at(col: Int, state: Array[Any]): Unit = {
    var board = state(3).asInstanceOf[Array[Array[Boolean]]]
    var queenAt = -1
    for (row <- 0 to 7) {
      if (board(row)(col) == true) queenAt = row
    }
    if (queenAt == -1) {
      //Invalid column
      state(5) = "Invalid column"
    }
    else {
      board(queenAt)(col) = false
      state(5) = true
    }
  }

  private def add_queen_at(row: Int, col: Int, state: Array[Any]): Unit ={
    var board = state(3).asInstanceOf[Array[Array[Boolean]]]
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
    //negative slope diagonal
    var (diagRow, diagCol) = (row - Math.min(row, col), col - Math.min(row, col))
    while (diagRow < 8 && diagCol < 8) {
      if ((diagRow != row || diagCol != col) && board(diagRow)(diagCol) == true) validQueen = false
      diagCol += 1
      diagRow += 1
    }
    //postive slope diagonal
    var (diagRow2, diagCol2) = (row, col)
    while (diagRow2 < 7 && diagCol2 > 0){
      diagRow2 += 1
      diagCol2 -= 1
    }
    while(diagRow2 >= 0 && diagCol2 < 8){
      if ((diagRow2 != row || diagCol2 != col) && board(diagRow2)(diagCol2) == true) validQueen = false
      diagCol2 += 1
      diagRow2 -= 1
    }

    if (validQueen) {
      board(row)(col) = true
      state(2) = state(2).asInstanceOf[Int] + 1
      state(5) = ""
    }
    else {
      //Invalid place for queen
      state(5) = "Invalid square for queen"
    }
  }



  val QueensDrawer = (state: Array[Any]) => {
      var gameState = state
      if (App.board.getComponentCount == 0) {
        gameState = get_init_state()
        App.board.setLayout(new GridLayout(gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int]))
        var buttons = Array.ofDim[JButton](gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int])
        for (i <- 0 until gameState(0).asInstanceOf[Int]) {
          for (j <- 0 until gameState(1).asInstanceOf[Int]) {
            buttons(i)(j) = new JButton()
            buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 15))
            buttons(i)(j).setText(i.toString + (97+j).toChar)
            buttons(i)(j).setForeground(Color.GRAY)
            if ((i + j) % 2 == 0) {
              buttons(i)(j).setBackground(Color.WHITE);
            } else {
              buttons(i)(j).setBackground(Color.BLACK);
            }
            App.board.add(buttons(i)(j))
          }
        }
      } else {
        gameState = state
        val buttons = App.board.getComponents
        var solved_rows = 0
        for (i <- 0 until gameState(0).asInstanceOf[Int]) {
          for (j <- 0 until gameState(1).asInstanceOf[Int]) {
            val button = buttons(i * gameState(1).asInstanceOf[Int] + j)
            if (gameState(3).asInstanceOf[Array[Array[Boolean]]](i)(j)) {
              button.asInstanceOf[JButton].setIcon(new ImageIcon("src/main/static/queen.png"))
              button.asInstanceOf[JButton].setText("")
              solved_rows += 1
            } else {
              button.asInstanceOf[JButton].setIcon(null)
              button.asInstanceOf[JButton].setText(i.toString + (97+j).toChar)
            }
          }
        }
        if (solved_rows == gameState(0).asInstanceOf[Int]) {
          gameState(5) = "Game finished"
        }
      }
      App.errorLabel.setText(gameState(5).asInstanceOf[String])
      App.board.revalidate()
      App.board.repaint()
  }

  private def get_init_state(): Array[Any] ={
    var state = new Array[Any](6)
    var (row, col, turn) = (8, 8, 0)
    var board: Array[Array[Boolean]] = Array.fill(8)(Array.fill(8)(false))
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