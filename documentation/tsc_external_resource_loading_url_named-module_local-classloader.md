# Test de chargement de resource externe via URL d'un named module depuis le classloader du module
Cette série de tests portent sur le cas d'une application 'named module' qui charge des ressources externes dans un
'named module'.

La librairie:
* est un 'named module'  
* contient la resource 
* contient la classe de chargement de la resource


| Test ID | Resource loader | Resource module | Resource path      | Projets         | Résultat |
|---------|-----------------|-----------------|--------------------|-----------------|---------|
| 201     | theapp          | lib             | root package       | theapp_c, lib_b | **OK**  |
| 202     | theapp          | lib             | exported package   | theapp_c, lib_b | KO      |
| 203     | theapp          | lib             | internal package   | theapp_c, lib_b | KO      |
| 204     | theapp          | lib             | opened package     | theapp_c, lib_b | **OK**  |
| 205     | theapp          | lib             | internal directory | theapp_c, lib_b | KO      |
| 206     | theapp          | lib             | opened directory   | theapp_c, lib_b | **OK**  |

On constate que cela fonctionne :
* dans les deux cas où le package et le répertoire ont été indiqués comme open 
* dans le root package.


Tout le code source est disponible dans le projet [theapp_c](./theapp_c/).

Code source de la méthode de chargement des ressources (theapp)
```java
public class ResourceReader {

    public static String loadAsString(String resourcePath) throws IOException {
        try (InputStream in = Sample.class.getResourceAsStream(resourcePath);
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

Il faut noter l'utilisation du classloader de la classe Sample qui appartient au module *lib*.
