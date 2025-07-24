# Spring AI ELT Vector Store: How to store documents in Chroma

## Overview
A reference project that demonstrates how to:

* build a **Spring Boot 3.5** application on **Java 21**
* read pdf documents from local storage
* transform it based on the TokenTextSplitter
* call OpenAI models through *spring-ai*
* store and retrieve embeddings in **Chroma**
* read and chunk documents with the *Pdf Reader* document reader
* expose simple REST end-points for data ingestion

## Tech Stack
- Spring Boot
- Spring AI
- OpenAI Integration
- Chroma as Vector Store
- Java 21
- Docker
- Maven

## Prerequisites
- JDK 21
- Maven
- OpenAI API key
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 2  Configuration

All secrets are read from environment variables or the `.env` file in the
project root (use the provided `.env.template` as a starting point):

| Variable | Purpose |
|----------|---------|
| `OPENAI_API_KEY`       | key for OpenAI completions / chat |

Example:
```
bash
# .env
OPENAI_API_KEY=sk-********************************
API_NINJAS_KEY=ninjas_********************************
```

## Quick Start

### Installation

1. Clone the repository:
```shell script
git clone [repository-url]
cd spring-ai-etl-vector-store
```

2. Configure OpenAI:
   Create `application.properties` in `src/main/resources/` and add:
```properties
spring.ai.openai.api-key=your-api-key-here
```

3. Build and run:
```shell script
docker-compose up

mvn clean install
mvn spring-boot:run
```

## API Usage

### Get Capital City
**Endpoint:** `GET /api/v1/etl/run-ingestion`

## Project Structure
```
spring-ai-etl-vecgtor-store/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
└── pom.xml
```

## Development

### Building
```shell script
mvn clean install
```

### Running Tests
```shell script
mvn test
```

## Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Support
For support and questions, please open an issue in the repository.
