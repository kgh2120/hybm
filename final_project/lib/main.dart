import 'package:flutter/material.dart';
import 'package:final_project/screen/home_screen.dart';
import 'package:camera/camera.dart';

Future<void> main() async {
  // 플러터 프레임워크가 실행할 준비가 될 때까지 기다림
  WidgetsFlutterBinding.ensureInitialized();

  final cameras = await availableCameras();
  final firstCamera = cameras.first;
  runApp(
    // 최상단엔 MaterialApp 필요
      MaterialApp(
        // 오른쪽 끝에 디버그 배너 보기 싫을 때 false로 설정
        debugShowCheckedModeBanner: false,
        // home: HomeScreen(firstCamera: firstCamera, imagePath: "lalala",),
        home: HomeScreen(firstCamera: firstCamera),
      )
  );
}