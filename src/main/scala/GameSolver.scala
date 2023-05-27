package App
import org.jpl7._
import scala.io.Source


object GameSolver {
  def solve(game: String = "", queryString: String = ""): Array[Array[Any]] = {

    var gameName = game
    var queryString2 = queryString
    if (game == "" && queryString == "") {
      try {
        val source = Source.fromFile("src/board.txt")
        val lines = source.getLines().take(2).toList
        source.close()

        gameName = lines(0)
        queryString2 = lines(1)
        println(game)
      } catch {
        case e: Exception =>
          println("An error occurred while reading the file.")
          e.printStackTrace()
      }
    }

  if (gameName == "sudoku") {
      val prolog = new Query("consult('src/main/prolog/sudoku.pl')")
      println("Sudoku Prolog Consult " + (if (prolog.hasSolution) "Succeeded" else "failed"))
      val query = new Query("sudoku(Rows, " + queryString2 + ").")

      if (query.hasSolution){
        val solution = query.oneSolution()
        val rowsList = solution.get("Rows").asInstanceOf[Compound].toTermArray
        val board: Array[Array[Any]] = rowsList.map(
          row => row.asInstanceOf[Compound].toTermArray.map(
            cell => cell.asInstanceOf[Integer].intValue
          )
        )

        return board
      }
    } else if (gameName == "8queens") {
      val prolog = new Query("consult('src/main/prolog/queens.pl')")
      println("Queens Prolog Consult " + (if (prolog.hasSolution) "Succeeded" else "failed"))
      val query = new Query("Qs = " + queryString2 + " ,eight_queens(Qs), maplist(between(1,8), Qs).")
      if (query.hasSolution){
        val solution = query.oneSolution()
        val rowsList = solution.get("Qs").asInstanceOf[Compound].toTermArray
        val board: Array[Array[Any]] = Array.ofDim[Any](8, 8)
        for (row <- 0 to 7) {
          for (col <- 0 to 7) {
            val value = rowsList(row).asInstanceOf[org.jpl7.Integer].intValue()
            if (value == (col + 1)) {
              board(row)(col) = true
            }
            else board(row)(col) = false
          }
        }
        return board
      }
    }
    return null
  }
}
