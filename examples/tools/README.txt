Exemple de projet utilisant Maven, JUnit, Apache commons Validate, et
Cobertura.

Compilation du projet :

  mvn compile

Exécution du programme :

  mvn -q exec:java -Dexec.mainClass=tools.Main

  (ou bien ./run.sh)

Exécution du programme avec un argument :

  mvn -q exec:java -Dexec.mainClass=tools.Main -Dexec.args=foo

  (ou bien ./run.sh foo)

Lancement des tests unitaires JUnit :

  mvn test

Création d'un rapport sur la couverture des tests unitaires
automatiques :

  mvn cobertura:cobertura
  firefox target/site/cobertura/index.html
