import 'package:amss/data/session_repository.dart';
import 'package:amss/data/settings.dart';
import 'package:amss/models/session.dart';
import 'package:amss/models/user.dart';
import 'package:flutter/material.dart';

AlertDialog reportDialog(BuildContext context, Session session, User user) {
  final formFieldLocation = TextEditingController();
  return AlertDialog(
    scrollable: true,
    title: const Text("Add report"),
    content: Column(
      children: [
        TextFormField(
          decoration: const InputDecoration(label: Text("Reason")),
          controller: formFieldLocation,
        )
      ],
    ),
    actions: <Widget>[
      TextButton(
        child: const Text('Add'),
        onPressed: () async {
          var repo = SessionRepository(dio: Settings().provideDio());
          try {
            await repo.reportPlayer(
                session.id, user.id, formFieldLocation.text);
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
}
