import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

csv_file = r"./datasets/unprocessed/opposite_quarters_ship_placements.csv"

data = pd.read_csv(csv_file, header=None)

BOARD_SIZE = 10


def plot_board(data_row, board_index):
    fig, ax = plt.subplots(figsize=(6, 6))
    ax.set_title(f"Ship Placement {board_index + 1}")

    ax.set_xticks(np.arange(0, BOARD_SIZE + 1, 1))
    ax.set_yticks(np.arange(0, BOARD_SIZE + 1, 1))
    ax.grid(True)
    ax.set_xticklabels([])
    ax.set_yticklabels([])

    for i in range(BOARD_SIZE):
        for j in range(BOARD_SIZE):
            cell_value = data_row[i * BOARD_SIZE + j]
            if cell_value == "~":
                color = "#1E90FF"

            else:
                color = "#B9D976"

            rect = plt.Rectangle((j, i), 1, 1, facecolor=color, edgecolor="black")
            ax.add_patch(rect)

    ax.set_xlim(0, BOARD_SIZE)
    ax.set_ylim(0, BOARD_SIZE)
    ax.set_aspect("equal")

    plt.gca().invert_yaxis()
    plt.show()


for index, row in data.iterrows():
    plot_board(row, index)
