import 'dart:convert';
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';
import 'package:camera/camera.dart';
import 'package:final_project/screen/camera_screen.dart';

class HomeScreen extends StatefulWidget {
  final CameraDescription firstCamera;
  String? _imagePath;

  HomeScreen({Key? key, required this.firstCamera}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  late WebViewController _controller;
  String? _imageBase64; // 이미지를 base64로 인코딩하여 저장

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
            // 페이지가 로드된 후 이미지 base64 문자열이 있다면 자바스크립트 함수 호출
            if (_imageBase64 != null) {
              _controller.runJavascript('sendReceipt("$_imageBase64");');
            }
          },
          initialUrl: 'https://k10a707.p.ssafy.io/',
          javascriptMode: JavascriptMode.unrestricted,
          onWebViewCreated: (WebViewController webViewController) {
            _controller = webViewController;
          },
          javascriptChannels: <JavascriptChannel>{
            _createCameraChannel(context),
          },
        ),
      ),
    );
  }

  JavascriptChannel _createCameraChannel(BuildContext context) {
    return JavascriptChannel(
      name: 'flutter_inappwebview',
      onMessageReceived: (JavascriptMessage message) {
        print('Received message: ${message.message}');
        if (message.message == 'button_clicked') {
          Navigator.of(context).push(
            MaterialPageRoute(builder: (BuildContext context) {
              return TakePictureScreen(
                camera: widget.firstCamera,
                onPictureTaken: (String imagePath) {
                  setState(() {
                    _imageBase64 = _convertImageToBase64(imagePath); // 이미지를 base64로 인코딩하여 저장
                  });
                  // 자바스크립트 함수 호출하여 이미지 base64 문자열 전달
                  _controller.runJavascript('sendReceipt("$_imageBase64");');
                },
              );
            }),
          );
        }
      },
    );
  }

  // 이미지를 base64로 인코딩하는 함수
  String _convertImageToBase64(String imagePath) {
    File imageFile = File(imagePath);
    List<int> imageBytes = imageFile.readAsBytesSync();
    return base64Encode(imageBytes);
  }
}
