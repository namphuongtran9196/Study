# pip install plotly
# conda install -c plotly plotly
import os
import cv2
import glob
import argparse
from V2_3DDFA.V2_3DDFA import Face3DDFA
from V2_3DDFA.render_obj import render_3D

def main(args):
    print("Loading Face3DDFA...")
    face_3ddfa = Face3DDFA("V2_3DDFA/configs/mb1_120x120.yml")
    
    img_paths = glob.glob(os.path.join(args.input, "*.jpg"))
    for i, path in enumerate(img_paths):
        print("Loading image {}/{}...".format(i,len(img_paths)))
        img = cv2.imread(path)
        if img is None:
            print("Error: Cannot read image")
            return
        
        img_name = os.path.basename(path).split(".")[0]
        
        print("Predicting...")
        _, landmarks = face_3ddfa.predict(img)
        
        print("Rendering...")
        render_3D(img, landmarks, face_3ddfa.tddfa.tri, img.shape[0], result_path="./outputs/{}.html".format(img_name))
        print("Rendering done! Result saved to {}.html".format(img_name))
    

def get_args():
    parser = argparse.ArgumentParser()
    parser.add_argument('--input', type=str, help='the input image', default="./inputs")
    return parser.parse_args()

if __name__ == "__main__":
    args = get_args()
    main(args)