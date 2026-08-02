# Number Guessing Game (Java Swing)

A sleek, graphical Number Guessing Game built using Java and Swing. The project follows the **MVC (Model-View-Controller)** architecture for clean, maintainable, and scalable code.

## 📸 Features
- **3 Difficulty Levels**: Easy (0-100), Medium (0-200), Hard (0-500).
- **Dynamic Scoring System**: Tracks current score, highest score, and games played.
- **Visual Feedback**: Color-coded feedback (Too high, Too low, Invalid input).
- **Modern UI**: Custom fonts, colors, and centered layouts using standard Java Swing.

## 📂 Project Structure
This project is separated into specific packages based on the MVC pattern:
- `com.game.model`: Handles game logic, random number generation, and score keeping.
- `com.game.view`: Handles all GUI components (JFrame, JButtons, Layouts).
- `com.game.controller`: The bridge connecting user interactions from the View to data updates in the Model.
- `com.game.main`: Contains the `Main.java` entry point.

## 🚀 How to Run

### Prerequisites
- **Java Development Kit (JDK) 8** or higher installed on your system.

### Compiling and Running via Command Line
1. Clone the repository:
   ```bash
   git clone https://github.com/bankarsamendra04-eng/NumberGuessingGame.git
   cd NumberGuessingGame/src
