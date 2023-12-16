class User {
  int id = -1;
  String username = "";
  String email = "";
  User();

  static User fromMap(Map<String, dynamic> json) {
    final user = User();
    user.id = json["id"];
    user.username = json["username"];
    user.email = json["email"];
    return user;
  }

  User.fromJson(Map<String, dynamic> json)
      : id = json['id'],
        username = json['username'],
        email = json['email'];

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'username': username,
      'email': email,
    };
  }
}
