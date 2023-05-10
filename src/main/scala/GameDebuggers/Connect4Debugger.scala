package GameDebuggers

object Connect4Debugger {
  def main(args: Array[String]): Unit = {

    val controller = Games.Connect4.ConnectController
    DebugEngine.engine(controller, debugDrawer)
  }

  val debugDrawer = (state: Array[Any]) =>{
    val board = state(3).asInstanceOf[Array[Array[Char]]]
    for ()
  }
}
