package Model

trait Drawer {
  // Input: state, controller
  // Output: None
  def apply(state: State, controller: Controller): Unit
}