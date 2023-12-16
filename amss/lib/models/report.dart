import 'user.dart';

class Report {
  int id = -1;
  User user = User();
  String reason = "";

  static Report fromMap(Map<String, dynamic> json) {
    final report = Report();
    report.id = json["id"];
    report.user = User.fromMap(json["user"]);
    report.reason = json["reason"];
    return report;
  }
}
