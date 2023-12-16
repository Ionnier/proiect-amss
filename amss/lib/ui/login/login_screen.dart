import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../home/home_screen.dart';
import 'login_vm.dart';

class LoginScreen extends StatelessWidget {
  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
        providers: [
          ChangeNotifierProvider(create: (context) => LoginVM()),
          // Provider(create: (context) => SomeOtherClass()),
        ],
        child: Consumer<LoginVM>(
          builder: (context, vm, child) {
            if (vm.wasLoggedIn()) {
              Future.delayed(Duration.zero, () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const HomeScreen()),
                );
              });
            }
            var widgets = List<Widget>.empty(growable: true);
            for (var i in vm.labels) {
              widgets.add(FractionallySizedBox(
                widthFactor: 0.5,
                child: TextField(
                  controller: i.$3,
                  decoration: InputDecoration(label: Text(i.$1)),
                  obscureText: i.$2,
                ),
              ));
              widgets.add(const SizedBox(
                height: 4,
              ));
            }
            widgets.add(const SizedBox(
              height: 8,
            ));
            if (vm.isLoading()) {
              widgets.add(const SizedBox(
                  width: 16, height: 16, child: CircularProgressIndicator()));
              widgets.add(const SizedBox(
                height: 2,
              ));
            }
            if (vm.getErrorMessage() != null) {
              widgets.add(Text(
                vm.getErrorMessage()!,
                style: const TextStyle(color: Colors.red),
              ));
            }
            widgets.add(Opacity(
                opacity: vm.isLoading() ? 0 : 1,
                child: FractionallySizedBox(
                    widthFactor: 0.5,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        Row(
                          children: [
                            const Spacer(),
                            vm.isSigningUp()
                                ? TextButton(
                                    onPressed: vm.pressLoginButton,
                                    style: TextButton.styleFrom(
                                        foregroundColor: Colors.black),
                                    child: Text(
                                      "Log in",
                                      style: Theme.of(context)
                                          .textTheme
                                          .bodyMedium,
                                    ))
                                : ElevatedButton(
                                    onPressed: vm.pressLoginButton,
                                    child: const Text("Log in"))
                          ],
                        ),
                        Row(
                          children: [
                            const Spacer(),
                            vm.isSigningUp()
                                ? ElevatedButton(
                                    onPressed: vm.pressSignUpButton,
                                    child: const Text("Sign up"))
                                : TextButton(
                                    onPressed: vm.pressSignUpButton,
                                    style: TextButton.styleFrom(
                                        foregroundColor: Colors.black),
                                    child: Text(
                                      "Sign up",
                                      style: Theme.of(context)
                                          .textTheme
                                          .bodyMedium,
                                    ),
                                  )
                          ],
                        ),
                        const SizedBox(
                          width: double.infinity,
                        ),
                      ],
                    ))));

            return Scaffold(
                key: vm.key,
                body: Padding(
                  padding: const EdgeInsets.all(16),
                  child: SizedBox(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Container(
                          // ignore: sort_child_properties_last
                          child: Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: widgets,
                            ),
                          ),
                          decoration: BoxDecoration(
                              // Red border with the width is equal to 5
                              border: Border.all(
                                  width: 2,
                                  color: Theme.of(context).primaryColor)),
                        ),
                        const SizedBox(
                          width: double.infinity,
                        )
                      ],
                    ),
                  ),
                ));
          },
        ));
  }
}
