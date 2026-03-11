# Griha - Arka Repairs Home

A sleek, modern Spring Boot + Thymeleaf web application for tracking and managing home maintenance tasks. Designed with premium, responsive Tailwind CSS styling.

## 🚀 Setup & Running Locally

This application is built to connect to a PostgreSQL database. 

1. **Database Configuration**:
   The application is currently configured to use an in-memory **H2 database** (`jdbc:h2:mem:grihadb`) for easy local testing without any setup. The database resets every time the app shuts down.

2. **Run the Application**:
   Using a terminal in this directory, run the Maven wrapper (or your global Maven command):
   ```bash
   mvn spring-boot:run
   ```

3. **Access the App**:
   Navigate to [http://localhost:8080/login](http://localhost:8080/login) to register your first account.
   *(You can also access the database directly at [http://localhost:8080/h2-console](http://localhost:8080/h2-console) using JDBC URL `jdbc:h2:mem:grihadb` and user `sa` (no password).)*

---

## ☁️ Deployment Notes

### Vercel Limitations
You requested deployment to **Vercel**. However, Vercel is highly optimized for frontend frameworks and serverless functions (like Node.js, Next.js, Python, or Go). **Vercel does not natively support long-running Java/Spring Boot web server applications.**

### Recommended Platforms
To deploy this Spring Boot application online, it is highly recommended to use Platforms as a Service (PaaS) that have native Java/Spring support, such as:
- **Render** (Free tier available)
- **Railway**
- **Heroku**

All of these platforms easily integrate with your GitHub repository and build the Spring Boot app automatically for deployment.
