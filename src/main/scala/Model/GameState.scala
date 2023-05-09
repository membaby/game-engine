package Model

trait GameState {
  var rows: Int
  var cols: Int
  var board: Array[Array[Char]]
  var turn: Int
  var history: List[Array[Array[Char]]]
}