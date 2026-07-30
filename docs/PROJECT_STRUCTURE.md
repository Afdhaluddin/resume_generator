# ResumeForge — Reorganized Project Structure

> **Goal**: Separate concerns, improve maintainability, and prepare for production scale.

---

## Root Level

```
resume-generator/
├── 📁 .github/
│   └── 📁 workflows/
│       ├── ci.yml                    # Run tests + build on PR
│       ├── deploy-vps.yml            # SSH + docker deploy on push to main
│       └── docker-build.yml          # Build + push images to registry
│
├── 📁 backend/
│   ├── 📁 .mvn/
│   │   └── wrapper/
│   ├── 📁 src/
│   │   ├── 📁 main/
│   │   │   ├── 📁 java/com/resumegen/
│   │   │   │   ├── 📁 config/               # App config (CORS, Security, Beans)
│   │   │   │   │   ├── WebConfig.java
│   │   │   │   │   ├── SecurityConfig.java  # (future: OAuth/JWT)
│   │   │   │   │   └── JacksonConfig.java
│   │   │   │   │
│   │   │   │   ├── 📁 controller/           # REST API layer
│   │   │   │   │   ├── ResumeController.java
│   │   │   │   │   └── HealthController.java
│   │   │   │   │
│   │   │   │   ├── 📁 dto/                  # Request/Response objects
│   │   │   │   │   ├── request/
│   │   │   │   │   │   ├── ResumeRequest.java
│   │   │   │   │   │   ├── PersonalInfoRequest.java
│   │   │   │   │   │   ├── EducationRequest.java
│   │   │   │   │   │   └── ExperienceRequest.java
│   │   │   │   │   └── response/
│   │   │   │   │       ├── ResumeResponse.java
│   │   │   │   │       ├── UsageResponse.java
│   │   │   │   │       └── ErrorResponse.java
│   │   │   │   │
│   │   │   │   ├── 📁 entity/               # Domain models (future: JPA entities)
│   │   │   │   │   ├── Resume.java
│   │   │   │   │   ├── User.java            # (future)
│   │   │   │   │   └── Template.java
│   │   │   │   │
│   │   │   │   ├── 📁 exception/            # Global exception handling
│   │   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   │   ├── ResumeNotFoundException.java
│   │   │   │   │   └── LimitExceededException.java
│   │   │   │   │
│   │   │   │   ├── 📁 mapper/               # DTO ↔ Entity conversion
│   │   │   │   │   └── ResumeMapper.java
│   │   │   │   │
│   │   │   │   ├── 📁 repository/           # Data access layer (future: JPA)
│   │   │   │   │   └── ResumeRepository.java
│   │   │   │   │
│   │   │   │   ├── 📁 service/              # Business logic
│   │   │   │   │   ├── IpLimitService.java
│   │   │   │   │   ├── ResumeCacheService.java
│   │   │   │   │   ├── PdfGenerationService.java
│   │   │   │   │   └── TemplateService.java # (future: dynamic templates)
│   │   │   │   │
│   │   │   │   ├── 📁 util/                 # Utilities
│   │   │   │   │   ├── IpAddressUtil.java   # IP extraction logic
│   │   │   │   │   └── PdfUtil.java         # PDF helper methods
│   │   │   │   │
│   │   │   │   └── ResumeGenApplication.java
│   │   │   │
│   │   │   └── 📁 resources/
│   │   │       ├── 📁 fonts/                # Custom fonts for PDFs
│   │   │       ├── 📁 templates/            # PDF template assets (images, etc.)
│   │   │       ├── 📁 pdf-styles/           # Template style definitions
│   │   │       │   ├── modern-style.json
│   │   │       │   ├── classic-style.json
│   │   │       │   └── professional-style.json
│   │   │       ├── application.properties
│   │   │       ├── application-dev.properties
│   │   │       ├── application-prod.properties
│   │   │       └── application-test.properties
│   │   │
│   │   └── 📁 test/
│   │       ├── 📁 java/com/resumegen/
│   │       │   ├── 📁 controller/
│   │       │   │   └── ResumeControllerTest.java
│   │       │   ├── 📁 service/
│   │       │   │   ├── IpLimitServiceTest.java
│   │       │   │   └── PdfGenerationServiceTest.java
│   │       │   └── ResumeGenApplicationTests.java
│   │       └── 📁 resources/
│   │           └── test-resume.json
│   │
│   ├── Dockerfile
│   ├── .dockerignore
│   ├── pom.xml
│   └── mvnw
│
├── 📁 frontend/
│   ├── 📁 public/
│   │   ├── favicon.ico
│   │   ├── robots.txt
│   │   └── 📁 icons/
│   │       ├── icon-192x192.png
│   │       └── icon-512x512.png
│   │
│   ├── 📁 src/
│   │   ├── 📁 assets/
│   │   │   ├── 📁 images/
│   │   │   │   ├── hero-bg.svg
│   │   │   │   ├── template-modern-preview.png
│   │   │   │   ├── template-classic-preview.png
│   │   │   │   └── template-professional-preview.png
│   │   │   └── 📁 fonts/
│   │   │
│   │   ├── 📁 components/
│   │   │   ├── 📁 ui/                       # Reusable UI components
│   │   │   │   ├── Button.vue
│   │   │   │   ├── Input.vue
│   │   │   │   ├── Textarea.vue
│   │   │   │   ├── Card.vue
│   │   │   │   ├── Badge.vue
│   │   │   │   └── StepIndicator.vue
│   │   │   │
│   │   │   ├── 📁 layout/
│   │   │   │   ├── AppHeader.vue
│   │   │   │   ├── AppFooter.vue
│   │   │   │   └── PageContainer.vue
│   │   │   │
│   │   │   └── 📁 resume/
│   │   │       ├── PersonalInfoForm.vue
│   │   │       ├── SummaryForm.vue
│   │   │       ├── ExperienceForm.vue
│   │   │       ├── EducationForm.vue
│   │   │       ├── SkillsForm.vue
│   │   │       └── PreviewCard.vue
│   │   │
│   │   ├── 📁 composables/                  # Vue 3 composition functions
│   │   │   ├── useResumeForm.js             # Form state + validation
│   │   │   ├── useApi.js                    # API calls wrapper
│   │   │   ├── usePdfPreview.js             # PDF blob handling
│   │   │   └── useIpLimit.js                # Usage counter logic
│   │   │
│   │   ├── 📁 constants/                    # Static config values
│   │   │   ├── api.js                       # API endpoint URLs
│   │   │   ├── templates.js                 # Template definitions
│   │   │   └── validation.js              # Form validation rules
│   │   │
│   │   ├── 📁 router/                       # Vue Router (future)
│   │   │   └── index.js
│   │   │
│   │   ├── 📁 stores/                       # Pinia stores (future)
│   │   │   ├── resumeStore.js
│   │   │   └── userStore.js
│   │   │
│   │   ├── 📁 types/                        # TypeScript definitions (future)
│   │   │   └── resume.ts
│   │   │
│   │   ├── 📁 utils/                        # Helper functions
│   │   │   ├── date.js                      # Date formatting
│   │   │   ├── validators.js              # Input validation
│   │   │   └── download.js                # File download helper
│   │   │
│   │   ├── 📁 views/
│   │   │   ├── HomeView.vue
│   │   │   ├── BuilderView.vue
│   │   │   ├── PreviewView.vue            # (future: full preview page)
│   │   │   └── PricingView.vue            # (future: upgrade page)
│   │   │
│   │   ├── App.vue
│   │   ├── main.js
│   │   └── style.css
│   │
│   ├── 📁 .vscode/                          # VS Code settings (shared)
│   │   └── extensions.json
│   │
│   ├── Dockerfile
│   ├── .dockerignore
│   ├── index.html
│   ├── nginx.conf
│   ├── package.json
│   ├── vite.config.js
│   ├── tailwind.config.js
│   └── postcss.config.js
│
├── 📁 docker/
│   ├── docker-compose.yml                   # Production compose
│   ├── docker-compose.dev.yml               # Development compose (bind mounts)
│   ├── docker-compose.test.yml              # CI test compose
│   └── 📁 volumes/
│       └── README.md                        # Data volume docs
│
├── 📁 scripts/
│   ├── deploy.sh                            # VPS deployment
│   ├── setup-local.sh                       # One-command local setup
│   └── backup.sh                            # Backup resumes (future)
│
├── 📁 docs/
│   ├── API.md                               # API specification
│   ├── DEPLOY.md                            # Deployment guide
│   ├── ARCHITECTURE.md                      # System design docs
│   └── TEMPLATES.md                         # PDF template design guide
│
├── 📁 infrastructure/
│   ├── 📁 terraform/                        # (future: IaC)
│   └── 📁 ansible/                          # (future: server provisioning)
│
├── .env.example                             # Environment variable template
├── .gitignore
├── README.md
└── LICENSE
```

