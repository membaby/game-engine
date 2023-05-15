package App

import java.awt._
import javax.swing._
import java.io.File
import javax.imageio.ImageIO
import scala.collection.immutable.List
import java.awt.event.{ActionEvent, ActionListener, MouseAdapter, MouseEvent}
import Games._


object App {

	def main(args: Array[String]): Unit = {

		// Helper Functions
		def createButton(panel: JPanel, iconPath: String, x: Int, y: Int): JLabel = {
			val btn = new JLabel(new ImageIcon(ImageIO.read(new File(iconPath))))
			btn.setBounds(x, y, 100, 84)
			btn.setCursor(new Cursor(Cursor.HAND_CURSOR))
			panel.add(btn)
			btn
		}

		def createTextField(panel: JPanel, x: Int, y: Int): JTextField = {
			val textField = new JTextField()
			textField.setBounds(x, y, 150, 50)
			textField.setFont(new Font("Arial", Font.BOLD, 30))
			panel.add(textField)
			textField
		}

		// Main Window
		val frame: JFrame = new JFrame("Ultimate X - Game Engine")
		val homepanel: JPanel = new JPanel(null)
		val gamePanel: JPanel = new JPanel(null)
		board = new JPanel(null)
		val panelsList = List[Component](homepanel, gamePanel)

		// Home Page
		val img_selectgame = new JLabel(new ImageIcon(ImageIO.read(new File("src/main/static/select-game.png"))))
		img_selectgame.setBounds(176, 40, 861, 92)
		homepanel.add(img_selectgame)

		// Game Page
		val btn_back = createButton(gamePanel, "src/main/static/arrow-left.png", 34, 10)
		val btn_act  = new JButton("Go")
		btn_act.setBounds(200, 680, 150, 50)
		gamePanel.add(btn_act)
		val user_input_1 = createTextField(gamePanel, 38, 680)

		val label_gametitle = new JLabel()
		label_gametitle.setBounds(160, 10, 1115, 84)
		label_gametitle.setFont(new Font("Arial", Font.BOLD, 50))
		gamePanel.add(label_gametitle)

		def setGameTitle(title: String): Unit = label_gametitle.setText(title)

		def addActionListener(field: JTextField, func: String): Unit = {
			// Remove all previous action listeners
			for (listener <- field.getActionListeners) {
				field.removeActionListener(listener)
			}
			// Add new action listener
			field.addActionListener(new ActionListener() {
				override def actionPerformed(e: ActionEvent): Unit = {
					if (e.getSource == user_input_1 && user_input_1.getText.nonEmpty) {
						println(s"Text entered: ${user_input_1.getText}")
						input = user_input_1.getText
						inputReady = true
						user_input_1.setText("")
					}
				}
			})
		}

		def start_game(game: String): Unit = {
			val (controller, drawer, title) = game match {
				case "Checkers"  => (Checkers.CheckersController, Checkers.CheckersDrawer, "Checkers")
				case "Sudoku" 	 => (Sudoku.SudokuController, Sudoku.SudokuDrawer, "Sudoku")
				case "TicTacToe" => (TicTacToe.TicTacToeController, TicTacToe.TicTacToeDrawer, "Tic Tac Toe")
				case "8Queens" 	 => (Queens.QueensController, Queens.QueensDrawer, "8 Queens")
				case "Chess" 		 => (Chess.ChessController, Chess.ChessDrawer, "Chess")
				case "connect-4" => (Connect4.ConnectController, Connect4.ConnectDrawer, "Connect 4")
			}
			setGameTitle(title)
			engineThread = new Thread(new Runnable {
				override def run(): Unit = AbstractEngine.abstract_engine(controller, drawer)
			})
			closeGame = false
			inputReady = false
			engineThread.start()
			addActionListener(user_input_1, "A")
		}


		def setActivePanel(panel: JPanel): Unit = {
			panelsList.foreach(panel => panel.setVisible(false))
			panel.setVisible(true)
		}

		def clearBoard(): Unit = {
			board.removeAll()
			board.revalidate()
			board.repaint()
		}

		// Buttons
		val gameNames = List("TicTacToe", "connect-4", "Checkers", "Chess", "Sudoku", "8Queens")
		val buttonsList = gameNames.zipWithIndex.map { case (gameName, idx) =>
			val button = new JLabel(new ImageIcon(ImageIO.read(new File(s"src/main/static/$gameName.png"))))
			button.setBounds(39 + (362 + 15) * (idx % 3), 166 + (idx / 3) * 294, 362, 242)
			button.setCursor(new Cursor(Cursor.HAND_CURSOR))
			homepanel.add(button)
			button.addMouseListener(new MouseAdapter {
				override def mouseClicked(e: MouseEvent): Unit = {
					setActivePanel(gamePanel)
					clearBoard()
					start_game(gameName)
				}
			})
		}

		btn_back.addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(homepanel)
				closeGame = true
			}
		})

		homepanel.setBackground(Color.YELLOW)
		gamePanel.setBackground(Color.ORANGE)
		gamePanel.add(board)
		board.setBounds(255, 110, 700, 530)

		panelsList.foreach(panel => {
			panel.setBounds(0, 0, 1200, 800)
			frame.add(panel)
		})

		// Main
		setActivePanel(homepanel)
		frame.setMinimumSize(new Dimension(1200, 800))
		frame.setLocationRelativeTo(null)
		frame.setResizable(false)
		frame.setLayout(new BorderLayout(10, 10))
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
		frame.pack()
		frame.setVisible(true)
	}

	private var engineThread: Thread = null
	var inputReady: Boolean = false
	var closeGame: Boolean = false
	var input: String = ""
	var board: JPanel = null
}