import json, urllib.request, ssl

ctx = ssl._create_unverified_context()

DOC_ID = "6cabce02-a8d7-47bd-ba42-45d1ee7f07c5"
API_KEY = "ol_api_hi4eZi8NiLsR4i9jkqEDz9btqelbGYKzh60r7i"
BASE_URL = "https://169.58.31.55:3000/api"

CONTENT = """# ResumeForge — Project Outline

> Last updated: 2026-07-31 | Status: **Complete & Deployed** | Git: `6decdb8`

---

## 1. Project Overview

**ResumeForge** is a full-stack resume generator web application for students, professionals (lawyers, doctors, programmers, businessmen), and job seekers. Users build professional, ATS-friendly resumes through an interactive multi-step form, choose from multiple templates, and download as high-quality PDF.

### Key Business Rules
- **Free tier**: 2 resume generations per IP address
- **Unlimited tier**: $9.99 one-time payment via Stripe
- **Email receipts**: Automatic HTML receipt sent after payment (via Spring Mail)
- **Admin dashboard**: View stats, revenue, and payment history
- **IP tracking**: In-memory, resets on server restart
- **Edit after generation**: Resumes cached via UUID for re-editing
- **No user accounts required**: Anonymous usage with IP-based limits or email-based payment verification

---

## 2. Tech Stack

### Backend
| Layer | Technology | Version |
|-------|-----------|---------|
| Framework | Spring Boot | 3.2.5 |
| Language | Java | 17 |
| Build Tool | Maven | 3.9+ |
| PDF Engine | OpenPDF (LibrePDF) | 2.0.2 |
| Payment | Stripe Java SDK | 24.21.0 |
| Email | Spring Mail | bundled |
| JSON | Jackson | bundled |

### Frontend
| Layer | Technology | Version |
|-------|-----------|---------|
| Framework | Vue.js | 3.4.21 |
| Build Tool | Vite | 5.2+ |
| Styling | Tailwind CSS | 3.4.3 |

### DevOps / Deployment
| Tool | Purpose |
|------|---------|
| Docker | Containerization |
| Docker Compose | Multi-service orchestration |
| Nginx | Static file serving + reverse proxy |
| Git | Version control |
| GitHub Actions | CI/CD — build, test, publish, deploy |
| GitHub Container Registry | Docker image hosting |
| eclipse-temurin | Java runtime |

---

## 3. Project Structure

```
resume-generator/
├── .github/workflows/               # GitHub Actions CI/CD
│   ├── ci.yml                       # Build & test on push/PR
│   ├── docker-publish.yml           # Push images to GHCR
│   └── deploy-vps.yml               # Auto-deploy to VPS via SSH
├── backend/                         # Spring Boot application
│   ├── Dockerfile
│   ├── .dockerignore
│   ├── pom.xml
│   └── src/main/java/com/resumegen/
│       ├── ResumeGenApplication.java
│       ├── config/
│       │   ├── WebConfig.java
│       │   └── StripeConfig.java
│       ├── controller/
│       │   ├── ResumeController.java
│       │   ├── PaymentController.java
│       │   ├── AdminController.java
│       │   └── SeoController.java
│       ├── dto/
│       │   ├── request/
│       │   │   ├── ResumeRequest.java
│       │   │   ├── PersonalInfoRequest.java
│       │   │   ├── EducationRequest.java
│       │   │   └── ExperienceRequest.java
│       │   └── response/
│       │       ├── UsageResponse.java
│       │       ├── ErrorResponse.java
│       │       └── PaymentRecord.java
│       ├── exception/
│       │   ├── GlobalExceptionHandler.java
│       │   ├── LimitExceededException.java
│       │   └── ResumeNotFoundException.java
│       ├── service/
│       │   ├── IpLimitService.java
│       │   ├── ResumeCacheService.java
│       │   ├── PdfGenerationService.java
│       │   ├── StripeService.java
│       │   └── EmailService.java
│       └── util/IpAddressUtil.java
│   └── src/main/resources/
│       ├── application.properties
│       └── application-prod.properties
├── frontend/                        # Vue 3 SPA
│   ├── Dockerfile
│   ├── .dockerignore
│   ├── nginx.conf
│   ├── package.json
│   ├── vite.config.js
│   ├── tailwind.config.js
│   ├── postcss.config.js
│   ├── index.html
│   └── src/
│       ├── main.js
│       ├── App.vue
│       ├── style.css
│       ├── views/
│       │   ├── HomeView.vue
│       │   ├── BuilderView.vue
│       │   ├── PricingView.vue
│       │   └── AdminView.vue
│       ├── components/
│       │   ├── layout/
│       │   │   ├── AppHeader.vue
│       │   │   └── AppFooter.vue
│       │   └── ui/StepIndicator.vue
│       ├── composables/
│       │   ├── useApi.js
│       │   └── useSeo.js
│       ├── constants/
│       │   ├── api.js
│       │   ├── templates.js
│       │   └── validation.js
│       └── utils/
│           ├── validators.js
│           └── download.js
│   └── scripts/
│       └── prerender.js
├── docker-compose.yml
├── .env.example
├── .gitignore
├── DEPLOY.md
└── README.md
```

---

## 4. API Endpoints

### Resume API (`/api/resume`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/resume/check-limit` | Check remaining free generations |
| POST | `/api/resume/generate` | Generate PDF resume |
| POST | `/api/resume/preview` | Preview PDF without using limit |
| GET | `/api/resume/{id}` | Retrieve cached resume by UUID |
| PUT | `/api/resume/{id}` | Update cached resume |

### Payment API (`/api/payment`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/payment/create-checkout-session` | Create Stripe Checkout Session |
| POST | `/api/payment/webhook` | Stripe webhook handler + email trigger |
| GET | `/api/payment/status` | Check if email has paid |

### Admin API (`/api/admin`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/stats` | Total customers, revenue, today's stats |
| GET | `/api/admin/payments` | List all payment records |

### SEO API
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/robots.txt` | robots.txt content |
| GET | `/sitemap.xml` | Dynamic XML sitemap |

---

## 5. Features Implemented

### Backend
- [x] IP-based rate limiting (2 free resumes per IP)
- [x] Stripe Checkout integration — one-time $9.99 payment
- [x] Webhook handler for payment confirmation
- [x] Email receipts — HTML email sent on payment (Spring Mail)
- [x] Admin dashboard API — stats and payment history
- [x] Unlimited mode for paid customers (by email)
- [x] In-memory resume cache with UUID lookup
- [x] OpenPDF PDF generation (3 templates)
- [x] CORS + REST API
- [x] Global exception handling

### Frontend
- [x] Vue 3 SPA with custom view router
- [x] Interactive multi-step resume builder
- [x] 3 resume templates: Modern, Classic, Professional
- [x] Pricing page with Free vs Unlimited comparison
- [x] Stripe Checkout redirect integration
- [x] Payment modal when limit exceeded
- [x] "Go Unlimited" button in header
- [x] Admin dashboard — stats cards + payment table
- [x] Tailwind CSS styling
- [x] SEO meta tags + JSON-LD structured data
- [x] Prerendered static HTML for homepage SEO
- [x] 200.html SPA fallback

### DevOps / CI/CD
- [x] Docker multi-stage builds (backend + frontend)
- [x] Docker Compose orchestration
- [x] Nginx reverse proxy with SPA fallback
- [x] Healthchecks on both services
- [x] Git version control
- [x] **GitHub Actions CI** — build, test, lint
- [x] **GitHub Actions Docker Publish** — push to GHCR
- [x] **GitHub Actions VPS Deploy** — auto-deploy on push

---

## 6. Stripe + Email Setup

### 1. Create Stripe Account
- Sign up at [stripe.com](https://stripe.com) → Switch to Test mode

### 2. Create Product & Price
- Products → Add Product → "ResumeForge Unlimited" → $9.99 One-time
- Save **Price ID** (`price_...`)

### 3. Get API Keys
- Developers → API Keys → copy **Secret key** (`sk_test_...`)
- Developers → Webhooks → Add endpoint: `https://your-domain.com/api/payment/webhook`
- Select event: `checkout.session.completed`
- Copy **Signing secret** (`whsec_...`)

### 4. Configure SMTP (Optional)
If not configured, receipts are logged but not sent.

**Gmail:**
```bash
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USER=your-email@gmail.com
SMTP_PASS=your-app-password
```

**SendGrid:**
```bash
SMTP_HOST=smtp.sendgrid.net
SMTP_PORT=587
SMTP_USER=apikey
SMTP_PASS=your-sendgrid-api-key
```

### 5. Deploy
```bash
cd resume-generator
cp .env.example .env
# Edit .env with your Stripe and SMTP keys
docker compose up --build -d
```

---

## 7. GitHub Actions CI/CD

### Workflows

| Workflow | File | Trigger | Purpose |
|----------|------|---------|---------|
| **CI** | `.github/workflows/ci.yml` | Push to `main`/`develop`, PRs | Build & test backend (Maven), build frontend (Vite), build Docker images |
| **Docker Publish** | `.github/workflows/docker-publish.yml` | Push to `main`, tags `v*` | Build & push images to **GitHub Container Registry** (`ghcr.io`) |
| **Deploy to VPS** | `.github/workflows/deploy-vps.yml` | Push to `main`, manual trigger | SSH into VPS, pull code, rebuild & restart containers |

### Required GitHub Secrets

Go to your GitHub repo → **Settings → Secrets and variables → Actions → New repository secret**

| Secret Name | Description | Example |
|------------|-------------|---------|
| `VPS_HOST` | Your VPS IP address or domain | `192.168.1.100` |
| `VPS_USER` | SSH username for your VPS | `root` or `ubuntu` |
| `VPS_SSH_KEY` | Private SSH key (full PEM format) | `-----BEGIN OPENSSH PRIVATE KEY-----...` |

**How to get the SSH key:**
```bash
cat ~/.ssh/id_rsa
# Copy the entire output including BEGIN/END lines
```

### Deploy Process

1. Push to `main` branch
2. CI workflow runs — tests and builds
3. Docker Publish workflow runs — pushes images to GHCR
4. Deploy to VPS workflow runs — SSHs into VPS and deploys

Or trigger manually: GitHub repo → Actions → Deploy to VPS → Run workflow

---

## 8. Admin Dashboard

Access at: `http://localhost:8081/#admin` (or your domain `/#admin`)

### Stats Cards
| Metric | Description |
|--------|-------------|
| Total Customers | Number of unique paying customers |
| Total Revenue | Cumulative revenue (`$9.99 × customers`) |
| Today's Customers | Customers who paid today |
| Today's Revenue | Revenue from today |

### Payment History Table
- Email | Amount | Date | Session ID

---

## 9. Payment Flow

```
User clicks "Upgrade to Unlimited"
  ↓
Frontend: POST /api/payment/create-checkout-session { email }
  ↓
Backend: Creates Stripe Checkout Session → returns { url, sessionId }
  ↓
Frontend: Redirects to Stripe Checkout page
  ↓
User completes payment on Stripe
  ↓
Stripe: POST /api/payment/webhook (checkout.session.completed)
  ↓
Backend:
  - Marks email as paid in memory
  - Sends HTML receipt email via Spring Mail
  ↓
Stripe redirects user to /payment/success?session_id=xxx
  ↓
Frontend: Stores email in localStorage, calls /api/payment/status
  ↓
User now has unlimited resume generation!
```

---

## 10. Deployment Instructions

### Option A: GitHub Actions (Recommended)

1. Add `VPS_HOST`, `VPS_USER`, `VPS_SSH_KEY` secrets to your GitHub repo
2. Push to `main` — auto-deploys to your VPS

### Option B: Manual Deploy on VPS

```bash
# SSH into your VPS
ssh user@your-vps-ip

# Clone the repo
git clone https://github.com/Afdhaluddin/resume_generator.git /opt/resume-generator
cd /opt/resume-generator

# Configure environment
cp .env.example .env
nano .env
# Fill in your Stripe and SMTP credentials

# Deploy
docker compose up --build -d

# Verify
docker compose ps
curl http://localhost:8080/api/resume/check-limit
```

### Access URLs
| Service | URL |
|---------|-----|
| Frontend | http://your-vps-ip or http://localhost:8081 |
| Admin Dashboard | http://your-vps-ip/#admin |
| Backend API | http://your-vps-ip:8080/api |
| Health Check | http://your-vps-ip:8080/api/resume/check-limit |

---

## 11. Environment Variables

### Required
```properties
STRIPE_SECRET_KEY=sk_test_...
STRIPE_WEBHOOK_SECRET=whsec_...
STRIPE_PRICE_ID=price_...
APP_FRONTEND_URL=https://your-domain.com
```

### Optional (for email receipts)
```properties
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USER=your-email@gmail.com
SMTP_PASS=your-app-password
SMTP_AUTH=true
SMTP_TLS=true
```

---

## 12. Git Repository

**GitHub:** [github.com/Afdhaluddin/resume_generator](https://github.com/Afdhaluddin/resume_generator)

```bash
git clone https://github.com/Afdhaluddin/resume_generator.git
cd resume_generator
git log --oneline
```

---

## 13. Future Enhancements

- [ ] PostgreSQL database for persistent storage
- [ ] User accounts with login/signup
- [ ] Password protection for admin dashboard
- [ ] Additional resume templates
- [ ] Multi-language support
- [ ] Resume analytics / tracking
- [ ] Export to Word (.docx) format
- [ ] Subscription model (monthly/annual)
- [ ] Affiliate/referral program
- [ ] Google Analytics integration
"""

headers = {
    "Authorization": f"Bearer {API_KEY}",
    "Content-Type": "application/json"
}

data = json.dumps({
    "id": DOC_ID,
    "title": "ResumeForge — Project Outline",
    "text": CONTENT
}).encode()

req = urllib.request.Request(f"{BASE_URL}/documents.update", data=data, headers=headers, method="POST")
try:
    resp = urllib.request.urlopen(req, context=ctx)
    result = json.loads(resp.read().decode())
    print(f"Updated: {result['data']['title']} (revision {result['data']['revision']})")
except urllib.error.HTTPError as e:
    print(f"HTTP {e.code}: {e.read().decode()}")
