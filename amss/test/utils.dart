import 'dart:convert';
import 'dart:io';
import 'package:path/path.dart';

Future<dynamic> getMappingData(String mappingFile) async {
  var file = join(
      Directory.current.parent.path, "mockserver", "mappings", mappingFile);
  var data = await File(file).readAsString();
  return Future.value(jsonDecode(data));
}
