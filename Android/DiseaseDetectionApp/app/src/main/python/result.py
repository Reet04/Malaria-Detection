from keras.models import load_model
from keras.preprocessing import image
from keras.preprocessing.image import ImageDataGenerator
import numpy as np
import json
import json_model
from keras.models import model_from_json

from keras.optimizers import SGD
import argparse
import tensorboard
# load the model we saved
with open('mdm.json', 'r') as f:
    model_json = json.load(f)

model = model_from_json(json.dumps(model_json))
model.load_weights('mdm.h5')

model.compile(loss='binary_crossentropy',
              optimizer=SGD(lr=1e-1, momentum=0.9),
              metrics=['accuracy'])


def result():

    # predicting images
    img = image.load_img('parasite.jpg', target_size=(64, 64))
   
    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=0)

    images = np.vstack([x])
    classes = model.predict(images, batch_size=10)

    if classes[0][0] > classes[0][1]:
        print("Parasite Positive")  # return
    else:
        print("Parasite Negative")

result()
