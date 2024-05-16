import 'package:flutter/material.dart';
import 'package:camera/camera.dart';

class TakePictureScreen extends StatefulWidget {
  final CameraDescription camera;
  final Function(String) onPictureTaken;

  const TakePictureScreen({Key? key, required this.camera, required this.onPictureTaken}) : super(key: key);

  @override
  _TakePictureScreenState createState() => _TakePictureScreenState();
}

class _TakePictureScreenState extends State<TakePictureScreen> {
  late CameraController _controller;
  late Future<void> _initializeControllerFuture;

  @override
  void initState() {
    super.initState();
    _controller = CameraController(
      widget.camera,
      ResolutionPreset.medium,
    );
    _initializeControllerFuture = _controller.initialize();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('영수증 촬영'),
        backgroundColor: Color(0xFFFCE6E7), // AppBar 배경색 설정
        titleTextStyle: TextStyle(
          fontFamily: 'CustomFont', // 폰트 패밀리 설정
          fontSize: 20,              // 폰트 크기 설정
          color: Colors.black,        // 폰트 색상 설정
        ),
      ),
      body: FutureBuilder<void>(
        future: _initializeControllerFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.done) {
            return Column(
              children: [
                Expanded(
                  flex: 5,
                  child: CameraPreview(_controller),
                ),
                Expanded(
                  flex: 1,
                  child: Center(
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Text(
                        '영수증을 촬영해 제품 정보를 얻어보세요.',
                        style: TextStyle(fontSize: 16, fontFamily: 'CustomFont'),
                        textAlign: TextAlign.center,
                      ),
                    ),
                  ),
                ),
              ],
            );
          } else {
            return Center(child: CircularProgressIndicator());
          }
        },
      ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.camera_alt),
        onPressed: () async {
          try {
            await _initializeControllerFuture;
            final image = await _controller.takePicture();

            if (!mounted) return;

            widget.onPictureTaken(image.path);
            Navigator.of(context).pop();
          } catch (e) {
            print(e);
          }
        },
      ),
    );
  }
}
