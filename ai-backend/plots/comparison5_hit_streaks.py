import pandas as pd
import matplotlib.pyplot as plt

file_path = "./plots/plot_data/hit_streaks.csv"
data = pd.read_csv(file_path)

data = data[data["Game"] <= 200]

plt.figure(figsize=(10, 6))

plt.rcParams.update({"font.size": 14})

player_colors = {
    "Player 1": "#DE5D83",
    "Player 2": "#6495ED",
}

for player in data["Player"].unique():
    player_data = data[data["Player"] == player]
    plt.plot(
        player_data["Game"],
        player_data["MaxConsecutiveHits"],
        label=player,
        marker="o",
        markersize=8,
        color=player_colors[player],
    )

plt.title("Player Hit-strike Over 200 Games")
plt.ylabel("Max Consecutive Hits")
plt.xlabel("Number of Games")
plt.legend(loc="upper left")
plt.grid(True)

plt.tight_layout()
plt.show()
