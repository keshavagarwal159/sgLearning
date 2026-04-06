# Self-Hosted Runner Setup Guide for GitHub

## Prerequisites

Before starting, ensure you have:
- Admin access to the GitHub repository
- A machine (Linux, macOS, or Windows) to run the runner
- Git installed on that machine
- Java 21 and Maven installed (for your project)
- Network connectivity from the runner machine to GitHub

---

## Step 1: Navigate to Repository Settings

1. Go to your GitHub repository
2. Click on **Settings** (top right, in the repository menu)
3. In the left sidebar, click **Actions** → **Runners**
4. Click the **New self-hosted runner** button

---

## Step 2: Select the Runner Configuration

1. **Choose OS**: Select the operating system of the machine where the runner will run:
   - Linux (x64)
   - Linux (ARM64)
   - macOS (x64 or ARM64)
   - Windows (x64 or ARM64)

2. **Choose Architecture**: Select the appropriate architecture

3. Click **Next** to proceed

---

## Step 3: Download and Extract the Runner

GitHub will display download and setup instructions. Follow these:

### On macOS/Linux:

```bash
# Create a directory for the runner
mkdir actions-runner
cd actions-runner

# Download the runner package
curl -o actions-runner-osx-x64-2.319.0.tar.gz -L https://github.com/actions/runner/releases/download/v2.319.0/actions-runner-osx-x64-2.319.0.tar.gz

# Extract the installer
tar xzf actions-runner-osx-x64-2.319.0.tar.gz

# Verify extraction
ls -la
```

### On Linux:

```bash
mkdir actions-runner
cd actions-runner

curl -o actions-runner-linux-x64-2.319.0.tar.gz -L https://github.com/actions/runner/releases/download/v2.319.0/actions-runner-linux-x64-2.319.0.tar.gz

tar xzf actions-runner-linux-x64-2.319.0.tar.gz

ls -la
```

### On Windows (PowerShell):

```powershell
mkdir actions-runner
cd actions-runner

Invoke-WebRequest -Uri "https://github.com/actions/runner/releases/download/v2.319.0/actions-runner-win-x64-2.319.0.zip" -OutFile "actions-runner-win-x64-2.319.0.zip"

Expand-Archive -Path "actions-runner-win-x64-2.319.0.zip" -DestinationPath "."

dir
```

---

## Step 4: Create the Configuration Token

1. In GitHub Settings → Actions → Runners, click **New self-hosted runner**
2. GitHub will display a configuration command with a unique token
3. **Copy the entire configuration command** (it includes your registration token)

Example (your token will be different):
```
./config.sh --url https://github.com/YOUR_USERNAME/YOUR_REPO --token ABCDEFGHIJKLMNOP1234567890
```

---

## Step 5: Configure the Runner

Navigate to the runner directory and run the configuration command:

### On macOS/Linux:

```bash
cd ~/path/to/actions-runner

# Run the configuration command (use the command from Step 4)
./config.sh --url https://github.com/YOUR_USERNAME/YOUR_REPO --token YOUR_TOKEN_HERE

# You'll be prompted to enter:
# 1. Runner name: (press Enter for default)
# 2. Runner group: (press Enter for default)
# 3. Labels: (optional - for tagging runners, press Enter to skip)
# 4. Working directory: (press Enter for default)
```

### On Windows (PowerShell):

```powershell
cd C:\path\to\actions-runner

# Run the configuration command
.\config.cmd --url https://github.com/YOUR_USERNAME/YOUR_REPO --token YOUR_TOKEN_HERE

# Answer prompts as above
```

**Expected Output:**
```
√ Connected to GitHub
√ Runner registration is complete
Runner name: 'MY-RUNNER'
```

---

## Step 6: Start the Runner

### Option A: Run as a Foreground Process (Testing)

```bash
# macOS/Linux
./run.sh

# Windows
.\run.cmd
```

You should see:
```
Listening for Jobs
```

Keep the terminal open. Stop with `Ctrl+C`.

### Option B: Install as a Service (Recommended)

#### On macOS/Linux:

```bash
# Install the service
sudo ./svc.sh install

# Start the service
sudo ./svc.sh start

# Check status
sudo ./svc.sh status
```

#### On Linux (systemd):

```bash
# Install as service
sudo ./svc.sh install

# Enable on startup
sudo systemctl enable actions-runner

# Start the service
sudo ./svc.sh start
```

#### On Windows (PowerShell - Run as Administrator):

```powershell
# Install the service
.\svc.cmd install

# Start the service
.\svc.cmd start

# Check status
Get-Service "GitHub Actions Runner" | Select-Object Status
```

---

## Step 7: Verify the Runner is Online

1. Go back to GitHub: **Settings** → **Actions** → **Runners**
2. You should see your runner listed with a **green circle** (indicating it's online)
3. The runner name will appear with status "Idle"

---

## Step 8: Update Your Workflow (Optional)

If you gave your runner a custom name or labels, update the workflow:

```yaml
jobs:
  build:
    runs-on: self-hosted  # Or use your custom runner name
    # or
    runs-on: [self-hosted, linux]  # If you added labels
```

---

## Test Your Setup

Make a small code change and push to `master`:

```bash
git add .
git commit -m "Test self-hosted runner"
git push origin master
```

Check the **Actions** tab in GitHub to see your workflow run on the self-hosted runner.

---

## Troubleshooting

### Runner shows offline in GitHub

```bash
# macOS/Linux - check service status
sudo ./svc.sh status

# Windows - check service
Get-Service "GitHub Actions Runner"

# View logs
cat _diag/Runner_<timestamp>.log
```

### Runner not responding to jobs

1. Ensure required tools are installed:
   ```bash
   java -version
   mvn -version
   git --version
   ```

2. Check runner folder permissions:
   ```bash
   sudo chown -R $USER:$USER ~/actions-runner
   ```

3. Restart the runner:
   ```bash
   # Linux/macOS
   sudo ./svc.sh stop
   sudo ./svc.sh start
   
   # Windows
   .\svc.cmd stop
   .\svc.cmd start
   ```

### Port or Network Issues

- Verify outbound HTTPS (port 443) is allowed
- Check firewall settings
- Ensure runner can reach `api.github.com`

### Remove/Unregister the Runner

```bash
# macOS/Linux
./config.sh remove --token YOUR_TOKEN

# Windows
.\config.cmd remove --token YOUR_TOKEN

# Then uninstall service (optional)
sudo ./svc.sh uninstall
```

---

## Best Practices

✅ **DO:**
- Keep the runner machine updated
- Use labels to organize runners (`linux`, `mac`, `windows`)
- Monitor runner health regularly
- Set appropriate access permissions
- Document your runner configuration

❌ **DON'T:**
- Expose runner tokens publicly
- Run the runner with root/admin unnecessarily
- Ignore security updates
- Store sensitive credentials on the runner machine

---

## Security Notes

- Runner tokens are sensitive - regenerate if exposed
- Restrict repository access to runners
- Use environment-level runners for multiple repos (enterprise feature)
- Consider running runners in isolated environments
- Update GitHub Actions runner regularly:
  ```bash
  ./config.sh --check-latest
  ```
