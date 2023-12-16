should we have everything? UI classes, Repositories, Utils?
```mermaid
classDiagram
    class BoardGameRepository {
      -Dio dio
      +BoardGameRepository(in dio: Dio)
      +getAll() List of Boardgame
    }

    class SessionRepository {
      -Dio dio
      +SessionRepository(in dio: Dio)
      +getAllSessions() List of Session
      +addSession(in location: String)
      +getSession(in id: Int) Session
      +selectGame(in sessionId: Int, in gameId: Int)
      +suggestGame(in sessionId: Int, in gameId: Int)
      +joinGame(in sessionId: Int)
      +commendPlayer(in sessionId: Int, in userId: Int)
      +reportPlayer(in sessionId: Int, in userId: Int, in reason: String) 
    }

    class User{
      +Int id
      +String username
      -String password
      +String email
      +User()
      +fromMap(in map: Map of String, dynamic)$
      +fromJson(in json: Map of String, dynamic)$
      +toJson() Map of String, dynamic
    }
    class Boardgame {
      +Int id
      +String name
      +Int minimumPlayers
      +Int maximumPlayers
      +Int minimumAgeRequirement
      +Int estimatedPlaytime
      +String htmlRules
      +fromMap(in map: Map of String, dynamic)$
    }

    class Session {
      +Int id
      +String location
      +GameState state
      +Boardgame? selectedGame
      +Boardgame suggestedGames[0..]
      +Participant participants[1..]
      +fromMap(map)$
    }

    class Participant {
        +User user
        +UserState userState
        +User commendedBy[0..]
        +Report reports[0..]
        +fromMap(in map: Map of String, dynamic)$
    }

    User --* Participant
    User --> Session
    Boardgame --> Session
    Participant --> Session


    class Report {
        +int id
        +User user
        +String reason
        +fromMap(in map: Map of String, dynamic)$
    }

    class GameState {
        CREATED
        OPEN
        CLOSED
        FINISHED
        +fromString(in value: String) GameState$
    }

    class UserState {
        HOST
        TENTATIVE
        ATTENDING
        +fromString(in value: String) UserState$
    }
    <<enum>> UserState
    <<enum>> GameState

    class Settings {
      -Settings instance
      -String jwtKey
      -String baseUrl
      -String userKey
      -Settings()
      +initialise()
      +getInstance()$
      +provideDio() Dio
      +getApiKey() String?
      +getCurrentUser() User?
      +setApiKey(in string: String?)
      +setUser(in user: User?)
    }
```