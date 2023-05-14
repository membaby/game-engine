package Games

import javax.swing._
import java.awt.{Color, Component, GridLayout}
import javax.swing.{JFrame, JLabel, JPanel}

object Chess {
  val EMPTY = ' '
  val (w_PAWN, w_ROOK, w_BISHOP, w_KNIGHT, w_QUEEN, w_KING) = ('p', 'r', 'b', 'n', 'q', 'k')
  val (b_PAWN, b_ROOK, b_BISHOP, b_KNIGHT, b_QUEEN, b_KING) = ('P', 'R', 'B', 'N', 'Q', 'K')


  val ChessController = (input: String, state: Array[Any]) => {
    var actualState = state
    if (actualState == null){
      actualState = get_init_state()
    }
    else{
      val validInput = "[0-7][a-h] [0-7][a-h]".r
      if (validInput.matches(input)){
        //Logic
        val (srcRow, srcCol) = (input.charAt(0), input.charAt(1))
        val validSrc = is_valid_source(srcRow, srcCol, actualState)
        if (validSrc){
          //Resume logic
          val board = actualState(3).asInstanceOf[Array[Array[Char]]]
          val (destRow, destCol) = (input.charAt(3), input.charAt(4))

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
    val src = state(3).asInstanceOf[Array[Array[Char]]](row)(col)
    return curPlayer match{
      case 1 => src >= 'a' && src <= 'z'
      case 2 => src >= 'A' && src <= 'Z'
    }
  }

  private def is_valid_move(srcRow: Int, srcCol: Int, destRow: Int, destCol: Int, state: Array[Any]): Boolean = {
    val board = state(3).asInstanceOf[Array[Array[Char]]]
    val piece = board(srcRow)(srcCol)
    if (piece < 'z' && piece > 'a'){//White player
      if (board(destRow)(destCol) < 'z' && board(destRow)(destCol) > 'a'){
        return false
      }
      else{//Black player
        if (board(destRow)(destCol) < 'Z' && board(destRow)(destCol) > 'A'){
          return false
        }
      }
    }
    return piece match{
      case w_PAWN =>{
        return false
      }
    }
  }

  /*
  Notes for drawer about board contents:
  Feel free to use the constants defined at the beginning of the file

  empty squares = ' '
  white pieces are:
  pawn = 'p'
  rook = 'r'
  bishop = 'b'
  knight = 'n'
  queen = 'q'
  king = 'k'
  black pieces are:
  pawn = 'P'
  rook = 'R'
  bishop = 'B'
  knight = 'N'
  queen = 'Q'
  king = 'K'
  */
  val ChessDrawer = (CurrentState: Array[Any]) => {

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