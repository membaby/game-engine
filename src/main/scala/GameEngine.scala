import Model._

case class GameEngine(drawer: Drawer, controller: Controller){
  var state: State = null;
  state = controller(state, "");
  drawer(state, controller);
}