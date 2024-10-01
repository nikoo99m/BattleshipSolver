import numpy as np
import pandas as pd
import os

file_path = "./datasets/unprocessed/opposite_quarters_ship_placements.csv"

if not os.path.exists(file_path):
    raise FileNotFoundError(f"The file at {file_path} was not found.")

data = pd.read_csv(file_path, header=None)

symbol_mapping = {
    "~": 0,
    "": 2,
}


def preprocess_board_with_hits(board_row):

    board = board_row[:-1].map(symbol_mapping)

    return np.array(board).reshape(10, 10)


try:
    preprocessed_data_with_hits = data.apply(preprocess_board_with_hits, axis=1)
except Exception as e:
    raise ValueError(f"An error occurred while processing the data: {e}")

preprocessed_array_with_hits = np.array(preprocessed_data_with_hits.tolist())


output_path_with_hits = "./datasets/processed/opposite_quarters_ship_placements.npy"

output_dir = os.path.dirname(output_path_with_hits)
os.makedirs(output_dir, exist_ok=True)

np.save(output_path_with_hits, preprocessed_array_with_hits)

print("Shape of preprocessed data:", preprocessed_array_with_hits.shape)
