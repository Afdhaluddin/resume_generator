# ResumeForge — Project Outline

## 1. Project Overview

**ResumeForge** is a full-stack resume generator web application designed for students, professionals (lawyers, doctors, programmers, businessmen), and job seekers. Users can build professional, ATS-friendly resumes through an interactive multi-step form, choose from multiple templates, and download their resume as a high-quality PDF.

### Key Business Rules
- **Free tier**: 2 resume generations per IP address
- **IP tracking**: In-memory, resets on server restart
- **Edit after generation**: Resumes are cached via UUID for re-editing
- **No user accounts required**: Anonymous usage with IP-based limits

---

## 2. Tech Stack

### Backend
| Layer | Technology | Version |
|-------|-----------|---------|
| Framework | Spring Boot | 3.2.5 |
| Language | Java | 17 |
| Build Tool | Maven | 3.9+ |
| PDF Engine | OpenPDF (LibrePDF) | 2.0.2 |
| JSON | Jackson | bundled |

### Frontend
| Layer | Technology | Version |
|-------|-----------|---------|
| Framework | Vue.js | 3.4.21 |
| Build Tool | Vite | 5.2+ |
| Styling | Tailwind CSS | 3.4.3 |
| Transitions | Vue `<Transition>` | native |

### DevOps / Deployment
| Tool | Purpose |
|------|---------|
| Docker | Containerization |
| Docker Compose | Multi-service orchestration |
| Nginx | Static file serving + reverse proxy |
| eclipse-temurin | Java runtime (Alpine Linux) |

---

## 3. Project Structure

```
resume-generator/
├── backend/                          # Spring Boot application
│   ├── Dockerfile                    # Multi-stage: Maven build → JRE runtime
│   ├── .dockerignore
│   ├── pom.xml                       # Maven dependencies
│   └── src/main/
│       ├── java/com/resumegen/
│       │   ├── ResumeGenApplication.java
│       │   ├── config/
│       │   │   └── WebConfig.java           # CORS configuration
│       │   ├── controller/
│       │   │   └── ResumeController.java    # REST API endpoints
│       │   ├── dto/
│       │   │   └── ResumeRequest.java       # Request data model (nested classes)
│       │   └── service/
│       │       ├── IpLimitService.java      # Per-IP usage tracking
│       │       ├── ResumeCacheService.java  # In-memory resume storage
│       │       └── PdfGenerationService.java # OpenPDF template engine
│       └── resources/
│           ├── application.properties       # Default config
│           └── application-prod.properties  # Production config
│
├── frontend/                         # Vue 3 SPA
│   ├── Dockerfile                    # Multi-stage: Node build → nginx
│   ├── .dockerignore
│   ├── nginx.conf                    # SPA routing + /api proxy
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js                # Dev proxy to localhost:8080
│   ├── tailwind.config.js
│   ├── postcss.config.js
│   └── src/
│       ├── main.js
│       ├── style.css
│       ├── App.vue                   # Root layout (header + router views)
│       └── views/
│           ├── HomeView.vue          # Landing page + template selector
│           └── BuilderView.vue       # 6-step interactive form wizard
│
├── docker-compose.yml                # Orchestrates backend + frontend
├── deploy.sh                         # One-command VPS deployment script
└── DEPLOY.md                         # Full deployment documentation
```

---

## 4. Backend Architecture

