import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

hard_file_path = "./plots/plot_data/player1_shots_in_wins.csv"
computer_file_path = "./plots/plot_data/player2_shots_in_wins.csv"

hard_data = pd.read_csv(hard_file_path)
computer_data = pd.read_csv(computer_file_path)

hard_shots = hard_data["ShotsInWins"]
computer_shots = computer_data["ShotsInWins"]

plt.figure(figsize=(10, 6))

hard_hist, hard_bins = np.histogram(
    hard_shots, bins=range(min(hard_shots), max(hard_shots) + 1, 1)
)
plt.plot(
    hard_bins[:-1],
    hard_hist,
    color="#8B0000",
    linestyle="-",
    linewidth=2,
    label="Player1",
)

computer_hist, computer_bins = np.histogram(
    computer_shots, bins=range(min(computer_shots), max(computer_shots) + 1, 1)
)
plt.plot(
    computer_bins[:-1],
    computer_hist,
    color="#50C878",
    linestyle="-",
    linewidth=2,
    label="Player 2",
)

plt.title("Comparison of Shots Taken in Wins by Player 1 and Player 2")
plt.xlabel("Number of Guesses")
plt.ylabel("Number of Wins")
plt.legend()
plt.grid(True)
plt.show()
