import 'package:amss/models/session.dart';
import 'package:dio/dio.dart';

class SessionRepository {
  final Dio _dio;

  SessionRepository({required Dio dio}) : _dio = dio;

  Future<List<Session>> getAllSessions() async {
    var response = await _dio.get("/session");
    if (response.statusCode != 200) {
      return Future.error(
          Exception("Received status = ${response.statusCode}"));
    }
    List<dynamic> sessionmap = [...response.data];
    return sessionmap.map((e) => Session.fromMap(e)).toList();
  }

  Future<void> addSession(String location) async {
    var response = await _dio.post("/session", data: {"location": location});
    if (response.statusCode != 200) {
      return Future.error(
          Exception("Received status = ${response.statusCode}"));
    }
    return;
  }

  Future<Session> getSession(int id) async {
    var response = await _dio.get("/session/$id");
    if (response.statusCode != 200) {
      return Future.error(
          Exception("Received status = ${response.statusCode}"));
    }
    return Session.fromMap(response.data);
  }

  Future<void> selectGame(int sessionId, int gameId) async {
    var response = await _dio
        .put("/session", data: {"gameId": sessionId, "sessionId": gameId});
    if (response.statusCode != 200) {
      return Future.error(
          Exception("Received status = ${response.statusCode}"));
    }
    return;
  }

  Future<void> suggestGame(int sessionId, int gameId) async {
    var response = await _dio
        .patch("/session", data: {"gameId": sessionId, "sessionId": gameId});
    if (response.statusCode != 200) {
      return Future.error(
          Exception("Received status = ${response.statusCode}"));
    }
    return;
  }

  Future<void> joinGame(int sessionId) async {
    var response = await _dio.post("/session/$sessionId/join");
    if (response.statusCode != 200) {
      return Future.error(
          Exception("Received status = ${response.statusCode}"));
    }
    return;
  }

  Future<void> commendPlayer(int sessionId, int userId) async {
    var response = await _dio.post("/session/$sessionId/commend/$userId");
    if (response.statusCode != 200) {
      return Future.error(
          Exception("Received status = ${response.statusCode}"));
    }
    return;
  }

  Future<void> reportPlayer(int sessionId, int userId, String reason) async {
    var response = await _dio
        .post("/session/$sessionId/report/$userId", data: {"reason": reason});
    if (response.statusCode != 200) {
      return Future.error(
          Exception("Received status = ${response.statusCode}"));
    }
    return;
  }
}
