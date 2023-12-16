import 'package:amss/models/enums.dart';
import 'package:amss/models/report.dart';
import 'package:amss/models/user.dart';

class Participant {
  User user = User();
  UserState state = UserState.tentative;
  List<User> commendedBy = List.of([]);
  List<Report> reports = List.of([]);

  static Participant fromMap(Map<String, dynamic> json) {
    final pp = Participant();
    pp.user = User.fromMap(json["user"]);
    pp.state = UserState.fromString(json["userState"]);

    if (json.containsKey("commendedBy")) {
      final commendedList = [...json["commendedBy"]];
      for (var user in commendedList) {
        pp.commendedBy.add(User.fromMap(user));
      }
    }

    if (json.containsKey("reports")) {
      final reports = [...json["reports"]];
      for (var report in reports) {
        pp.reports.add(Report.fromMap(report));
      }
    }

    return pp;
  }
}
