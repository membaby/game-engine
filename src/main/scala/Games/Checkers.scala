package Games
import App._

import javax.swing._
import java.awt.{Color, Component, GridLayout}
import java.util.ArrayList
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
      val validSrc = is_valid_source(srcRow, srcCol, actualState)
      if (board(destRow)(destCol) != ' '){
        //Invalid Destination
      }
      else if (!validSrc) {
        //Invalid source
      }
      else {
        val dests = get_possible_dests(srcRow, srcCol, actualState)
        if (dests.contains((destRow, destCol))){
          //Process the move
          val player = board(srcRow)(srcCol)
          board(srcRow)(srcCol) = ' '
          board(destRow)(destCol) = player
          if (Math.abs(destRow - srcRow) == 1){
            //Normal move
            state(2) = state(2).asInstanceOf[Int] + 1
          }
          else if (Math.abs(destRow - srcRow) == 2){
            //Jump move
            val (midRow, midCol) = ((destRow - srcRow)/2 + srcRow, (destCol-srcCol)/2 + srcCol)
            board(midRow)(midCol) = ' '
            //Should check if double jump is possible. If so then don't increment turn
            state(2) = state(2).asInstanceOf[Int] + 1
          }
        }
        else{
          //Invalid destination
        }
      }
    }
    else {//Invalid input
    }
    actualState
  }

  private def is_valid_source(row: Int, col: Int, state: Array[Any]): Boolean = {
    val player1 = (state(2).asInstanceOf[Int] % 2) == 0
    val board = state(3).asInstanceOf[Array[Array[Char]]]
    return board(row)(col) match{
      case '1' => player1 == true
      case '2' => player1 == false
      case ' ' => false
    }
  }

  private def get_possible_dests(row: Int, col: Int, state: Array[Any]): ArrayList[(Int, Int)]={
    val dests = new ArrayList[(Int, Int)]()
    val currentPlayer = (state(2).asInstanceOf[Int] % 2)+1
    val board = state(3).asInstanceOf[Array[Array[Char]]]
    val direction = if(currentPlayer == 1) -1 else 1 //If it's player 1 then direction is up (-1). Otherwise it's down (1)
    //Checking the ahead diagonals
    val (rowDiag1, colDiag1) = (row + direction, col - 1) //Ahead and left
    val (rowDiag2, colDiag2) = (row + direction, col + 1) //Ahead and right
    if (row >= 7 || row <= 0) return dests
    if (col > 0){
      if (board(rowDiag1)(colDiag1) == ' ') dests.add((rowDiag1, colDiag1))
      else if (board(rowDiag1)(colDiag1) - '0' != currentPlayer) {
        val (rowDiagNext, colDiagNext) = (row + 2 * direction, col - 2)
        if (board(rowDiagNext)(colDiagNext) == ' ') dests.add((rowDiagNext, colDiagNext))
      }
    }
    if (col < 7){
      if (board(rowDiag2)(colDiag2) == ' ') dests.add((rowDiag2, colDiag2))
      else if (board(rowDiag2)(colDiag2) - '0' != currentPlayer) {
        val (rowDiagNext, colDiagNext) = (row + 2 * direction, col - 2)
        if (board(rowDiagNext)(colDiagNext) == ' ') dests.add((rowDiagNext, colDiagNext))
      }
    }
    return dests
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
            buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 15))
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
          } else {
            buttons(i)(j).setBackground(Color.BLACK);
          }
          App.board.add(buttons(i)(j))
        }
      }
    } else {
      val buttons = App.board.getComponents
      for (i <- 0 until gameState(0).asInstanceOf[Int]) {
        for (j <- 0 until gameState(1).asInstanceOf[Int]) {
          val button = buttons(i * gameState(1).asInstanceOf[Int] + j)
          val text = gameState(3).asInstanceOf[Array[Array[Char]]](i)(j).asInstanceOf[Int]
          if (text == 49) {
            button.asInstanceOf[JButton].setIcon(new ImageIcon("src/main/static/black-circle.png"))
            button.asInstanceOf[JButton].setText("")
          }
          else if (text == 50) {
            button.asInstanceOf[JButton].setIcon(new ImageIcon("src/main/static/white-circle.png"))
            button.asInstanceOf[JButton].setText("")
          } else {
            if (button.asInstanceOf[JButton].getText.length != 1) {
              button.asInstanceOf[JButton].setText(i.toString + (97 + j).toChar)
              button.asInstanceOf[JButton].setIcon(null)
            }
          }
        }
      }
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