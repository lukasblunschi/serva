# Serva

Serva is a small web application for web hosting accounting: tracking services,
invoices and customer data for webhosters.

Serva runs as a traditional webapp inside a servlet container like Tomcat.
Data is stored in a MariaDB database.

## Status

- Stable, simple implementation suitable for small deployments.
- See `LICENSE.txt` for license information.

## Quickstart

- Deploy the WAR to Tomcat or any servlet container.
- See `doc/setup/setup-webapp.txt` for notes and database setup.

## Requirements

- Java 11.
- MariaDB server for data storage.

## Configuration

- The default configuration template is at `src/serva.properties`.
- Important values:
  - `company.name` — company name on invoices.
  - `mail.*` — SMTP settings for outgoing email.
  - `path.*` — various application paths.
- Copy and edit the properties file as needed for your deployment.

## Build with Gradle

This repository contains a Gradle Kotlin DSL build.

To build the project and create the WAR file:

```sh
./gradlew clean assemble
```

The WAR file can now be found under `build/libs/`.

## Troubleshooting

- If the app cannot connect to the DB, verify:
  - MariaDB is running and reachable.
  - User, password and database name match config.
  - Character set (utf8) if you see encoding issues (see
	`doc/setup/setup-webapp.txt`).
- Check logs (log files and console output) for startup errors.

## Contributing

- Contributions welcome — please open issues or pull requests.
- See `LICENSE.txt` and `NOTICE.txt` for legal details.

## Author / Contact

- See `NOTICE.txt` and project files for contact/attribution.

## License

- This project is licensed under the terms in `LICENSE.txt`.