---

## Key Improvements Over Current Structure

### 1. **Backend — Separation of Concerns**

| New Directory | Purpose |
|--------------|---------|
| `dto/request/` + `dto/response/` | Split request/response objects |
| `entity/` | Domain models (prepares for JPA migration) |
| `exception/` | Centralized exception handling with `@ControllerAdvice` |
| `mapper/` | DTO ↔ Entity conversion (MapStruct ready) |
| `repository/` | Data access layer (JPA-ready) |
| `util/` | Reusable utilities (IP extraction, PDF helpers) |
| `resources/fonts/` | Custom typography assets |
| `resources/pdf-styles/` | JSON-defined template styles |

### 2. **Frontend — Component Organization**

| New Directory | Purpose |
|--------------|---------|
| `components/ui/` | Atomic reusable components (Button, Input, Card) |
| `components/layout/` | Page-level layout components |
| `components/resume/` | Domain-specific form sections |
| `composables/` | Vue 3 composition functions (state logic) |
| `constants/` | Static config (API URLs, validation rules) |
| `stores/` | Pinia state management (future) |
| `types/` | TypeScript definitions (future migration) |
| `utils/` | Pure helper functions |

### 3. **Root Level — DevOps & Documentation**

| New Directory | Purpose |
|--------------|---------|
| `.github/workflows/` | CI/CD pipelines |
| `docker/` | All Docker configs in one place |
| `scripts/` | Automation scripts |
| `docs/` | Markdown documentation |
| `infrastructure/` | Terraform/Ansible (future) |
| `.env.example` | Template for environment variables |

