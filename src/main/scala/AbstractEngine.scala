package App

import scala.util.control.Breaks.break

object AbstractEngine
{
	def abstract_engine(controller: (String, Array[Any]) => Array[Any], drawer: Array[Any] => Unit) : Unit = 
	{
		var state: Array[Any] = null
		val concrete_controller = abstract_controller(controller)
		val concrete_drawer = abstract_drawer(drawer)
		state = concrete_controller("", null)
		while (true)
		{
			concrete_drawer(state)
			while (!App.inputReady && !App.closeGame) {
				Thread.sleep(16)
			}
			if (App.closeGame) break
			var input: String = App.input
			App.inputReady = false
			state = concrete_controller(input, state)
		}
	}


	private def abstract_controller(game_controller: (String, Array[Any]) => Array[Any]) : ((String, Array[Any]) => Array[Any]) =
	{
		val concrete_controller = (input:String, state: Array[Any]) =>
		{
			//Parse input looking for general commands. Example: exit
			//If not a valid general command then pass input and state to game_controller
			//game_controller should show an error message if the input is invalid
			//Return the new state that game_controller returns. 
			game_controller(input, state)
		}
		return concrete_controller
	}


	private def abstract_drawer(game_drawer: Array[Any] => Unit) : Array[Any] => Unit =
	{
		val concrete_drawer = (state: Array[Any]) =>
		{
			game_drawer(state)
		}
		return concrete_drawer
	}
}