# ProiectPA

Enunt : RouteSeeker: Determinarea rutelor pentru jogging/plimbari intr-un oras (Finding fixed-length cycles in a graph)

Solutie :
- Folosirea a 3 harti asociate unor zone dintr un oras, care vor fi desenate sub forma unor grafuri;
- Coordonatele hartilor vor fi importate din fisiere csv si vor fi inserate intr o baza de date, cu care se realizeaza o conexiune prin jdbc;
- La deschiderea aplicatiei se va cere inregistararea sau logarea utilizatorului, realizarea acestor pasi ducand la deschiderea ferestrei cu harti;
- Navbar-ul va fi reprezentat prin existenta unor butoane care permit: schimbarea hartii pe care se afla, alegerea punctului de start, scrierea lungimii aproximative, pe care utilizatorul ar dori sa o parcurga;
- Dupa introducerea lungimii, butonul FindRoute va apela algoritmul de gasire de cicluri;
- Pentru gasirea ciclurilor se va folosi algoritmul Johnson, care cauta ciclurile elementare folosindu se de componentele tare conexe din graful, considerat bidirectional;
