# GitHub Actions Setup - Maven Build CI

## What's Been Set Up

I've created a GitHub Actions workflow that automatically builds your Maven project using GitHub-hosted runners. Here's what happens:

### Workflow File
- **Location**: `.github/workflows/build.yml`
- **Runner**: `ubuntu-latest` (GitHub-hosted runner)

### What the Workflow Does

1. **Triggers**: Automatically runs on:
   - Push to `main` or `develop` branches
   - Pull Requests to `main` or `develop` branches

2. **Build Steps**:
   - Checks out your code
   - Sets up Java 21 (matches your project configuration)
   - Caches Maven dependencies for faster builds
   - Runs `./mvnw clean package` (cleans and builds the project)
   - Runs `./mvnw test` (executes unit tests)
   - Uploads build artifacts
   - Publishes test results

## How to Use

### 1. Push to GitHub
```bash
git add .
git commit -m "Add GitHub Actions CI workflow"
git push origin main
```

### 2. View Workflow Runs
- Go to your GitHub repository
- Click on the **"Actions"** tab
- You'll see all workflow runs here
- Click on a run to see detailed logs

## Customization Options

### Change Trigger Branches
Edit `.github/workflows/build.yml` and modify:
```yaml
on:
  push:
    branches: [ main, develop, staging ]
```

### Run on Schedule (Optional)
Add cron jobs to run at specific times:
```yaml
on:
  schedule:
    - cron: '0 2 * * *'  # Daily at 2 AM UTC
```

### Add More Java Versions
To test against multiple Java versions:
```yaml
strategy:
  matrix:
    java-version: ['17', '21']
```

### Add Notifications (Optional)
You can add Slack, Email, or other notifications on failure by adding another job.

## GitHub-Hosted Runners

Your workflow uses `ubuntu-latest`, which provides:
- ✅ Free usage (up to 3000 minutes/month for private repos)
- ✅ No infrastructure to manage
- ✅ Latest OS and tool updates
- ✅ Support for matrix builds
- ✅ Pre-installed tools (Git, Maven, Java, etc.)

### Alternative Runners Available:
- `ubuntu-latest` (Linux) - recommended for Java projects
- `macos-latest` (macOS)
- `windows-latest` (Windows)

## Troubleshooting

If the build fails:
1. Check the **Actions** tab in GitHub
2. Click on the failed run
3. Expand the failed step to see error logs
4. Common issues:
   - Java version mismatch → Update `java-version` in workflow
   - Maven cache issues → Add `cache: maven`
   - Missing dependencies → Check `pom.xml`

## Next Steps

- [ ] Push this workflow to GitHub
- [ ] Check the Actions tab to confirm it runs
- [ ] Review build logs to ensure success
- [ ] Set up branch protection rules to require CI pass
- [ ] (Optional) Add more build steps or notifications
