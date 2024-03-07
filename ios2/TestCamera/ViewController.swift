////
////  ViewController.swift
////  TestCamera
////
////  Created by TRẦN THANH DƯƠNG on 31/10/2022.
////

import UIKit
import AVKit
import Vision

class ViewController: UIViewController, AVCaptureVideoDataOutputSampleBufferDelegate {
    
//    @objc func pressed() {
//        print("Pressed!")
//    }
//
//    let identifierLabel: UIButton = {
//        let label = UIButton()
//        label.setTitle("Hello", for: .normal)
//        label.setTitleColor(.white, for: .normal)
//        label.backgroundColor = .green
//        label.tintColor = .white
////        label.textAlignment = .center
////        label.translatesAutoresizingMaskIntoConstraints = false
//        label.addTarget(self, action: #selector(pressed), for: .touchUpInside)
//
//        return label
//    }()
    
    @IBOutlet private weak var previewView: PreviewView!
    /Users/jaydentran1909/xcode/TestCamera/TestCamera/PreviewView.swift
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let captureSession = AVCaptureSession()
        captureSession.sessionPreset = .photo
        
        guard let captureDevice = AVCaptureDevice.default(for: .video) else { return }
        guard let input = try? AVCaptureDeviceInput(device: captureDevice) else { return }
        captureSession.addInput(input)
        
        captureSession.startRunning()
        
        let previewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
        previewLayer.frame = view.layer.bounds
        previewLayer.videoGravity = AVLayerVideoGravity.resizeAspectFill
        view.layer.addSublayer(previewLayer)
        
        
        let dataOutput = AVCaptureVideoDataOutput()
        dataOutput.setSampleBufferDelegate(self, queue: DispatchQueue(label: "videoQueue"))
        captureSession.addOutput(dataOutput)
        

//        setupIdentifierConfidenceLabel()
    }
//
//    fileprivate func setupIdentifierConfidenceLabel() {
//        view.addSubview(identifierLabel)
//        identifierLabel.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: -32).isActive = true
//        identifierLabel.leftAnchor.constraint(equalTo: view.leftAnchor).isActive = true
//        identifierLabel.rightAnchor.constraint(equalTo: view.rightAnchor).isActive = true
//        identifierLabel.heightAnchor.constraint(equalToConstant: 50).isActive = true
//    }
    
    func captureOutput(_ output: AVCaptureOutput, didOutput sampleBuffer: CMSampleBuffer, from connection: AVCaptureConnection) {
//        print("Camera was able to capture a frame:", Date())
        
        guard let pixelBuffer: CVPixelBuffer = CMSampleBufferGetImageBuffer(sampleBuffer) else { return }
        
        // !!!Important
        // make sure to go download the models at https://developer.apple.com/machine-learning/ scroll to the bottom
        guard let model = try? VNCoreMLModel(for: SqueezeNet().model) else { return }
        let request = VNCoreMLRequest(model: model) { (finishedReq, err) in
            
            guard let results = finishedReq.results as? [VNClassificationObservation] else { return }
            
            guard let firstObservation = results.first else { return }
//            print(firstObservation.identifier, firstObservation.confidence)
            
//            DispatchQueue.main.async {
//                self.identifierLabel.text = "\(firstObservation.identifier) \(firstObservation.confidence * 100)"
//            }
            
        }
        
        try? VNImageRequestHandler(cvPixelBuffer: pixelBuffer, options: [:]).perform([request])
    }

}
