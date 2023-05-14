package  GameDebuggers


object QueensDebugger {
  def main(args: Array[String]): Unit = {

    val controller = Games.Queens.QueensController
    DebugEngine.engine(controller, debugDrawer)
  }

  private val debugDrawer = (state: Array[Any]) => {
    val (rows, cols) = (state(0).asInstanceOf[Int], state(1).asInstanceOf[Int])
    val board = state(3).asInstanceOf[Array[Array[Boolean]]]
    for (i <- 0 until rows) {
      for (j <- 0 until cols) {
        printf("%10b", board(i)(j))
      }
      println()
    }
    println()
  }
}
