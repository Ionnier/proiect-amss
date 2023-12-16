import 'package:amss/models/boardgame.dart';
import 'package:amss/models/enums.dart';
import 'package:amss/models/participant.dart';

class Session {
  int id = -1;
  String location = "";
  GameState gameState = GameState.created;
  Boardgame? selectedGame;
  List<Boardgame> suggestedGames = List.of([]);
  List<Participant> participants = List.of([]);

  static Session fromMap(Map<String, dynamic> json) {
    final ss = Session();
    ss.id = json["id"];
    ss.location = json["location"];
    ss.gameState = GameState.fromString(json["state"]);
    if (json.containsKey("selectedGame")) {
      ss.selectedGame = Boardgame.fromMap(json["selectedGame"]);
    }

    if (json.containsKey("suggestedGames")) {
      final suggestedGames = [...json["suggestedGames"]];
      for (var game in suggestedGames) {
        ss.suggestedGames.add(Boardgame.fromMap(game));
      }
    }

    if (json.containsKey("participants")) {
      final participants = [...json["participants"]];
      for (var part in participants) {
        ss.participants.add(Participant.fromMap(part));
      }
    }

    return ss;
  }
}
