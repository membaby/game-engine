import java.awt._
import javax.swing._
import java.io.File
import javax.imageio.ImageIO
import scala.collection.immutable.List
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

import Model._
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
		val board: JPanel = new JPanel(null)
		val panelsList = List[Component](homepanel, gamePanel)

		// Home Page
		val img_selectgame = new JLabel(new ImageIcon(ImageIO.read(new File("src/main/static/select-game.png"))));
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
				setGameTitle("Tic Tac Toe")
				TicTacToe.setup(board)
			}
		})

		buttonsList(1).addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(gamePanel)
				clearBoard()
				setGameTitle("Connect 4")
				Connect.setup(board)
			}
		})

		buttonsList(2).addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(gamePanel)
				clearBoard()
				setGameTitle("Checkers")
				Checkers.setup(board)
			}
		})

		buttonsList(3).addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(gamePanel)
				clearBoard()
				setGameTitle("Chess")
				Chess.setup(board)
			}
		})

		buttonsList(5).addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(gamePanel)
				clearBoard()
				setGameTitle("8 Queens")
				Queens.setup(board)
			}
		})

		buttonsList(4).addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				setActivePanel(gamePanel)
				clearBoard()
				setGameTitle("Sudoku")
				Sudoku.setup(board)
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