---

## Migration Plan

### Phase 1: Immediate (No code changes)
```bash
# 1. Create new directories
mkdir -p backend/src/main/java/com/resumegen/{exception,mapper,repository,util}
mkdir -p backend/src/main/resources/{fonts,templates,pdf-styles}
mkdir -p backend/src/test/java/com/resumegen/{controller,service}
mkdir -p frontend/src/{composables,constants,stores,types,utils}
mkdir -p frontend/src/components/{ui,layout,resume}
mkdir -p .github/workflows docker scripts docs infrastructure/terraform

# 2. Move Docker files
cp docker-compose.yml docker/docker-compose.yml
cp backend/Dockerfile docker/backend.Dockerfile
cp frontend/Dockerfile docker/frontend.Dockerfile
cp frontend/nginx.conf docker/nginx.conf

# 3. Move scripts
cp deploy.sh scripts/
```

### Phase 2: Refactor (Small code changes)
- Extract `IpAddressUtil` from `ResumeController`
- Create `ErrorResponse` DTO
- Add `@ControllerAdvice` exception handler
- Split `ResumeRequest` into separate request DTOs

### Phase 3: Enhance (New features)
- Add `Pinia` stores to frontend
- Create reusable UI components
- Add JPA entities + repositories
- Implement `TemplateService` for dynamic PDF styles

---

## Docker Compose Variants

### Production (`docker/docker-compose.yml`)
```yaml
services:
  backend:
    build:
      context: ../backend
      dockerfile: ../docker/backend.Dockerfile
  frontend:
    build:
      context: ../frontend
      dockerfile: ../docker/frontend.Dockerfile
```

### Development (`docker/docker-compose.dev.yml`)
```yaml
services:
  backend:
    volumes:
      - ../backend/src:/app/src  # Hot reload
  frontend:
    volumes:
      - ../frontend/src:/app/src
    command: npm run dev
```

---

## Environment Variables (`.env.example`)

```bash
# Application
APP_NAME=ResumeForge
APP_ENV=production
APP_URL=https://your-domain.com

# Backend
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8080
RESUME_FREE_LIMIT=2

# Database (future)
DB_HOST=postgres
DB_PORT=5432
DB_NAME=resumegen
DB_USER=resumegen
DB_PASSWORD=changeme

# Redis (future)
REDIS_HOST=redis
REDIS_PORT=6379

# Stripe (future)
STRIPE_PUBLIC_KEY=pk_test_...
STRIPE_SECRET_KEY=sk_test_...

# Frontend
VITE_API_BASE_URL=/api
```

---

## File Count Comparison

| Category | Current | Proposed | Change |
|----------|---------|----------|--------|
| Backend Java files | 8 | 15+ | +7 (tests, utils, mappers, exceptions) |
| Frontend Vue/JS files | 5 | 20+ | +15 (components, composables, stores, utils) |
| Config files | 6 | 12+ | +6 (CI/CD, Docker variants, env) |
| Documentation | 2 | 4+ | +2 (API spec, architecture) |
| Scripts | 1 | 3+ | +2 (setup, backup) |

---

*This structure prepares ResumeForge for production scale, team collaboration, and future feature additions.*
