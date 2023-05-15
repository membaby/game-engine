package GameDebuggers

object ChessDebugger {
  def main(args: Array[String]): Unit = {
    val controller = Games.Chess.ChessController
    DebugEngine.engine(controller, debugDrawer)
  }

  private val debugDrawer = (state: Array[Any]) => {
    val (rows, cols) = (state(0).asInstanceOf[Int], state(1).asInstanceOf[Int])
    val board = state(3).asInstanceOf[Array[Array[Char]]]
    printf("%3c", ' ')
    for (i <- 'a' to 'h') {
      printf("%5c", i)
    }
    println()
    for (i <- 0 until rows) {
      printf("%3d", i)
      for (j <- 0 until cols) {
        printf("%5c", board(i)(j))
      }
      println()
    }
    val playing = state(2).asInstanceOf[Int] % 2 + 1
    println("Player " + playing + " is playing\n")
  }
}
