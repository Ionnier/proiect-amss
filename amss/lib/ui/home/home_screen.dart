import 'package:amss/data/session_repository.dart';
import 'package:amss/data/settings.dart';
import 'package:amss/main.dart';
import 'package:amss/models/enums.dart';
import 'package:amss/models/session.dart';
import 'package:amss/ui/add_session/add_session_dialog.dart';
import 'package:amss/ui/details/details_screen.dart';
import 'package:amss/ui/home/home_vm.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (context) {
            return HomeViewModel(
                repository: SessionRepository(dio: Settings().provideDio()));
          },
        ),
      ],
      child: Consumer<HomeViewModel>(
        builder: (context, vm, child) {
          if (!vm.wasPopulatedOnce) {
            Future.delayed(Duration.zero, () {
              vm.wasPopulatedOnce = true;
              vm.updateSessions();
            });
          }
          Future.delayed(Duration.zero, () {
            if (vm.hasError != null) {
              ScaffoldMessenger.of(context).showSnackBar(SnackBar(
                content: Text(vm.hasError!),
              ));
              vm.clearError();
            }
          });

          return Scaffold(
            body: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: vm.isLoading
                  ? const SizedBox(
                      width: double.infinity,
                      height: double.infinity,
                      child: SizedBox(
                        height: 16,
                        child: CircularProgressIndicator(),
                      ),
                    )
                  : ListView(
                      children: vm.sessionList
                          .map((e) => createSessionCard(context, e))
                          .toList(),
                    ),
            ),
            appBar: AppBar(
              automaticallyImplyLeading: false,
              actions: [
                IconButton(
                    onPressed: () async {
                      Settings().setUser(null);
                      Settings().setApiKey(null);
                      Navigator.pop(context);
                    },
                    icon: const Icon(Icons.logout))
              ],
            ),
            floatingActionButton: FloatingActionButton(
              onPressed: () async {
                var result = await showDialog(
                    context: context, builder: getAddSessionDialog);
                if (result == true) {
                  vm.updateSessions();
                }
              },
              child: const Icon(Icons.plus_one),
            ),
          );
        },
      ),
    );
  }

  Widget createSessionCard(BuildContext context, Session session) {
    return InkWell(
        child: Column(
      children: [
        Card(
          child: InkWell(
            onTap: () {
              Navigator.of(context).push(MaterialPageRoute(
                builder: (context) {
                  return SessionDetailsScreen(initialSession: session);
                },
              ));
            },
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 4),
              child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Column(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          Text(
                            "Session ${session.id}",
                            style: Theme.of(context).textTheme.titleSmall,
                          ),
                          const SizedBox(
                            height: 4,
                          ),
                          Row(children: [
                            const Icon(Icons.king_bed),
                            Text(
                              session.participants
                                  .firstWhere((element) =>
                                      element.state == UserState.host)
                                  .user
                                  .username,
                              style: Theme.of(context).textTheme.titleSmall,
                            ),
                          ]),
                          const SizedBox(
                            height: 4,
                          ),
                          Row(children: [
                            const Icon(Icons.location_pin),
                            Text(session.location)
                          ])
                        ]),
                    Column(children: [
                      if (session.selectedGame != null)
                        Text(session.selectedGame!.name),
                      if (session.selectedGame != null)
                        const SizedBox(
                          height: 4,
                        ),
                      Text(session.gameState.name.capitalize(),
                          style: Theme.of(context).textTheme.bodySmall),
                      const SizedBox(
                        height: 4,
                      ),
                      Row(children: [
                        const Icon(Icons.person),
                        Text(
                            "${session.participants.length}/${session.selectedGame?.maximumPlayers ?? "-"}")
                      ]),
                    ]),
                  ]),
            ),
          ),
        ),
        const SizedBox(
          height: 16,
        )
      ],
    ));
  }
}
