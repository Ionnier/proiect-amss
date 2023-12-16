import 'dart:io';

enum UserState {
  host,
  tentative,
  attending;

  static UserState fromString(String value) {
    for (var hey in values) {
      if (hey.name.toLowerCase().trim() == value.toLowerCase().trim()) {
        return hey;
      }
    }
    throw PathNotFoundException;
  }
}

enum GameState {
  created,
  open,
  closed,
  finished;

  static GameState fromString(String value) {
    for (var hey in values) {
      if (hey.name.toLowerCase().trim() == value.toLowerCase().trim()) {
        return hey;
      }
    }
    throw PathNotFoundException;
  }
}
