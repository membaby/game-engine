import java.awt._
import javax.swing._
import java.io.File
import javax.imageio.ImageIO
import scala.collection.immutable.List
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import Games._


object App {

	private val (sudoku, ttt, queens, connect4, chess, checkers) = (0,1,2,3,4,5)

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
		val board: JPanel = new JPanel(null)
		val panelsList = List[Component](homepanel, gamePanel)

		// Home Page
		val img_selectgame = new JLabel(new ImageIcon(ImageIO.read(new File("src/main/static/select-game.png"))))
		img_selectgame.setBounds(176, 40, 861, 92)
		homepanel.add(img_selectgame)

		// Buttons
		val gameNames = List("TicTacToe", "connect-4", "Checkers", "Chess", "Sudoku", "8Queens")
		val buttonsList = gameNames.zipWithIndex.map { case (gameName, idx) =>
			val button = new JLabel(new ImageIcon(ImageIO.read(new File(s"src/main/static/$gameName.png"))))
			button.setBounds(39 + (362 + 15) * (idx % 3), 166 + (idx / 3) * 294, 362, 242)
			button.setCursor(new Cursor(Cursor.HAND_CURSOR))
			homepanel.add(button)
		}

		// Game Page
		val btn_back = createButton(gamePanel, "src/main/static/arrow-left.png", 34, 10)
		val btn_undo = createButton(gamePanel, "src/main/static/Undo.png", 935, 10)
		val btn_redo = createButton(gamePanel, "src/main/static/Redo.png", 1050, 10)
		val user_input_1 = createTextField(gamePanel, 34, 680)
		val user_input_2 = createTextField(gamePanel, 200, 680)

		val label_gametitle = new JLabel();
		label_gametitle.setBounds(160, 10, 1115, 84)
		label_gametitle.setFont(new Font("Arial", Font.BOLD, 50))
		gamePanel.add(label_gametitle)

		def setGameTitle(title: String): Unit = {
			label_gametitle.setText(title)
		}



		buttonsList(0).addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(gamePanel)
				clearBoard()
				start_game(ttt)
			}
		})

		buttonsList(1).addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(gamePanel)
				clearBoard()
				start_game(connect4)
			}
		})

		buttonsList(2).addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(gamePanel)
				clearBoard()
				start_game(checkers)
			}
		})

		buttonsList(3).addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(gamePanel)
				clearBoard()
				start_game(chess)
			}
		})

		buttonsList(5).addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(gamePanel)
				clearBoard()
				start_game(queens)
			}
		})

		buttonsList(4).addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(gamePanel)
				clearBoard()
				start_game(sudoku)
			}
		})

		btn_back.addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(homepanel)
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

		def setActivePanel(panel: JPanel): Unit = {
			panelsList.foreach(panel => panel.setVisible(false))
			panel.setVisible(true)
		}

		def clearBoard(): Unit = {
			board.removeAll()
			board.revalidate()
			board.repaint()
		}

		def start_game(game: Int): Unit = {
			val (controller, drawer, title) = game match {
				case checkers => (Checkers.CheckersController, Checkers.CheckersDrawer, "Checkers")
				case sudoku => (Sudoku.SudokuController, Sudoku.SudokuDrawer, "Sudoku")
				case ttt => (TicTacToe.TicTacToeController, TicTacToe.TicTacToeDrawer, "Tick Tack Toe")
				case queens => (Queens.QueensController, Queens.QueensDrawer, "8 Queens")
				case chess => (Chess.ChessController, Chess.ChessDrawer, "Chess")
				case connect4 => (Connect.ConnectController, Connect.ConnectDrawer, "Connect 4")

			}
			setGameTitle(title)
			AbstractEngine.abstract_engine(controller, drawer)

		}

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
}