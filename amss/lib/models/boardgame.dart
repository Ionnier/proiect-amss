class Boardgame {
  int id = -1;
  String name = "";
  int minimumPlayers = -1;
  int maximumPlayers = -1;
  int minimumAgeRequirement = -1;
  int estimatedPlayTime = -1;
  String htmlRules = "";

  static Boardgame fromMap(Map<String, dynamic> json) {
    final bg = Boardgame();
    bg.id = json["id"];
    bg.name = json["name"];
    bg.minimumPlayers = json["minimumPlayers"];
    bg.maximumPlayers = json["maximumPlayers"];
    bg.minimumAgeRequirement = json["minimumAgeRequirement"];
    bg.estimatedPlayTime = json["estimatedPlayTime"];
    bg.htmlRules = json["htmlRules"];
    return bg;
  }
}
