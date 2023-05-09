package Games
import javax.swing._
import java.awt.{Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}
import scala.util.matching.Regex


object Connect4 {

  def setup(panel: JPanel): Unit = {
//    var state = new ConnectGameState()
//    ConnectDrawer(state)
//    panel.setLayout(new GridLayout(state.rows, state.cols))
//    var buttons = Array.ofDim[JButton](state.rows, state.cols)
//    for (i <- 0 until state.rows) {
//      for (j <- 0 until state.cols) {
//        buttons(i)(j) = new JButton(state.board(i)(j).toString)
//        buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 20))
//        buttons(i)(j).setActionCommand(i.toString + j.toString)
//        panel.add(buttons(i)(j))
//      }
//    }
  }

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

  }

  private def get_init_state(): Array[Any] = {
    var state = Array[Any](5)
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