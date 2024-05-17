import 'package:flutter/material.dart';
import 'package:flutter_qr_bar_scanner/qr_bar_scanner_camera.dart';
import 'package:flutter_beep/flutter_beep.dart';
import 'package:flutter_vibrate/flutter_vibrate.dart';

class BarcodeScanScreen extends StatefulWidget {
  @override
  _BarcodeScanScreenState createState() => _BarcodeScanScreenState();
}

class _BarcodeScanScreenState extends State<BarcodeScanScreen> {
  String? _qrInfo = '바코드를 스캔해 제품정보를 얻어보세요.';
  bool _scanned = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('바코드 촬영'),
        backgroundColor: Color(0xFFFCE6E7), // AppBar 배경색 설정
        titleTextStyle: TextStyle(
          fontFamily: 'CustomFont', // 폰트 패밀리 설정
          fontSize: 20,              // 폰트 크기 설정
          color: Colors.black,        // 폰트 색상 설정
        ),
      ),
      body: Column(
        children: [
          Expanded(
            flex: 5,
            child: QRBarScannerCamera(
              onError: (context, error) => Center(child: Text(error.toString(), textAlign: TextAlign.center)),
              qrCodeCallback: (code) {
                if (!_scanned) {
                  setState(() {
                    _qrInfo = code;
                    _scanned = true;
                  });
                  FlutterBeep.beep();
                  Vibrate.feedback(FeedbackType.success);
                  Navigator.pop(context, code); // Return scanned code
                }
              },
            ),
          ),
          Expanded(
            flex: 1,
            child: Center(
              child: Text(
                _qrInfo!,
                style: TextStyle(fontSize: 16, fontFamily: 'CustomFont'), // 폰트 패밀리 설정
              ),
            ),
          ),
        ],
      ),
    );
  }
}
