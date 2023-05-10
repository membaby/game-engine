package GameDebuggers


object SudokuDebugger{
  def main(args: Array[String]): Unit = {
    val controller = Games.Sudoku.SudokuController
    DebugEngine.engine(controller, debugDrawer)
  }


  private val debugDrawer = (state: Array[Any]) =>{
    var actualState = state
    if (actualState == null) actualState = Games.Sudoku.get_init_state()
    val board = actualState(3).asInstanceOf[Array[Array[Int]]]
    for (i <- 0 to 8){
      for (j <- 0 to 8){
        printf("%5d", board(i)(j))
      }
      println()
    }
    println()
  }
}

