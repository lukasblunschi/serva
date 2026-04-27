# Serva

Serva is a small web application for web hosting accounting: tracking services,
invoices and customer data for webhosters.

Serva can run as a standalone Java application (embedded Jetty) or as a
traditional webapp inside a servlet container like Tomcat. Data is stored in a
MariaDB database.

## Status

- Stable, simple implementation suitable for small deployments.
- See `LICENSE.txt` for license information.

## Quickstart (standalone)

1. Create a MariaDB database and user (see `doc/setup/setup-standalone.txt`).

2. Restore the empty schema:

   ```sh
   cd scripts
   ./restore-db.sh empty.sql
   ```

3. Start Serva standalone:

   ```sh
   ./run-standalone.sh
   ```

4. Open the app:

   http://localhost:8081/serva

## Quickstart (webapp)

- Deploy the WAR to Tomcat or any servlet container.
- See `doc/setup/setup-webapp.txt` for notes and database setup.

## Requirements

- Java 11 (the `run-standalone.sh` assumes a Java 11 JDK by default; adjust
  `JAVAHOME` in `run-standalone.sh` if needed).
- MariaDB server for data storage.
- Ports: Standalone mode listens on Jetty's configured port (default used in
  `run-standalone.sh` is 8081).

## Configuration

- The default configuration template is at `src/serva.properties`.
- Important values:
  - `company.name` — company name on invoices.
  - `mail.*` — SMTP settings for outgoing email.
  - `path.*` — various application paths.
- Copy and edit the properties file as needed for your deployment.

## Running details

- The repository includes a `run-standalone.sh` script that assembles the
  required classpath and launches the main class:

  ```text
  ch.serva.RunServaStandalone
  ```

- A distribution jar is present under `dist/` in released archives (example:
  `dist/serva-0.3.0.jar`). Alternatively, build/release steps may be in other
  tooling you use.

## Build with Gradle

This repository now contains a Gradle Kotlin DSL build.
To build the project and populate the runtime libraries used by the standalone script, run:

```sh
./gradlew clean assemble copyRuntimeLibs
```

Then start the standalone server with:

```sh
./run-standalone.sh
```

The standalone script expects Java 11. You can set `JAVAHOME` in
`run-standalone.sh` to point to your Java 11 installation.

## Troubleshooting

- If the app cannot connect to the DB, verify:
  - MariaDB is running and reachable.
  - User, password and database name match config.
  - Character set (utf8) if you see encoding issues (see
	`doc/setup/setup-standalone.txt`).
- Check logs (log files and console output) for startup errors.

## Contributing

- Contributions welcome — please open issues or pull requests.
- See `LICENSE.txt` and `NOTICE.txt` for legal details.

## Author / Contact

- See `NOTICE.txt` and project files for contact/attribution.

## License

- This project is licensed under the terms in `LICENSE.txt`.
