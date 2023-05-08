

object AbstractEngine
{
	def abstract_engine(controller: (String, Array[Any]) => Array[Any], drawer: Array[Any] => Unit) : Unit = 
	{
		var state: Array[Any] = null
		//Initialize abstract controller
		val concrete_controller: (String, Array[Any]) => Array[Any] = abstract_controller(controller)
		//Initialize abtract drawer
		val concrete_drawer: Array[Any] => Unit = abstract_drawer(drawer)

		while (true)
		{
			//pass state to drawer
			//Take input from user
			//pass input and state to concrete controller
		}
	}


	def abstract_controller(game_controller: (String, Array[Any]) => Array[Any]) : ((String, Array[Any]) => Array[Any]) = 
	{
		val concrete_controller = (input:String, state: Array[Any]) =>
		{
			//Parse input looking for general commands. Example: exit
			//If not a valid general command then pass input and state to game_controller
			//game_controller should show an error message if the input is invalid
			//Return the new state that game_controller returns. 
			new Array[Any](0)
		}
		return concrete_controller
	}


	def abstract_drawer(game_drawer: Array[Any] => Unit) : Array[Any] => Unit = 
	{
		val concrete_drawer = (state: Array[Any]) =>
		{
			//Draw general info like Letters and numbers of the grid
			//use game_drawer to draw the state
		}
		return concrete_drawer
	}
}