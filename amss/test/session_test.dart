import 'package:amss/models/session.dart';
import 'package:test/test.dart';

import 'utils.dart';

void main() {
  test('session from map', () async {
    final json =
        (await getMappingData("sessions/getAll.json"))["response"]["body"];
    for (var x in json) {
      Session.fromMap(x);
    }
  });
}
