
# Tests de chargement de ressource locale via URL d'un named module
Cette série de tests portent sur le cas d'une application 'named module' qui charge des ressources locales (ie dans le JAR courant).

Les tests à effectuer étant locaux, ils sont assez simples&nbsp;:

| Test ID | Resource loader | Resource module | Resource path      | Projets         | Résultat |
|---------|-----------------|-----------------|--------------------|-----------------|----------|
| 001     | theapp          | theapp          | root package       | theapp_a        | OK       |
| 002     | theapp          | theapp          | exported package   | theapp_a        | OK       |
| 003     | theapp          | theapp          | internal package   | theapp_a        | OK       |
| 004     | theapp          | theapp          | opened package     | theapp_a        | OK       |
| 005     | theapp          | theapp          | internal directory | theapp_a        | OK       |
| 006     | theapp          | theapp          | opened directory   | theapp_a        | OK       |

On note que tous les résultats sont OK, car la notion de module n'a pas cours au sein d'un même module.

Tout le code source est disponible dans le projet [theapp_a](./theapp_a/).

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
