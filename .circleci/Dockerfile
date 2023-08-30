# Usa la imagen base con OpenJDK 17
FROM cimg/openjdk:17.0

# Cambia al usuario root para configuraciones y actualizaciones
USER root

# Actualiza el sistema, instala PostgreSQL y limpia la cache de paquetes
RUN apt-get update && \
    apt-get install -y gnupg2 && \
    apt-get install -y postgresql && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copia el archivo SQL para crear la tabla
COPY data-test.sql /data-test.sql

# Cambia al usuario postgres para configuraciones de PostgreSQL
USER postgres

# Inicia PostgreSQL, configura la contraseña y crea la base de datos
RUN /etc/init.d/postgresql start && \
    psql --command "ALTER USER postgres PASSWORD 'ciriaqui';" && \
    createdb -O postgres ciriaqui_test

# Cambia al usuario root para realizar configuraciones adicionales
USER root

# Modifica la configuración de autenticación en PostgreSQL
RUN echo "host all all 0.0.0.0/0 md5" >> /etc/postgresql/14/main/pg_hba.conf

# Reinicia PostgreSQL para que los cambios surtan efecto
RUN /etc/init.d/postgresql restart

# Espera un momento para asegurarse de que PostgreSQL se reinicie correctamente
RUN sleep 5

# Cambia al directorio de trabajo de la aplicación
WORKDIR /app

# Copia todo el contenido de tu proyecto al contenedor
COPY . /app

# Instala las dependencias de Gradle
RUN ./gradlew dependencies

# Ejecuta el archivo SQL para crear la tabla
USER postgres
RUN /etc/init.d/postgresql start && \
    psql -U postgres -d ciriaqui_test -f /data-test.sql

# Cambia nuevamente al usuario root
USER root
