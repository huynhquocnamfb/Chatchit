# Spring Chat Application

Chatchit is a full-stack web application built using Spring Boot 3.x for the backend, PostgreSQL for the database, Node.js with TypeScript for the server-side rendering, and React.js with TypeScript for the frontend. It also integrates Firebase for authentication, Amazon S3 for file storage, and Google OAuth2 for authentication.

## Features

- **User Authentication:** Firebase Authentication is used for user authentication, providing secure login and registration functionality.
- **Contact Management:** Allows users to manage their contacts, including adding, removing, updating, and retrieving contacts.
- **File Storage:** Integrates with Amazon S3 for storing and retrieving files, providing reliable and scalable file storage solutions.
- **Social Authentication:** Supports Google OAuth2 for social authentication, allowing users to sign in with their Google accounts.
- **API Documentation:** Utilizes Swagger for generating interactive API documentation.

## Technologies Used

- **Backend Framework:** Spring Boot 3.x
- **Database:** PostgreSQL
- **Server-side Rendering:** Node.js with TypeScript
- **Frontend Framework:** React.js with TypeScript
- **Authentication:** Firebase Authentication, Google OAuth2
- **File Storage:** Amazon S3
- **API Documentation:** Swagger
- **Deployment:** Docker, Kubernetes (optional)

## Setup

### Prerequisites

- Java Development Kit (JDK 17) up.
- Node.js and npm
- PostgreSQL database
- Firebase project for authentication
- Amazon S3 bucket for file storage
- Google API credentials for OAuth2

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/men3sgit/WEB-chatchit.git
    ```

2. **Backend Setup:**

    - Navigate to the `backend` directory.
    - Configure the database connection in `application.yml`.
    - Set up Firebase Authentication and Google OAuth2 credentials.
    - Build and run the Spring Boot application.

    ```bash
    cd back-end
    ./mvnw spring-boot:run
    ```

3. **Frontend Setup:**

    - Navigate to the `frontend` directory.
    - Install dependencies:

    ```bash
    cd front-end
    yarn install
    ```

    - Set up Firebase authentication configuration.
    - Build and run the React application.

    ```bash
    yarn start
    ```

## Usage

- Access the application at `http://localhost:3000` in your web browser.
- Sign in using Firebase authentication or Google OAuth2.
- Manage your contacts, upload files, and explore other features of the application.
- Explore the API documentation using Swagger at `http://localhost:9090/swagger-ui.html`.

## Deployment

- For production deployment, configure Docker and Kubernetes for containerized deployment.
- Set up continuous integration and continuous deployment (CI/CD) pipelines for automated deployment to cloud platforms.

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for any bugs, feature requests, or improvements.

## License

This project is licensed under the MIT License.
