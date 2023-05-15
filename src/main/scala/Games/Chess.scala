package Games
import App._

import javax.swing._
import java.awt.{Color, Component, GridLayout}
import java.util.ArrayList
import javax.swing.{JFrame, JLabel, JPanel}

object Chess {
  private val EMPTY = ' '
  private val (w_PAWN, w_ROOK, w_BISHOP, w_KNIGHT, w_QUEEN, w_KING) = ('p', 'r', 'b', 'n', 'q', 'k')
  private val (b_PAWN, b_ROOK, b_BISHOP, b_KNIGHT, b_QUEEN, b_KING) = ('P', 'R', 'B', 'N', 'Q', 'K')


  val ChessController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null){
      actualState = get_init_state()
    }
    else{
      val validInput = "[0-7][a-h] [0-7][a-h]".r
      if (validInput.matches(input)){
        val (srcRow, srcCol) = (input.charAt(0)-'0', input.charAt(1)-'a')
        val validSrc = is_valid_source(srcRow, srcCol, actualState)
        if (validSrc){
          val board = actualState(3).asInstanceOf[Array[Array[Char]]]
          val (destRow, destCol) = (input.charAt(3)-'0', input.charAt(4)-'a')
          val possibleDestinations = get_possible_moves(srcRow, srcCol, actualState)
          if (possibleDestinations.contains((destRow, destCol))){
            val piece = board(srcRow)(srcCol)
            board(destRow)(destCol) = piece
            board(srcRow)(srcCol) = EMPTY
            state(2) = state(2).asInstanceOf[Int] + 1
          }
          else{
            //Invalid move
          }

        }
        else{
          //Invalid source
        }
      }
      else{
        //Invalid input
      }
    }
    actualState
  }


  private def is_valid_source(row: Int, col: Int, state: Array[Any]): Boolean = {
    val curPlayer = state(2).asInstanceOf[Int]%2 + 1
    return curPlayer match{
      case 1 => is_white_piece(row, col, state(3).asInstanceOf[Array[Array[Char]]])
      case 2 => is_black_piece(row, col, state(3).asInstanceOf[Array[Array[Char]]])
    }
  }

  private def get_possible_moves(srcRow: Int, srcCol: Int, state: Array[Any]): ArrayList[(Int, Int)] = {
    val board = state(3).asInstanceOf[Array[Array[Char]]]
    val piece = board(srcRow)(srcCol)
    var dests = new ArrayList[(Int, Int)]()
    if (piece == w_PAWN){
      if (srcRow != 0) {
        if (board(srcRow - 1)(srcCol) == EMPTY) dests.add((srcRow - 1, srcCol)) //Empty ahead
        if (srcCol != 0 && is_black_piece(srcRow - 1, srcCol - 1, board)) {
          //Enemy at left diagonal
          dests.add((srcRow - 1, srcCol - 1))
        }
        if (srcCol != 7 && is_black_piece(srcRow - 1, srcCol + 1, board)) {
          //Enemy at right diagonal
          dests.add((srcRow - 1, srcCol + 1))
        }
      }
      if (srcRow == 6 && board(srcRow - 2)(srcCol) == EMPTY) dests.add((srcRow - 2, srcCol)) //First move
    }
    else if(piece == b_PAWN){
      if (srcRow != 7) {
        if (board(srcRow + 1)(srcCol) == EMPTY) dests.add((srcRow + 1, srcCol)) //Empty ahead
        if (srcCol != 0 && is_white_piece(srcRow + 1, srcCol - 1, board)) {
          //Enemy at left diagonal
          dests.add((srcRow + 1, srcCol - 1))
        }
        if (srcCol != 7 && is_white_piece(srcRow + 1, srcCol + 1, board)) {
          //Enemy at right diagonal
          dests.add((srcRow + 1, srcCol + 1))
        }
      }
      if (srcRow == 1 && board(srcRow + 2)(srcCol) == EMPTY) dests.add((srcRow + 2, srcCol)) //First move
    }
    else if(piece == w_ROOK || piece == b_ROOK){
      fill_dests_in_line(srcRow, srcCol, board, true, dests)
      fill_dests_in_line(srcRow, srcCol, board, false, dests)
    }
    else if(piece == w_BISHOP || piece == b_BISHOP){
      fill_dests_in_diagonal(srcRow, srcCol, board, true, dests)
      fill_dests_in_diagonal(srcRow, srcCol, board, false, dests)
    }
    else if(piece == w_KNIGHT || piece == b_KNIGHT){
      val arr = new Array[(Int, Int)](8)
      arr(0) = (srcRow - 2, srcCol - 1)
      arr(1) = (srcRow - 2, srcCol + 1)
      arr(2) = (srcRow - 1, srcCol - 2)
      arr(3) = (srcRow + 1, srcCol - 2)
      arr(4) = (srcRow + 2, srcCol - 1)
      arr(5) = (srcRow + 2, srcCol + 1)
      arr(6) = (srcRow - 1, srcCol + 2)
      arr(7) = (srcRow + 1, srcCol + 2)
      val white = board(srcRow)(srcCol) == w_KNIGHT
      for (i <- 0 to 7) {
        val (row, col) = arr(i)
        if (!(row < 0 || row > 7 || col < 0 || col > 7)) {
          if (board(row)(col) == EMPTY || (white && is_black_piece(row, col, board)) || (!white && is_white_piece(row, col, board))) {
            dests.add((row, col))
          }
        }
      }
    }
    else if(piece == w_QUEEN || piece == b_QUEEN){
      fill_dests_in_line(srcRow, srcCol, board, true, dests)
      fill_dests_in_line(srcRow, srcCol, board, false, dests)
      fill_dests_in_diagonal(srcRow, srcCol, board, true, dests)
      fill_dests_in_diagonal(srcRow, srcCol, board, false, dests)
    }
    else if(piece == w_KING || piece == b_KING){
      val white = board(srcRow)(srcCol) == w_KING
      for (i <- -1 to 1) {
        for (j <- -1 to 1) {
          if (i == srcRow && j == srcCol) {} //The source square
          else if (i < 0 || i > 7 || j < 0 || j > 7) {} //Out of board
          else {
            val (row, col) = (srcRow + i, srcCol + j)
            if (board(row)(col) == EMPTY || (white && is_black_piece(row, col, board)) || (!white && is_white_piece(row, col, board))) {
              dests.add((row, col))
            }
          }
        }
      }
    }
    return dests
  }

  private def is_white_piece(row: Int, col: Int, board: Array[Array[Char]]): Boolean={
    return board(row)(col) <= 'z' && board(row)(col) >= 'a'
  }

  private def is_black_piece(row: Int, col: Int, board: Array[Array[Char]]): Boolean = {
    return board(row)(col) <= 'Z' && board(row)(col) >= 'A'
  }

  private def fill_dests_in_line(srcRow: Int, srcCol: Int, board: Array[Array[Char]], vertical: Boolean, dests: ArrayList[(Int,Int)]): Unit={
    val piece = board(srcRow)(srcCol)
    val white = piece <= 'z' && piece >= 'a'
    var blocked1 = false
    var blocked2 = false
    var loc1 = if (vertical) srcRow-1 else srcCol-1
    var loc2 = if (vertical) srcRow+1 else srcCol+1
    while (!blocked2 && !blocked1) { //Vertical checks
      if (!blocked1) {
        if (loc1 >= 0){
          val (row, col) = if (vertical) (loc1,srcCol) else (srcRow,loc1)
          if (board(row)(col) == EMPTY){//This square is empty
            dests.add((row, col))
            loc1 -= 1
          }
          else if ((white && is_black_piece(row, col, board) || (!white && is_white_piece(row, col, board)))){//This square has an enemy
            dests.add((row, col))
            blocked1 = true
          }
        }
        else blocked1 = true//This square is outside the board
      }
      if (!blocked2) {
        if (loc2 >= 0) {
          val (row, col) = if (vertical) (loc2, srcCol) else (srcRow, loc2)
          if (board(row)(col) == EMPTY) { //This square is empty
            dests.add((row, col))
            loc2 += 1
          }
          else if ((white && is_black_piece(row, col, board) || (!white && is_white_piece(row, col, board)))) { //This square has an enemy
            dests.add((row, col))
            blocked2 = true
          }
        }
        else blocked2 = true //This square is outside the board
      }
    }
  }

  private def fill_dests_in_diagonal(srcRow: Int, srcCol: Int, board: Array[Array[Char]], positiveSlope: Boolean, dests: ArrayList[(Int,Int)]): Unit={
    val rowIncrementer = if(positiveSlope) -1 else 1
    val colIncrementer = if(positiveSlope) 1 else -1
    val white = board(srcRow)(srcCol) < 'z' && board(srcRow)(srcCol) > 'a'
    var (row1, col1) = (srcRow - rowIncrementer, srcCol - colIncrementer)
    var (row2, col2) = (srcRow + rowIncrementer, srcCol + colIncrementer)
    var (blocked1, blocked2) = (false, false)
    while(!blocked1 && !blocked2){
      if (!blocked1){
        if (row1 < 0 || row1 > 7 || col1 < 0 || col1 > 7) blocked1 = true// Out of board
        else{
          if (board(row1)(col1) == EMPTY){//Empty
            dests.add((row1, col1))
            row1 -= rowIncrementer
            col1 -= colIncrementer
          }
          else if ((white && is_black_piece(row1, col1, board)) || (!white && is_white_piece(row1, col1, board))){//Enemy piece
            dests.add((row1, col1))
            blocked1 = true
          }
          else blocked1 = true//Friendly piece
        }
      }
      if (!blocked2){
        if (row2 < 0 || row2 > 7 || col2 < 0 || col2 > 7) blocked2 = true // Out of board
        else {
          if (board(row2)(col2) == EMPTY) { //Empty
            dests.add((row2, col2))
            row2 += rowIncrementer
            col2 += colIncrementer
          }
          else if ((white && is_black_piece(row2, col2, board)) || (!white && is_white_piece(row2, col2, board))) { //Enemy piece
            dests.add((row2, col2))
            blocked2 = true
          }
          else blocked2 = true //Friendly piece
        }
      }
    }
  }


  val ChessDrawer = (CurrentState: Array[Any]) => {
      var gameState = CurrentState.asInstanceOf[Array[Any]]
      if (App.board.getComponentCount == 0) {
        App.board.setLayout(new GridLayout(gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int]))
        var buttons = Array.ofDim[JButton](gameState(0).asInstanceOf[Int], gameState(1).asInstanceOf[Int])
        for (i <- 0 until gameState(0).asInstanceOf[Int]) {
          for (j <- 0 until gameState(1).asInstanceOf[Int]) {
            buttons(i)(j) = new JButton()
            val value = gameState(3).asInstanceOf[Array[Array[Char]]](i)(j)
            if (value != ' ') {
              if (value.isUpper) buttons(i)(j).setIcon(new ImageIcon("src/main/static/chess/" + value + "-black.png"))
              else buttons(i)(j).setIcon(new ImageIcon("src/main/static/chess/" + value + "-white.png"))
            } else {
              buttons(i)(j).setFont(new java.awt.Font("Arial", 1, 15))
              buttons(i)(j).setText(i.toString + (97 + j).toChar)
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
            val value = gameState(3).asInstanceOf[Array[Array[Char]]](i)(j)
            if (value != ' ') {
              if (value.isUpper) button.asInstanceOf[JButton].setIcon(new ImageIcon("src/main/static/chess/" + value + "-black.png"))
              else button.asInstanceOf[JButton].setIcon(new ImageIcon("src/main/static/chess/" + value + "-white.png"))
              button.asInstanceOf[JButton].setText("")
            } else {
              button.asInstanceOf[JButton].setFont(new java.awt.Font("Arial", 1, 15))
              button.asInstanceOf[JButton].setText(i.toString + (97 + j).toChar)
              button.asInstanceOf[JButton].setIcon(null)
            }
          }
        }
      }
      App.board.revalidate()
      App.board.repaint()
    }

  private def get_init_state(): Array[Any]={
    val state = new Array[Any](4)
    val rows = 8
    val cols = 8
    val turns = 0
    val board = Array.fill(8)(Array.fill(8)(' '))
    board(1) = Array.fill(8)(b_PAWN)
    board(6) = Array.fill(8)(w_PAWN)
    board(0)(0) = b_ROOK
    board(0)(7) = b_ROOK
    board(0)(1) = b_BISHOP
    board(0)(6) = b_BISHOP
    board(0)(2) = b_KNIGHT
    board(0)(5) = b_KNIGHT
    board(0)(3) = b_QUEEN
    board(0)(4) = b_KING
    board(7)(0) = w_ROOK
    board(7)(7) = w_ROOK
    board(7)(1) = w_BISHOP
    board(7)(6) = w_BISHOP
    board(7)(2) = w_KNIGHT
    board(7)(5) = w_KNIGHT
    board(7)(3) = w_QUEEN
    board(7)(4) = w_KING
    state(0) = rows
    state(1) = cols
    state(2) = turns
    state(3) = board

    return state
  }
}