### 4.1 API Endpoints

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/api/resume/check-limit` | Check remaining free resumes | None |
| `POST` | `/api/resume/generate` | Generate & download PDF (counts against limit) | None |
| `POST` | `/api/resume/preview` | Generate preview PDF (no limit count) | None |
| `GET` | `/api/resume/{id}` | Retrieve cached resume data by UUID | None |
| `PUT` | `/api/resume/{id}` | Update cached resume & regenerate PDF | None |

### 4.2 Request/Response Schema

**ResumeRequest** (JSON body for generate/preview):
```json
{
  "template": "modern|classic|professional",
  "personalInfo": {
    "fullName": "string",
    "email": "string",
    "phone": "string",
    "address": "string",
    "city": "string",
    "country": "string",
    "linkedIn": "string",
    "website": "string",
    "jobTitle": "string"
  },
  "summary": "string",
  "education": [
    {
      "institution": "string",
      "degree": "string",
      "fieldOfStudy": "string",
      "startDate": "string",
      "endDate": "string",
      "description": "string"
    }
  ],
  "experience": [
    {
      "company": "string",
      "position": "string",
      "startDate": "string",
      "endDate": "string",
      "description": "string",
      "achievements": ["string"]
    }
  ],
  "skills": ["string"],
  "languages": ["string"]
}
```

**check-limit Response**:
```json
{
  "canGenerate": true,
  "remaining": 2,
  "used": 0,
  "limit": 2
}
```

### 4.3 PDF Templates

| Template | Style | Best For |
|----------|-------|----------|
| **Modern** | Bold colored header, clean sans-serif, centered layout | Tech, Creative professionals |
| **Classic** | Serif typography, elegant divider lines, centered | Law, Medicine, Academia |
| **Professional** | Two-column layout with sidebar, teal accents | Business, Management, Consulting |

### 4.4 Services

**IpLimitService**
- Stores `IP → AtomicInteger` in `ConcurrentHashMap`
- Methods: `canGenerate(ip)`, `recordGeneration(ip)`, `getRemaining(ip)`, `getUsage(ip)`
- Configurable limit via `resume.free-limit` property (default: 2)

**ResumeCacheService**
- Stores `UUID → ResumeRequest` in `ConcurrentHashMap`
- Methods: `saveResume(req)`, `getResume(id)`, `updateResume(id, req)`, `exists(id)`
- Enables re-editing without re-entering all data

**PdfGenerationService**
- Uses OpenPDF (fork of iText) for reliable PDF generation
- Three template methods: `generateModernTemplate()`, `generateClassicTemplate()`, `generateProfessionalTemplate()`
- Supports colored headers, line separators, two-column layouts, bullet points
- Output: `byte[]` streamed as `application/pdf` with `Content-Disposition: attachment`

### 4.5 IP Detection

Reads headers in priority order:
1. `X-Forwarded-For` (first IP in comma-separated list)
2. `X-Real-IP`
3. `request.getRemoteAddr()` (fallback)

This ensures accurate IP detection behind nginx reverse proxy.

---

## 5. Frontend Architecture

### 5.1 Views

**HomeView.vue** — Landing Page
- Hero section with CTA
- Feature cards (Quick, Professional, PDF Export)
- Template gallery (3 clickable cards with live previews)
- Target audience badges (Students, Lawyers, Doctors, Programmers, Businessmen)

**BuilderView.vue** — Resume Builder Wizard
- 6-step progress indicator with click-to-navigate
- Step 1: Personal Information (full name, contact, social)
- Step 2: Professional Summary (textarea with example)
- Step 3: Work Experience (dynamic add/remove entries, achievements as bullet list)
- Step 4: Education (dynamic add/remove entries)
- Step 5: Skills & Languages (comma-separated input with live tag display)
- Step 6: Preview & Download (summary card, Preview PDF button, Download button)

### 5.2 State Management
- **No Pinia/Vuex needed** — form state is reactive object in `BuilderView.vue`
- Usage counter fetched from backend and displayed in `App.vue` header
- Resume ID stored after generation for potential editing

### 5.3 Styling
- Tailwind CSS utility-first approach
- Custom color palette: `primary-50` through `primary-900`
- Custom scrollbar styling
- Vue `<Transition name="step">` for slide animations between steps

### 5.4 API Integration
- All API calls use `fetch()` with `/api/*` paths
- Dev proxy in `vite.config.js` forwards `/api` → `http://localhost:8080`
- Production: nginx proxies `/api` → `http://backend:8080/api`

---

## 6. Docker Deployment

### 6.1 Containers

| Service | Base Image | Exposed Port | Internal Port |
|---------|-----------|-------------|---------------|
| Backend | `eclipse-temurin:17-jre-alpine` | 8080 | 8080 |
| Frontend | `nginx:alpine` | 80 | 80 |

### 6.2 Multi-Stage Builds

**Backend Dockerfile**:
1. Stage 1: `eclipse-temurin:17-jdk-alpine` + Maven → builds JAR
2. Stage 2: `eclipse-temurin:17-jre-alpine` + `wget` → runs JAR as non-root user

**Frontend Dockerfile**:
1. Stage 1: `node:20-alpine` → `npm ci` + `npm run build`
2. Stage 2: `nginx:alpine` → serves `dist/` with custom `nginx.conf`

### 6.3 Network Architecture

```
Internet → VPS Port 80 → nginx (frontend container)
                          └── /api/* → backend:8080 (Docker internal network)
```

- Frontend and backend communicate via Docker bridge network `resumegen-net`
- Backend port 8080 is exposed on host for direct API access if needed
- CORS not needed in production since nginx handles same-origin proxying

### 6.4 Health Checks

- **Backend**: `wget --spider http://localhost:8080/api/resume/check-limit`
- **Frontend**: `wget --spider http://localhost:80/`
- Frontend `depends_on` backend with `condition: service_healthy`

### 6.5 Environment Variables

| Variable | File | Default | Purpose |
|----------|------|---------|---------|
| `SPRING_PROFILES_ACTIVE` | docker-compose.yml | `prod` | Activates `application-prod.properties` |
| `CORS_ALLOWED_ORIGINS` | docker-compose.yml | `http://localhost,http://localhost:80` | Comma-separated CORS origins |
| `resume.free-limit` | application.properties | `2` | Free resumes per IP |

---

## 7. Deployment Steps (VPS)

### Option A: Automated (deploy.sh)
```bash
scp -r resume-generator user@vps-ip:/opt/
ssh user@vps-ip
cd /opt/resume-generator
sudo ./deploy.sh
```

### Option B: Manual
```bash
cd /opt/resume-generator
docker compose build
docker compose up -d
```

### Post-Deployment Verification
```bash
# Check containers
docker compose ps

# Check backend health
curl http://localhost:8080/api/resume/check-limit

# View logs
docker compose logs -f
```

---

## 8. Security Considerations

| Concern | Mitigation |
|---------|-----------|
| IP spoofing | Reads `X-Forwarded-For` from trusted proxy (nginx) only |
| CORS | Restricted to known origins; production uses same-origin proxy |
| Container security | Backend runs as non-root user (uid 1001) |
| PDF injection | OpenPDF handles text rendering safely (no raw HTML) |
| Rate limiting | IP-based count with 2 free generations |

---

## 9. Known Limitations & Future Enhancements

### Current Limitations
1. **IP limits are in-memory** — reset on server restart or container recreation
2. **No persistent storage** — resumes lost on restart
3. **No user accounts** — cannot track usage across devices
4. **Single instance** — no horizontal scaling

### Future Enhancements
| Feature | Priority | Approach |
|---------|----------|----------|
| Redis for IP limits | High | Replace `ConcurrentHashMap` with Redis |
| PostgreSQL for resume storage | High | Add JPA + PostgreSQL dependency |
| User accounts + OAuth | Medium | Spring Security + JWT |
| Payment integration | Medium | Stripe for premium tier (unlimited resumes) |
| More templates | Medium | Add 3-5 more designs |
| AI resume suggestions | Low | OpenAI API for summary/achievement generation |
| PDF preview thumbnails | Low | Generate PNG preview alongside PDF |

---

## 10. Development Commands

### Local Development (without Docker)

**Backend:**
```bash
cd backend
mvn spring-boot:run
# API available at http://localhost:8080
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
# App available at http://localhost:5173
```

### Docker Development

```bash
# Build and start
docker compose up -d --build

# View logs
docker compose logs -f

# Stop
docker compose down

# Rebuild after code changes
docker compose build --no-cache && docker compose up -d
```

---

## 11. File Inventory

### Backend Files (11)
- `pom.xml` — Maven configuration
- `ResumeGenApplication.java` — Spring Boot entry point
- `WebConfig.java` — CORS setup
- `ResumeController.java` — REST API (5 endpoints)
- `ResumeRequest.java` — DTO with nested `PersonalInfo`, `Education`, `Experience`
- `IpLimitService.java` — IP usage tracking
- `ResumeCacheService.java` — UUID-based cache
- `PdfGenerationService.java` — 3-template PDF engine
- `application.properties` — Default config
- `application-prod.properties` — Production config
- `Dockerfile` — Multi-stage container build

### Frontend Files (12)
- `index.html` — HTML entry point
- `package.json` — NPM dependencies
- `vite.config.js` — Build + dev proxy config
- `tailwind.config.js` — Theme customization
- `postcss.config.js` — PostCSS plugins
- `main.js` — Vue app initialization
- `style.css` — Global styles + Tailwind directives
- `App.vue` — Root component with header + view router
- `HomeView.vue` — Landing page with template gallery
- `BuilderView.vue` — 6-step form wizard
- `Dockerfile` — Multi-stage container build
- `nginx.conf` — Production nginx configuration

### DevOps Files (4)
- `docker-compose.yml` — Service orchestration
- `deploy.sh` — Automated VPS deployment
- `DEPLOY.md` — Deployment documentation
- `README.md` (this document) — Project outline

---

*Document generated for ResumeForge project. Last updated: 2026-07-29*
