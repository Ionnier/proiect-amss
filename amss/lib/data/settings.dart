import 'dart:convert';

import 'package:amss/models/user.dart';
import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Settings {
  static final Settings _singleton = Settings._internal();
  SharedPreferences? _prefs;
  static const _jwtKey = "JWT_KEY";
  static const _userKey = "USER_KEY";
  static const _baseUrl = "http://localhost:8080";

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
        baseUrl: _baseUrl, headers: headers, validateStatus: (value) => true));
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

  User? getCurrentUser() {
    final asString = _prefs!.getString(_userKey);
    if (asString == null) {
      return null;
    }
    return User.fromJson(jsonDecode(asString));
  }

  void setUser(User? user) async {
    if (user == null) {
      _prefs!.remove(_userKey);
      return;
    }
    _prefs!.setString(_userKey, jsonEncode(user));
  }

  Settings._internal();
}
