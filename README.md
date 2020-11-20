# GOAT (Greatest of all Tournaments)

This is the main repository for the Greatest of all Tournaments.  This repository currently contains two modules:

1.  `/goat-backend` is the REST API for the GOAT platform.  It is a Java project built with Maven.
2.  `/goat-database` is code to setup the PostgreSQL database backing the platform.  

## Running the platform

### Environment Variables

The code contains a few environment variables.  These are either values that change depending on where the code will be run or secrets that should not be in the source code.  See @cbpodd for an explanation of how to inject these into your code.

### Building with Docker Compose

The simplest way to build/run the code is with docker compose.  This requires Docker and Docker-Compose to be installed on a user's system - see [this link](https://docs.docker.com/get-docker/) to install Docker and [this link](https://docs.docker.com/compose/install/) to install Docker Compose.

To build with Docker Compose, create a file in the root directory of the project called `common.env`.  **DO NOT CHECK THIS FILE INTO SOURCE CONTROL**.  This should be in the format specified below, and include each of the necessary environment variables for the project (see @cbpodd for information on what environment variables are necessary).

`common.env`:

```
ENVIRONMENT_VARIABLE1=value1
ENVIRONMENT_VARIABLE2=value2
```

Then, run the following command to build, test, and run the project from the root directory of the project:

```
docker-compose up -d --build
```

To remove the environment installation, type the following command.

```
docker-compose down
```
