package GameDebuggers

import scala.io.StdIn.readLine

object DebugEngine {
  def engine(controller: (String, Array[Any])=>Array[Any], drawer: Array[Any]=>Unit): Unit = {
    var state: Array[Any] = null
    state = controller("", null)
    while(true){
      drawer(state)
      val input = readLine()
      state = controller(input, state)
    }
  }
}
