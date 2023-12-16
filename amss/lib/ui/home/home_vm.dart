import 'package:amss/data/session_repository.dart';
import 'package:amss/models/session.dart';
import 'package:flutter/material.dart';

class HomeViewModel extends ChangeNotifier {
  final SessionRepository _repository;

  List<Session> sessionList = List.empty();
  bool isLoading = false;
  bool wasPopulatedOnce = false;
  String? hasError;

  void clearError() {
    hasError = null;
    notifyListeners();
  }

  HomeViewModel({required SessionRepository repository})
      : _repository = repository;

  void updateSessions() async {
    if (isLoading) {
      return;
    }
    isLoading = true;
    hasError = null;
    notifyListeners();
    try {
      sessionList = await _repository.getAllSessions();
    } on Exception catch (e) {
      hasError = e.toString();
    }
    isLoading = false;
    notifyListeners();
  }
}
