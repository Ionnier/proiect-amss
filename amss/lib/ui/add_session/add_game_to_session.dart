import 'package:amss/data/boardgame_repository.dart';
import 'package:amss/data/session_repository.dart';
import 'package:amss/data/settings.dart';
import 'package:amss/models/boardgame.dart';
import 'package:amss/models/session.dart';
import 'package:flutter/material.dart';
import 'package:select_form_field/select_form_field.dart';

Future<AlertDialog?> addGameToSession(
    BuildContext context, Session session) async {
  final boardGameRepository = BoardGameRepository(dio: Settings().provideDio());
  Boardgame? selectedBoardgame;
  try {
    final list = await boardGameRepository.getAll();
    final List<Map<String, dynamic>> items = list
        .map((e) =>
            {'value': e.id.toString(), 'label': e.name} as Map<String, dynamic>)
        .toList();
    return AlertDialog(
      scrollable: true,
      title: const Text("Suggest game"),
      content: Column(
        children: [
          SelectFormField(
            type: SelectFormFieldType.dropdown, // or can be dialog
            items: items,
            onChanged: (val) {
              selectedBoardgame =
                  list.firstWhere((element) => int.parse(val) == element.id);
            },
          )
        ],
      ),
      actions: <Widget>[
        TextButton(
          child: const Text('Add'),
          onPressed: () async {
            if (selectedBoardgame == null) {
              return;
            }
            var repo = SessionRepository(dio: Settings().provideDio());
            try {
              await repo.suggestGame(session.id, selectedBoardgame!.id);
              if (context.mounted) {
                Navigator.pop(context, true);
              }
            } on Exception catch (e) {
              if (context.mounted) {
                ScaffoldMessenger.of(context)
                    .showSnackBar(SnackBar(content: Text(e.toString())));
              }
            }
          },
        ),
      ],
    );
  } on Exception catch (e) {
    if (context.mounted) {
      ScaffoldMessenger.of(context)
          .showSnackBar(SnackBar(content: Text(e.toString())));
    }
    return null;
  }
}
