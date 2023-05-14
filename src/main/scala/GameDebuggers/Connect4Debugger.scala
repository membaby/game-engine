package GameDebuggers

object Connect4Debugger {
  def main(args: Array[String]): Unit = {
    val controller = Games.Connect4.ConnectController
    DebugEngine.engine(controller, debugDrawer)
  }

  private val debugDrawer = (state: Array[Any]) =>{
    val (rows, cols) = (state(0).asInstanceOf[Int], state(1).asInstanceOf[Int])
    val board = state(3).asInstanceOf[Array[Array[Char]]]
    for (i <- 0 until rows) {
      for (j <- 0 until cols) {
        printf("%5d", board(i)(j))
      }
      println()
    }
    val playing = state(2).asInstanceOf[Int]%2 + 1
    println("Player" + playing + "\n")
  }
}
