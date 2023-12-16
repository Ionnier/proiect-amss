import 'package:amss/models/boardgame.dart';
import 'package:flutter/material.dart';
import 'package:flutter_html_v3/flutter_html.dart';

class BoardGameDetailsScreen extends StatelessWidget {
  final Boardgame boardgame;
  const BoardGameDetailsScreen({super.key, required this.boardgame});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(),
        body: ListView(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                Text(
                  boardgame.name,
                  style: Theme.of(context).textTheme.headlineMedium,
                ),
                Column(
                  children: [
                    Row(
                      children: [
                        const Icon(Icons.numbers),
                        Text(
                            "${boardgame.minimumPlayers} - ${boardgame.maximumPlayers}")
                      ],
                    ),
                    const SizedBox(
                      height: 2,
                    ),
                    Row(
                      children: [
                        const Icon(Icons.time_to_leave),
                        Text("${boardgame.estimatedPlayTime} minutes")
                      ],
                    ),
                    const SizedBox(
                      height: 2,
                    ),
                    Row(
                      children: [
                        const Icon(Icons.portable_wifi_off_outlined),
                        Text("${boardgame.minimumAgeRequirement}+")
                      ],
                    ),
                  ],
                )
              ],
            ),
            const SizedBox(
              height: 4,
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Html(data: boardgame.htmlRules),
            )
          ],
        ));
  }
}
