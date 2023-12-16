import 'package:amss/models/user.dart';
import 'package:flutter/material.dart';
import 'package:uuid/uuid.dart';

import '../../data/settings.dart';

class LoginVM extends ChangeNotifier {
  bool _isLoading = false;
  bool _isSigningUp = false;
  String? _errorMessage;
  bool _completedAuth = false;
  List<(String, bool, TextEditingController)> labels = [
    ("email", false, TextEditingController()),
    ("password", true, TextEditingController())
  ];
  Key key = Key(const Uuid().v8());

  void _tellUi() {
    key = Key(const Uuid().v8());
    notifyListeners();
  }

  bool isLoading() {
    return _isLoading;
  }

  String? getFirstButtonMessage() {
    if (_isLoading) {
      return null;
    }
    return "Login";
  }

  String? getSecondButtonMessage() {
    if (_isLoading) {
      return null;
    }
    return "Sign Up";
  }

  String? getErrorMessage() {
    if (_isLoading) {
      return null;
    }
    return _errorMessage;
  }

  bool wasLoggedIn() {
    return _completedAuth || Settings().getApiKey() != null;
  }

  void pressLoginButton() async {
    if (_isLoading) {
      return;
    }
    _errorMessage = null;
    if (_isSigningUp) {
      _isSigningUp = false;
      labels = _updateLabels();
      _tellUi();
      return;
    }
    var username = labels.first.$3.text;
    var password = labels.last.$3.text;
    if (username.isEmpty || password.isEmpty) {
      _errorMessage = "Invalid data";
      _tellUi();
      return;
    }
    await doRequest("/login",
        {"email": labels.first.$3.text, "password": labels.last.$3.text});
  }

  void pressSignUpButton() async {
    if (_isLoading) {
      return;
    }
    _errorMessage = null;
    if (!_isSigningUp) {
      _isSigningUp = true;
      labels = _updateLabels();
      _tellUi();
      return;
    }
    await doRequest("/signup", {
      "email": labels.first.$3.text,
      "username": labels[1].$3.text,
      "password": labels.last.$3.text
    });
  }

  bool isSigningUp() {
    return _isSigningUp;
  }

  List<(String, bool, TextEditingController)> _updateLabels() {
    if (_isSigningUp) {
      return [
        ("email", false, TextEditingController()),
        ("username", false, TextEditingController()),
        ("password", true, TextEditingController())
      ];
    }
    return [
      ("username", false, TextEditingController()),
      ("password", true, TextEditingController())
    ];
  }

  Future<void> doRequest(String path, Object data) async {
    final dio = Settings().provideDio();
    try {
      _isLoading = true;
      _tellUi();
      var response = await dio.post(path, data: data);
      _isLoading = false;
      if (response.statusCode != 200) {
        _errorMessage = "Received statusCode=${response.statusCode}";
        _tellUi();
        return;
      }
      if (response.data == null) {
        _errorMessage = "Received empty body";
        _tellUi();
        return;
      }
      var token = response.data["token"];
      var user = User.fromMap(response.data["user"]);
      Settings().setApiKey(token);
      Settings().setUser(user);
      _completedAuth = true;
      _tellUi();
    } on Exception catch (e) {
      _errorMessage = e.toString();
      _tellUi();
    }
  }
}
