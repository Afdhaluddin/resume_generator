#!/bin/bash
set -e

APP_DIR=~/resume-generator
TIMESTAMP=$(date +%s)
NEW_DIR=~/resume-generator-$TIMESTAMP
GITHUB_REPO="Afdhaluddin/resume_generator"

echo "=== ResumeForge Deploy ==="
echo "Timestamp: $TIMESTAMP"

# Download fresh code (with retry)
echo "[1/6] Downloading fresh code..."
mkdir -p "$NEW_DIR"
cd "$NEW_DIR"
for i in 1 2 3; do
  curl -fsSL "https://github.com/$GITHUB_REPO/archive/refs/heads/main.tar.gz" -o main.tar.gz && break
  echo "Download attempt $i failed, retrying in 5s..."
  sleep 5
done
if [ ! -f main.tar.gz ]; then
  echo "ERROR: Failed to download tarball after 3 attempts"
  rm -rf "$NEW_DIR"
  exit 1
fi
tar -xzf main.tar.gz --strip-components=1
rm -f main.tar.gz

if [ ! -f "$NEW_DIR/docker-compose.yml" ]; then
  echo "ERROR: tarball extraction failed"
  rm -rf "$NEW_DIR"
  exit 1
fi

# Preserve .env
if [ -f "$APP_DIR/.env" ]; then
  echo "[2/6] Preserving existing .env..."
  cp "$APP_DIR/.env" "$NEW_DIR/.env"
fi

# Verify docker access
if ! docker ps > /dev/null 2>&1; then
  echo "ERROR: Cannot run docker. Make sure the user is in the 'docker' group."
  rm -rf "$NEW_DIR"
  exit 1
fi
COMPOSE_CMD="docker compose"

# Stop old containers
echo "[3/6] Stopping current containers..."
OLD_COMPOSE=""
if [ -L "$APP_DIR" ]; then
  OLD_TARGET=$(readlink -f "$APP_DIR" 2>/dev/null || readlink "$APP_DIR" 2>/dev/null || echo "")
  if [ -n "$OLD_TARGET" ] && [ -f "$OLD_TARGET/docker-compose.yml" ]; then
    OLD_COMPOSE="$OLD_TARGET/docker-compose.yml"
  fi
elif [ -f "$APP_DIR/docker-compose.yml" ]; then
  OLD_COMPOSE="$APP_DIR/docker-compose.yml"
fi

if [ -n "$OLD_COMPOSE" ]; then
  echo "Stopping containers from: $OLD_COMPOSE"
  $COMPOSE_CMD -f "$OLD_COMPOSE" down || true
fi

$COMPOSE_CMD -p resume-generator down 2>/dev/null || true

# Force remove any containers using our ports or names
echo "Checking for port conflicts..."
docker ps -q --filter 'publish=8080' | xargs -r docker stop 2>/dev/null || true
docker ps -q --filter 'publish=8081' | xargs -r docker stop 2>/dev/null || true
docker ps -q --filter 'name=resumegen' | xargs -r docker stop 2>/dev/null || true
docker ps -aq --filter 'name=resumegen' | xargs -r docker rm 2>/dev/null || true

# Swap to new code
echo "[4/6] Swapping to new code..."
if [ -d "$APP_DIR" ] && [ ! -L "$APP_DIR" ]; then
  mv "$APP_DIR" "$APP_DIR.backup-$TIMESTAMP"
elif [ -L "$APP_DIR" ]; then
  rm -f "$APP_DIR"
fi
ln -s "$NEW_DIR" "$APP_DIR"
echo "Symlink created: $APP_DIR -> $NEW_DIR"

cd "$APP_DIR"

# Ensure .env exists
echo "[5/6] Setting up .env..."
if [ ! -f .env ]; then
  if [ -f .env.example ]; then
    cp .env.example .env || true
  fi
  if [ ! -f .env ]; then
    echo "Creating minimal .env"
    echo '# Stripe' > .env
    echo 'STRIPE_SECRET_KEY=sk_test_your_key_here' >> .env
    echo 'STRIPE_PUBLISHABLE_KEY=pk_test_your_key_here' >> .env
    echo 'STRIPE_WEBHOOK_SECRET=whsec_your_secret_here' >> .env
    echo 'STRIPE_PRICE_ID=price_your_price_here' >> .env
    echo '' >> .env
    echo '# Email (SMTP)' >> .env
    echo 'SMTP_HOST=smtp.gmail.com' >> .env
    echo 'SMTP_PORT=587' >> .env
    echo 'SMTP_USERNAME=your-email@gmail.com' >> .env
    echo 'SMTP_PASSWORD=your-app-password' >> .env
    echo 'EMAIL_FROM=noreply@resumeforge.app' >> .env
    echo '' >> .env
    echo '# Admin' >> .env
    echo 'ADMIN_API_KEY=change-me-admin-key' >> .env
    echo '' >> .env
    echo '# App' >> .env
    echo 'FREE_RESUME_LIMIT=2' >> .env
  fi
  echo "WARNING: Please edit .env with your actual credentials!"
fi

# Build and start
echo "[6/6] Building and starting containers..."
$COMPOSE_CMD build --no-cache
$COMPOSE_CMD up -d

# Cleanup old deployments (keep last 2)
echo "Cleaning up old deployments..."
docker image prune -f
ls -td ~/resume-generator-*/ 2>/dev/null | tail -n +3 | xargs -r rm -rf 2>/dev/null || true
ls -td ~/resume-generator.backup-*/ 2>/dev/null | tail -n +3 | xargs -r rm -rf 2>/dev/null || true

# Health check
echo "Waiting for services..."
sleep 20

# Debug: check what's listening
echo "Checking open ports..."
netstat -tlnp 2>/dev/null || ss -tlnp 2>/dev/null || echo "Cannot check ports"

# Debug backend health check
BACKEND_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/api/resume/check-limit 2>&1)
echo "Backend HTTP status: $BACKEND_CODE"
if [ "$BACKEND_CODE" = "200" ]; then
  BACKEND="OK"
else
  BACKEND="FAIL"
  echo "Backend response body:"
  curl -s http://localhost:8080/api/resume/check-limit 2>&1 || true
fi

# Debug frontend health check  
FRONTEND_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8081/ 2>&1)
echo "Frontend HTTP status: $FRONTEND_CODE"
if [ "$FRONTEND_CODE" = "200" ]; then
  FRONTEND="OK"
else
  FRONTEND="FAIL"
fi

if [ "$BACKEND" = "OK" ] && [ "$FRONTEND" = "OK" ]; then
  echo "========================================"
  echo " Deployment successful!"
  echo "========================================"
  echo "Frontend: http://$VPS_HOST:8081"
  echo "Backend:  http://$VPS_HOST:8080"
else
  echo "ERROR: Health check failed!"
  $COMPOSE_CMD logs --tail 50
  exit 1
fi
