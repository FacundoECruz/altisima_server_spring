# altisima_server_spring
spring boot server for altisima

Migration from the backend built with express to this backend made with spring.

[![wakatime](https://wakatime.com/badge/user/52fea420-cbe4-4ed2-96b9-796155f63dad/project/4ef7d779-c68b-4ded-9209-388a65a00548.svg)](https://wakatime.com/badge/user/52fea420-cbe4-4ed2-96b9-796155f63dad/project/4ef7d779-c68b-4ded-9209-388a65a00548)

 ## Deployar:
#### Local
- Modificar archivo `application.properties` con el host de la base de datos productiva.
- `docker build -t facundoecruz/altisima_spring:latest .`: esto va a construir la imagen latest.
- `docker push facundoecruz/altisima_spring:latest`
#### Render
- Ir al proyecto > settings > deploy > edit: en ImageUrl tiene que ir `docker.io/facundoecruz/altisima_spring:latest`

### Una vez probado y andando la imagen latest, procedemos a dejar la imagen estable.

#### Local
- Modificar archivo `application.properties` con el host de la base de datos productiva.
- `docker build -t facundoecruz/altisima_spring:[nueva_version] .`: esto va a construir la imagen latest.
- `docker push facundoecruz/altisima_spring:[nueva_version]`
#### Render
- Ir al proyecto > settings > deploy > edit: en ImageUrl tiene que ir `docker.io/facundoecruz/altisima_spring:[nueva_version]`
- Guarda que anda lento, tener la version en Ctrl + v