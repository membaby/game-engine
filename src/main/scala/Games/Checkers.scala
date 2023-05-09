package Games

import javax.swing._
import java.awt.{Color, Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}


object Checkers {

  def setup(panel: JPanel): Unit = {
//    var state = new CheckersGameState()
//    CheckersDrawer(state)
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

  val CheckersController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null){
      actualState = get_init_state()
    }
    val board = state(3).asInstanceOf[Array[Array[Char]]]
    val inputPattern = "[0-7][a-h] [0-7][a-h]".r
    if (inputPattern.matches(input)){
      val (destRow, destCol) = (input.charAt(3) - '0', input.charAt(4) - 'a')
      val (srcRow, srcCol) = (input.charAt(0) - '0', input.charAt(1) - 'a')
      val player1 = state(2).asInstanceOf[Int] % 2 == 0
      if (board(destRow)(destCol) != ' '){
        //Invalid Destination
      }
      else{
        if (player1) {
          if (board(srcRow)(srcCol) != '1') {
            //Invalid Source
          }
          else {
            var possibleDests = Array[(Int, Int)](4)
            if (board(srcRow-1)(srcCol-1) == ' ') possibleDests(0) = (srcRow-1, srcCol-1)
            else if (board(srcRow-2)(srcCol-2) == ' ') possibleDests(1) = (srcRow-2, srcCol-2)
            if (board(srcRow - 1)(srcCol + 1) == ' ') possibleDests(2) = (srcRow - 1, srcCol + 1)
            else if (board(srcRow - 2)(srcCol + 2) == ' ') possibleDests(3) = (srcRow - 2, srcCol + 2)
            //Missing rest of implementation
          }
        }
        else {
          if (board(srcRow)(srcCol) != '2') {
            //Invalid Source
          }
          else {
            //Missing rest of implementation
          }
      }

      }

    }
    else{
      //Invalid Input
    }
  }

  val CheckersDrawer = (CurrentState: Array[Any]) => {

  }

  private def get_init_state(): Array[Any] ={
    var state = Array[Any](5)
    val (row, col, turn) = (8, 8, 0)
    val board = Array.fill(8)(Array.fill(8)(' '))
    var row1 = 0
    var row2 = 1
    while (row1 < 8){
      board(0)(row1) = '2'
      board(1)(row2) = '2'
      board(2)(row1) = '2'
      row1 += 2
      row2 += 2
    }
    var (row8, row7) = (0, 1)
    while(row8 < 8){
      board(7)(row8) = '1'
      board(6)(row7) = '1'
      board(5)(row8) = '1'
      row8 += 2
      row7 += 2
    }


    return state
  }

}