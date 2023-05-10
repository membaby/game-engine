package GameDebuggers

object Connect4Debugger {
  def main(args: Array[String]): Unit = {

    val controller = Games.Connect4.ConnectController
    DebugEngine.engine(controller, debugDrawer)
  }

  val debugDrawer = (state: Array[Any]) =>{
    val (rows, cols) = (state(0).asInstanceOf[Int], state(1).asInstanceOf[Int])
    val board = state(3).asInstanceOf[Array[Array[Char]]]
    for (i <- 0 to rows) {
      for (j <- 0 to cols) {
        printf("%5d", board(i)(j))
      }
      println()
    }
    println()
  }
}
