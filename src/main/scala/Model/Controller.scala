package Model

trait Controller {
  // Input: state, input
  // Output: State object
  def apply(state: State, input: String): State
}