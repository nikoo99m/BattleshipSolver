import pandas as pd
import matplotlib.pyplot as plt

file_path = "./plots/plot_data/remaining_ships.csv"
df = pd.read_csv(file_path)

df_100 = df[df["Game"] <= 100]

player1_data = df_100[df_100["Player"] == "Player 1"]
player2_data = df_100[df_100["Player"] == "Player 2"]

plt.figure(figsize=(12, 6))

plt.plot(
    player1_data["Game"],
    player1_data["RemainingShips"],
    label="Player 1",
    marker="o",
    color="#808000",
    markersize=5,
    linestyle="-",
)

plt.plot(
    player2_data["Game"],
    player2_data["RemainingShips"],
    label="Player 2",
    marker="o",
    color="#7DC8B8",
    markersize=5,
    linestyle="-",
)

plt.title("Remaining Ships Over 100 Games for Each Player")
plt.xlabel("Number of Games")
plt.ylabel("Remaining Ships")
plt.xticks(range(0, 101, 10))
plt.yticks(range(0, 6))
plt.legend()
plt.grid(True)


plt.show()
