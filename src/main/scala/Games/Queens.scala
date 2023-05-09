package Games
import javax.swing._
import java.awt.{Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}
import java.awt.Color
import scala.util.matching.Regex

object Queens {

  def setup(panel: JPanel): Unit = {
//    var state = new QueensGameState()
//    QueensDrawer(state)
//    panel.setLayout(new GridLayout(state.rows, state.cols))
//    var buttons = Array.ofDim[JButton](state.rows, state.cols)
//    for (i <- 0 until state.rows) {
//      for (j <- 0 until state.cols) {
//        buttons(i)(j) = new JButton(state.board(i)(j).toString)
//        buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 20))
//        buttons(i)(j).setActionCommand(i.toString + j.toString)
//        if ((j+i+1) % 2 == 0) buttons(i)(j).setBackground(Color.DARK_GRAY)
//        panel.add(buttons(i)(j))
//      }
//    }
  }

  val QueensController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null){
      actualState = get_init_state()
    }
    val addPattern: Regex = "[0-7][a-h]".r
    val deletePattern: Regex = "delete [a-h]".r
    if (deletePattern.matches(input)){
      var board = state(3).asInstanceOf[Array[Array[Boolean]]]
      val col = input.charAt(7) - 'a'
      var queenAt = -1
      for (row <- 0 to 7){
        if (board(row)(col) == true) queenAt = row
      }
      if (queenAt == -1){
        //Invalid column
      }
      else{
        board(queenAt)(col) = false
      }
    }
    else if (addPattern.matches(input)){
      var board = state(3).asInstanceOf[Array[Array[Boolean]]]
      val (row, col) = (input.charAt(0)-'0', input.charAt(1)-'a')
      var validQueen = true
      //Check column
      for (y <- 0 to 7){
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
      if (validQueen){
        board(diagRow)(diagCol) = true
        state(2) = state(2).asInstanceOf[Int] + 1
      }
      else{
        //Invalid place for queen
      }
    }
    else{
      //Invalid input
    }
    actualState
  }

  val QueensDrawer = (state: Array[Any]) => {

  }

  private def get_init_state(): Array[Any] ={
    var state = Array[Any](5)
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