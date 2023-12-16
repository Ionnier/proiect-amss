import 'package:amss/data/session_repository.dart';
import 'package:amss/data/settings.dart';
import 'package:flutter/material.dart';

AlertDialog getAddSessionDialog(BuildContext context) {
  final formFieldLocation = TextEditingController();
  return AlertDialog(
    scrollable: true,
    title: const Text("Add session"),
    content: Column(
      children: [
        TextFormField(
          decoration: const InputDecoration(label: Text("Location")),
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
            await repo.addSession(formFieldLocation.text);
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
