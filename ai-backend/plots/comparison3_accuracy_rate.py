import pandas as pd
import matplotlib.pyplot as plt

file_path = "./plots/plot_data/accuracy.csv"
df = pd.read_csv(file_path)

df["AccuracyRate"] = df["AccuracyRate"].str.rstrip("%").astype("float")

player1_data = df[df["Player"] == "Player 1"]
player2_data = df[df["Player"] == "Player 2"]

window_size = 5
player1_data["Moving_Avg"] = (
    player1_data["AccuracyRate"].rolling(window=window_size).mean()
)
player2_data["Moving_Avg"] = (
    player2_data["AccuracyRate"].rolling(window=window_size).mean()
)

plt.figure(figsize=(12, 6))
plt.plot(
    player1_data["Game"],
    player1_data["Moving_Avg"],
    label="Player 1 - Moving Avg",
    marker="o",
    markersize=5,
    alpha=0.7,
    color="#9966CC",
)
plt.plot(
    player2_data["Game"],
    player2_data["Moving_Avg"],
    label="Player 2 - Moving Avg",
    marker="o",
    markersize=5,
    alpha=0.7,
    color="#6495ED",
)

plt.title("Accuracy Rate Comparison Between Player 1 and Player 2")
plt.xlabel("Number of Games")
plt.ylabel("Accuracy Rate (%)")
plt.legend()
plt.grid(True)

plt.show()
