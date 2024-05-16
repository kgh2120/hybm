import 'dart:convert';
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';
import 'package:camera/camera.dart';
import 'package:final_project/screen/camera_screen.dart';
import 'package:final_project/screen/barcode_scan_screen.dart';

class HomeScreen extends StatefulWidget {
  final CameraDescription firstCamera;

  HomeScreen({Key? key, required this.firstCamera}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  late WebViewController _controller;
  File? _imageFile; // 이미지 파일 저장

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: WebView(
          onPageStarted: (String url) {
            print('페이지 시작중: $url');
          },
          onPageFinished: (String url) {
            print('페이지 끝나는 중: $url');
            // 페이지가 로드된 후 이미지 파일이 있다면 자바스크립트 함수 호출
            if (_imageFile != null) {
              _controller.runJavascript('sendReceipt("${_imageFile!.path}");');
            }
          },
          initialUrl: 'https://k10a707.p.ssafy.io/',
          javascriptMode: JavascriptMode.unrestricted,
          onWebViewCreated: (WebViewController webViewController) {
            _controller = webViewController;
          },
          javascriptChannels: <JavascriptChannel>{
            _createCameraChannel(context),
            _createBarcodeChannel(context), // 바코드 채널 추가
          },
        ),
      ),
    );
  }

  // 카메라 사용 채널
  JavascriptChannel _createCameraChannel(BuildContext context) {
    return JavascriptChannel(
      name: 'flutter_inappwebview',
      onMessageReceived: (JavascriptMessage message) {
        print('Received message: ${message.message}');
        if (message.message == 'receipt_camera') {
          Navigator.of(context).push(
            MaterialPageRoute(builder: (BuildContext context) {
              return TakePictureScreen(
                camera: widget.firstCamera,
                onPictureTaken: (String imagePath) async {
                  final base64Image = await _convertImageToBase64(imagePath);
                  setState(() {
                    _imageFile = File(imagePath); // 이미지 파일 저장
                  });
                  // 자바스크립트 함수 호출하여 이미지 파일 경로 전달
                  _controller.runJavascript('sendReceipt("$base64Image");');
                },
              );
            }),
          );
        }
      },
    );
  }

  // 바코드 스캔 채널
  JavascriptChannel _createBarcodeChannel(BuildContext context) {
    return JavascriptChannel(
      name: 'flutter_inappwebview_barcode',
      onMessageReceived: (JavascriptMessage message) {
        print('Received message: ${message.message}');
        if (message.message == 'barcode_camera') {
          Navigator.of(context).push(
            MaterialPageRoute(builder: (BuildContext context) {
              return BarcodeScanScreen();
            }),
          ).then((scannedCode) {
            if (scannedCode != null) {
              _controller.runJavascript('getBarcode("$scannedCode");');
            }
          });
        }
      },
    );
  }

  Future<String> _convertImageToBase64(String imagePath) async {
    final imageFile = File(imagePath);
    final bytes = await imageFile.readAsBytes();
    final base64Image = base64Encode(bytes);
    return base64Image;
  }
}
