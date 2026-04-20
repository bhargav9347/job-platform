# 🚀 JobVista - Next-Gen Job Portal Management System

![JobVista Hero](job_portal_hero.png)

JobVista is a premium, full-stack web application designed to bridge the gap between talented students and top employers. Built with **Spring Boot 3**, **Spring Security**, and a modern **Glassmorphism UI**, it offers a seamless experience for career management and recruitment.

## ✨ Features

### 👨‍🎓 For Students
- **Smart Profiles**: Manage skills, experience, and education in one place.
- **Resume Upload**: Integration for PDF/Doc resume storage.
- **Job Search**: Advanced filtering by category, location, and experience.
- **Real-time Tracking**: Monitor application statuses (Applied, Shortlisted, Rejected).

### 🧑‍💼 For Employers
- **Recruiter Dashboard**: High-level analytics on job performance and applicants.
- **Job Management**: Complete CRUD operations for job listings.
- **Applicant Tracking (ATS)**: Review candidate details and take instant actions.
- **Decision Engine**: Easily shortlist or reject candidates with status updates.

## 🛠️ Technical Stack
- **Backend**: Java 17+, Spring Boot 3.2, Spring Data JPA, Spring Security (BCrypt)
- **Frontend**: Thymeleaf, HTML5, CSS3 (Modern Glassmorphism Design)
- **Database**: H2 In-Memory (Easily switch to MySQL/Postgres in `application.properties`)
- **CI/CD**: GitHub Actions (Automatic Build & Test)
- **Deployment**: Dockerized for instant cloud deployment.

## 🚀 One-Click Deployment

### 1. GitHub Codespaces
Deploy a fully functional development environment in seconds:
1. Click the **Code** button in this repo.
2. Select the **Codespaces** tab.
3. Click **Create codespace on main**.
4. Once loaded, run `./mvnw spring-boot:run`.

### 2. Cloud Deployment (Render/Railway)
This project includes a `Dockerfile` for instant production deployment:
1. Connect this GitHub repo to [Render](https://render.com).
2. It will automatically detect the Dockerfile and deploy your live site.

## 🛠️ Local Setup
1. Clone the repository: `git clone https://github.com/bhargav9347/job-platform.git`
2. Run using Maven Wrapper: `./mvnw spring-boot:run`
3. Access at: `http://localhost:8080`

---
*Built with ❤️ for Modern Careers.*
