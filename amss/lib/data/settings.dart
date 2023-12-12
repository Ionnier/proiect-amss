import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Settings {
  static final Settings _singleton = Settings._internal();
  SharedPreferences? _prefs;
  static const _jwtKey = "JWT_KEY";
  static const baseUrl = "http://localhost:3000";

  factory Settings() {
    return _singleton;
  }

  Future<void> initialise() async {
    _prefs = await SharedPreferences.getInstance();
    return;
  }

  Dio provideDio() {
    Map<String, dynamic> headers = {};
    headers.addAll({"Accept": 'application/json'});
    var apiKey = getApiKey();
    if (apiKey != null) {
      headers.addAll({"Authorization": 'Bearer $apiKey'});
    }
    return Dio(BaseOptions(
        baseUrl: baseUrl, headers: headers, validateStatus: (value) => true));
  }

  String? getApiKey() {
    return _prefs!.getString(_jwtKey);
  }

  void setApiKey(String? value) {
    if (value == null) {
      _prefs!.remove(_jwtKey);
      return;
    }
    _prefs!.setString(_jwtKey, value);
  }

  Settings._internal();
}
