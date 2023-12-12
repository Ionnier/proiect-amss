```mermaid
stateDiagram-v2
    [*] --> Initialization
    Initialization --> Accepting_Requests
    Initialization --> Maintanence
    Accepting_Requests --> Maintanence
``` 