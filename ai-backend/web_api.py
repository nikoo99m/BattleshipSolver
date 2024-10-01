from flask import Flask, request, jsonify
import numpy as np
import matplotlib.pyplot as plt
from tensorflow.keras.models import load_model
import logging
import random

app = Flask(__name__)

logging.basicConfig(level=logging.DEBUG)
model = load_model("./models/main_model.h5")

STATUS_MAPPING = {
    "🟦": 0.5,  # Water  [Unexplored Cell]   [Marked as Uncertain]
    "⛵️": 0.5,  # Ship   [Unexplored Cell]   [Marked as Uncertain]
    "❌": 0,  # Miss   [Explored Cell]     [Marked as Water]
    "💥": 2,  # Hit    [Explored Cell]     [Marked as Ship]
    "⬛️": 2,  # Sunk   [Explored Cell]     [Marked as Ship]
}

STATUS_SYMBOLS = {0: "M", 2: "H", 2: "S"}  # Miss  # Hit  # Sunk


def preprocess_board(board):
    board_numeric = np.vectorize(STATUS_MAPPING.get)(board)
    board_numeric = board_numeric.astype(float)
    board_numeric = board_numeric.reshape(1, 10, 10, 1)
    return board_numeric


def remove_predictions_near_sunk_ships(predictions, raw_board):

    for i in range(10):
        for j in range(10):
            if raw_board[i][j] == "⬛":
                for x in range(max(0, i - 1), min(10, i + 2)):
                    for y in range(max(0, j - 1), min(10, j + 2)):
                        predictions[x][y] = 0.0
    return predictions


@app.route("/predict", methods=["POST"])
def predict():
    data = request.get_json()
    raw_board = np.array(data["board"])
    processed_board = preprocess_board(raw_board)

    predictions = model.predict(processed_board)
    predictions = predictions.reshape(10, 10)

    predictions = remove_predictions_near_sunk_ships(predictions, raw_board)

    logging.debug(f"Predictions after removal: \n{predictions}")

    predictions_list = predictions.tolist()

    return jsonify(predictions_list)


if __name__ == "__main__":
    app.run(debug=True)
