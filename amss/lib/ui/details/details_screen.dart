import 'package:amss/data/boardgame_repository.dart';
import 'package:amss/data/session_repository.dart';
import 'package:amss/data/settings.dart';
import 'package:amss/main.dart';
import 'package:amss/models/enums.dart';
import 'package:amss/models/session.dart';
import 'package:amss/ui/add_session/add_game_to_session.dart';
import 'package:amss/ui/add_session/report_dialog.dart';
import 'package:amss/ui/boardgame/boardgame_screen.dart';
import 'package:amss/ui/details/details_vm.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class SessionDetailsScreen extends StatelessWidget {
  final Session initialSession;
  const SessionDetailsScreen({super.key, required this.initialSession});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (context) {
            final dio = Settings().provideDio();
            return DetailsVM(
                BoardGameRepository(dio: dio), SessionRepository(dio: dio),
                selectedSession: initialSession);
          },
        ),
      ],
      child: Consumer<DetailsVM>(
        builder: (context, vm, child) {
          return Scaffold(
            appBar: AppBar(),
            body: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: ListView(
                children: [
                  Row(
                    children: [
                      const Spacer(),
                      Text(
                        "Session ${vm.selectedSession.id}",
                        style: Theme.of(context).textTheme.headlineMedium,
                      ),
                      const Spacer()
                    ],
                  ),
                  const SizedBox(
                    height: 2,
                  ),
                  Row(
                    children: [
                      const Spacer(),
                      const Icon(Icons.location_pin),
                      Text(
                        vm.selectedSession.location,
                        style: Theme.of(context).textTheme.bodySmall,
                      ),
                      const Spacer()
                    ],
                  ),
                  const SizedBox(
                    height: 2,
                  ),
                  Row(
                    children: [
                      const Spacer(),
                      Text(vm.selectedSession.gameState.name.capitalize(),
                          style: Theme.of(context).textTheme.bodySmall),
                      const Spacer()
                    ],
                  ),
                  const SizedBox(
                    height: 8,
                  ),
                  createGamesWidget(context, vm.selectedSession, vm),
                  const SizedBox(
                    height: 8,
                  ),
                  createParticipantsWidget(context, vm.selectedSession, vm),
                ],
              ),
            ),
          );
        },
      ),
    );
  }

  Widget createGamesWidget(
      BuildContext context, Session session, DetailsVM vm) {
    List<Widget> widgets = List.empty(growable: true);
    widgets.add(Row(children: [
      const Icon(Icons.gamepad),
      const SizedBox(
        height: 2,
      ),
      session.gameState == GameState.created
          ? const Text("Boardgames")
          : const Text("Selected boardgame")
    ]));
    widgets.add(const SizedBox(
      height: 8,
    ));
    for (var element in session.suggestedGames) {
      if (session.gameState == GameState.created ||
          session.selectedGame == element) {
        widgets.add(InkWell(
          onTap: () {
            Navigator.of(context).push(MaterialPageRoute(
              builder: (context) {
                return BoardGameDetailsScreen(boardgame: element);
              },
            ));
          },
          child: Padding(
            padding: const EdgeInsets.all(4.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                const SizedBox(
                  width: 4,
                ),
                Text(element.name),
                const Spacer(),
                Column(
                  children: [
                    if (session.gameState == GameState.created &&
                        session.participants
                                .firstWhere((element) =>
                                    element.state == UserState.host)
                                .user
                                .id ==
                            Settings().getCurrentUser()!.id)
                      TextButton(
                          onPressed: () async {
                            final asd =
                                SessionRepository(dio: Settings().provideDio());
                            try {
                              await asd.selectGame(session.id, element.id);
                              if (context.mounted) {
                                vm.updateSession();
                              }
                            } on Exception catch (e) {
                              if (context.mounted) {
                                ScaffoldMessenger.of(context).showSnackBar(
                                    SnackBar(content: Text(e.toString())));
                              }
                            }
                          },
                          child: const Text("Pick")),
                  ],
                )
              ],
            ),
          ),
        ));
      }
    }
    widgets.add(const SizedBox(
      height: 2,
    ));
    if (session.gameState == GameState.created) {
      widgets.add(ElevatedButton(
          onPressed: () async {
            var dialog = await addGameToSession(context, session);
            if (dialog != null && context.mounted) {
              var result = await showDialog(
                  context: context, builder: (context) => dialog);
              if (result == true) {
                vm.updateSession();
              }
            }
          },
          child: const Text("Suggest")));
    }
    widgets.add(const SizedBox(
      height: 2,
    ));

    return Container(
        decoration: BoxDecoration(
            // Red border with the width is equal to 5
            border:
                Border.all(width: 0.2, color: Theme.of(context).primaryColor)),
        child: Padding(
          padding: const EdgeInsets.all(4.0),
          child: Column(
            children: widgets,
          ),
        ));
  }

  Widget createParticipantsWidget(
      BuildContext context, Session session, DetailsVM vm) {
    List<Widget> widgets = List.empty(growable: true);

    widgets.add(const Row(children: [
      Icon(Icons.account_box),
      SizedBox(
        width: 2,
      ),
      Text("Participants")
    ]));
    widgets.add(const SizedBox(
      height: 8,
    ));

    for (var element in session.participants) {
      widgets.add(InkWell(
        onTap: () => {},
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Row(
              children: [
                Opacity(
                  opacity: element.state == UserState.host ? 1 : 0,
                  child: const Icon(Icons.king_bed),
                ),
                Text(element.user.username)
              ],
            ),
            const Spacer(),
            if (session.gameState == GameState.finished ||
                session.gameState == GameState.closed)
              Column(
                children: [
                  Row(
                    children: [
                      if (session.gameState == GameState.closed)
                        Text(element.reports.length.toString()),
                      IconButton(
                        onPressed: session.gameState == GameState.closed
                            ? null
                            : () async {
                                var dialog = reportDialog(
                                    context, session, element.user);
                                if (context.mounted) {
                                  var result = await showDialog(
                                      context: context,
                                      builder: (context) => dialog);
                                  if (result == true) {
                                    vm.updateSession();
                                  }
                                }
                              },
                        icon: const Icon(Icons.report),
                      ),
                    ],
                  ),
                  const SizedBox(
                    height: 4,
                  ),
                  Row(
                    children: [
                      if (session.gameState == GameState.closed)
                        Text(element.commendedBy.length.toString()),
                      IconButton(
                        onPressed: (session.gameState == GameState.closed ||
                                element.commendedBy.any(
                                      (element) =>
                                          element.id ==
                                          Settings().getCurrentUser()?.id,
                                    ) ==
                                    true)
                            ? null
                            : () async {
                                var repo = SessionRepository(
                                    dio: Settings().provideDio());
                                try {
                                  await repo.commendPlayer(
                                      session.id, element.user.id);
                                  vm.updateSession();
                                } on Exception catch (e) {
                                  if (context.mounted) {
                                    ScaffoldMessenger.of(context).showSnackBar(
                                        SnackBar(content: Text(e.toString())));
                                  }
                                }
                              },
                        icon: const Icon(Icons.thumb_up),
                      ),
                    ],
                  ),
                ],
              ),
          ],
        ),
      ));
    }

    if (session.gameState == GameState.created ||
        session.gameState == GameState.open) {
      widgets.add(ElevatedButton(
          onPressed: () async {
            final asd = SessionRepository(dio: Settings().provideDio());
            try {
              await asd.joinGame(session.id);
              if (context.mounted) {
                vm.updateSession();
              }
            } on Exception catch (e) {
              if (context.mounted) {
                ScaffoldMessenger.of(context)
                    .showSnackBar(SnackBar(content: Text(e.toString())));
              }
            }
          },
          child: const Text("Join")));
    }
    return Container(
        decoration: BoxDecoration(
            // Red border with the width is equal to 5
            border:
                Border.all(width: 0.2, color: Theme.of(context).primaryColor)),
        child: Padding(
          padding: const EdgeInsets.all(4.0),
          child: Column(
            children: widgets,
          ),
        ));
  }
}
