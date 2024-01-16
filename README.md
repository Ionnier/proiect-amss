# proiect-amss

Diagrame:
* generala
    * [Flow Chart](diagrams/flowchart.md) -> Ecranele / Flow-ul din aplicatie

    * Interaction Overview Diagram -> Ce se intampla in cadrul fiecarui ecran la nivel de front și server

        * [Login](diagrams/overview_login.md)
        * [Home](diagrams/overview_home.md)
        * [Session Details](diagrams/overview_details.md)
        * [Boardgame](diagrams/overview_boardgame.md)

* individuale
    * front ([@Ionnier](https://github.com/ionnier))
        * [User Story](diagrams/front_use_cases.drawio) -> ce poate face utilizatorul
            * fiecare omulet din stanga reprezinta un rol al utilizatorului
            * Există conexiunea Host - Participant și Participant - User întrucât există acțiuni suplimentare față de rolul de bază (Participant-ul face acțiunile User-ului + some extra)
            * sageata de extends -> acțiunea oferă funcționalitate extra față de cea precedentă
            * sageata includes -> atunci când este efectuată acțiunea se va declanșa și actiunea de la includes
        * [Class](diagrams/front_class_diagram.md) -> ce entitati și cum sunt facute request-urile
            * entitățile sunt mapate 
            * "-", "+" pentru vizibilitate
            * subliniat -> funcție statică
            * clasa specială sau care implementază o interfață -> deasupra numelui o  <\<Interface\>> (in cazul meu, Enum pentru enum class)

    * backend ([@raduitache](https://github.com/raduitache))
        * [Sequence](diagrams/AMSS_Sequence_Diagram.drawio) -> cum trateaza server-ul request-urile
        * [Package](diagrams/AMSS_Package_Diagram.drawio) -> cum este organizat serverul

Patterns utilizate:
 * Singleton

    * Fiecare componenta din Spring este un Singleton (@RestController, @Configuration)
    * Clasa [Settings](/amss/lib/data/settings.dart) din Front, are instanță privată și un singur getter static ce returnează acea instanță, fiecare metodă este dată membră deci poate fi folosită doar după ce iei o instanță a clasei
 * Builder Pattern
    * [BoardgameBuilder](/backend/src/main/java/com/example/backend/builders/BoardGameBuilder.java) date membre in clasa de builder ce au cateva valori default, pe clasa de builder se folosesc metode de set pentru fiecare ca la final sa obtii o entitate
 * Observer & State Pattern

   * UI-ul aplicației este creat în spiritul acestui pattern
    
        * [UI-ul](/amss/lib/ui/login/login_screen.dart) apelează metode din viewmodel, aceste metode își schimbă comportamentul în funcție de starea internă a VM-ului (Observer pattern)
        * [ViewModel-ul](/amss/lib/ui/login/login_vm.dart) notifică UI-ul când se schimbă datele  -> State Pattern
        * [Diagramă](/diagrams/front_login_diagram.md)
  * Dependency Injection

    * [front](/amss/lib/ui/details/details_screen.dart) - via Consumer / Provider
    * [backend](/backend/src/main/java/com/example/backend/BackendApplication.java) - via Spring Beans
* Additional stuff

    *  [Prezentare](/prezentare/Bahrim_Tache_AMSS.pdf)