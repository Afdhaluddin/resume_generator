# ResumeForge - Deployment Guide

## Quick Deploy (GitHub Actions)

### 1. Add Repository Secrets

Go to your GitHub repo → Settings → Secrets and variables → Actions → New repository secret

Add these secrets:

| Secret Name | Description | Example |
|------------|-------------|---------|
| `VPS_HOST` | Your VPS IP address or domain | `192.168.1.100` or `resumeforge.app` |
| `VPS_USER` | SSH username for your VPS | `root` or `ubuntu` |
| `VPS_SSH_KEY` | Private SSH key (pem format) | `-----BEGIN OPENSSH PRIVATE KEY-----...` |

**How to get the SSH key:**
```bash
# On your local machine (the one that can SSH into your VPS)
cat ~/.ssh/id_rsa
# Copy the entire output including BEGIN/END lines
```

### 2. Push to main branch

Every push to `main` will:
1. Run CI (build + test)
2. Build Docker images
3. Deploy to your VPS automatically

Or trigger manually: GitHub repo → Actions → Deploy to VPS → Run workflow

---

## Manual Deploy (On Your VPS)

### Prerequisites
- Docker & Docker Compose installed
- Git installed

### Steps

```bash
# 1. Clone the repository
ssh user@your-vps-ip
git clone https://github.com/Afdhaluddin/resume_generator.git /opt/resume-generator
cd /opt/resume-generator

# 2. Configure environment
cp .env.example .env
nano .env
# Fill in your Stripe and SMTP credentials

# 3. Deploy
docker compose up --build -d

# 4. Verify
docker compose ps
curl http://localhost:8080/api/resume/check-limit
```

---

## Architecture

```
User Browser
     |
     | HTTP port 80 (or 8081)
     v
+-------------+       +------------------+
|   Nginx     |------>|  Spring Boot     |
|  (Frontend) | /api  |    Backend       |
|   :80       |------>|    :8080         |
+-------------+       +------------------+
```

- **Frontend (nginx)**: Serves the Vue.js SPA and proxies `/api/*` to the backend
- **Backend (Spring Boot)**: Handles PDF generation, payments, email, admin API
- **No database needed**: IP limits, resume cache, and payment records are stored in-memory

---

## Environment Variables

### Required
| Variable | Description |
|----------|-------------|
| `STRIPE_SECRET_KEY` | Stripe secret key (test or live) |
| `STRIPE_WEBHOOK_SECRET` | Stripe webhook signing secret |
| `STRIPE_PRICE_ID` | Stripe price ID for unlimited plan |
| `APP_FRONTEND_URL` | Your domain (e.g., `https://resumeforge.app`) |

### Optional (for email receipts)
| Variable | Default | Description |
|----------|---------|-------------|
| `SMTP_HOST` | — | SMTP server host |
| `SMTP_PORT` | 587 | SMTP server port |
| `SMTP_USER` | — | SMTP username |
| `SMTP_PASS` | — | SMTP password |
| `SMTP_AUTH` | true | Enable SMTP auth |
| `SMTP_TLS` | true | Enable TLS |

---

## Docker Commands

```bash
# View logs
docker compose logs -f
docker compose logs -f backend
docker compose logs -f frontend

# Stop all services
docker compose down

# Restart
docker compose restart

# Rebuild after code changes
docker compose build --no-cache
docker compose up -d

# Check running containers
docker compose ps

# Shell into containers
docker exec -it resumegen-backend sh
docker exec -it resumegen-frontend sh
```

---

## Updating After Code Changes

### With GitHub Actions (Recommended)
Just push to `main`:
```bash
git add .
git commit -m "feat: your change"
git push origin main
```

### Manual update on VPS
```bash
cd /opt/resume-generator
git pull origin main
docker compose down
docker compose build --no-cache
docker compose up -d
```

---

## Custom Domain / HTTPS

### Using Cloudflare (Recommended)
1. Point your domain to your VPS IP
2. Enable Cloudflare proxy (orange cloud)
3. SSL/TLS → Full (strict)

### Using Let's Encrypt with Certbot
```bash
# Install certbot
docker run -it --rm \
  -v /etc/letsencrypt:/etc/letsencrypt \
  -v /var/lib/letsencrypt:/var/lib/letsencrypt \
  certbot/certbot certonly --standalone -d yourdomain.com

# Update frontend/nginx.conf to use SSL certificates
```

### Using Nginx Proxy Manager
Deploy alongside the app:
```yaml
# Add to docker-compose.yml
  nginx-proxy-manager:
    image: jc21/nginx-proxy-manager:latest
    ports:
      - "80:80"
      - "443:443"
      - "81:81"
    volumes:
      - npm-data:/data
      - npm-letsencrypt:/etc/letsencrypt
```

---

## Troubleshooting

### Port 80 already in use
```bash
sudo lsof -i :80
# Change frontend port in docker-compose.yml:
# ports:
#   - "8081:80"
```

### Backend won't start
```bash
docker compose logs backend
# Common issues: Stripe keys missing, port conflict
```

### CORS errors
- Nginx proxies API requests, so CORS should not be needed in production
- If accessing backend directly, update `CORS_ALLOWED_ORIGINS`

### Emails not sending
- Check SMTP credentials in `.env`
- If SMTP not configured, receipts are logged to console only
- Check backend logs: `docker compose logs backend | grep -i email`

---

## GitHub Actions Workflows

| Workflow | File | Trigger | Purpose |
|----------|------|---------|---------|
| CI | `.github/workflows/ci.yml` | Push to main/develop, PRs | Build & test |
| Docker Publish | `.github/workflows/docker-publish.yml` | Push to main, tags | Build & push images to GHCR |
| Deploy to VPS | `.github/workflows/deploy-vps.yml` | Push to main, manual | SSH deploy to VPS |
