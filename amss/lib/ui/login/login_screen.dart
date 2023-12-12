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
              widgets.add(
                TextField(
                  controller: i.$3,
                  decoration: InputDecoration(label: Text(i.$1)),
                  obscureText: i.$2,
                ),
              );
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
            widgets.add(Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                ElevatedButton(
                    onPressed: vm.pressLoginButton,
                    child: const Text("Log in")),
                ElevatedButton(
                    onPressed: vm.pressSignUpButton,
                    child: const Text("Sign up")),
              ],
            ));
            if (vm.getErrorMessage() != null) {
              widgets.add(Text(
                vm.getErrorMessage()!,
                style: const TextStyle(color: Colors.red),
              ));
            }

            return Scaffold(
                key: vm.key,
                body: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: widgets,
                  ),
                ));
          },
        ));
  }
}
