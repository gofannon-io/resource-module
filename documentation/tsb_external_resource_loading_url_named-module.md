## Test de chargement de resource externe via URL d'un named module
Cette série de tests portent sur le cas d'une application 'named module' qui charge des ressources externes dans un
'named module'.
La classe utilisée pour charger la resource appartient à l'application qui ne contient pas la resource.


| Test ID | Resource loader | Resource module | Resource path      | Projets         | Résultat |
|---------|-----------------|-----------------|--------------------|-----------------|----------|
| 101     | theapp          | lib             | root package       | theapp_b, lib_b | KO       |
| 102     | theapp          | lib             | exported package   | theapp_b, lib_b | KO       |
| 103     | theapp          | lib             | internal package   | theapp_b, lib_b | KO       |
| 104     | theapp          | lib             | opened package     | theapp_b, lib_b | KO       |
| 105     | theapp          | lib             | internal directory | theapp_b, lib_b | KO       |
| 106     | theapp          | lib             | opened directory   | theapp_b, lib_b | KO       |


On note que tous les résultats sont KO.

Tout le code source est disponible dans le projet [theapp_b](./theapp_b/).

Code source de la méthode de chargement des ressources (theapp)
```java
public class ResourceReader {

    public static String loadAsString(String resourcePath) throws IOException {
        try (InputStream in = ResourceReader.class.getResourceAsStream(resourcePath);
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)
        ) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[1000];
            int size;
            while ((size = reader.read(buffer)) >= 0) {
                stringBuilder.append(buffer, 0, size);
            }
            return stringBuilder.toString();
        }
    }
}
```

Il faut noter l'utilisation du classloader de la classe ResourceReader qui appartient au module theappb. 
