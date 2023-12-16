import 'package:amss/models/boardgame.dart';
import 'package:dio/dio.dart';

class BoardGameRepository {
  final Dio _dio;
  BoardGameRepository({required Dio dio}) : _dio = dio;

  Future<List<Boardgame>> getAll() async {
    var response = await _dio.get("/boardgame");
    if (response.statusCode != 200) {
      return Future.error(
          Exception("Received status = ${response.statusCode}"));
    }
    List<dynamic> sessionmap = [...response.data];
    return sessionmap.map((e) => Boardgame.fromMap(e)).toList();
  }
}
