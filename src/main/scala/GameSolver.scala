package App
import org.jpl7._

object GameSolver {
  def solve(game: String, queryString: String): Array[Array[Any]] = {
    if (game == "sudoku") {
      val prolog = new Query("consult('src/main/prolog/sudoku.pl')")
      println("Sudoku Prolog Consult " + (if (prolog.hasSolution) "Succeeded" else "failed"))
      val query = new Query("sudoku(Rows, " + queryString + ").")

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
    } else if (game == "8queens") {
      val prolog = new Query("consult('src/main/prolog/queens.pl')")
      println("Queens Prolog Consult " + (if (prolog.hasSolution) "Succeeded" else "failed"))
      val query = new Query("Qs = " + queryString + " ,eight_queens(Qs), maplist(between(1,8), Qs).")
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
