import tensorflow as tf
from tensorflow.keras.models import load_model
import numpy as np
import os

print("Current working directory:", os.getcwd())


model_path = "./models/main_model.h5"
model = load_model(model_path)

# test data
X_test_new = np.load(
    "./datasets/processed/random_ship_placements.npy", allow_pickle=True
).reshape((1000, 10, 10, 1))

# Loading the corresponding labels
y_test = np.load("./datasets/processed/main_dataset.npy", allow_pickle=True).reshape(
    (1000, 10, 10, 1)
)


loss, accuracy = model.evaluate(X_test_new, y_test)
print(f"Test Loss: {loss}")
print(f"Test Accuracy: {accuracy}")
