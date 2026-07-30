# ResumeForge - Docker Deployment Guide

## Quick Start (On Your VPS)

### 1. Upload the project

Copy the entire `resume-generator` folder to your VPS:

```bash
# From your local machine
scp -r resume-generator user@your-vps-ip:/opt/
```

### 2. Run the deployment script

```bash
ssh user@your-vps-ip
cd /opt/resume-generator
chmod +x deploy.sh
sudo ./deploy.sh
```

Or manually:

```bash
cd /opt/resume-generator

# Build images
docker compose build

# Start services
docker compose up -d
```

### 3. Access the app

Open your browser to: `http://YOUR_VPS_IP/`

The backend API is also available at: `http://YOUR_VPS_IP:8080`

---

## Architecture

```
User Browser
     |
     | HTTP port 80
     v
+-------------+       +------------------+
|   Nginx     |------>|  Spring Boot     |
|  (Frontend) | /api  |    Backend       |
|   :80       |------>|    :8080         |
+-------------+       +------------------+
```

- **Frontend (nginx)**: Serves the Vue.js SPA and proxies `/api/*` to the backend
- **Backend (Spring Boot)**: Handles PDF generation, IP limiting, and resume caching
- **No database needed**: IP limits and resume cache are stored in-memory

---

## Environment Variables

| Variable | Default | Description |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | `prod` | Spring profile (prod uses `application-prod.properties`) |
| `CORS_ALLOWED_ORIGINS` | `http://localhost,http://localhost:80` | Comma-separated allowed CORS origins |
| `resume.free-limit` | `2` | Free resume generations per IP |

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

# Shell into backend
docker exec -it resumegen-backend sh

# Shell into frontend
docker exec -it resumegen-frontend sh
```

---

## Updating After Code Changes

### Frontend changes:
```bash
cd /opt/resumegen/frontend
# Edit files, then:
cd /opt/resumegen
docker compose build frontend
docker compose up -d frontend
```

### Backend changes:
```bash
cd /opt/resumegen/backend
# Edit files, then:
cd /opt/resumegen
docker compose build backend
docker compose up -d backend
```

### Both:
```bash
cd /opt/resumegen
docker compose down
docker compose build --no-cache
docker compose up -d
```

---

## Custom Domain / HTTPS (Optional)

To use a custom domain with HTTPS, update `frontend/nginx.conf`:

```nginx
server {
    listen 80;
    server_name yourdomain.com;
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl;
    server_name yourdomain.com;
    
    ssl_certificate /etc/nginx/ssl/cert.pem;
    ssl_certificate_key /etc/nginx/ssl/key.pem;
    
    # ... rest of config
}
```

Or use a reverse proxy like **Nginx Proxy Manager** or **Traefik** with automatic Let's Encrypt.

---

## Troubleshooting

### Port 80 already in use
```bash
# Check what's using port 80
sudo lsof -i :80

# Change frontend port in docker-compose.yml:
# ports:
#   - "8081:80"  # Use 8081 instead of 80
```

### Backend won't start
```bash
docker compose logs backend
# Common issues: Java version mismatch, port conflict
```

### CORS errors in browser
- Since nginx proxies API requests, CORS shouldn't be needed in production
- If accessing backend directly, update `CORS_ALLOWED_ORIGINS` in docker-compose.yml

### IP limit not working correctly
- IP tracking is in-memory and resets on container restart
- For production, consider adding a persistent store (Redis/DB)
- The backend reads `X-Forwarded-For` and `X-Real-IP` headers from nginx
