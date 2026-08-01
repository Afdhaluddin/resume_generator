#!/bin/bash
set -e

# ResumeForge VPS Setup Script
# Run this as root on your VPS to set up a dedicated CI/CD user

CI_USER="github-ci"
APP_DIR="/home/$CI_USER/resume-generator"

echo "=== ResumeForge VPS Setup ==="

# 1. Fix DNS
echo "[1/5] Fixing DNS..."
if ! grep -q "8.8.8.8" /etc/resolv.conf 2>/dev/null; then
    echo "nameserver 8.8.8.8" > /etc/resolv.conf
    echo "nameserver 8.8.4.4" >> /etc/resolv.conf
    echo "DNS updated"
else
    echo "DNS already configured"
fi

# Test DNS
if nslookup github.com > /dev/null 2>&1; then
    echo "DNS resolution OK"
else
    echo "WARNING: DNS resolution still failing!"
fi

# 2. Create CI user (if doesn't exist)
echo "[2/5] Setting up CI user: $CI_USER..."
if id "$CI_USER" &>/dev/null; then
    echo "User $CI_USER already exists"
else
    useradd -m -s /bin/bash "$CI_USER"
    echo "User $CI_USER created"
fi

# 3. Add user to docker group
echo "[3/5] Adding $CI_USER to docker group..."
usermod -aG docker "$CI_USER"

# Verify docker is installed
if ! command -v docker &> /dev/null; then
    echo "ERROR: Docker not found. Please install Docker first."
    echo "See: https://docs.docker.com/engine/install/"
    exit 1
fi

# 4. Create app directory
echo "[4/5] Creating app directory..."
mkdir -p "$APP_DIR"
chown -R "$CI_USER:$CI_USER" "$(dirname $APP_DIR)"

# 5. Set up SSH key for GitHub Actions
echo "[5/5] Setting up SSH..."
CI_HOME="/home/$CI_USER"
mkdir -p "$CI_HOME/.ssh"

# Generate SSH key pair if not exists
if [ ! -f "$CI_HOME/.ssh/id_ed25519" ]; then
    ssh-keygen -t ed25519 -C "github-actions" -f "$CI_HOME/.ssh/id_ed25519" -N ""
    echo "SSH key generated at $CI_HOME/.ssh/id_ed25519"
    echo ""
    echo "=== IMPORTANT ==="
    echo "Copy this private key to GitHub Secrets as VPS_SSH_KEY:"
    cat "$CI_HOME/.ssh/id_ed25519"
    echo ""
    echo "Copy this public key to authorized_keys:"
    cat "$CI_HOME/.ssh/id_ed25519.pub" >> "$CI_HOME/.ssh/authorized_keys"
else
    echo "SSH key already exists"
fi

chmod 700 "$CI_HOME/.ssh"
chmod 600 "$CI_HOME/.ssh/authorized_keys"
chown -R "$CI_USER:$CI_USER" "$CI_HOME/.ssh"

echo ""
echo "========================================"
echo " Setup complete!"
echo "========================================"
echo ""
echo "Next steps:"
echo "1. Log out and log back in (or run: newgrp docker)"
echo "2. Copy the private key to GitHub Secrets as VPS_SSH_KEY"
echo "3. Set GitHub Secret VPS_USER=$CI_USER"
echo "4. Set GitHub Secret VPS_HOST=$(hostname -I | awk '{print $1}')"
echo "5. Trigger the deploy workflow from GitHub Actions"
echo ""
echo "To verify: su - $CI_USER && docker ps"
