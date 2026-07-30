#!/bin/bash
set -e

echo "=========================================="
echo "  ResumeForge VPS Deployment Script"
echo "=========================================="

# Configuration
PROJECT_DIR="/opt/resumegen"
GIT_REPO="${GIT_REPO:-}"  # Set this if pulling from git

echo ""
echo "Step 1: Installing prerequisites..."
if ! command -v docker &> /dev/null; then
    echo "Installing Docker..."
    curl -fsSL https://get.docker.com | sh
    sudo usermod -aG docker $USER
fi

if ! command -v docker-compose &> /dev/null && ! docker compose version &> /dev/null; then
    echo "Installing Docker Compose plugin..."
    sudo apt-get update
    sudo apt-get install -y docker-compose-plugin
fi

echo ""
echo "Step 2: Setting up project directory..."
sudo mkdir -p "$PROJECT_DIR"

# If deploying from local files, ensure they are copied first
if [ -z "$GIT_REPO" ]; then
    echo "Copy the resume-generator folder to $PROJECT_DIR before running this script."
    echo "Or set GIT_REPO environment variable to clone from git."
    # Uncomment below to clone from git:
    # sudo git clone "$GIT_REPO" "$PROJECT_DIR"
fi

cd "$PROJECT_DIR"

echo ""
echo "Step 3: Building Docker images..."
docker compose build --no-cache

echo ""
echo "Step 4: Starting services..."
docker compose up -d

echo ""
echo "Step 5: Waiting for services to be healthy..."
sleep 10

# Check health
echo ""
echo "Checking backend health..."
if curl -s http://localhost:8080/api/resume/check-limit > /dev/null; then
    echo "Backend is healthy!"
else
    echo "Backend may still be starting. Check logs with: docker compose logs -f backend"
fi

echo ""
echo "=========================================="
echo "  Deployment Complete!"
echo "=========================================="
echo ""
echo "Your ResumeForge is running at:"
echo "  http://$(curl -s ifconfig.me || echo 'YOUR_VPS_IP')/"
echo ""
echo "Backend API (direct): http://YOUR_VPS_IP:8080"
echo ""
echo "Useful commands:"
echo "  View logs:     docker compose logs -f"
echo "  Stop:          docker compose down"
echo "  Restart:       docker compose restart"
echo "  Update:        docker compose pull && docker compose up -d"
echo ""
