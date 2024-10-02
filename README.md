# Fleet Master: Developing an Intelligent Battleship Game Solver 🧠

This project aims to develop an artificial intelligence that is capable of producing effective offensive strategies to defeat players in Battleship game which is consists of two main components: a **game engine** and an **AI backend** that communicate via `localhost`. The Java game serves as the front-end, while the AI backend provides additional functionality via a REST API.

### ⚠️ Important: 

- **The AI backend must be running when playing against the computer on hard difficulty, as it utilises supervised learning to enhance its gameplay.**

- **there are 2 branches: one is the main branch and conatains the main code and  the other branch is for evaluation purposes.**

## 🎮 Battleship Game: Features and Rules

### Game Overview

Battleship is a two-player strategy game where each player tries to sink the opponent's fleet of ships. The game is played on two grids per player: one grid for the player's ships and one grid for recording shots on the opponent's ships.

#### 🛠️ Game Setup

1. **Grids:**
   - Each player has two 10x10 grids: one for their own ships and one for tracking their shots on the opponent's grid.

2. **Ships:**
   Each player has a fleet of ships in varying lengths:
   - 1 Aircraft Carrier (5 squares)
   - 1 Battleship (4 squares)
   - 1 Submarine (3 squares)
   - 1 Cruiser (3 squares)
   - 1 Destroyer (2 squares)

3. **Placing Ships:**
   - Ships can be placed horizontally or vertically but not diagonally.
   - Ships cannot be placed near each other.
   - Ships cannot overlap or be placed off the grid.
   - Players place their ships secretly on their own grid before the game begins.

### Gameplay

 **Ship Placement and Taking Turns:**
   - There is an option for players at the beginning of the game to choose if they want random ship placement or want to place their ships manually.
   - After placing ships, players take turns calling out coordinates (separately choosing row number and column number (0-9)) to target a location on the opponent's grid.

2. **Winning the Game:**
   - The game continues until one player has sunk all of the opponent's ships.
   - The first player to sink all of the opponent's ships wins the game.

### 🤖 Computer player Difficulty Levels 

A player can choose the level of challenge when playing against the computer:

- Easy: The computer opponent uses random shots and human like ship placements. 🟢

- Intermediate: The computer opponent uses hant and target strategy and human like ship placement. 🟡

- Hard: The computer opponent employs hunt and target stratey combined with supervised learning and human like ship placement. 🔴




