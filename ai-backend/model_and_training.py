import numpy as np
import tensorflow as tf
from tensorflow.keras import layers, models
from tensorflow.keras.losses import binary_crossentropy
from sklearn.model_selection import train_test_split

import tensorflow as tf
from tensorflow.keras import layers, models

model = models.Sequential(
    [
        layers.Input(shape=(10, 10, 1)),
        layers.Conv2D(32, (3, 3), activation="relu", padding="same"),
        layers.Conv2D(64, (3, 3), activation="relu", padding="same"),
        layers.Dropout(0.5),
        layers.Conv2D(128, (3, 3), activation="relu", padding="same"),
        layers.Conv2D(1, (1, 1), activation="sigmoid", padding="same"),
    ]
)


model.compile(optimizer="adam", loss="binary_crossentropy", metrics=["accuracy"])

data_path = "./datasets/processed/partially_near_walls_ship_placements.npy"

X = np.load(data_path)
y = np.copy(X)


X = X.reshape(-1, 10, 10, 1)
y = y.reshape(-1, 10, 10, 1)

X_train, X_val, y_train, y_val = train_test_split(X, y, test_size=0.2, random_state=42)


datagen = tf.keras.preprocessing.image.ImageDataGenerator(
    rotation_range=90, horizontal_flip=True, vertical_flip=True, fill_mode="nearest"
)


train_generator = datagen.flow(X_train, y_train, batch_size=32)
val_generator = datagen.flow(X_val, y_val, batch_size=32)


callback = tf.keras.callbacks.EarlyStopping(monitor="val_loss", patience=3)


model.fit(
    train_generator, epochs=50, validation_data=val_generator, callbacks=[callback]
)


model.save("./models/partially_near_walls_ship_placements.h5")

predicted_probs = model.predict(X)

import matplotlib.pyplot as plt

example_board = predicted_probs[0, :, :, 0]

plt.figure(figsize=(6, 6))
plt.imshow(example_board, cmap="gray", interpolation="nearest")
plt.colorbar(label="Probability of Ship Presence")
plt.title("Heatmap of Predicted Ship Locations")
plt.xlabel("X coordinate")
plt.ylabel("Y coordinate")

heatmap_path = (
    "C:/Users/Diamond/Desktop/newIdea/enhancedAI/predict_intelligent_v2_no_spread.png"
)
plt.savefig(heatmap_path)


plt.show()
