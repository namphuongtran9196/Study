FROM namphuongtran9196/base_ubuntu_gpu:latest as BUILD

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get clean

RUN pip install --no-cache-dir onnxruntime-gpu opencv-python pyyaml matplotlib torch==1.11.0 plotly
RUN chmod 777 /code
WORKDIR /code
COPY . /code

ENTRYPOINT ["python", "test.py"]
