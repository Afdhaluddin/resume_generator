# ResumeForge Deployment Guide

## Quick Start

### 1. VPS Setup (run once as root)

SSH into your VPS as root and run:

```bash
# Download and run the setup script
curl -fsSL https://raw.githubusercontent.com/Afdhaluddin/resume_generator/main/scripts/setup-vps.sh | bash
```

Or manually:

```bash
# Fix DNS
echo "nameserver 8.8.8.8" > /etc/resolv.conf
echo "nameserver 8.8.4.4" >> /etc/resolv.conf

# Create CI user
useradd -m -s /bin/bash github-ci
usermod -aG docker github-ci

# Generate SSH key for GitHub Actions
su - github-ci -c "ssh-keygen -t ed25519 -C github-actions -f ~/.ssh/id_ed25519 -N ''"
cat /home/github-ci/.ssh/id_ed25519.pub >> /home/github-ci/.ssh/authorized_keys

# Show private key (copy to GitHub Secrets)
cat /home/github-ci/.ssh/id_ed25519
```

### 2. GitHub Secrets

Go to **GitHub Repo** → **Settings** → **Secrets and variables** → **Actions** → **New repository secret**

| Secret Name | Value |
|-------------|-------|
| `VPS_HOST` | Your VPS IP (e.g., `169.58.31.55`) |
| `VPS_USER` | `github-ci` |
| `VPS_SSH_KEY` | Paste the private key from step 1 |

### 3. Deploy

Push to `main` branch or manually trigger from **GitHub Actions** → **Deploy to VPS** → **Run workflow**

---

## Troubleshooting

### DNS Resolution Failed

If you see `Could not resolve host: github.com`:

```bash
# As root on VPS
echo "nameserver 8.8.8.8" > /etc/resolv.conf
echo "nameserver 8.8.4.4" >> /etc/resolv.conf

# Make persistent (Ubuntu/Debian)
apt update && apt install -y resolvconf
systemctl enable resolvconf
```

### Docker Permission Denied

If the deploy fails with docker permission errors:

```bash
# As root on VPS
usermod -aG docker github-ci
# Then log out and back in as github-ci, or run:
newgrp docker
```

### Port Already in Use

If port 8080 or 80 is already allocated:

```bash
# As CI user on VPS
docker ps -q --filter "name=resumegen" | xargs -r docker stop
docker ps -aq --filter "name=resumegen" | xargs -r docker rm
```

---

## Architecture

```
GitHub Push
    ↓
GitHub Actions (deploy.yml)
    ↓
SSH into VPS as github-ci
    ↓
Download tarball → Extract → Symlink swap
    ↓
Docker Compose build + up
    ↓
Health check
```

### Directory Layout on VPS

```
/home/github-ci/
├── resume-generator -> resume-generator-1234567890  (symlink to active)
├── resume-generator-1234567890                       (current deploy)
├── resume-generator-1234567880                       (previous deploy)
└── resume-generator.backup-1234567870                (old real dir backup)
```

### Why Symlink Swap?

- Avoids permission issues when replacing root-owned files from Docker containers
- Allows atomic deployment (instant switch)
- Keeps old versions as rollback backups
