import 'package:amss/data/boardgame_repository.dart';
import 'package:amss/data/session_repository.dart';
import 'package:amss/models/session.dart';
import 'package:flutter/material.dart';

class DetailsVM extends ChangeNotifier {
  final BoardGameRepository _boardGameRepository;
  final SessionRepository _sessionRepository;
  Session selectedSession;
  bool isLoading = false;
  String? hasError;

  DetailsVM(this._boardGameRepository, this._sessionRepository,
      {required this.selectedSession});

  void updateSession() async {
    if (isLoading) {
      return;
    }
    hasError = null;
    isLoading = true;
    notifyListeners();
    isLoading = false;
    try {
      selectedSession = await _sessionRepository.getSession(selectedSession.id);
    } on Exception catch (e) {
      hasError = e.toString();
    }
    notifyListeners();
  }
}
