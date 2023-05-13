package Games
import App._
import javax.swing._
import java.awt.{Color, Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}


object Checkers {

  val CheckersController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null){
      actualState = get_init_state()
    }
    val board = actualState(3).asInstanceOf[Array[Array[Char]]]
    val inputPattern = "[0-7][a-h] [0-7][a-h]".r
    if (inputPattern.matches(input)){
      val (destRow, destCol) = (input.charAt(3) - '0', input.charAt(4) - 'a')
      val (srcRow, srcCol) = (input.charAt(0) - '0', input.charAt(1) - 'a')
      val player1 = actualState(2).asInstanceOf[Int] % 2 == 0
      if (board(destRow)(destCol) != ' '){
        //Invalid Destination
      }
      else{
        if (player1) {
          if (board(srcRow)(srcCol) != '1') {
            //Invalid Source
          }
          else {
            var possibleDests = new Array[(Int, Int)](4)
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

    actualState
  }

  val CheckersDrawer = (CurrentState: Array[Any]) => {
    var gameState = CurrentState.asInstanceOf[Array[Any]]
    if (App.board.getComponentCount == 0) {
      App.board.setLayout(new GridLayout(gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int]))
      var buttons = Array.ofDim[JButton](gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int])
      for (i <- 0 until gameState(0).asInstanceOf[Int]) {
        for (j <- 0 until gameState(1).asInstanceOf[Int]) {
          buttons(i)(j) = new JButton()
          if (gameState(3).asInstanceOf[Array[Array[Char]]](i)(j) == ' ') {
            buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 20))
            buttons(i)(j).setText(i.toString + (97 + j).toChar)
            buttons(i)(j).setForeground(Color.GRAY);
          } else if (gameState(3).asInstanceOf[Array[Array[Char]]](i)(j) == '1') {
            buttons(i)(j).setIcon(new ImageIcon("src/main/static/black-circle.png"))
            buttons(i)(j).setText("")
          } else if (gameState(3).asInstanceOf[Array[Array[Char]]](i)(j) == '2') {
            buttons(i)(j).setIcon(new ImageIcon("src/main/static/white-circle.png"))
            buttons(i)(j).setText("")
          }
          if ((i + j) % 2 == 0) {
            buttons(i)(j).setBackground(Color.WHITE);
            buttons(i)(j).setText("")
          } else {
            buttons(i)(j).setBackground(Color.BLACK);
          }
          App.board.add(buttons(i)(j))
        }
      }
    } else {
//      val buttons = App.board.getComponents
//      for (i <- 0 until gameState(0).asInstanceOf[Int]) {
//        for (j <- 0 until gameState(1).asInstanceOf[Int]) {
//          val button = buttons(i * gameState(1).asInstanceOf[Int] + j)
//          val text = gameState(3).asInstanceOf[Array[Array[Char]]](i)(j).asInstanceOf[Int]
//          if (text == 49) {
//            button.asInstanceOf[JButton].setIcon(new ImageIcon("src/main/static/red-circle.png"))
//            button.asInstanceOf[JButton].setText("")
//          }
//          else if (text == 50) {
//            button.asInstanceOf[JButton].setIcon(new ImageIcon("src/main/static/blue-circle.png"))
//            button.asInstanceOf[JButton].setText("")
//          } else {
//            if (button.asInstanceOf[JButton].getText.length != 1) {
//              button.asInstanceOf[JButton].setText("")
//              button.asInstanceOf[JButton].setIcon(null)
//            }
//          }
//        }
//      }
    }
    App.board.revalidate()
    App.board.repaint()
  }

  private def get_init_state(): Array[Any] ={
    var state = new Array[Any](5)
    val (row, col, turn) = (8, 8, 0)
    val board = Array.fill(8)(Array.fill(8)(' '))
    var row1 = 1
    var row2 = 0
    var history = List(board)

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


    state(0) = row
    state(1) = col
    state(2) = turn
    state(3) = board
    state(4) = history


    return state
  }

}